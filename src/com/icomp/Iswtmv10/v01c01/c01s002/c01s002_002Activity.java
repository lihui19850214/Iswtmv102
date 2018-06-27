package com.icomp.Iswtmv10.v01c01.c01s002;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;

import com.apiclient.constants.EquipmentTypeEnum;
import com.apiclient.constants.GrindingEnum;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.FastQueryVO;
import com.apiclient.vo.RFIDQueryVO;
import com.apiclient.vo.RfidContainerVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s019.C01S019_002Activity;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.btnNext)
    Button mBtnNext;

    //调用接口
    private Retrofit retrofit;

    // rfid标签code集合
    List<String> rfidCodeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s002_002);
        ButterKnife.bind(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();

    }

    @OnClick({R.id.btnScan, R.id.btnCancel, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnScan:
                scan();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnNext:
                if (rfidCodeList == null || rfidCodeList.size() == 0) {
                    createAlertDialog(c01s002_002Activity.this, "请扫描标签", Toast.LENGTH_LONG);
                } else {
                    // TODO 授权信息是什么，未确定，暂时不开启授权
                    is_need_authorization = false;
                    authorizationWindow(new AuthorizationWindowCallBack() {
                        @Override
                        public void success(AuthCustomer authCustomer) {
                            requestData(authCustomer);
                        }

                        @Override
                        public void fail() {

                        }
                    });
                }
                break;
        }
    }

    //扫描数量
    public int scanNumber;
    //群扫存放rfidString的List
    public List<String> rfidList;

    //扫描线程
    private ScanThread scanThread;

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            mBtnScan.setClickable(false);
            mBtnCancel.setClickable(false);
            mBtnNext.setClickable(false);

            //显示扫描弹框的方法
            scanPopupWindow();

            //启动扫描线程
            scanThread = new ScanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }


    //扫描线程
    private class ScanThread extends Thread{
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000FB125";
            if ("close".equals(rfidString)) {
                isCanScan = true;
                mBtnScan.setClickable(true);
                mBtnCancel.setClickable(true);
                mBtnNext.setClickable(true);

                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isCanScan = true;
                        mBtnScan.setClickable(true);
                        mBtnCancel.setClickable(true);
                        mBtnNext.setClickable(true);

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });


                if (null == rfidList) {
                    rfidList = new ArrayList<>();
                }

                if (!rfidList.contains(rfidString)) {
                    try {
                        loading.show();

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
                                            String rfidCode = "";
                                            // 材料刀
                                            CuttingToolBind cuttingToolBind = rfidQueryVO.getCuttingToolBind();
                                            // 合成刀具
                                            SynthesisCuttingToolBind synthesisCuttingToolBind = rfidQueryVO.getSynthesisCuttingToolBind();
                                            // 设备
                                            ProductLineEquipment productLineEquipment = rfidQueryVO.getEquipment();
                                            // 人员
                                            AuthCustomer authCustomer = rfidQueryVO.getAuthCustomer();


                                            StringBuffer content = new StringBuffer();
                                            // 材料刀
                                            if (cuttingToolBind != null) {
                                                rfidCode = cuttingToolBind.getRfidContainer().getCode();
                                                // 添加材料刀数据
                                                content.append("物料号：").append(cuttingToolBind.getCuttingTool().getBusinessCode()).append("\n");
                                                String bladeCode = cuttingToolBind.getBladeCode();
                                                if (bladeCode != null && bladeCode.indexOf("-") > 0) {
                                                    bladeCode = bladeCode.split("-")[1];
                                                }
                                                content.append("刀身码：").append(bladeCode).append("\n");
                                                content.append("最后操作：").append(cuttingToolBind.getRfidContainer().getPrevOperation()).append("\n");
                                                content.append("操作者：").append(cuttingToolBind.getRfidContainer().getOperatorName()).append("\n");

                                                try {
                                                    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                                                    String date = df.format(cuttingToolBind.getRfidContainer().getOperatorTime());
                                                    content.append("操作时间：").append(date).append("\n");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    content.append("操作时间：").append("").append("\n");
                                                }

                                                if (cuttingToolBind.getProcessingCount() != null) {
                                                    content.append("累计加工：").append(cuttingToolBind.getProcessingCount()).append("\n");
                                                } else {
                                                    content.append("累计加工：").append("").append("\n");
                                                }

                                                String grinding = "";
                                                if (GrindingEnum.inside.getKey().equals(cuttingToolBind.getCuttingTool().getGrinding())) {
                                                    grinding = GrindingEnum.inside.getName();
                                                } else if (GrindingEnum.outside.getKey().equals(cuttingToolBind.getCuttingTool().getGrinding())) {
                                                    grinding = GrindingEnum.outside.getName();
                                                } else if (GrindingEnum.outside_tuceng.getKey().equals(cuttingToolBind.getCuttingTool().getGrinding())) {
                                                    grinding = GrindingEnum.outside_tuceng.getName();
                                                }
                                                content.append("修磨方式：").append(grinding).append("\n");
                                            }
                                            // 合成刀
                                            else if (synthesisCuttingToolBind != null) {
                                                rfidCode = synthesisCuttingToolBind.getRfidContainer().getCode();
                                                // 添加合成刀数据
                                                content.append("合成刀：").append(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode()).append("\n");
                                                content.append("最后操作：").append(synthesisCuttingToolBind.getRfidContainer().getPrevOperation()).append("\n");
                                                content.append("操作者：").append(synthesisCuttingToolBind.getRfidContainer().getOperatorName()).append("\n");

                                                try {
                                                    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                                                    String date = df.format(synthesisCuttingToolBind.getRfidContainer().getOperatorTime());
                                                    content.append("操作时间：").append(date).append("\n");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                if (synthesisCuttingToolBind.getProcessingCount() != null) {
                                                    content.append("累计加工：").append(synthesisCuttingToolBind.getProcessingCount());
                                                }
                                            }
                                            // 设备
                                            else if (productLineEquipment != null) {
                                                rfidCode = productLineEquipment.getRfidContainer().getCode();

                                                String type = "";
                                                if (EquipmentTypeEnum.Processing.getKey().equals(productLineEquipment.getType())) {
                                                    type = EquipmentTypeEnum.Processing.getName();
                                                } else if (EquipmentTypeEnum.Grinding.getKey().equals(productLineEquipment.getType())) {
                                                    type = EquipmentTypeEnum.Grinding.getName();
                                                }

                                                //添加设备数据
                                                content.append("设备名称：").append(productLineEquipment.getName()).append("\n");
                                                content.append("设备类型：").append(type);
                                            }
                                            // 员工
                                            else if (authCustomer != null) {
                                                rfidCode = authCustomer.getRfidContainer().getCode();
                                                // 添加人员数据
                                                content.append("员工号：").append(authCustomer.getEmployeeCode()).append("\n");
                                                content.append("真实姓名：").append(authCustomer.getName()).append("\n");
                                                content.append("部门：").append(authCustomer.getAuthDepartment().getName()).append("\n");
                                                content.append("职务：").append(authCustomer.getAuthPosition().getName());
                                            }

                                            // 查询出信息
                                            if (!"".equals(content.toString())) {
                                                showDialogAlert(content.toString(), rfidString, rfidCode);
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                        }
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
    }

    /**
     * 显示数据提示dialog
     */
    private void showDialogAlert(String content, final String rfid, final String rfidCode) {
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
                rfidCodeList.add(rfidCode);

                //扫描数量 scanNumber+1
                scanNumber = scanNumber + 1;

                // 设置清空数量
                Message message = new Message();
                message.obj = rfidString;
                setValueHandler.sendMessage(message);

                dialog.cancel();
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
        dialog.getWindow().setLayout((int) (screenWidth * 1), (int) (screenHeight * 0.8));
    }

    //扫描Handler
    @SuppressLint("HandlerLeak")
    Handler setValueHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //显示当前清空数量
            mTvNum.setText("清空数量：" + scanNumber);
        }
    };

    //提交清空 RFID 标签数据
    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();
            // TODO 授权信息是什么，未确定，暂时不开启授权
