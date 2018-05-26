package com.icomp.Iswtmv10.v01c00.c00s000;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import android.widget.Toast;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.vo.AuthCustomerVO;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.CardRead;
import com.icomp.common.utils.RfidRead;
import com.icomp.common.utils.SysApplication;
import com.icomp.common.utils.UpdateManager;
import com.icomp.common.utils.WifiUtils;
import com.icomp.wsdl.common.TestWebService;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.LocalRequest;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.InputStream;
import java.util.Locale;

/**
 * 系统初始化页面
 *
 * @author FanLL
 * @date 2016年5月13日10:58:49
 */
public class C00S000_000Activity extends CommonActivity {

    AnimView mAnimView = null;
    boolean openflag = true;
    //获取屏幕宽高
    Display display;
    private boolean mIsForword = true;
    private int mProgress = 0;

    C00S000Wsdl c00S000Wsdl = new C00S000Wsdl();
    LocalRequest request = new LocalRequest();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        display = getWindowManager().getDefaultDisplay();


        try {
            //取得用户已保存的默认语言
            SharedPreferences sharedPreferences = getSharedPreferences("langVaue", CommonActivity.MODE_APPEND);
            String langValue = sharedPreferences.getString("langValue", null);
            if (langValue == null) {
                //保存语言信息
                sharedPreferences = getSharedPreferences("userInfo", Context.MODE_APPEND);
                //获取编辑器
                Editor editor = sharedPreferences.edit();
                editor.putString("langCode", "01");
                editor.putString("langValue", "zh_CN");
                //提交修改
                editor.commit();
                langValue = "zh_CN";
            }
            //设置系统默认语言
            Resources resources = getResources();// 获得res资源对象
            Configuration config = resources.getConfiguration();// 获得设置对象
            DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨
            String[] codes = langValue.split("_");
            Locale locale = new Locale(codes[0], codes.length > 1 ? codes[1] : "");
            if (!Locale.getDefault().getLanguage().equals(codes[0])) {
                Locale.setDefault(locale);
                config.locale = locale;
                resources.updateConfiguration(config, dm);
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {

        }
        // 显示自定义的View
        mAnimView = new AnimView(C00S000_000Activity.this, display.getWidth(), display.getHeight());
        setContentView(mAnimView);
        //mAnimView.setBackgroundResource(R.color.textColor);
/*        visitJniThread = new VisitJniThread();
        visitJniThread.mUpdateManager = new UpdateManager(this);
        visitJniThread.start();*/
    }

    private VisitJniThread visitJniThread;

    private class VisitJniThread extends Thread {
        UpdateManager mUpdateManager;

        @Override
        public void run() {
            Message msg = new Message();
            mUpdateManager.checkUpdate();
            mhandler.sendMessage(msg);
        }

        /**
         *
         */
        @SuppressLint("HandlerLeak")
        Handler mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (mUpdateManager.isUpdateFlag()) {
                    mIsForword = false;
                    mUpdateManager.show();
                }
            }
        };
    }

    public class AnimView extends SurfaceView implements Callback, Runnable {

        /**
         * 屏幕的宽高 *
         */
        private int mScreenWidth = 0;
        private int mScreenHeight = 0;

        Paint mPaint = null;

        /**
         * 进度条资源 *
         */
        private Bitmap mLoadBack = null;
        private Bitmap mLoadShow = null;

        /**
         * 进度条数值 取值范围为0到100 *
         */
        private int mProgressBar = 0;

        /**
         * 主线程 *
         */
        private Thread mThread = null;
        /**
         * 线程循环标志 *
         */
        private boolean mIsRunning = false;

        private SurfaceHolder mSurfaceHolder = null;
        private Canvas mCanvas = null;

        private Context mContext = null;
        private Intent wifiIntent;

        /**
         * 构造方法
         *
         * @param context
         */
        public AnimView(Context context, int screenWidth, int screenHeight) {
            super(context);
            mContext = context;
            mPaint = new Paint();
            // 设置背景色
            mScreenWidth = screenWidth;
            mScreenHeight = screenHeight;
            /** 获取mSurfaceHolder **/
            mSurfaceHolder = getHolder();
            mSurfaceHolder.addCallback(this);
            setFocusable(true);

            init();
        }

        protected void Draw() {
            // 这里计算进度条进度
            Loading();
            /** 这里表示进度加载完成 **/
            if (mProgressBar >= 100) {
                mIsRunning = false;
            }
        }

        private void init() {

            mLoadBack = ReadBitMap(mContext, R.drawable.ui_slot_04);
            mLoadShow = ReadBitMap(mContext, R.drawable.ui_slot_04);
        }

        /**
         * 读取本地资源的图片
         *
         * @param context
         * @param resId
         * @return
         */
        public Bitmap ReadBitMap(Context context, int resId) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
        }

        /**
         * 绘制画带阴影的文字
         *
         * @param mCanvas
         * @param str0
         * @param color
         * @param x
         * @param y
         */
        public final void drawRimString(Canvas mCanvas, String str0, int color, int x, int y) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            mCanvas.drawPaint(paint);
            mCanvas.drawColor(getResources().getColor(R.color.baseColor));
            if (mProgressBar != 0) {
                int width = mLoadShow.getWidth() * mProgressBar / 100;
                if (width <= 0) {
                    width = 0;
                } else if (width >= mLoadShow.getWidth()) {
                    width = mLoadShow.getWidth();
                    mProgressBar = 100;
                }
                Bitmap bitMap = BitmapClipBitmap(mLoadShow, 0, 0, width, mLoadShow.getHeight());
                mCanvas.drawBitmap(bitMap, (mScreenWidth - mLoadBack.getWidth()) >> 1, mScreenHeight + mScreenHeight / 2 >> 1, mPaint);
            }
            str0 = getString(R.string.logoInfo) + mProgressBar + "%";
            y = mScreenHeight + mScreenHeight / 2 >> 1;
            Paint titlePaint = new Paint();
            Display display = getWindowManager().getDefaultDisplay();
            titlePaint.setColor(Color.parseColor("#FFFFFF"));
            titlePaint.setTextSize(35);// 设置字体大小
            titlePaint.setTextAlign(Align.CENTER);
            mCanvas.drawText(getString(R.string.logoText), display.getWidth() / 2, y / 2, titlePaint);

            titlePaint.setColor(Color.parseColor("#FFFFFF"));
            titlePaint.setTextSize(40);// 设置字体大小
            titlePaint.setTextAlign(Align.CENTER);
            String versionName = "V1.0";
            try {
                versionName = C00S000_000Activity.this.getPackageManager().getPackageInfo(C00S000_000Activity.this.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (versionName == null) {
                versionName = "V1.0";
            }
            mCanvas.drawText(versionName, display.getWidth() / 2, display.getHeight() / 2, titlePaint);

            x = (mScreenWidth >> 1) - (((int) mPaint.measureText(str0)) >> 1);

            int backColor = mPaint.getColor();
            mPaint.setColor(~color);
            mPaint.setColor(color);
            mCanvas.drawText(str0, x, y, mPaint);
            mPaint.setColor(backColor);
        }

        /**
         * 程序切割图片
         *
         * @param bitmap
         * @param x
         * @param y
         * @param w
         * @param h
         * @return
         */
        public Bitmap BitmapClipBitmap(Bitmap bitmap, int x, int y, int w, int h) {
            return Bitmap.createBitmap(bitmap, x, y, w, h);
        }

        @SuppressLint("HandlerLeak")
        Handler mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mIsForword = false;
                // 提示用户，联系系统管理员
                new AlertDialog.Builder(C00S000_000Activity.this).setTitle(getString(R.string.infoMsg)).setMessage(getString(R.string.webInfoMsg)).setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIsForword = true;
                    }
                }).show();

            }
        };

        public void Loading() {
            // 这里应该是去读取资源， 由于没有大量的资源 这里我暂时只用线程去等待
            try {
                openflag = true;
                switch (mProgress) {
                    case 0:
                        break;
                    case 3:
//                        boolean wifi = WifiUtils.isWiFiActive(getApplicationContext());
//                        if (!wifi) {
//                            mProgress--;
//                            // TODO开启无线网络配置页面
//                            if (wifiIntent == null) {
//                                wifiIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
//                                startActivity(wifiIntent);
//                            }
//                        }
                        break;
                    case 5:
//                        // 检测webservice 是否可以连接
//                        TestWebService testWebServic = new TestWebService();
//                        // storm修改过，源代码!testWebServic.testWebService()
//                        if (!!testWebServic.testWebService()) {
//                            Message message = new Message();
//                            mhandler.sendMessage(message);
//                            mProgress--;
//                        }
                        break;
                    case 6:
                        // 自动更新检测
                        // 这里来检测版本是否需要更新
                        break;
                    default:

                }
                mProgress++;
                mProgressBar = 10 * mProgress;

            } catch (Exception e) {
                e.printStackTrace();
            }
            int y = 0;
            drawRimString(mCanvas, "", Color.WHITE, (mScreenWidth >> 1) - (((int) mPaint.measureText("")) >> 1), y);

        }

        @Override
        public void run() {
            while (mIsRunning) {
                if (mIsForword) {
                    // 在这里加上线程安全锁
                    synchronized (mSurfaceHolder) {
                        /** 拿到当前画布 然后锁定 **/
                        mCanvas = mSurfaceHolder.lockCanvas();
                        Draw();
                        /** 绘制结束后解锁显示在屏幕上 **/
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
            if (openflag) {
                // 打开系统读头
                RfidRead.initRfid(C00S000_000Activity.this, true);
                // 打开系统读卡器
                CardRead.initRead(C00S000_000Activity.this, true);

                // 打开登录页面
                Intent intent = new Intent();
                intent.setClass(C00S000_000Activity.this, C00S000_001Activity.class);
                // intent.putExtra("Name", "feng88724");
                startActivity(intent);
                finish();


//                //设定用户访问信息
//                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
//                String userInfoJson = sharedPreferences.getString("loginInfo", null);
//
//                if (userInfoJson != null) {
//                    Gson gson = new Gson();
//                    AuthCustomer authCustomer = gson.fromJson(userInfoJson, AuthCustomer.class);
//
//                    AuthCustomerVO authCustomerVO = new AuthCustomerVO();
//                    authCustomerVO.setAccount(authCustomer.getAccount());
//                    authCustomerVO.setPassword(authCustomer.getPassword());
//
//                    Retrofit retrofit = RetrofitSingle.newInstance();
//                    //调用接口，查询合成刀具组成信息
//                    IRequest iRequest = retrofit.create(IRequest.class);
//
//                    String jsonStr = gson.toJson(authCustomerVO);
//                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
//
//                    Call<String> loganForPDA = iRequest.loganForPDA(body);
//                    loganForPDA.enqueue(new MyCallBack<String>() {
//                        @Override
//                        public void _onResponse(Response<String> response) {
//                            try {
//                                if (response.raw().code() == 200) {
//                                    // 打开登录页面
//                                    Intent intent = new Intent();
//                                    intent.setClass(C00S000_000Activity.this, C00S000_002Activity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    // 打开登录页面
//                                    Intent intent = new Intent();
//                                    intent.setClass(C00S000_000Activity.this, C00S000_001Activity.class);
//                                    // intent.putExtra("Name", "feng88724");
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                // 打开登录页面
//                                Intent intent = new Intent();
//                                intent.setClass(C00S000_000Activity.this, C00S000_001Activity.class);
//                                // intent.putExtra("Name", "feng88724");
//                                startActivity(intent);
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void _onFailure(Throwable t) {
//                            // 打开登录页面
//                            Intent intent = new Intent();
//                            intent.setClass(C00S000_000Activity.this, C00S000_001Activity.class);
//                            // intent.putExtra("Name", "feng88724");
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//
//                }

            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
            // surfaceView的大小发生改变的时候

        }

        @Override
        public void surfaceCreated(SurfaceHolder arg0) {
            /** 启动游戏主线程 **/
            mIsRunning = true;
            mThread = new Thread(this);
            mThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            // surfaceView销毁的时候
            mIsRunning = false;
        }
    }


}
