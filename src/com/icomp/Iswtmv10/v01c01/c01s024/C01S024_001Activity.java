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
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.vo.CuttingToolBindVO;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SharpenVO;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s018.C01S018_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import com.icomp.wsdl.v01c01.c01s024.C01S024Wsdl;
import com.icomp.wsdl.v01c01.c01s024.endpoint.C01S024Request;
import com.icomp.wsdl.v01c01.c01s024.endpoint.C01S024Respons;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            //点击扫描按钮以后，设置扫描按钮不可用
            btnScan.setClickable(false);
            btnCancel.setClickable(false);
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
    public class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //调用单扫方法
            rfidString = singleScan();

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

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
                cuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(cuttingToolBindVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> getInCuttingToolBind = iRequest.getInCuttingToolBind(body);
                getInCuttingToolBind.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                Gson gson = new Gson();
                                CuttingToolBind cuttingToolBind = gson.fromJson(response.body(), CuttingToolBind.class);

                                if (cuttingToolBind != null) {
                                    // 添加材料刀数据
                                    addDataForCailiao();

                                    // 添加合成刀数据
                                    addDataForHechengdao();

                                    //添加设备数据
                                    addDataForEquipment();

                                    // 添加人员数据
                                    addDataForpersonnel();
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

    /**
     * 添加材料刀数据
     */
    private void addDataForCailiao() {
        hechengInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        cailiaoInfo.setVisibility(View.VISIBLE);


        tvCailiaoInfoMaterialNumber.setText("材料号");//材料号
        tvCailiaoInfoFinalExecution.setText("最后执行操作");//最后执行操作
        tvCailiaoInfoOperator.setText("操作者");//操作者
        tvCailiaoInfoOperationTime.setText("操作时间");//操作时间
        tvCailiaoInfoGrindingTimes.setText("修磨次数");//修磨次数
        tvCailiaoInfoCumulativeAmountOfProcessing.setText("累计加工量");//累计加工量
    }

    /**
     * 添加合成刀数据
     */
    private void addDataForHechengdao() {
        cailiaoInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.VISIBLE);


        tvHechengInfoSyntheticToolCode.setText("合成刀具编码");//合成刀具编码
        tvHechengInfoFinalExecution.setText("最后执行操作");//最后执行操作
        tvHechengInfoOperator.setText("操作者");//操作者
        tvHechengInfoOperationTime.setText("操作时间");//操作时间
        tvHechengInfoGrindingTimes.setText("修磨次数");//修磨次数
        tvHechengInfoCumulativeAmountOfProcessing.setText("累计加工量");//累计加工量
    }

    /**
     * 添加设备数据
     */
    private void addDataForEquipment() {
        cailiaoInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.VISIBLE);


        tvEquipmentInfoEquipmentName.setText("设备名称");//设备名称
    }

    /**
     * 添加人员数据
     */
    private void addDataForpersonnel() {
        cailiaoInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.VISIBLE);


        tvPersonnelInfoEmployeeNumber.setText("员工号");//员工号
        tvPersonnelInfoRealName.setText("真实姓名");//真实姓名
        tvPersonnelInfoDepartment.setText("部门");//部门
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
