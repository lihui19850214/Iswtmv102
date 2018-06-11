package com.icomp.Iswtmv10.v01c01.c01s002;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.vo.RFIDQueryVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 清空标签页面1
 */
public class c01s002_002Activity extends CommonActivity {

    @BindView(R.id.tvNum)
    TextView mTvNum;
    @BindView(R.id.btnScan)
    Button mBtnScan;
    @BindView(R.id.btnStop)
    Button mBtnStop;
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.btnNext)
    Button mBtnNext;

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s002_002);
        ButterKnife.bind(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();

    }

    @OnClick({R.id.btnScan, R.id.btnStop, R.id.btnCancel, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnScan:
                scan();
                break;
            case R.id.btnStop:
                stop_scan();
                break;
            case R.id.btnCancel:
                if (!isCanScan) {
                    stop_scan();
                }
                finish();
                break;
            case R.id.btnNext:
                if (!isCanScan) {
                    stop_scan();
                }

                is_need_authorization = true;
                authorizationWindow(new AuthorizationWindowCallBack() {
                    @Override
                    public void success(AuthCustomer authCustomer) {
                        requestData(authCustomer);
                    }

                    @Override
                    public void fail() {

                    }
                });
                break;
        }
    }

    private void stop_scan() {
        scanOrNot = false;
        isCanScan = true;
        close();
        mBtnScan.setClickable(true);
        mBtnScan.setText(getString(R.string.scan));
        mBtnScan.setBackgroundResource(R.drawable.border);
    }

    //扫描数量
    public int scanNumber;
    //群扫存放rfidString的List
    public List<String> rfidList;

    //扫描线程
    private scanThread scanThread;

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            mBtnScan.setText("扫描中");
            mBtnScan.setClickable(false);
            mBtnScan.setBackgroundResource(R.color.hintcolor);

            //设置扫描或停止条件为true
            scanOrNot = true;
            isCanScan = false;

            //启动扫描线程
            scanThread = new scanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class scanThread extends Thread{
        @Override
        public void run() {
            super.run();
            //需每次置rfidString为null
            rfidString = null;

            while (null == rfidString && scanOrStop) {
                rfidString = alwaysScan();
            }

            if (null != rfidString) {
                Message message = new Message();
                message.obj = rfidString;
                scanHandler.sendMessage(message);
            }
        }
    }

    //扫描Handler
    @SuppressLint("HandlerLeak")
    Handler scanHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rfidString = msg.obj.toString();

            if (null == rfidList) {
                rfidList = new ArrayList<>();
            }

            if (!rfidList.contains(rfidString)) {
                try {
                    loading.show();

                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);

                    //TODO 需要处理参数
                    RFIDQueryVO rFIDQueryVO = new RFIDQueryVO();
                    rFIDQueryVO.setRfidCode(rfidString);

                    String jsonStr = objectToJson(rFIDQueryVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> queryByRFID = iRequest.queryByRFID(body);
                    queryByRFID.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    RFIDQueryVO rfidQueryVO = jsonToObject(response.body(), RFIDQueryVO.class);

                                    if (rfidQueryVO != null) {
                                        CuttingToolBind cuttingToolBind = rfidQueryVO.getCuttingToolBind();// 材料刀，单品刀
                                        ProductLineEquipment productLineEquipment = rfidQueryVO.getEquipment();// 设备
                                        SynthesisCuttingToolBind synthesisCuttingToolBind = rfidQueryVO.getSynthesisCuttingToolBind();// 合成刀具

                                        StringBuffer content = new StringBuffer();
                                        if (cuttingToolBind != null) {
                                            content.append("材料号：" + cuttingToolBind.getCuttingTool().getBusinessCode() + "\n");
                                            //content.append("状态：" + cuttingToolBind.getCuttingTool() + "\n");//TODO 不知道是哪个字段
                                            content.append("刀身码：" + cuttingToolBind.getBladeCode());
                                        } else if (productLineEquipment != null) {
                                            content.append("设备代码：" + productLineEquipment.getCode());
                                        } else if (synthesisCuttingToolBind != null) {
                                            content.append("合成刀具编码：" + synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());
                                        } else if (false) {// TODO 是否还有人员信息和标签
                                            content.append("员工号：" + "\n");
                                            content.append("真实姓名：" + "\n");
                                            content.append("部门：" + "\n");
                                        }

                                        if (!"".equals(content.toString())) {
                                            showDialogAlert(content.toString(), rfidString);
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    createAlertDialog(c01s002_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                    //重新启动扫描线程
                                    scanThread = new scanThread();
                                    scanThread.start();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                            } finally {
                                loading.dismiss();
                            }
                        }

                        @Override
                        public void _onFailure(Throwable t) {
                            if (!isCanScan) {
                                stop_scan();
                            }
                            loading.dismiss();
                            createAlertDialog(c01s002_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (null != loading && loading.isShowing()) {
                        loading.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                }
            } else {
                // 重复扫描
                Toast.makeText(getApplicationContext(), "重复扫描", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 显示数据提示dialog
     */
    private void showDialogAlert(String content, final String rfid) {
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
                //将rfidString放入params.rfidList列表中
                rfidList.add(rfid);

                //扫描数量 scanNumber+1
                scanNumber = scanNumber + 1;
                //显示当前清空数量
                mTvNum.setText("清空数量：" + scanNumber);
                mBtnNext.setText("清空");


                //重新启动扫描线程
                scanThread = new scanThread();
                scanThread.start();

                dialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新启动扫描线程
                scanThread = new scanThread();
                scanThread.start();

                dialog.cancel();
            }
        });

        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
    }



    //提交清空 RFID 标签数据
    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            // TODO 需要处理参数
            RFIDQueryVO rFIDQueryVO = new RFIDQueryVO();
            // TODO 授权信息不知道放哪
//        authorizationList.get(0);
            String jsonStr = objectToJson(rFIDQueryVO);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> clearRFID = iRequest.clearRFID(body);

            clearRFID.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到成功详细页面
                            Intent intent = new Intent(c01s002_002Activity.this, c01s002_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(c01s002_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                    } finally {
                        loading.dismiss();
                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    loading.dismiss();
                    createAlertDialog(c01s002_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }



}