//            Map<String, String> headsMap = new HashMap<>();
//
//            // 授权信息集合
//            List<ImpowerRecorder> impowerRecorderList = new ArrayList<>();
//            // 授权信息
//            ImpowerRecorder impowerRecorder = new ImpowerRecorder();
//
//            try {
//                // 需要授权信息
//                if (is_need_authorization && authCustomer != null) {
//                    //设定用户访问信息
//                    @SuppressLint("WrongConstant")
//                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
//                    String userInfoJson = sharedPreferences.getString("loginInfo", null);
//
//                    AuthCustomer customer = jsonToObject(userInfoJson, AuthCustomer.class);
//
//                    Set<String> rfids = rfidToMap.keySet();
//                    for (String rfid : rfids) {
//                        CuttingToolBind cuttingToolBind = rfidToMap.get(rfid);
//                        impowerRecorder = new ImpowerRecorder();
//
//                        // ------------ 授权信息 ------------
//                        impowerRecorder.setToolCode(cuttingToolBind.getCuttingTool().getBusinessCode());// 合成刀编码
//                        impowerRecorder.setRfidLasercode(authCustomer.getRfidContainer().getLaserCode());// rfid标签
//                        impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
//                        impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
//                        impowerRecorder.setOperatorKey(OperationEnum.Cutting_tool_Inside.getKey().toString());//操作key
//
//                        impowerRecorderList.add(impowerRecorder);
//                    }
//                }
//                headsMap.put("impower", objectToJson(impowerRecorderList));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
//                return;
//            } catch (IOException e) {
//                e.printStackTrace();
//                createAlertDialog(c01s002_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
//                return;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
//                return;
//            }

            List<RfidContainerVO> rfidContainerVOList = new ArrayList<>();

            for (String rfidCode : rfidCodeList) {
                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setCode(rfidCode);

                rfidContainerVOList.add(rfidContainerVO);
            }


            RFIDQueryVO rFIDQueryVO = new RFIDQueryVO();
            rFIDQueryVO.setRfidContainerVOList(rfidContainerVOList);

            String jsonStr = objectToJson(rFIDQueryVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
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
