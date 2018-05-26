package com.icomp.Iswtmv10.v01c00.c00s000;

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
import android.widget.Toast;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.RfidContainerVO;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import com.icomp.common.utils.UpdateManager;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRespons;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.Map;

/**
 * 登录页面1
 * Created by FanLL on 2017/8/10.
 */

public class C00S000_001Activity extends CommonActivity {

    @BindView(R.id.btnInputLogin)
    Button btnInputLogin;
    @BindView(R.id.btnScanLogin)
    Button btnScanLogin;

    UpdateManager updateManager;
    //用户名、密码
    private String userName, passWord;
    //输入登录弹框
    private PopupWindow popupWindowInput;
    //检测软件更新的线程
    private visitJinThread visitJinThread;
    //输入登录线程
    private inputAuthorizationThread inputAuthorizationThread;
    //扫描登录的线程
    private scanLoginThread scanLoginThread;

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c00s000_001);
        ButterKnife.bind(this);
//        //创建Activity时，添加到List进行管理
//        SysApplication.getInstance().addActivity(this);


        //开启检测软件更新的线程
        //visitJinThread = new visitJinThread();
        //updateManager = new UpdateManager(C00S000_001Activity.this);
        //visitJinThread.start();

        retrofit = RetrofitSingle.newInstance();

//        //设定用户访问信息
//        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
//        String userInfoJson = sharedPreferences.getString("loginInfo", null);
//
//        if (userInfoJson != null) {
//            Gson gson = new Gson();
//            AuthCustomer authCustomer = gson.fromJson(userInfoJson, AuthCustomer.class);
//
//            userName =  authCustomer.getAccount();
//            passWord = authCustomer.getPassword();
//
//            //开启输入授权线程
//            inputAuthorizationThread = new inputAuthorizationThread();
//            inputAuthorizationThread.start();
//        }

    }

    @OnClick({R.id.btnInputLogin, R.id.btnScanLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnInputLogin:
                //点击输入登录按钮
                showInputLogin();
                break;
            case R.id.btnScanLogin:
                //点击扫描登录按钮
                showScanLogin();
                break;
            default:
        }
    }

    //检测更新版本的线程
    private class visitJinThread extends Thread {
        @Override
        public void run() {
            super.run();
            Message message = new Message();
            updateManager.checkUpdate();
            visitJinHandler.sendMessage(message);
        }
    }

    //检测更新版本的Handler
    @SuppressLint("HandlerLeak")
    private Handler visitJinHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (updateManager.isUpdateFlag()) {
                updateManager.show();
            }
        }
    };

    //点击输入登录按钮的方法
    private void showInputLogin() {
        //设置显示弹框的参数
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.c00s000_001_1activity, null);
        popupWindowInput = new PopupWindow(view, (int) (0.8*screenWidth), (int) (0.6*screenHeight), true);
        popupWindowInput.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);
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
        //登录按钮
        Button btnLogin = (Button) view.findViewById(R.id.btn_login);
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
        //小圆×的点击处理
        ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //输入授权弹框消失，显示授权弹框
                popupWindowInput.dismiss();
            }
        });
    }

    //输入登录线程
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


            AuthCustomerVO authCustomerVO = new AuthCustomerVO();
            authCustomerVO.setAccount(userName);
            authCustomerVO.setPassword(passWord);
//            authCustomerVO.setPageSize(null);
//            authCustomerVO.setCurrentPage(null);

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(authCustomerVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> loganForPDA = iRequest.loganForPDA(body);
            loganForPDA.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Gson gson = new Gson();
                            AuthCustomer authCustomer = gson.fromJson(response.body(), AuthCustomer.class);
                            if (authCustomer != null) {
                                authCustomer.setPassword(passWord);

                                Message message = new Message();
                                message.obj = gson.toJson(authCustomer);
                                scanLoginHandler.sendMessage(message);
                            } else {
                                createAlertDialog(C00S000_001Activity.this, "账号或密码错误", Toast.LENGTH_LONG);
                            }
                        } else {
                            final String errorStr = response.errorBody().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                                    sharedPreferences.edit().clear().commit();
                                    createAlertDialog(C00S000_001Activity.this, errorStr, Toast.LENGTH_LONG);
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnScanLogin.setClickable(true);
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
                            }
                        });

                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnScanLogin.setClickable(true);
                            if (null != loading && loading.isShowing()) {
                                loading.dismiss();
                            }
                            createAlertDialog(C00S000_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                        }
                    });
                }
            });

        }
    }

    //点击扫描登录按钮的方法
    private void showScanLogin() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScanLogin.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描登录的线程
            scanLoginThread = new scanLoginThread();
            scanLoginThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描登录的线程
    private class scanLoginThread extends Thread {
        @Override
        public void run() {
            super.run();
            //调用单扫方法
            rfidString = singleScan();
            if ("close".equals(rfidString)) {
                btnScanLogin.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScanLogin.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                AuthCustomerVO authCustomerVO = new AuthCustomerVO();
                authCustomerVO.setRfidContainerVO(rfidContainerVO);

                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(authCustomerVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> loganForPDA = iRequest.loganForPDA(body);
                loganForPDA.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                Gson gson = new Gson();
                                AuthCustomer authCustomer = gson.fromJson(response.body(), AuthCustomer.class);
//                                authCustomer.setPassword(passWord);

                                Message message = new Message();
                                message.obj = gson.toJson(authCustomer);
                                scanLoginHandler.sendMessage(message);
                            } else {
                                final String errorStr = response.errorBody().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createAlertDialog(C00S000_001Activity.this, errorStr, Toast.LENGTH_LONG);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != loading && loading.isShowing()) {
                                        loading.dismiss();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void _onFailure(Throwable t) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
                                createAlertDialog(C00S000_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
            }
        }
    }

    //扫描登录的Handler
    @SuppressLint("HandlerLeak")
    private Handler scanLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (popupWindowInput != null && popupWindowInput.isShowing()) {
                //输入授权弹框消失
                popupWindowInput.dismiss();
            }
            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            try {
                String authCustomer = (String) msg.obj;

                //设定用户访问信息
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                //新建编辑器
                SharedPreferences.Editor editer = sharedPreferences.edit();
                editer.putString("loginInfo", authCustomer);
                editer.commit();//提交修改

                //扫描登录成功，跳转到系统菜单页面
                Intent intent = new Intent(C00S000_001Activity.this, C00S000_003Activity.class);
                startActivity(intent);
//                SysApplication.getInstance().exit();
                finish();
            } catch (Exception e) {
                btnScanLogin.setClickable(true);
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

            stopScan();

    }

}
