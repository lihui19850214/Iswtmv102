package com.icomp.Iswtmv10.v01c01.c01s012;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
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
import com.apiclient.vo.ChangeRFIDVO;
import com.apiclient.vo.CuttingToolBindVO;
import com.apiclient.vo.RFIDQueryVO;
import com.apiclient.vo.RfidContainerVO;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s019.C01S019_000Activity;
import com.icomp.Iswtmv10.v01c01.c01s019.C01S019_001Activity;
import com.icomp.Iswtmv10.v01c01.c01s024.C01S024_001Activity;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 标签置换页面1
 */
public class C01S012_001Activity extends CommonActivity {

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


    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnScan)
    Button btnScan;

    //调用接口
    private Retrofit retrofit;

    // 已绑定的标签
    String oldLaserCode = "";
    // 空标签
    String newLaserCode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s012_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

    }

    @OnClick({R.id.btnReturn, R.id.btnScan, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                //关闭当前页
                finish();
                break;
            case R.id.btnScan:
                // 扫描已绑定标签
                scan2();
                break;
            case R.id.tvScan:
                // 扫描空标签
                scan();
                break;
            default:
        }
    }

    /**
     * ------------------扫描已绑定标签开始------------------
     */
    //扫描线程
    private ScanThread2 scanThread2;

    //点击扫描按钮的方法
    private void scan2() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            //点击扫描按钮以后，设置扫描按钮不可用
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
            tvScan.setClickable(false);
            //隐藏布局
            hideLayout();
            //显示扫描弹框的方法
            scanPopupWindow();
            //开启扫描线程
            scanThread2 = new ScanThread2();
            scanThread2.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    public class ScanThread2 extends Thread {
        @Override
        public void run() {
            super.run();
            //调用单扫方法
            rfidString = singleScan();
//            rfidString="18000A00000E5206";
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                btnReturn.setClickable(true);
                tvScan.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        btnReturn.setClickable(true);
                        tvScan.setClickable(true);
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
                                        Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    createAlertDialog(C01S012_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                            } finally {
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
                            }
                        }

                        @Override
                        public void _onFailure(Throwable t) {
                            if (null != loading && loading.isShowing()) {
                                loading.dismiss();
                            }
                            createAlertDialog(C01S012_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
                oldLaserCode = rfidQueryVO.getCuttingToolBind().getRfidContainer().getLaserCode();
            }

            if (rfidQueryVO.getSynthesisCuttingToolBind() != null) {
                // 添加合成刀数据
                addDataForHechengdao(rfidQueryVO.getSynthesisCuttingToolBind());
                oldLaserCode = rfidQueryVO.getSynthesisCuttingToolBind().getRfidContainer().getLaserCode();
            }

            if (rfidQueryVO.getEquipment() != null) {
                //添加设备数据
                addDataForEquipment(rfidQueryVO.getEquipment());
                oldLaserCode = rfidQueryVO.getEquipment().getRfidContainer().getLaserCode();
            }

            if (rfidQueryVO.getAuthCustomer() != null) {
                // 添加人员数据
                addDataForpersonnel(rfidQueryVO.getAuthCustomer());
                oldLaserCode = rfidQueryVO.getAuthCustomer().getRfidContainer().getLaserCode();
            }
        }
    };

    /**
     * 隐藏布局文件
     */
    private void hideLayout() {
        hechengInfo.setVisibility(View.GONE);
        equipmentInfo.setVisibility(View.GONE);
        personnelInfo.setVisibility(View.GONE);
        cailiaoInfo.setVisibility(View.GONE);

        tvScan.setVisibility(View.GONE);
    }

    /**
     * 添加材料刀数据
     */
    private void addDataForCailiao(CuttingToolBind cuttingToolBind) {
        cailiaoInfo.setVisibility(View.VISIBLE);
        tvScan.setVisibility(View.VISIBLE);


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
        hechengInfo.setVisibility(View.VISIBLE);
        tvScan.setVisibility(View.VISIBLE);


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
        equipmentInfo.setVisibility(View.VISIBLE);
        tvScan.setVisibility(View.VISIBLE);


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
        personnelInfo.setVisibility(View.VISIBLE);
        tvScan.setVisibility(View.VISIBLE);


        tvPersonnelInfoEmployeeNumber.setText(authCustomer.getEmployeeCode());//员工号
        tvPersonnelInfoRealName.setText(authCustomer.getName());//真实姓名
        tvPersonnelInfoDepartment.setText(authCustomer.getAuthDepartment().getName());//部门
        tvPersonnelInfoJob.setText(authCustomer.getAuthPosition().getName());//职务
    }
    /**
     * ------------------扫描已绑定标签结束------------------
     */



    /**
     * ----------------------扫描空标签开始----------------------
     **/
    //扫描线程
    private ScanThread scanThread;

    /**
     * 扫描
     */
    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnReturn.setClickable(false);
            btnScan.setClickable(false);
            tvScan.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread = new ScanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class ScanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();
//            rfidString = "RFID1";
            if ("close".equals(rfidString)) {
                btnReturn.setClickable(true);
                btnScan.setClickable(true);
                tvScan.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnReturn.setClickable(true);
                        btnScan.setClickable(true);
                        tvScan.setClickable(true);
                        isCanScan = true;
                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });

                try {
                    newLaserCode = rfidString;
                    requestData();
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
    /**
     * ----------------------扫描空标签结束----------------------
     **/

    //置换按钮处理
    public void requestData() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.show();
                }
            });

            IRequest iRequest = retrofit.create(IRequest.class);

            ChangeRFIDVO changeRFIDVO = new ChangeRFIDVO();
            changeRFIDVO.setLaserCode(oldLaserCode);
            changeRFIDVO.setNewLaserCode(newLaserCode);

            String jsonStr = objectToJson(changeRFIDVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> changeRFIDForToll = iRequest.changeRFIDForToll(body);

            changeRFIDForToll.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到成功详细页面
                            Intent intent = new Intent(C01S012_001Activity.this, C01S012_002Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S012_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S012_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    /**
     * 显示数据提示dialog
     */
    private void showDialogAlert(String content) {
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
                scan();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
    }

}
