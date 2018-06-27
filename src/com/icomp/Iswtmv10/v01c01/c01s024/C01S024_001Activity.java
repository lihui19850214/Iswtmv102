package com.icomp.Iswtmv10.v01c01.c01s024;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.EquipmentTypeEnum;
import com.apiclient.constants.GrindingEnum;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.vo.*;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.text.SimpleDateFormat;

/**
 * 快速查询页面1
 */
public class C01S024_001Activity extends CommonActivity {


    @BindView(R.id.cailiaoInfo)
    LinearLayout cailiaoInfo;

    @BindView(R.id.tv_cailiaoInfo_materialNumber)//物料号
    TextView tvCailiaoInfoMaterialNumber;
    @BindView(R.id.tv_cailiaoInfo_bladeCode)//刀身码
    TextView tvCailiaoInfoBladeCode;
    @BindView(R.id.tv_cailiaoInfo_finalExecution)//最后操作
    TextView tvCailiaoInfoFinalExecution;
    @BindView(R.id.tv_cailiaoInfo_operator)//操作者
    TextView tvCailiaoInfoOperator;
    @BindView(R.id.tv_cailiaoInfo_operationTime)//操作事件
    TextView tvCailiaoInfoOperationTime;
    @BindView(R.id.tv_cailiaoInfo_cumulativeAmountOfProcessing)//累计加工
    TextView tvCailiaoInfoCumulativeAmountOfProcessing;
    @BindView(R.id.tv_cailiaoInfo_grinding_method)//刃磨方式
    TextView tvCailiaoInfoGrindingMethod;


    @BindView(R.id.hechengInfo)
    LinearLayout hechengInfo;

    @BindView(R.id.tv_hechengInfo_syntheticToolCode)//合成刀
    TextView tvHechengInfoSyntheticToolCode;
    @BindView(R.id.tv_hechengInfo_finalExecution)//最后操作
    TextView tvHechengInfoFinalExecution;
    @BindView(R.id.tv_hechengInfo_operator)//操作者
    TextView tvHechengInfoOperator;
    @BindView(R.id.tv_hechengInfo_operationTime)//操作时间
    TextView tvHechengInfoOperationTime;
    @BindView(R.id.tv_hechengInfo_cumulativeAmountOfProcessing)//累计加工
    TextView tvHechengInfoCumulativeAmountOfProcessing;


    @BindView(R.id.equipmentInfo)
    LinearLayout equipmentInfo;

    @BindView(R.id.tv_equipmentInfo_equipmentName)//设备名称
    TextView tvEquipmentInfoEquipmentName;
    @BindView(R.id.tv_equipmentInfo_equipmentType)//设备类型
    TextView tvEquipmentInfoEquipmentType;


    @BindView(R.id.personnelInfo)
    LinearLayout personnelInfo;

    @BindView(R.id.tv_personnelInfo_employeeNumber)//员工号
    TextView tvPersonnelInfoEmployeeNumber;
    @BindView(R.id.tv_personnelInfo_realName)//真实姓名
    TextView tvPersonnelInfoRealName;
    @BindView(R.id.tv_personnelInfo_department)//部门
    TextView tvPersonnelInfoDepartment;
    @BindView(R.id.tv_personnelInfo_job)//职务
    TextView tvPersonnelInfoJob;

    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s024_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

//        GetItemHeight.getScreenHeight(context);
//        GetItemHeight.getScreenWidth(context);
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

    //扫描线程
    private scanThread scanThread;

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
//            rfidString="18000A00000EB967";
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

                try {
                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);

                    RFIDQueryVO rfidQueryVOParam = new RFIDQueryVO();
                    rfidQueryVOParam.setRfidCode(rfidString);

                    String jsonStr = objectToJson(rfidQueryVOParam);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> queryByRFID = iRequest.queryByRFID(body);
                    queryByRFID.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    RFIDQueryVO rfidQueryVO = jsonToObject(response.body(), RFIDQueryVO.class);

