package com.icomp.Iswtmv10.v01c01.c01s024;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s018.C01S018_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import com.icomp.entity.base.Equipment;
import com.icomp.wsdl.v01c01.c01s024.C01S024Wsdl;
import com.icomp.wsdl.v01c01.c01s024.endpoint.C01S024Request;
import com.icomp.wsdl.v01c01.c01s024.endpoint.C01S024Respons;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.text.SimpleDateFormat;

/**
 * 快速查询页面1
 * Created by FanLL on 2017/6/15.
 */

public class C01S024_001Activity extends CommonActivity {


    @BindView(R.id.cailiaoInfo)
    LinearLayout cailiaoInfo;
    @BindView(R.id.tv_cailiaoInfo_materialNumber)
    TextView tvCailiaoInfoMaterialNumber;
    @BindView(R.id.tv_cailiaoInfo_finalExecution)
    TextView tvCailiaoInfoFinalExecution;
    @BindView(R.id.tv_cailiaoInfo_operator)
    TextView tvCailiaoInfoOperator;
    @BindView(R.id.tv_cailiaoInfo_operationTime)
    TextView tvCailiaoInfoOperationTime;
    @BindView(R.id.tv_cailiaoInfo_grindingTimes)
    TextView tvCailiaoInfoGrindingTimes;
    @BindView(R.id.tv_cailiaoInfo_cumulativeAmountOfProcessing)
    TextView tvCailiaoInfoCumulativeAmountOfProcessing;


    @BindView(R.id.hechengInfo)
    LinearLayout hechengInfo;
    @BindView(R.id.tv_hechengInfo_syntheticToolCode)
    TextView tvHechengInfoSyntheticToolCode;
    @BindView(R.id.tv_hechengInfo_finalExecution)
    TextView tvHechengInfoFinalExecution;
    @BindView(R.id.tv_hechengInfo_operator)
    TextView tvHechengInfoOperator;
    @BindView(R.id.tv_hechengInfo_operationTime)
    TextView tvHechengInfoOperationTime;
    @BindView(R.id.tv_hechengInfo_grindingTimes)
    TextView tvHechengInfoGrindingTimes;
    @BindView(R.id.tv_hechengInfo_cumulativeAmountOfProcessing)
    TextView tvHechengInfoCumulativeAmountOfProcessing;


    @BindView(R.id.equipmentInfo)
    LinearLayout equipmentInfo;
    @BindView(R.id.tv_equipmentInfo_equipmentName)
    TextView tvEquipmentInfoEquipmentName;


    @BindView(R.id.personnelInfo)
    LinearLayout personnelInfo;
    @BindView(R.id.tv_personnelInfo_employeeNumber)
    TextView tvPersonnelInfoEmployeeNumber;
    @BindView(R.id.tv_personnelInfo_realName)
    TextView tvPersonnelInfoRealName;
    @BindView(R.id.tv_personnelInfo_department)
    TextView tvPersonnelInfoDepartment;

    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    //扫描线程
    private scanThread scanThread;

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s024_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        finish();
    }

    //扫描按钮处理
    @OnClick(R.id.btnScan)
    public void onViewClicked() {
        //点击扫描按钮的方法
        scan();
    }

    //点击扫描按钮的方法
    private void scan() {
//        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            //点击扫描按钮以后，设置扫描按钮不可用
            btnScan.setClickable(false);
            btnCancel.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //开启扫描线程
            scanThread = new scanThread();
            scanThread.start();
//        } else {
//            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
//        }
    }

    //扫描线程
    public class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //调用单扫方法
