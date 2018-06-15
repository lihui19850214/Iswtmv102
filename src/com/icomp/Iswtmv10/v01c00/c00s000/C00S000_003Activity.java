package com.icomp.Iswtmv10.v01c00.c00s000;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.Power;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.PowerSearchVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s025.C01S025_001Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C00S000_003Adapter;
import com.icomp.common.adapter.C00S000_003ViewPageAdapter;
import com.icomp.common.constat.Constat;
import com.icomp.entity.base.Vgrantlist;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.MenuRespons;

import java.math.BigDecimal;
import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Headers;

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

    // 菜单
    private Map<String, Vgrantlist> menuMap = new HashMap<>();

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

        // 生成菜单项
        generateMenu();

        loading.show();
        initThread = new InitThread();
        initThread.start();
    }

    /**
     * 生成菜单项
     */
    private void generateMenu() {
        //E/菜单: 打码-Cutting_tool_Add_Code
        //E/菜单: 绑定-Cutting_tool_Bind
        //E/菜单: 合成刀具换装-SynthesisCuttingTool_Exchange
        //E/菜单: 合成刀具组装-SynthesisCuttingTool_Config
        //E/菜单: 合成刀具拆分-SynthesisCuttingTool_UnConfig
        //E/菜单: 合成刀具安上设备-SynthesisCuttingTool_Install
        //E/菜单: 合成刀卸下设备-SynthesisCuttingTool_UnInstall
        //E/菜单: 刀具报废-Cutting_tool_scap
        //E/菜单: 出库-Out_Library
        //E/菜单: 合成刀具初始化-SynthesisCuttingTool_Init
        //E/菜单: 员工卡初始化-Employee_code_Init
        //E/菜单: 设备初始化-Equipment_Init
        //E/菜单: 厂内刃磨-Cutting_tool_Inside
        //E/菜单: 外委刃磨-Cutting_tool_OutSide
//        E/菜单: 厂外涂层-Cutting_tool_Inside_Coating
        //E/菜单: 标签置换-RFID_Change
        //E/菜单: 标签清空-RFID_Clear
        //E/菜单: 快速查询-Fast_Query
        //E/菜单: 射频设置-Setting_RFID

//        E/菜单: 审核-Audit
//        E/菜单: 领料-Picking

        Vgrantlist tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("1");
        tool_outgoing.setCapabilityName("刀具出库");
        tool_outgoing.setCapabilityUrl("v01c01.c01s004.c01s004_003Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s004"));
        menuMap.put("Out_Library", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("2");
        tool_outgoing.setCapabilityName("刀具打码");
        tool_outgoing.setCapabilityUrl("v01c01.c01s007.C01S007_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s002"));
        menuMap.put("Cutting_tool_Add_Code", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("3");
        tool_outgoing.setCapabilityName("合成刀打码");
        tool_outgoing.setCapabilityUrl("v01c01.c01s025.C01S025_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s001"));//没有找到图标
        menuMap.put("合成刀打码", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("4");
        tool_outgoing.setCapabilityName("刀具绑定");
        tool_outgoing.setCapabilityUrl("v01c01.c01s015.C01S015_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s015"));
        menuMap.put("Cutting_tool_Bind", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("5");
        tool_outgoing.setCapabilityName("刀具换装");
        tool_outgoing.setCapabilityUrl("v01c01.c01s010.c01s010_002Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s010"));
        menuMap.put("SynthesisCuttingTool_Exchange", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("6");
        tool_outgoing.setCapabilityName("厂内修磨");
        tool_outgoing.setCapabilityUrl("v01c01.c01s018.C01S018_002Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s018"));
        menuMap.put("Cutting_tool_Inside", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("7");
        tool_outgoing.setCapabilityName("厂外修磨");
        tool_outgoing.setCapabilityUrl("v01c01.c01s019.C01S019_000Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s019"));
        menuMap.put("Cutting_tool_OutSide", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("7");
        tool_outgoing.setCapabilityName("厂外修磨");
        tool_outgoing.setCapabilityUrl("v01c01.c01s019.C01S019_000Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s019"));
        menuMap.put("Cutting_tool_Inside_Coating", tool_outgoing);


        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("8");
        tool_outgoing.setCapabilityName("安上设备");
        tool_outgoing.setCapabilityUrl("v01c01.c01s011.C01S011_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s011"));
        menuMap.put("SynthesisCuttingTool_Install", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("9");
        tool_outgoing.setCapabilityName("卸下设备");
        tool_outgoing.setCapabilityUrl("v01c01.c01s013.C01S013_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s013"));
        menuMap.put("SynthesisCuttingTool_UnInstall", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("10");
        tool_outgoing.setCapabilityName("刀具报废");
        tool_outgoing.setCapabilityUrl("v01c01.c01s005.c01s005_002_2Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s005"));
        menuMap.put("Cutting_tool_scap", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("11");
        tool_outgoing.setCapabilityName("刀具拆分");
        tool_outgoing.setCapabilityUrl("v01c01.c01s008.C01S008_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s008"));
        menuMap.put("SynthesisCuttingTool_UnConfig", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("12");
        tool_outgoing.setCapabilityName("刀具组装");
        tool_outgoing.setCapabilityUrl("v01c01.c01s009.C01S009_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s009"));
        menuMap.put("SynthesisCuttingTool_Config", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("13");
        tool_outgoing.setCapabilityName("标签置换");
        tool_outgoing.setCapabilityUrl("v01c01.c01s012.C01S012_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s014"));//没有找到图标
        menuMap.put("RFID_Change", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("14");
        tool_outgoing.setCapabilityName("快速查询");
        tool_outgoing.setCapabilityUrl("v01c01.c01s024.C01S024_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s024"));
        menuMap.put("Fast_Query", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("15");
        tool_outgoing.setCapabilityName("清空RFID标签");
        tool_outgoing.setCapabilityUrl("v01c01.c01s002.c01s002_002Activity");
        tool_outgoing.setCapabilityImg(getResource("a4c01s005"));
        menuMap.put("RFID_Clear", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("16");
        tool_outgoing.setCapabilityName("射频设置");
        tool_outgoing.setCapabilityUrl("v01c02.c02s005.C02S005_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c02s005"));
        menuMap.put("Setting_RFID", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("17");
        tool_outgoing.setCapabilityName("合成刀具初始化");
        tool_outgoing.setCapabilityUrl("v01c03.c03s001.C03S001_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s001"));//没有找到图标
        menuMap.put("SynthesisCuttingTool_Init", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("18");
        tool_outgoing.setCapabilityName("设备初始化");
        tool_outgoing.setCapabilityUrl("v01c03.c03s003.C03S003_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s001"));//没有找到图标
        menuMap.put("Equipment_Init", tool_outgoing);

        tool_outgoing = new Vgrantlist();
        tool_outgoing.setCapabilityID("19");
        tool_outgoing.setCapabilityName("员工初始化");
        tool_outgoing.setCapabilityUrl("v01c03.c03s005.C03S005_001Activity");
        tool_outgoing.setCapabilityImg(getResource("a0c01s001"));//没有找到图标
        menuMap.put("Employee_code_Init", tool_outgoing);

//        tool_outgoing = new Vgrantlist();
//        tool_outgoing.setCapabilityID("19");
//        tool_outgoing.setCapabilityName("卸下专机");
//        tool_outgoing.setCapabilityUrl("v01c01.c01s013.C01S013_003Activity");
//        tool_outgoing.setCapabilityImg(getResource("a0c01s004"));
//        menuMap.put("19", tool_outgoing);
    }

    /**
     * 获取图片名称获取图片的资源id的方法，返回16进制字符串
     * @param imageName
     * @return
     */
    public String getResource(String imageName) {
        Context ctx = getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());

        return Integer.toHexString(resId);
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
        List<Vgrantlist> menuList = new ArrayList<>(18);//共18个功能


        ObjectMapper mapper = new ObjectMapper();

        //设定用户访问信息
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        String userInfoJson = sharedPreferences.getString("loginInfo", null);

        AuthCustomer authCustomer = null;
        try {
            authCustomer = mapper.readValue(userInfoJson, AuthCustomer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 网络访问API
        Retrofit retrofit = RetrofitSingle.newInstance();
        IRequest iRequest = retrofit.create(IRequest.class);

        PowerSearchVO powerSearchVO = new PowerSearchVO();
        powerSearchVO.setUserCode(authCustomer.getCode());


        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(powerSearchVO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getPowerForUser = iRequest.getPowerForUser(body);

        try {
            Response<String> response = getPowerForUser.execute();

            /**
             * "外委刃磨"和"厂外涂层"在APP端是一个功能
             * 外委刃磨-Cutting_tool_OutSide
             * 厂外涂层-Cutting_tool_Inside_Coating
             */
            boolean isExist = false;
            if (response.raw().code() == 200) {
                List<Power> powerList = mapper.readValue(response.body(), getCollectionType(mapper, List.class, Power.class));
                if (powerList != null) {
                    for (Power power : powerList) {
                        // 判断是否为外委刃磨或场外涂层
                        if ("Cutting_tool_Inside_Coating".equals(power.getIdentify()) || "Cutting_tool_OutSide".equals(power.getIdentify())) {
                            if (menuMap.containsKey(power.getIdentify()) && !isExist) {
                                menuList.add(menuMap.get(power.getIdentify()));
                            }

                            isExist = true;
                        } else {
                            if (menuMap.containsKey(power.getIdentify())) {
                                menuList.add(menuMap.get(power.getIdentify()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 按照 Vgrantlist 类中 CapabilityID 字段升序
        Collections.sort(menuList, new Comparator<Vgrantlist>(){
            public int compare(Vgrantlist o1, Vgrantlist o2) {
                //按照点击次数降序排列
                if(Integer.valueOf(o1.getCapabilityID()) > Integer.valueOf(o2.getCapabilityID())){
                    return 1;
                }
                if(Integer.valueOf(o1.getCapabilityID()) == Integer.valueOf(o2.getCapabilityID())){
                    return 0;
                }
                return -1;
            }
        });

        MenuRespons menuRespons = new MenuRespons();
        menuRespons.setVgrantlist(menuList);
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
            if (respons.getVgrantlist() == null || respons.getVgrantlist().size() == 0) {
                createAlertDialog(C00S000_003Activity.this, "没有权限访问", Toast.LENGTH_LONG);
                return;
            }
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
