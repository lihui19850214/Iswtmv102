package com.icomp.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.*;
import android.text.method.ReplacementTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.RfidContainerVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.utils.CardRead;
import com.icomp.common.utils.DialogFactory;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRespons;
import com.rscja.deviceapi.RFIDWithUHF;
import com.t_epc.UHFApplication;
import com.t_epc.reader.server.ReaderHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class CommonActivity extends Activity {
    @SuppressLint("SimpleDateFormat")
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);//设定格式
    public PopupWindow popupWindow;
    private PopupWindow popupWindow2;//登录按钮
    protected String url = "";
    protected Context packageContext;
    protected String gruantUserID;// 授权用户ID
    protected String gruantUserName;// 授权用户姓名
    protected String userID;// 交接人ID
    protected String activityName;
    private ImageView tvBatteryChanged;
    private ImageView tvBatteryWifi;
    private TextView txtBatteryChanged;
    public static int SCANF_TIME = 10000;
    public static final String UHF_POWERON_ACTION = "com.android.uhf.POWERON";
    public static final int SUCCESS = 0;
    IntentFilter wifiIntentFilter;    // wifi监听器
    public String stateCode;
    public String customerID, handSetId;
    public boolean transferConfirn = false;
    public DialogFactory loading = null;
    // 取得用户登录信息
    //  public SharedPreferences preferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);

    /**
     * 丢刀授权开关 开 ：true 关：false
     */
    public static final Boolean GIVE_ABLE = true;
    //参数
    public static final String PARAM = "param";
    public static final String PARAM1 = "param1";
    public static final String PARAM2 = "param2";
    public static final String PARAM3 = "param3";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String NINE = "9";

    // 是否清除 Activity 之间的传递参数，true为清除；false为不清除；
    public boolean isClearParamMap = true;
    // Activity 之间的传递参数：0为第一个页面的值；1为第二个页面的值；以此类推
    public static final Map<Integer, Map<String, Object>> PARAM_MAP = new HashMap<>();

    private final int SCANKEY_LEFT = 300;
    private final int SCANKEY_RIGHT = 301;
    private final int SCANKEY_CENTER = 302;
    public InputMethodManager manager;

    public RFIDWithUHF rfidWithUHF;
    /**
     * 判断扫描或停止条件，true为扫描，false为停止
     */
    public boolean scanOrNot = false;
    /**
     * RFID标签唯一序列ID
     */
    protected String rfidString;
    /**
     * 扫描时间
     */
    public static int SCAN_TIME = 10000;
    /**
     * 扫描时间到将rfidString赋值为CLOSE
     */
    public static final String CLOSE = "close";
    /**
     * rfidString的固定长度16位或24位
     */
    public static final String REGULARLENGTH1 = "0000000000000000";
    public final String REGULARLENGTH2 = "000000000000000000000000";

    /**
     * PopupWindow是否显示 true:显示 false:不显示
     */
    public static Boolean IS_SHOW = false;
    //0-33 最大值为33
    public byte rfidFrequency = 32;//rfid频率

    /** 判断是否上电的标示*/
