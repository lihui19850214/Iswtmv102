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
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s005.modul.TongDaoModul;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.wsdl.v01c00.c00s000.C00S000Wsdl;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRequest;
import com.icomp.wsdl.v01c00.c00s000.endpoint.UserRespons;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 厂内修磨页面3
 * Created by FanLL on 2017/7/10.
 */

public class C01S018_003Activity extends CommonActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llContainer)
    LinearLayout mLlContainer;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.tv_00)
    TextView tv00;
    @BindView(R.id.tvScan)
    TextView tvScan;


    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();
    // 根据物料号查询的数据
    private Map<String, CuttingTool> materialNumToMap = new HashMap<>();

    InsideVO insideVO = new InsideVO();

    private String equipmentCode;//设备code


    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_003);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();



        Map<String, Object> paramMap = PARAM_MAP.get(1);
        rfidToMap = (Map<String, CuttingToolBind>) paramMap.get("rfidToMap");
        materialNumToMap = (Map<String, CuttingTool>) paramMap.get("materialNumToMap");
        insideVO = (InsideVO) paramMap.get("insideVO");


        for (SharpenVO sharpenVO:insideVO.getSharpenVOS()) {
            addLayout(sharpenVO.getCuttingToolBusinessCode(), sharpenVO.getCuttingToolBladeCode(), sharpenVO.getCount().toString());
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
                Intent intent = new Intent(C01S018_003Activity.this, C01S018_002Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:
                if (equipmentCode == null || "".equals(equipmentCode)) {
                    createAlertDialog(C01S018_003Activity.this, "请扫描设备", Toast.LENGTH_LONG);
                } else {
                    authorizationWindow(1, new AuthorizationWindowCallBack() {
                        @Override
                        public void success(List<AuthCustomer> authorizationList) {
                            requestData(authorizationList);
                        }

                        @Override
                        public void fail() {

                        }
                    });
                }
                break;
        }
    }

    /**
     * 添加布局
     */
    private void addLayout(String cailiao, String laserCode, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_changneixiumo2, null);

        TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);

        tvCaiLiao.setText(cailiao);

        tvCaiLiao.setText(cailiao);
        if (laserCode == null || "".equals(laserCode)) {
            tvsingleProductCode.setText("-");
            tvNum.setText(num);
        } else {
            tvsingleProductCode.setText(laserCode);
            tvNum.setText("-");
        }

        mLlContainer.addView(mLinearLayout);
    }



    //扫描线程
    private scanThread scanThread;

    /**
     * 扫描
     */
    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnCancel.setClickable(false);
            btnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
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
            rfidString = singleScan();//TODO 生产环境需要解开
//            rfidString = "18000A00000D434A";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                btnCancel.setClickable(true);
                btnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScan.setClickable(true);
                        btnCancel.setClickable(true);
                        btnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                ProductLineEquipmentVO productLineEquipmentVO = new ProductLineEquipmentVO();
                productLineEquipmentVO.setRfidContainerVO(rfidContainerVO);

                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(productLineEquipmentVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> searchProductLineEquipment = iRequest.searchProductLineEquipment(body);
                searchProductLineEquipment.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                Gson gson = new Gson();
                                final ProductLineEquipment productLineEquipment = gson.fromJson(response.body(), ProductLineEquipment.class);
                                if (productLineEquipment != null) {
                                    equipmentCode = productLineEquipment.getCode();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv00.setText(productLineEquipment.getName());
                                        }
                                    });
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
                                        createAlertDialog(C01S018_003Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S018_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
            }
        }
    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        Intent intent2 = getIntent();
//        if (intent2 == null) {
//            return;
//        } else {
//            Bundle bundle = intent2.getExtras();
//            if (bundle == null) {
//                return;
//            }
//            boolean isClear = bundle.getBoolean("isClear", false);
//            if (isClear) {
//                mLlContainer.removeAllViews();
//            }
//        }
//    }


    //提交添加场内刃磨
    private void requestData(List<AuthCustomer> authorizationList) {
        loading.show();


        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> headsMap = new HashMap<>();
        // 授权信息
        ImpowerRecorder impowerRecorder = new ImpowerRecorder();

        try {
            // 需要授权信息
            if (is_need_authorization && authorizationList != null) {
                //设定用户访问信息
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                String userInfoJson = sharedPreferences.getString("loginInfo", null);

                AuthCustomer authCustomer = mapper.readValue(userInfoJson, AuthCustomer.class);

                // 授权信息
                impowerRecorder.setOperatorUserCode(authCustomer.getCode());//操作者code
                impowerRecorder.setOperatorUserName(authCustomer.getName());//操作者姓名
                impowerRecorder.setImpowerUser(authorizationList.get(0).getCode());//授权人code
                impowerRecorder.setImpowerUserName(authorizationList.get(0).getName());//授权人名称
                impowerRecorder.setOperatorKey(OperationEnum.Cutting_tool_Inside.getKey().toString());//操作key
                impowerRecorder.setOperatorValue(OperationEnum.Cutting_tool_Inside.getName());//操作者code
            }
            headsMap.put("impower", mapper.writeValueAsString(impowerRecorder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        IRequest iRequest = retrofit.create(IRequest.class);

        insideVO.setEquipmentCode(equipmentCode);
        insideVO.setAuthCustomer(authorizationList.get(0));

        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(insideVO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> addInsideFactory = iRequest.addInsideFactory(body, headsMap);

        addInsideFactory.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        //跳转到成功详细页面
                        Intent intent = new Intent(C01S018_003Activity.this, C01S018_004Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        createAlertDialog(C01S018_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    loading.dismiss();
                }
            }

            @Override
            public void _onFailure(Throwable t) {
                loading.dismiss();
                createAlertDialog(C01S018_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
    }


}