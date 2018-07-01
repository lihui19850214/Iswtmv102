package com.icomp.Iswtmv10.v01c03.c03s005;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.RfidContainerVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 员工卡初始化页面2
 */
public class C03S005_002Activity extends CommonActivity {

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.btnScan)
    Button btnScan;

    //扫描线程
    private scanThread scanThread;

    //员工初始化参数类
    public AuthCustomer params = new AuthCustomer();

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s005_002);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();

        try {
            //接受上一个页面传递的数值
            params = (AuthCustomer) getIntent().getSerializableExtra(PARAM);
            //员工号
            tv01.setText(params.getEmployeeCode());
            //真实姓名
            tv02.setText(params.getName());
            //部门
            tv03.setText(params.getAuthDepartment().getName());
        }  catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        finish();
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        Intent intent = new Intent(this, C03S005_001Activity.class);
        //点击返回时将员工卡号传递到上一个页面
        intent.putExtra(PARAM1,params.getEmployeeCode());
        startActivity(intent);
        finish();
    }

    //扫描按钮处理
    @OnClick(R.id.btnScan)
    public void onViewClicked() {
        scan();
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            //点击扫描按钮以后，设置扫描按钮不可用
            btnScan.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //开启扫描线程
            scanThread = new scanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();
            Message message = new Message();
            if ("close".equals(rfidString)) {
                overtimeHandler.sendMessage(message);
                btnScan.setClickable(true);
                isCanScan = true;
            } else if(null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                try {
                    //调用接口，查询员工信息
                    IRequest iRequest = retrofit.create(IRequest.class);
                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    AuthCustomerVO authCustomerVO = new AuthCustomerVO();
                    authCustomerVO.setRfidContainerVO(rfidContainerVO);
                    authCustomerVO.setCode(params.getCode());

                    String jsonStr = objectToJson(authCustomerVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> initEmployee = iRequest.initEmployee(body);
                    initEmployee.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    //跳转到员工初始化成功页
                                    Intent intent = new Intent(C03S005_002Activity.this, C03S005_003Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C03S005_002Activity.this, errorStr, Toast.LENGTH_LONG);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
                                    createAlertDialog(C03S005_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != loading && loading.isShowing()) {
                                loading.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

//     //重写键盘上扫描按键的方法
//     @Override
//     protected void btnScan() {
//         super.btnScan();
//         if(isCanScan) {
//             isCanScan = false;
//         } else {
//             return;
//         }
//         scan();
//     }

}