                                    if (rfidQueryVO != null) {
                                        Message message = new Message();
                                        message.obj = rfidQueryVO;
                                        //输入授权和扫描授权的handler
                                        quicQkueryHandler.sendMessage(message);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C01S024_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S024_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    //显示根据标签查询结果
    @SuppressLint("HandlerLeak")
    Handler quicQkueryHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RFIDQueryVO rfidQueryVO = (RFIDQueryVO) msg.obj;

            if (rfidQueryVO.getCuttingToolBind() != null) {
                // 添加材料刀数据
                addDataForCailiao(rfidQueryVO.getCuttingToolBind());
            }

            if (rfidQueryVO.getSynthesisCuttingToolBind() != null) {
                // 添加合成刀数据
                addDataForHechengdao(rfidQueryVO.getSynthesisCuttingToolBind());
            }

            if (rfidQueryVO.getEquipment() != null) {
                //添加设备数据
                addDataForEquipment(rfidQueryVO.getEquipment());
            }

            if (rfidQueryVO.getAuthCustomer() != null) {
                // 添加人员数据
                addDataForpersonnel(rfidQueryVO.getAuthCustomer());
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


        tvCailiaoInfoMaterialNumber.setText(cuttingToolBind.getCuttingTool().getBusinessCode());//物料号
        String bladeCode = cuttingToolBind.getBladeCode();
        if (bladeCode != null && bladeCode.indexOf("-") > 0) {
            tvCailiaoInfoBladeCode.setText(bladeCode.split("-")[1]);//刀身码
        }

        tvCailiaoInfoFinalExecution.setText(cuttingToolBind.getRfidContainer().getPrevOperation());//最后操作
        tvCailiaoInfoOperator.setText(cuttingToolBind.getRfidContainer().getOperatorName());//操作者

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            String date = df.format(cuttingToolBind.getRfidContainer().getOperatorTime());
            tvCailiaoInfoOperationTime.setText(date);//操作时间
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cuttingToolBind.getProcessingCount() != null) {
            tvCailiaoInfoCumulativeAmountOfProcessing.setText(cuttingToolBind.getProcessingCount() + "");//累计加工量
        }

        String grinding = "";
        if (GrindingEnum.inside.getKey().equals(cuttingToolBind.getCuttingTool().getGrinding())) {
            grinding = GrindingEnum.inside.getName();
        } else if (GrindingEnum.outside.getKey().equals(cuttingToolBind.getCuttingTool().getGrinding())) {
            grinding = GrindingEnum.outside.getName();
        } else if (GrindingEnum.outside_tuceng.getKey().equals(cuttingToolBind.getCuttingTool().getGrinding())) {
            grinding = GrindingEnum.outside_tuceng.getName();
        }
        tvCailiaoInfoGrindingMethod.setText(grinding);//修磨方式
    }

    /**
     * 添加合成刀数据
     */
    private void addDataForHechengdao(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        cailiaoInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.VISIBLE);


        tvHechengInfoSyntheticToolCode.setText(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());//合成刀
        tvHechengInfoFinalExecution.setText(synthesisCuttingToolBind.getRfidContainer().getPrevOperation());//最后操作
        tvHechengInfoOperator.setText(synthesisCuttingToolBind.getRfidContainer().getOperatorName());//操作者

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            String date = df.format(synthesisCuttingToolBind.getRfidContainer().getOperatorTime());
            tvHechengInfoOperationTime.setText(date);//操作时间
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (synthesisCuttingToolBind.getProcessingCount() != null) {
            tvHechengInfoCumulativeAmountOfProcessing.setText(synthesisCuttingToolBind.getProcessingCount() + "");//累计加工
        }
    }

    /**
     * 添加设备数据
     */
    private void addDataForEquipment(ProductLineEquipment productLineEquipment) {
        cailiaoInfo.setVisibility(View.GONE);
        hechengInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.VISIBLE);


        tvEquipmentInfoEquipmentName.setText(productLineEquipment.getName());//设备名称

        String type = "";
        if (EquipmentTypeEnum.Processing.getKey().equals(productLineEquipment.getType())) {
            type = EquipmentTypeEnum.Processing.getName();
        } else if (EquipmentTypeEnum.Grinding.getKey().equals(productLineEquipment.getType())) {
            type = EquipmentTypeEnum.Grinding.getName();
        }

        tvEquipmentInfoEquipmentType.setText(type);//设备类型
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
        tvPersonnelInfoJob.setText(authCustomer.getAuthPosition().getName());//职务
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