//    protected boolean mSwitchFlag = false;
//    private VirtualKeyListenerBroadcastReceiver mVirtualKeyListenerBroadcastReceiver;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        super.onCreate(savedInstanceState);
        wifiIntentFilter = new IntentFilter();
        wifiIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        ((UHFApplication) getApplication()).addActivity(this);
        loading = new DialogFactory(this, R.layout.c00s000_015activity, R.style.DialogTheme);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        // 获取参数，是否清除 Activity 之间的传递参数
        isClearParamMap = getIntent().getBooleanExtra("isClearParamMap",true);
        if (isClearParamMap) {
            PARAM_MAP.clear();
        }

        // 初始化读头
        initRFID();
    }


    /**
     * 点击功能菜单,用户再次登录
     *
     * @return
     */
    protected void reLogin(View parent, int pageID) {

        // 取得当前手持机登录验证机制
        // 保存用户登录信息
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_APPEND);
        String loginStauts = sharedPreferences.getString("loginStauts", "1");
        if (loginStauts.equals("1")) {
            Intent intent = new Intent();
            try {
                intent.setClass(packageContext, Class.forName("com.icomp.Iswtmv10." + url));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }// 从哪里跳到哪里
            packageContext.startActivity(intent);
        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View vPopupWindow = layoutInflater.inflate(pageID, null);
            vPopupWindow.setBackgroundResource(R.drawable.rounded_corners_view);
            popupWindow = new PopupWindow(vPopupWindow, 400, 280);
            //            popupWindow.setFocusable(true);
            //            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences sp = getSharedPreferences("haha", Activity.MODE_PRIVATE);
        //旧手持机安装屏幕关闭的问题
//        setScreenBrightness(sp.getInt("light", 5));
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    private void setScreenBrightness(int paramInt) {
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow
                .getAttributes();
        float f = paramInt / 2550.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    private VisitJniThread thread;

    private class VisitJniThread extends Thread {
        @Override
        public void run() {
            CardRead.initRead(CommonActivity.this);// 打开Rfid读头模块
            String cardString = null;
            Date date = new Date();
            do {
                cardString = CardRead.readCard(true);
                if (new Date().getTime() - date.getTime() >= SCANF_TIME) {
                    cardString = "close";
                }
            } while (cardString == null && !"close".equals(cardString));
            CardRead.close();
            Message message = new Message();
            message.obj = cardString;
            scanfmhandler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler scanfmhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String returnString = msg.obj.toString();
            if ("close".equals(returnString)) {
                createAlertDialog(CommonActivity.this, getString(R.string.C01S001001_1), Toast.LENGTH_LONG);
            } else {
                try {
                    UserRequest request = new UserRequest();
                    request.setEmployeeCard(returnString);
                    C00S000Wsdl service = new C00S000Wsdl();
                    UserRespons respons = service.userLogin(request);
                    if (respons.getMessageText() == null) {
                        // 保存用户登录信息
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_APPEND);
                        Editor editor = sharedPreferences.edit();// 获取编辑器
                        editor.putString("userName", respons.getCustomer().getUserName());
                        editor.putString("customerID", respons.getCustomer().getCustomerID());
                        editor.putString("langCode", respons.getLanguagetable().getLanguageCode());
                        editor.putString("langValue", respons.getLanguagetable().getLanguageValue());
                        editor.commit();// 提交修改

                        try {

                            Intent intent = new Intent();
                            intent.setClass(packageContext, Class.forName("com.icomp.Iswtmv10." + url));// 从哪里跳到哪里
                            packageContext.startActivity(intent);

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        popupWindow2.dismiss();
                        popupWindow.dismiss();
                    } else {
                        createAlertDialog(CommonActivity.this, respons.getMessageText(), Toast.LENGTH_LONG);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            popupWindow2.dismiss();
            popupWindow.dismiss();
        }
    };

    private GruantThread thread2;

    private class GruantThread extends Thread {
        @Override
        public void run() {
            CardRead.initRead(CommonActivity.this);// 打开Rfid读头模块
            String cardString = null;
            Date date = new Date();
            do {
                cardString = CardRead.readCard(true);
                if (new Date().getTime() - date.getTime() >= SCANF_TIME) {
                    cardString = "close";
                }
            } while (cardString == null && !"close".equals(cardString));
            CardRead.close();
            Message message = new Message();
            message.obj = cardString;
            gruantmhandler.sendMessage(message);
        }
    }

    Handler gruantmhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String returnString = msg.obj.toString();
            if ("close".equals(returnString)) {
                createAlertDialog(CommonActivity.this, getString(R.string.C01S001001_1), Toast.LENGTH_LONG);
            } else {
                try {
                    UserRequest request = new UserRequest();
                    // 设定访问用户信息
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                    String customerID = sharedPreferences.getString("customerID", "");
                    String langCode = sharedPreferences.getString("langCode", "");
                    String langValue = sharedPreferences.getString("langValue", "zh_en");// 语言值（zh_en）
                    String handSetId = sharedPreferences.getString("handsetid", null);// 手持机Id
                    request.setCustomerID(customerID);
                    request.setLanguageCode(langCode);
                    request.setLanguageValue(langValue);
                    request.setEmployeeCard(returnString);
                    request.setActivityName(activityName);
                    C00S000Wsdl service = new C00S000Wsdl();
                    UserRespons respons = service.userGruant(request);
                    if (respons.getStatus() == SUCCESS) {
                        // 保存用户登录信息
                        gruantUserID = respons.getCustomer().getCustomerID();
                        gruantUserName = respons.getUserDetailName();
                    } else {
                        createAlertDialog(CommonActivity.this, respons.getMessageText(), Toast.LENGTH_LONG);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            popupWindow2.dismiss();
            popupWindow.dismiss();
        }
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == SCANKEY_LEFT)
                || (keyCode == SCANKEY_RIGHT)
                || (keyCode == SCANKEY_CENTER)
                || (keyCode == SUCCESS)
                || (keyCode == 213)
                || (keyCode == 139)
            ) {
            btnScan();
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            btnReturn();
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void btnScan() {
        //todo 键盘按钮
//        connect();
    }

    protected void btnReturn() {
        keycodeBack();
    }

    /**
     * 黄色按键 扫描
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeScanf() {
        return false;
    }

    /**
     * F1按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeMenu() {
        return false;
    }

    /**
     * F3按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeBack() {
        return false;
    }

    /**
     * F4按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeF4() {
        return false;
    }

    /**
     * down 按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeUp() {
        return true;
    }

    /**
     * UP 按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeDown() {
        return true;
    }

    /**
     * <-按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeLeft() {
        return true;
    }

    /**
     * ->按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeRight() {
        return true;
    }

    /**
     * Enter按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeEnter() {
        return false;
    }

    /**
     * 取消按钮处理
     */
    protected void appCancel() {
    }

    /**
     * 返回按钮处理
     */
    protected void appReturn() {

    }

    protected byte[] objectToBytes(Object obj) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ObjectOutputStream sOut = new ObjectOutputStream(out);

        sOut.writeObject(obj);

        sOut.flush();

        byte[] bytes = out.toByteArray();

        return bytes;

    }

    protected Object bytesToObject(byte[] bytes) throws Exception {

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);

        ObjectInputStream sIn = new ObjectInputStream(in);

        return sIn.readObject();

    }

    /**
     * 创建一个提示信息的对话框
     *
     * @param activity 要显示在那activity中
     * @param contry   提示内容
     */

    public void createAlertDialog(Context activity, CharSequence contry, int i) {
        // 创建一个AlertDialog的构建者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(CommonActivity.this);
        builder.setTitle(R.string.infoMsg);// 设置标题
        if (contry == null) {
            contry = getString(R.string.CommonActivity_1);
        }
        builder.setMessage(contry);// 提示信息
        builder.setCancelable(false);// 设置对话框不能被取消
        // 设置正面的按钮
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();  // 显示对话框
    }


    private ReaderHelper mReaderHelper;


    private Handler mLoopHandler = new Handler();


    /**
     * 背景变暗方法
     */
    public void dark() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
    }

    /**
     * 恢复背景亮度
     */
    public void bright() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }


    //*****************************************************************************

    /**
     * 授权接口,输入授权
     */
    public UserRespons authorize(String username, String password, String activityName) {
        // 设定访问用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        customerID = sharedPreferences.getString("customerID", "");
        UserRequest request = new UserRequest();
        C00S000Wsdl service = new C00S000Wsdl();
        request.setUserName(username);//用户名
        request.setUserPass(password);//密码
        request.setLoginType("0");//输入授权0  扫卡1
        request.setActivityName(activityName);//页面名
        request.setCustomerID(customerID);//用户ID
        try {
            UserRespons response = service.userGruant(request);
            if (null != response) {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //防止点击扫描后点击此按钮
    public void stopScan() {
        //设置扫描或停止条件为false
        scanOrStop = false;
        scanOrNot = false;
        //关闭读头
        close();
        //关闭弹框
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 授权---扫卡授权
     */
    public UserRespons authorize(String activityName, String employeeCard) {
        // 设定访问用户信息
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        customerID = sharedPreferences.getString("customerID", "");
        UserRequest request = new UserRequest();
        request.setCustomerID(customerID);//用户Id
        request.setLoginType("1");//授权类型（0:输入授权。1：刷卡授权）
        request.setActivityName(activityName);//页面名称
        request.setEmployeeCard(employeeCard);//RFID
        C00S000Wsdl service = new C00S000Wsdl();
        try {
            UserRespons respons = service.userGruant(request);
            if (null != respons) {
                return respons;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //小圆×的点击处理
    public void ivCancel(View view) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public static int P_X = 300;
    public static int P_Y = 240;
    //屏幕的宽度、高度
    protected int screenWidth, screenHeight;

    //把字符串中的小写转为大写
    public static String exChangeBig(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    //将输入的字符串大写显示
    public class AllCapTransformationMethod extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] aa = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return aa;
        }
    }

    //显示扫描弹框的方法
    public void scanPopupWindow() {
        if (null == popupWindow || !popupWindow.isShowing()) {
            //在哪个页面打开窗口
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            //用inflate方法调用界面
            final View view = layoutInflater.inflate(R.layout.c00s000_010activity, null);

            //从res下选择要作为背景的源文件
//            view.setBackgroundResource(R.drawable.my_selectors);

            //设置源文件的长宽
            popupWindow = new PopupWindow(view, P_X, P_Y);

            //从res下选择要作为背景的源文件
//            popupWindow2.setBackgroundDrawable(new BitmapDrawable());
            //设置弹框外部无法获取焦点
//            popupWindow2.setFocusable(true);

            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置popWindow的显示位置
            popupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);
        }
    }


    //超过扫描时间的Handler
    @SuppressLint("HandlerLeak")
    public Handler overtimeHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            //关闭扫描弹框
            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            createAlertDialog(null, getString(R.string.overtimeToast), Toast.LENGTH_LONG);
        }
    };

    //处理网络异常的Handler
    @SuppressLint("HandlerLeak")
    public Handler internetErrorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            createAlertDialog(null, getString(R.string.netConnection), Toast.LENGTH_LONG);
        }
    };

    /**********FanLL**********/

     /*
     * 点击收起软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            //获取输入框当前的location位置  
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }
    /**
     * 扫描 读头
     */
    //扫描或停止条件
    protected boolean scanOrStop = false;
    //控制键盘扫描按键连续点击的布尔变量
    protected boolean isCanScan = true;
    /**
     * 初始化读头模块
     */
    public void initRFID() {
        try {
            rfidWithUHF = RFIDWithUHF.getInstance();
            if (null != rfidWithUHF) {
                new InitTask().execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化读头模块时的进度对话框
     */
    public class InitTask extends AsyncTask<String, Integer, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            return rfidWithUHF.init();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(CommonActivity.this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage(getString(R.string.init));
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            progressDialog.dismiss();
//            if (!aBoolean) {
//                Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
//            }
        }

    }

    /**
     * 单次扫描方法
     */
    public String singleScan() {
        rfidString = null;
        scanOrNot = true;
        String ecodeid = null;
        Date date = new Date();
        do {
            String[] rfid = rfidWithUHF.readTagFromBuffer();
            if (null != rfid && !rfid[0].equals(REGULARLENGTH1) && !rfid[0].equals(REGULARLENGTH2)) {
                rfidString = rfid[1];
            }
            if ("".equals(rfidString) || null == rfidString) {
                // 判断超过扫描时间，读头关闭
                if (System.currentTimeMillis() - date.getTime() >= SCAN_TIME) {
                    rfidString = CLOSE;
                }
            }
        }// 读头不关闭的条件：1、rfidString为空2、rfidString ！= close（没超过扫描时间）3、扫描条件为true
        while (null == rfidString && scanOrNot && !"close".equals(rfidString));
        // 关闭读头模块
        close();
        scanOrNot = false;
        return rfidString;
    }

    /**
     * 连续扫描方法
     */
    public String alwaysScan() {
        String[] rfid = rfidWithUHF.readTagFromBuffer();
        if (null != rfid && !rfid[0].equals(REGULARLENGTH1) && !rfid[0].equals(REGULARLENGTH2)) {
            rfidString = rfid[1];
        }

        return rfidString;
    }

    /**
     * 关闭读头模块
     */
    public void close() {
        if (null != rfidWithUHF) {
            rfidWithUHF.stopInventory();
            scanOrNot = false;
        }
    }


    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }





    //授权弹框
    private PopupWindow popupWindowAuthorization;
    //输入授权按钮、扫描授权按钮、返回按钮
    private Button btnInputAuthorization, btnScanAuthorization, btnReturnAuthorization;
    //输入授权弹框
    private PopupWindow popupWindowInput;
    //用户名、密码
    private String userName, passWord;
    //授权扫描的RfidString
    public String authorizationRfidString;

    //输入授权线程
    private inputAuthorizationThread inputAuthorizationThread;
    //扫描授权线程
    private scanAuthorizationThread scanAuthorizationThread;
    // 网络访问API
    private Retrofit retrofit = RetrofitSingle.newInstance();

    //授权人ID
    private List<AuthCustomer> authorizationList = new ArrayList<>();
    // 授权后显示的名称
    TextView tvAuthorizationName;
    // 授权人数，默认是 1 人
    private int authorizedPeopleNumber = 1;

    // 是否需要授权 true为需要授权；false为不需要授权
    public static boolean is_need_authorization = false;

    // 授权登陆回调方法
    AuthorizationWindowCallBack authorizationWindowCallBack = null;

    public void authorizationWindow(int authorizedPeopleNumber, AuthorizationWindowCallBack callBack){
        authorizationList = new ArrayList<>();
        authorizationWindowCallBack = callBack;

        // 需要授权
        if (is_need_authorization) {
            if (authorizedPeopleNumber > 0) {
                this.authorizedPeopleNumber = authorizedPeopleNumber;
            }

            showAuthorizationWindow();
        } else {
            // 不需要授权传 null
            authorizationWindowCallBack.success(null);
        }
    }

    //授权弹窗
    private void showAuthorizationWindow() {
        //设置显示弹框的参数
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.authorization, null);
        popupWindowAuthorization = new PopupWindow(view, (int) (0.8 * screenWidth), (int) (0.6 * screenHeight), true);
        popupWindowAuthorization.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

        //输入授权按钮处理
        btnInputAuthorization = (Button) view.findViewById(R.id.btnInputAuthorization);
        btnInputAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowAuthorization.dismiss();
                //输入授权弹框
                showInputAuthorizationWindow();
            }
        });

        //扫描授权按钮处理
        btnScanAuthorization = (Button) view.findViewById(R.id.btnScanAuthorization);
        btnScanAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
                    //扫描弹框
                    scanAuthorization();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //返回按钮处理
        btnReturnAuthorization = (Button) view.findViewById(R.id.btnReturnAuthorization);
        btnReturnAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //授权弹框消失
                popupWindowAuthorization.dismiss();
            }
        });

    }

    //输入授权弹框
    private void showInputAuthorizationWindow() {

        //设置显示弹框的参数
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.c00s000_001_1activity, null);
        popupWindowInput = new PopupWindow(view, (int) (0.8 * screenWidth), (int) (0.6 * screenHeight), true);
        popupWindowInput.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

        TextView btnTitle = (TextView) view.findViewById(R.id.btn_title);
        btnTitle.setText(R.string.inputAuthorization);
        //用户名
        final EditText etUserName = (EditText) view.findViewById(R.id.et_username);
        //将光标设置在最后
        etUserName.setSelection(etUserName.getText().length());
        userName = etUserName.getText().toString().trim();
        //密码
        final EditText etPassWord = (EditText) view.findViewById(R.id.et_password);
        //将光标设置在最后
        etPassWord.setSelection(etPassWord.getText().length());
        passWord = etPassWord.getText().toString().trim();
        LinearLayout llUser = (LinearLayout) view.findViewById(R.id.ll_user);
        llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/7/13
            }
        });
        LinearLayout llPassWord = (LinearLayout) view.findViewById(R.id.ll_lock);
        llPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/7/13
            }
        });
        //授权按钮
        Button btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnLogin.setText(R.string.authorization);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(etUserName.getText().toString().trim())) {
                    createAlertDialog(null, getString(R.string.pleaseInputUserName), Toast.LENGTH_LONG);
                } else if ("".equals(etPassWord.getText().toString().trim())) {
                    createAlertDialog(null, getString(R.string.pleaseInputPassWord), Toast.LENGTH_LONG);
                } else {
                    userName = etUserName.getText().toString().trim();
                    passWord = etPassWord.getText().toString().trim();
                    //开启输入授权线程
                    inputAuthorizationThread = new inputAuthorizationThread();
                    inputAuthorizationThread.start();
                }
            }
        });
        //重置按钮
        Button btnReset = (Button) view.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserName.setText("");
                userName = "";
                etPassWord.setText("");
                passWord = "";
            }
        });
        ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //输入授权弹框消失，显示授权弹框
                popupWindowInput.dismiss();
                showAuthorizationWindow();
            }
        });
    }

    //输入授权线程
    private class inputAuthorizationThread extends Thread {
        @Override
        public void run() {
            super.run();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.show();
                }
            });

            IRequest iRequest = retrofit.create(IRequest.class);
            ObjectMapper mapper = new ObjectMapper();

            AuthCustomerVO authCustomerVO = new AuthCustomerVO();
            authCustomerVO.setAccount(userName);
            authCustomerVO.setPassword(passWord);

