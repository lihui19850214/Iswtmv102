package com.icomp.Iswtmv10.v01c01.c01s018;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRespons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 厂内修磨页面6
 * Created by FanLL on 2017/7/11.
 */

public class C01S018_013Activity extends CommonActivity {

    @BindView(R.id.ll_01)
    LinearLayout ll01;

    //接受参数的JsonString
    private String jsonString;
    //接受上一个页面传递的List结果集
    private List<C01S018_012.DataBean> c01s018012DataBeanList = new ArrayList<>();
    //传递给后台的材料号拼接的字符串，传递给后台的Rfid载体ID拼接的字符串，传递给后台的是否需要授权标识的字符串
    private String toolCodes, rfidContainerIDs, authorizationFlgs;
    //厂外修磨参数类
    C01S018_012.DataBean c01s018DataBean = new C01S018_012.DataBean();

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_013);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();
        try {
            jsonString = getIntent().getStringExtra(PARAM);
            Gson gson = new Gson();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                c01s018DataBean = gson.fromJson(jsonArray.getJSONObject(i).toString().trim(), C01S018_012.DataBean.class);
                c01s018012DataBeanList.add(c01s018DataBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //将接受的数据显示在列表上
        for (int i = 0; i < c01s018012DataBeanList.size(); i++) {
            final View linearLayout = LayoutInflater.from(this).inflate(R.layout.item_changneixiumo, null);
//            TextView tvserialNumber = (TextView) linearLayout.findViewById(R.id.tvserialNumber);//序号
//            tvserialNumber.setText(i + 1 + "");
            TextView tvmaterialNumber = (TextView) linearLayout.findViewById(R.id.tvmaterialNumber);//合成刀具编码
            tvmaterialNumber.setText(exChangeBig(c01s018012DataBeanList.get(i).getSynthesisParametersCode()));
            TextView tvsingleProductCode = (TextView) linearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
            tvsingleProductCode.setText(exChangeBig(c01s018012DataBeanList.get(i).getLaserCode()));
            TextView tvRfidContainerID = (TextView) linearLayout.findViewById(R.id.tvRfidContainerID);//Rfid载体ID
            tvRfidContainerID.setText(exChangeBig(c01s018012DataBeanList.get(i).getRfidContainerID()));
            TextView tvauthorizationFlgs = (TextView) linearLayout.findViewById(R.id.tvauthorizationFlgs);//是否需要授权标识
            tvauthorizationFlgs.setText(c01s018012DataBeanList.get(i).getAuthorizationFlgs());
            //显示在列表上
            ll01.addView(linearLayout);
            //拼接材料号和RFID载体ID为String类型以","为间隔
            if (i == 0) {
                toolCodes = tvmaterialNumber.getText().toString().trim();
                rfidContainerIDs = tvRfidContainerID.getText().toString().trim();
                authorizationFlgs = tvauthorizationFlgs.getText().toString().trim();
            } else {
                toolCodes = toolCodes + "," + tvmaterialNumber.getText().toString().trim();
                rfidContainerIDs = rfidContainerIDs + "," + tvRfidContainerID.getText().toString().trim();
                authorizationFlgs = authorizationFlgs + "," + tvauthorizationFlgs.getText().toString().trim();
            }
        }
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        finish();
    }

    //确定按钮处理--跳转到下一页面
    public void btnConfirm(View view) {
        boolean repeat = false;
        for (int i = 0; i < ll01.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) ll01.getChildAt(i);
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                View view1 = linearLayout.getChildAt(j);
                if (view1 instanceof TextView) {
                    switch (view1.getId()) {
                        case R.id.tvauthorizationFlgs://是否需要授权标识
                            if (((TextView) view1).getText().toString().trim().equals(ONE)) {
                                repeat = true;
                                break;
                            }
                            break;
                    }
                }
            }
        }
        if (repeat) {
            //授权弹框
            showAuthorizationWindow();
        } else {
            //调用接口--提交一体刀修磨信息的方法
            upData();
        }
    }

    //调用接口--提交一体刀修磨信息的方法
    public void upData() {
        loading.show();
        //设定用户访问信息
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        //用户ID
        String customerID = sharedPreferences.getString("customerID", "");
//        if (null != gruantUserID && !"".equals(gruantUserID)) {
//            customerID = gruantUserID;
//        }
        //调用接口，提交一体刀修磨信息
        IRequest iRequest = retrofit.create(IRequest.class);
        Call<String> saveGrindingOneKnifeInfo = iRequest.saveGrindingOneKnifeInfo(toolCodes, rfidContainerIDs, authorizationFlgs, customerID, gruantUserID);
        saveGrindingOneKnifeInfo.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response response) {
                try {
                    String json = response.body().toString();
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getBoolean("success")) {
                        Intent intent = new Intent(C01S018_013Activity.this, C01S018_004Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        createAlertDialog(C01S018_013Activity.this, jsonObject.getString("message"), Toast.LENGTH_LONG);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    loading.dismiss();
                }
            }

            @Override
            public void _onFailure(Throwable t) {
                loading.dismiss();
                createAlertDialog(C01S018_013Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
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
    //授权人ID
    private String gruantUserID;
    //授权参数类
    private UserRequest userRequest = null;
    private UserRespons userRespons = null;
    private C00S000Wsdl c00S000Wsdl = new C00S000Wsdl();
    //授权Activity
    private String activityName = "C01S018_002Activity";
    //输入授权线程
    private inputAuthorizationThread inputAuthorizationThread;
    //扫描授权线程
    private scanAuthorizationThread scanAuthorizationThread;

    //授权弹窗
    private void showAuthorizationWindow() {

        //设置显示弹框的参数
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.authorization, null);
        popupWindowAuthorization = new PopupWindow(view, (int) (0.8*screenWidth), (int) (0.6*screenHeight), true);
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
                //扫描弹框
                scanAuthorization();
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
    protected void showInputAuthorizationWindow() {

        //设置显示弹框的参数
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.c00s000_001_1activity, null);
        popupWindowInput = new PopupWindow(view, (int) (0.8*screenWidth), (int) (0.6*screenHeight), true);
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
                    loading.show();
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
    protected class inputAuthorizationThread extends Thread {
        @Override
        public void run() {
            super.run();
            //设定用户访问信息
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
            //手持机ID
            String handSetId = sharedPreferences.getString("handsetid", "");
            userRequest = new UserRequest();
            userRequest.setHandSetId(handSetId);
            userRequest.setUserName(userName);
            userRequest.setUserPass(passWord);
            userRequest.setLoginType(ZERO);
            userRequest.setActivityName(activityName);
            Message message = new Message();
            try{
                //授权接口
                userRespons = c00S000Wsdl.userGruant(userRequest);
                if (null != userRespons) {
                    message.obj = userRespons;
                    //输入授权和扫描授权的handler
                    inputAuthorizationThreadHandler.sendMessage(message);
                } else {
                    internetErrorHandler.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                internetErrorHandler.sendMessage(message);
            }
        }
    }

    //输入授权和扫描授权的handler
    @SuppressLint("HandlerLeak")
    Handler inputAuthorizationThreadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            userRespons =(UserRespons) msg.obj;
            if (ZERO.equals(userRespons.getStateCode())) {
                gruantUserID = userRespons.getCustomer().getCustomerID();
                loading.show();
                //调用接口--提交一体刀修磨信息的方法
                upData();
            } else {
                createAlertDialog(null, userRespons.getStateMsg(), Toast.LENGTH_LONG);
                btnScanAuthorization.setClickable(true);
            }
        }
    };

    //扫描授权弹框
    protected void scanAuthorization() {
        //点击扫描按钮以后，设置扫描按钮不可用
        btnScanAuthorization.setClickable(false);
        //显示扫描弹框的方法
        scanPopupWindow();
        //开启扫描授权线程
        scanAuthorizationThread = new scanAuthorizationThread();
        scanAuthorizationThread.start();
    }

    //扫描授权线程
    protected class scanAuthorizationThread extends Thread {
        @Override
        public void run() {
            //单扫方法
            authorizationRfidString = singleScan();
            Message message = new Message();
            if ("close".equals(authorizationRfidString)) {
                btnScanAuthorization.setClickable(true);
                overtimeHandler.sendMessage(message);
            } else if (null != authorizationRfidString && !"close".equals(authorizationRfidString)) {
                //设定用户访问信息
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                //手持机ID
                String handSetId = sharedPreferences.getString("handsetid", "");
                userRequest = new UserRequest();
                userRequest.setHandSetId(handSetId);
                userRequest.setEmployeeCard(authorizationRfidString);
                userRequest.setLoginType(ONE);
                userRequest.setActivityName(activityName);
                try{
                    //授权接口
                    userRespons = c00S000Wsdl.userGruant(userRequest);
                    if (null != userRespons) {
                        message.obj = userRespons;
                        //输入授权和扫描授权的handler
                        inputAuthorizationThreadHandler.sendMessage(message);
                    } else {
                        internetErrorHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    internetErrorHandler.sendMessage(message);
                    btnScanAuthorization.setClickable(true);
                }
            }
        }
    }

}
