package com.icomp.Iswtmv10.v01c00.c00s000;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C00S000_003Adapter;
import com.icomp.common.adapter.C00S000_003ViewPageAdapter;
import com.icomp.common.constat.Constat;
import com.icomp.common.utils.SysApplication;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRespons;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统设置菜单页面
 *
 * @author yzq
 */
public class C00S000_004Activity extends CommonActivity {
    String[] menu_url_array;
    String[] menu_cap_array;
    int total = 0;
    @BindView(R.id.vp_01)
    ViewPager vp01;
    @BindView(R.id.btn_return)
    Button btnReturn;

    String[] menu_capName_array;
    //当前显示第几页
    private int page = 0;

    @Override
    public boolean keycodeBack() {
        appReturn();
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.c00s000_004activity);
        ButterKnife.bind(this);
        loading.show();
        initThread = new InitThread();
        initThread.start();
    }

    /**
     * 请求数据线程
     */
    public class InitThread extends Thread {
        @Override
        public void run() {
            try {
                MenuRespons respons = initConnect();
                Message message = new Message();
                message.obj = respons;
                initHandler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                internetErrorHandler.sendEmptyMessage(0);
            }
        }
    }

    public InitThread initThread;
    /**
     * 处理数据handler
     */
    Handler initHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            loading.dismiss();
            MenuRespons respons = (MenuRespons) msg.obj;
            if (null != respons.getMessageText()) {
                createAlertDialog(C00S000_004Activity.this, respons.getMessageText(), Toast.LENGTH_LONG);
                return;
            }
            int[] menu_image_array = null;
            List<View> viewList = new ArrayList<View>();
            total = respons.getTotal();
            //图片个数
            menu_image_array = new int[respons.getVgrantlist().size()];
            //模块名称
            menu_capName_array = new String[respons.getVgrantlist().size()];
            menu_url_array = new String[respons.getVgrantlist().size()];
            menu_cap_array = new String[respons.getVgrantlist().size()];
            for (int i = 0; i < respons.getVgrantlist().size(); i++) {
                //图标
                menu_image_array[i] = Integer.parseInt(respons.getVgrantlist().get(i).getCapabilityImg().replaceAll("^0[x|X]", ""), 16);
                menu_capName_array[i] = respons.getVgrantlist().get(i).getCapabilityName();
                menu_url_array[i] = respons.getVgrantlist().get(i).getCapabilityUrl();
                //模块名
                menu_cap_array[i] = respons.getVgrantlist().get(i).getCapabilityID();
            }
            //需要显示几页
            int PageCount = respons.getVgrantlist().size() / Constat.MENU_SIZE;
            if (respons.getVgrantlist().size() % Constat.MENU_SIZE != 0) {
                PageCount += 1;
            }
            for (int s = 0; s < PageCount; s++) {
                View view = getLayoutInflater().inflate(R.layout.gridview_layout, null);
                GridView gridView = (GridView) view.findViewById(R.id.gv_01);
                gridView.setOnItemClickListener(listener);
                gridView.setSelector(R.drawable.x_selectshape3);
                gridView.setAdapter(new C00S000_003Adapter(C00S000_004Activity.this, menu_image_array, s, PageCount, menu_capName_array, vp01));
                viewList.add(view);

            }
            //viewpager适配器
            if (viewList.size() > 0) {
                vp01.setAdapter(new C00S000_003ViewPageAdapter(C00S000_004Activity.this, viewList));
                //设置监听
                vp01.setOnPageChangeListener(onPageChangeListener);
            }

        }
    };
    /**
     * 监听当前滑动到第几页
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            page = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 连接网络请求数据方法
     */
    public MenuRespons initConnect() throws Exception {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // 取得用户登录信息
        SharedPreferences preferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        String userName = preferences.getString("userName", "");
        String langCode = preferences.getString("langCode", "");
        String langValue = preferences.getString("langValue", "");
        String capID = preferences.getString("capID", "");
        // 取得当前页面的菜单项目
        C00S000Wsdl t = new C00S000Wsdl();
        MenuRequest request = new MenuRequest();
        request.setUserName(userName);
        request.setLanguageCode(langCode);
        request.setLanguageValue(langValue);
        request.setCapabilityLevel(new BigDecimal(2));
        request.setCapCapabilityID(capID);

        return t.getMenu(request);
    }

    /**
     * 返回按钮
     */
    @OnClick(R.id.btn_return)
    public void onClick() {
        finish();
    }

    /**
     * gridView 的onItemLick响应事件
     */
    public OnItemClickListener listener = new OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position <= menu_url_array.length - 1) {

                String imgUrl = menu_url_array[page * Constat.MENU_SIZE + position];// 取得要跳转的Activit名
                packageContext = C00S000_004Activity.this;
                url = imgUrl;
                if (imgUrl == null || "".equals(imgUrl)) {
                    return;
                }
                Intent intent = new Intent();
                try {
                    intent.setClass(packageContext, Class.forName("com.icomp.Iswtmv10." + url));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }// 从哪里跳到哪里
                packageContext.startActivity(intent);
            }
        }

    };


    /**
     * 在菜单页面中点击左右方向键时候，菜单切换的处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            return super.onKeyDown(keyCode, event);
        }
        return vp01.onKeyDown(keyCode, event);
    }

    /**
     * down 按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeUp() {
        return false;
    }

    /**
     * UP 按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeDown() {
        return false;
    }

    /**
     * <-按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeLeft() {
        return false;
    }

    /**
     * ->按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    protected boolean keycodeRight() {
        return false;
    }


    /**
     * 友盟错误统计
     */
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
