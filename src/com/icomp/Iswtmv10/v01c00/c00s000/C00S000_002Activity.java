package com.icomp.Iswtmv10.v01c00.c00s000;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C00S000_002Adapter;
import com.icomp.entity.base.System;
import com.icomp.entity.base.Vgrantlist;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRespons;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统主菜单
 *
 * @author yzq
 */
public class C00S000_002Activity extends CommonActivity {

    String[] menu_url_array;
    String[] menu_cap_array;
    String[] menu_capName_array;

    @BindView(R.id.gv_menu)
    GridView gvMenu;
    @BindView(R.id.tv_01)
    TextView tv01;
    private String cardString;

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
//        // 跳转到菜单页面
//        Intent intent = new Intent();
//        intent.setClass(this, C00S000_001Activity.class);//从哪里跳到哪里
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //上一个界面传来的员工卡号
        cardString = getIntent().getStringExtra(PARAM);
        super.onCreate(savedInstanceState);
//        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.c00s000_002activity);
        ButterKnife.bind(this);
        SharedPreferences preferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        String name = preferences.getString("name", "");
        tv01.setText(name);
        loading.show();
        thread = new VisitJniThread();
        thread.start();
    }

    public VisitJniThread thread;



    public class VisitJniThread extends Thread {
        @Override
        public void run() {
            MenuRespons menuRespons = null;
            try {
                menuRespons = initViews();
                Message message = new Message();
                message.obj = menuRespons;
                VisitJniHandler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                internetErrorHandler.sendEmptyMessage(0);
            }

        }

        private MenuRespons initViews() throws Exception {
//            Date date = new Date();
//            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 取得用户登录信息
            SharedPreferences preferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
            String userName = preferences.getString("userName", "");
            String langCode = preferences.getString("langCode", "");
            String langValue = preferences.getString("langValue", "");
            // 取得当前页面的菜单项目
            C00S000Wsdl t = new C00S000Wsdl();
            MenuRequest request = new MenuRequest();
            request.setUserName(userName);
            request.setLanguageCode(langCode);
            request.setLanguageValue(langValue);
            request.setCapabilityLevel(BigDecimal.ONE);

            //MenuRespons respons = t.getMenu(request);

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
            tool_outgoing.setCapabilityID("12");
            tool_outgoing.setCapabilityName("刀具报废");
            tool_outgoing.setCapabilityUrl("v01c01.c01s005.c01s005_002_2Activity");
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





            MenuRespons respons = new MenuRespons();
            respons.setVgrantlist(list);
            respons.setStateMsg(null);

//            createAlertDialog(C00S000_002Activity.this , dateformat.format(date) , Toast.LENGTH_LONG);
            return respons;
        }

        @SuppressLint("HandlerLeak")
        Handler VisitJniHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                MenuRespons respons = (MenuRespons) msg.obj;

                if (loading != null) {
                    loading.dismiss();
                }

                if (null != respons.getStateMsg()) {
                    createAlertDialog(C00S000_002Activity.this, respons.getStateMsg(), Toast.LENGTH_SHORT);
                } else {
                    /** 菜单图片 **/
                    int[] menu_image_array = new int[respons.getVgrantlist().size()];
                    menu_url_array = new String[respons.getVgrantlist().size()];
                    menu_cap_array = new String[respons.getVgrantlist().size()];
                    menu_capName_array = new String[respons.getVgrantlist().size()];

                    int i = 0;
                    for (Vgrantlist vgrantlist : respons.getVgrantlist()) {
                        menu_image_array[i] = Integer.parseInt(vgrantlist.getCapabilityImg().replaceAll("^0[x|X]", ""), 16);
                        menu_url_array[i] = vgrantlist.getCapabilityUrl();
                        menu_cap_array[i] = vgrantlist.getCapabilityID();
                        menu_capName_array[i] = vgrantlist.getCapabilityName();
                        i++;
                    }

                    //menuGrid.setAdapter(getMenuAdapter(menu_image_array));
                    gvMenu.setAdapter(new C00S000_002Adapter(C00S000_002Activity.this, menu_image_array, menu_capName_array));
                    gvMenu.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            if (arg2 <= menu_url_array.length - 1) {
                                String url = menu_url_array[arg2];// 取得要跳转的Activit名
                                String capID = menu_cap_array[arg2];

                                //取得用户登录信息
                                SharedPreferences preferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                                Editor editor = preferences.edit();// 获取编辑器
                                editor.putString("capID", capID);
                                editor.commit();// 提交修改

                                try {
                                    Intent intent = new Intent();
                                    intent.setClass(C00S000_002Activity.this, Class.forName("com.icomp.Iswtmv10." + url));// 从哪里跳到哪里
                                    startActivity(intent);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        };
    }


}