//            String jsonStr = "";
//            try {
//                jsonStr = mapper.writeValueAsString(authCustomerVO);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }

            Gson gson = new Gson();
            String jsonStr = gson.toJson(authCustomerVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> loganForPDA = iRequest.loganForPDA(body);

            // 消息标识：-1为异常，正常不会有这种情况；0为成功；1为返回错误信息；2为报异常；
            // 当标识为 -1 时 obj 没有值；为 0 时 obj 值为 AuthCustomer；为 1 或 2 时 obj 值为 String，提示给用户
            Message message = new Message();
            message.what = -1;
            try {
                Response<String> response = loganForPDA.execute();

                if (response.raw().code() == 200) {
                    AuthCustomer authCustomer = mapper.readValue(response.body(), AuthCustomer.class);
                    if (authCustomer != null) {
                        message.what = 0;
                        message.obj = authCustomer;
                    } else {
                        message.what = 1;
                        message.obj = "账号或密码错误";
                    }
                } else {
                    message.what = 1;
                    message.obj = response.errorBody().string();
                }
            } catch (Exception e) {
                e.printStackTrace();

                message.what = 2;
                message.obj = getString(R.string.netConnection);
            } finally {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                });

                //输入授权和扫描授权的handler
                inputAuthorizationThreadHandler.sendMessage(message);
            }
        }
    }

    //输入授权和扫描授权的handler
    @SuppressLint("HandlerLeak")
    Handler inputAuthorizationThreadHandler = new Handler() {
        /**
         * 消息标识：-1为特殊情况，正常不会有这种情况；0为成功；1为返回错误信息；2为报异常；
         * 当标识为 -1 时 obj 没有值；为 0 时 obj 值为 AuthCustomer；为 1 或 2 时 obj 值为 String，提示给用户
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            // 消息标识
            int what = msg.what;

            // 成功
            if (what == 0) {
                // 授权信息
                AuthCustomer authCustomer = (AuthCustomer) msg.obj;
                // 授权人列表
                authorizationList.add(authCustomer);

                Toast.makeText(getApplicationContext(), getString(R.string.authorizationSuccess), Toast.LENGTH_SHORT).show();

                // 判断是否已达到授权人数
                if (authorizationList.size() == authorizedPeopleNumber) {
                    // 成功
                    authorizationWindowCallBack.success(authorizationList);
                    if (popupWindowInput != null && popupWindowInput.isShowing()) {
                        //输入授权弹框消失，显示授权弹框
                        popupWindowInput.dismiss();
                    }

                    // 授权完成后，如果是扫描登陆需要关闭授权框
                    if (null != popupWindowAuthorization && popupWindowAuthorization.isShowing()) {
                        popupWindowAuthorization.dismiss();
                    }
                } else {
                    if (popupWindowInput != null && popupWindowInput.isShowing()) {
                        //输入授权弹框消失，显示授权弹框
                        popupWindowInput.dismiss();
                        showAuthorizationWindow();
                    }
                }
            }
            // 返回错误信息
            else if (what == 1) {
                String erroeBody = msg.obj.toString();
                createAlertDialog(CommonActivity.this, erroeBody, Toast.LENGTH_SHORT);
            }
            // 联网抛异常
            else if (what == 2) {
                String erroeInfo = msg.obj.toString();
                createAlertDialog(CommonActivity.this, erroeInfo, Toast.LENGTH_SHORT);
            }

            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }

            // 扫描按钮不为空
            if (btnScanAuthorization != null) {
                // 扫描按钮可用
                btnScanAuthorization.setClickable(true);
            }
        }
    };

    //扫描授权弹框
    private void scanAuthorization() {
        //点击扫描按钮以后，设置扫描按钮不可用
        btnScanAuthorization.setClickable(false);
        //显示扫描弹框的方法
        scanPopupWindow();
        //开启扫描授权线程
        scanAuthorizationThread = new scanAuthorizationThread();
        scanAuthorizationThread.start();
    }

    //扫描授权线程
    private class scanAuthorizationThread extends Thread {
        @Override
        public void run() {
            //单扫方法
            authorizationRfidString = singleScan();// TODO 生产环境解开
            Message message = new Message();
            if ("close".equals(authorizationRfidString)) {
                btnScanAuthorization.setClickable(true);
                overtimeHandler.sendMessage(message);
            } else if (null != authorizationRfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        loading.show();
                    }
                });

                IRequest iRequest = retrofit.create(IRequest.class);
                ObjectMapper mapper = new ObjectMapper();

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(authorizationRfidString);

                AuthCustomerVO authCustomerVO = new AuthCustomerVO();
                authCustomerVO.setRfidContainerVO(rfidContainerVO);

//                String jsonStr = "";
//                try {
//                    jsonStr = mapper.writeValueAsString(authCustomerVO);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }

                Gson gson = new Gson();
                String jsonStr = gson.toJson(authCustomerVO);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> loganForPDA = iRequest.loganForPDA(body);

                // 消息标识：-1为异常，正常不会有这种情况；0为成功；1为返回错误信息；2为报异常；
                // 当标识为 -1 时 obj 没有值；为 0 时 obj 值为 AuthCustomer；为 1 或 2 时 obj 值为 String，提示给用户
                message = new Message();
                message.what = -1;
                try {
                    Response<String> response = loganForPDA.execute();

                    if (response.raw().code() == 200) {
                        message.what = 0;
                        message.obj = mapper.readValue(response.body(), AuthCustomer.class);;
                    } else {
                        message.what = 1;
                        message.obj = response.errorBody().string();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    message.what = 2;
                    message.obj = getString(R.string.netConnection);
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.dismiss();
                        }
                    });

                    //输入授权和扫描授权的handler
                    inputAuthorizationThreadHandler.sendMessage(message);
                }
            }
        }
    }


    /**
     * 异常显示数据提示dialog
     */
    public void exceptionProcessShowDialogAlert(String content, final ExceptionProcessCallBack callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog2);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_alert, null);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(content);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.confirm();
                dialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.cancel();
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
    }


    /**
     * 异常显示数据提示dialog
     */
    public void stopProcessShowDialogAlert(String content, final ExceptionProcessCallBack callback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog2);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_alert2, null);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(content);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.confirm();
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isCanScan) {
            isCanScan = true;
            stopScan();
        }
    }

}