//            rfidString = singleScan();
rfidString="18000A00000D6440";
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                btnCancel.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        btnCancel.setClickable(true);
                        isCanScan = true;
                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });


                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                FastQueryVO fastQueryVO = new FastQueryVO();
                fastQueryVO.setRfidLaserCode(rfidString);

                final ObjectMapper mapper = new ObjectMapper();

                String jsonStr = "";
                try {
                    jsonStr = mapper.writeValueAsString(fastQueryVO);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> fastQuery = iRequest.fastQuery(body);
                fastQuery.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                FastQueryVO fastQueryVO = mapper.readValue(response.body(), FastQueryVO.class);

                                if (fastQueryVO != null) {
                                    Message message = new Message();
                                    message.obj = fastQueryVO;
                                    //输入授权和扫描授权的handler
                                    quicQkueryHandler.sendMessage(message);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (null != loading && loading.isShowing()) {
                                                loading.dismiss();
                                            }
                                            Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            } else {
                                final String errorStr = response.errorBody().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (null != loading && loading.isShowing()) {
                                            loading.dismiss();
                                        }
                                        createAlertDialog(C01S024_001Activity.this, errorStr, Toast.LENGTH_LONG);
                                    }
                                });

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != loading && loading.isShowing()) {
                                        loading.dismiss();
                                    }
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
                                createAlertDialog(C01S024_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });

            }
        }
    }

    //显示根据标签查询结果
    @SuppressLint("HandlerLeak")
    Handler quicQkueryHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FastQueryVO fastQueryVO = (FastQueryVO) msg.obj;

            if (fastQueryVO.getCuttingToolBind() != null) {
                // 添加材料刀数据
                addDataForCailiao(fastQueryVO.getCuttingToolBind());
            }

            if (fastQueryVO.getSynthesisCuttingToolBind() != null) {
                // 添加合成刀数据
                addDataForHechengdao(fastQueryVO.getSynthesisCuttingToolBind());
            }

            if (fastQueryVO.getEquipment() != null) {
                //添加设备数据
                addDataForEquipment(fastQueryVO.getEquipment());
            }

            if (fastQueryVO.getAuthCustomer() != null) {
                // 添加人员数据
                addDataForpersonnel(fastQueryVO.getAuthCustomer());
            }
        }
    };

    /**
     * 添加材料刀数据
     */
    private void addDataForCailiao(CuttingToolBind cuttingToolBind) {
        hechengInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        cailiaoInfo.setVisibility(View.VISIBLE);

        tvCailiaoInfoMaterialNumber.setText(cuttingToolBind.getCuttingTool().getBusinessCode());//材料号
        tvCailiaoInfoFinalExecution.setText(cuttingToolBind.getRfidContainer().getOperatorName());//最后执行操作

        tvCailiaoInfoOperator.setText("");//操作者

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String date = df.format(cuttingToolBind.getRfidContainer().getOperatorTime());
        tvCailiaoInfoOperationTime.setText(date);//操作时间

        tvCailiaoInfoGrindingTimes.setText(cuttingToolBind.getSharpenTimes());//修磨次数
        tvCailiaoInfoCumulativeAmountOfProcessing.setText("");//累计加工量
    }

    /**
     * 添加合成刀数据
     */
    private void addDataForHechengdao(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        cailiaoInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.VISIBLE);


        tvHechengInfoSyntheticToolCode.setText(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());//合成刀具编码
        tvHechengInfoFinalExecution.setText(synthesisCuttingToolBind.getRfidContainer().getOperatorName());//最后执行操作
        tvHechengInfoOperator.setText("");//操作者
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String date = df.format(synthesisCuttingToolBind.getRfidContainer().getOperatorTime());
        tvHechengInfoOperationTime.setText(date);//操作时间
        tvHechengInfoGrindingTimes.setText("");//修磨次数
        tvHechengInfoCumulativeAmountOfProcessing.setText("");//累计加工量
    }

    /**
     * 添加设备数据
     */
    private void addDataForEquipment(ProductLineEquipment equipment) {
        cailiaoInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.VISIBLE);


        tvEquipmentInfoEquipmentName.setText(equipment.getName());//设备名称
    }

    /**
     * 添加人员数据
     */
    private void addDataForpersonnel(AuthCustomer authCustomer) {
        cailiaoInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.VISIBLE);


        tvPersonnelInfoEmployeeNumber.setText(authCustomer.getEmployeeCode());//员工号
        tvPersonnelInfoRealName.setText(authCustomer.getName());//真实姓名
        tvPersonnelInfoDepartment.setText(authCustomer.getAuthDepartment().getName());//部门
    }


//    //重写键盘上扫描按键的方法
//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if (isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//        //扫描方法
//        scan();
//    }

}
