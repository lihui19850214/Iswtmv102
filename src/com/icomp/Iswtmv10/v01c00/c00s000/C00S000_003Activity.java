package com.icomp.Iswtmv10.v01c00.c00s000;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C00S000_003Adapter;
import com.icomp.common.adapter.C00S000_003ViewPageAdapter;
import com.icomp.common.constat.Constat;
import com.icomp.entity.base.Vgrantlist;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRespons;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 刀具管理菜单页面
 *
 * @author yzq
 */
public class C00S000_003Activity extends CommonActivity {
    @BindView(R.id.ScrollLayout)
    ViewPager mScrollLayout;
    // GridView menuGrid;
    String[] menu_url_array;
    String[] menu_cap_array;

    int total = 0;
    String[] menu_capName_array;
//    @BindView(R.id.btn_return)
//    Button btnReturn;
    @BindView(R.id.tv_01)
    TextView tv01;
    //当前显示第几页
    private int page = 0;

    @Override
    public boolean keycodeBack() {
        appReturn();
        return false;
    }

    /**
     * 返回按钮处理
     */
    @Override
    public void appReturn() {
        //finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c00s000_003activity);
        ButterKnife.bind(this);
        SharedPreferences preferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        String name = preferences.getString("name", "");
        tv01.setText(name);
        loading.show();
        initThread = new InitThread();
        initThread.start();
    }

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

    public MenuRespons getMenu() {
        List<Vgrantlist> list = new ArrayList<Vgrantlist>(17);//共19个功能

        Vgrantlist tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("1");
        tool_outgoing.setCapabilityName("刀具出库");
        tool_outgoing.setCapabilityUrl("v01c01.c01s004.c01s004_003Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("2");
        tool_outgoing.setCapabilityName("刀具打码");
        tool_outgoing.setCapabilityUrl("v01c01.c01s007.C01S007_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("3");
        tool_outgoing.setCapabilityName("刀具绑定");
        tool_outgoing.setCapabilityUrl("v01c01.c01s015.C01S015_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("4");
        tool_outgoing.setCapabilityName("刀具换装");
        tool_outgoing.setCapabilityUrl("v01c01.c01s010.c01s010_002Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("10");
        tool_outgoing.setCapabilityName("厂内修磨");
        tool_outgoing.setCapabilityUrl("v01c01.c01s018.C01S018_002Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("11");
        tool_outgoing.setCapabilityName("厂外修磨");
        tool_outgoing.setCapabilityUrl("v01c01.c01s019.C01S019_000Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("7");
        tool_outgoing.setCapabilityName("安上设备");
        tool_outgoing.setCapabilityUrl("v01c01.c01s011.C01S011_002Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("8");
        tool_outgoing.setCapabilityName("卸下设备");
        tool_outgoing.setCapabilityUrl("v01c01.c01s013.C01S013_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
//            tool_outgoing = new Vgrantlist();
//            tool_outgoing.setCapabilityID("9");
//            tool_outgoing.setCapabilityName("卸下专机");
//            tool_outgoing.setCapabilityUrl("v01c01.c01s013.C01S013_003Activity");
//            tool_outgoing.setCapabilityImg("0x7f020000");
//            list.add(tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("12");
        tool_outgoing.setCapabilityName("刀具报废");
        tool_outgoing.setCapabilityUrl("v01c01.c01s005.c01s005_002_2Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("5");
        tool_outgoing.setCapabilityName("刀具拆分");
        tool_outgoing.setCapabilityUrl("v01c01.c01s008.C01S008_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("6");
        tool_outgoing.setCapabilityName("刀具组装");
        tool_outgoing.setCapabilityUrl("v01c01.c01s009.C01S009_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("13");
        tool_outgoing.setCapabilityName("标签置换");
        tool_outgoing.setCapabilityUrl("v01c01.c01s012.C01S012_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("14");
        tool_outgoing.setCapabilityName("快速查询");
        tool_outgoing.setCapabilityUrl("v01c01.c01s024.C01S024_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("15");
        tool_outgoing.setCapabilityName("清空RFID标签");
        tool_outgoing.setCapabilityUrl("v01c01.c01s002.c01s002_002Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("16");
        tool_outgoing.setCapabilityName("射频设置");
        tool_outgoing.setCapabilityUrl("v01c02.c02s005.C02S005_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("17");
        tool_outgoing.setCapabilityName("合成刀具初始化");
        tool_outgoing.setCapabilityUrl("v01c03.c03s001.C03S001_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("18");
        tool_outgoing.setCapabilityName("加工设备初始化");
        tool_outgoing.setCapabilityUrl("v01c03.c03s003.C03S003_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);
        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("19");
        tool_outgoing.setCapabilityName("员工初始化");
        tool_outgoing.setCapabilityUrl("v01c03.c03s005.C03S005_001Activity");
        tool_outgoing.setCapabilityImg("0x7f020000");
        list.add(tool_outgoing);



        MenuRespons menuRespons = new MenuRespons();
        menuRespons.setVgrantlist(list);
        menuRespons.setStateMsg(null);

        return menuRespons;
    }

    /**
     * 请求数据线程
     */
    public class InitThread extends Thread {
        @Override
        public void run() {
            try {
                MenuRespons respons = getMenu();
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
                createAlertDialog(C00S000_003Activity.this, respons.getMessageText(), Toast.LENGTH_LONG);
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
                gridView.setAdapter(new C00S000_003Adapter(C00S000_003Activity.this, menu_image_array, s, PageCount, menu_capName_array, mScrollLayout));
                viewList.add(view);

            }
            //viewpager适配器
            if (viewList.size() > 0) {
                mScrollLayout.setAdapter(new C00S000_003ViewPageAdapter(C00S000_003Activity.this, viewList));
                //设置监听
                mScrollLayout.setOnPageChangeListener(onPageChangeListener);
            }

        }
    };

    /**
     * gridView 的onItemLick响应事件
     */
    public OnItemClickListener listener = new OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position <= menu_url_array.length - 1) {

                String imgUrl = menu_url_array[page * Constat.MENU_SIZE + position];// 取得要跳转的Activit名
                if (imgUrl == null || "".equals(imgUrl)) {
                    return;
                }
                packageContext = C00S000_003Activity.this;
                url = imgUrl;
                reLogin(view, R.layout.c00s000_016activity);
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
     * 返回按钮点击事件
     */
//    @OnClick(R.id.btn_return)
//    public void onClick() {
//        appReturn();
//    }

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
        return mScrollLayout.onKeyDown(keyCode, event);
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

}
