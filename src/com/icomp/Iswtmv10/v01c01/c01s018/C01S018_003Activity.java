package com.icomp.Iswtmv10.v01c01.c01s018;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.dto.InFactoryDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s011.C01S011_002Activity;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 厂内修磨页面2
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


    @BindView(R.id.tvScan)
    TextView tvScan;

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;


    // 设备列表
    List<ProductLineEquipment> productLineEquipmentList = new ArrayList<>();
    //选中的设备列表项
    ProductLineEquipment productLineEquipment = null;

    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();
    // rfid对应GrindingVO
    Map<String, GrindingVO> rfidToGrindingVOMap = new HashMap<>();
    // 根据物料号对应刀身码
    private Map<String, String> businessCodeToBladeCodeMap = new HashMap<>();

    InFactoryDTO inFactoryDTO = new InFactoryDTO();


    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_003);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            rfidToMap = (Map<String, CuttingToolBind>) paramMap.get("rfidToMap");
            rfidToGrindingVOMap = (Map<String, GrindingVO>) paramMap.get("rfidToGrindingVOMap");
            businessCodeToBladeCodeMap = (Map<String, String>) paramMap.get("businessCodeToBladeCodeMap");
            inFactoryDTO = (InFactoryDTO) paramMap.get("inFactoryDTO");


            Set<String> rfids = rfidToGrindingVOMap.keySet();

            for (String rfid : rfids) {
                GrindingVO grindingVO = rfidToGrindingVOMap.get(rfid);
                String bladeCode = businessCodeToBladeCodeMap.get(grindingVO.getCuttingTool().getBusinessCode());
                addLayout(grindingVO.getCuttingTool().getBusinessCode(), bladeCode);
            }

            init();
        } catch (Exception e) {
            e.printStackTrace();
            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
        }
    }

    private void init() {
        try {
            loading.show();

            String jsonStr = "{}";
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> queryGrindingEquipments = iRequest.queryGrindingEquipments(body);

            queryGrindingEquipments.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            // 设备下拉列表项
                            productLineEquipmentList = jsonToObject(response.body(), List.class, ProductLineEquipment.class);
                            if (productLineEquipmentList == null || productLineEquipmentList.size() == 0) {
                                productLineEquipmentList = new ArrayList<>();
                                createToast(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT);
                            }
                        } else {
//                            createAlertDialog(C01S018_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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
//                    createAlertDialog(C01S018_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
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
                    createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                }
            });
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnNext, R.id.tvScan, R.id.ll_01})
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
                if (productLineEquipment == null) {
//                    createAlertDialog(C01S018_003Activity.this, "请扫描设备", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请扫描设备", Toast.LENGTH_SHORT);
                } else {
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
            case R.id.ll_01:
                showPopupWindow();
                break;
        }
    }

    /**
     * 添加布局
     */
    private void addLayout(String cailiao, String laserCode) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_changneixiumo2, null);

        TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);
//        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);

        tvCaiLiao.setText(cailiao);
        if (null!=laserCode&&!"".equals(laserCode)&&laserCode.split("-").length>=1){
            tvsingleProductCode.setText(laserCode.split("-")[1]);
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
            createToast(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT);
        }
    }


    //扫描线程
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要解开
//            rfidString = "18000A00000FB125";
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

                try {
                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    ProductLineEquipmentVO equipmentVO = new ProductLineEquipmentVO();
                    equipmentVO.setRfidContainerVO(rfidContainerVO);

                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);

                    String jsonStr = objectToJson(equipmentVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> queryGrindingEquipmentsByRFID = iRequest.queryGrindingEquipmentsByRFID(body);
                    queryGrindingEquipmentsByRFID.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    final ProductLineEquipment productLineEquipmentTemp = jsonToObject(response.body(), ProductLineEquipment.class);
                                    if (productLineEquipmentTemp != null) {
                                        boolean isPiPei = false;
                                        // 设备下拉列表项
                                        for (ProductLineEquipment equipment : productLineEquipmentList) {
                                            if (equipment.equals(equipment.getCode())) {
                                                tv01.setText(equipment.getName());
                                                productLineEquipment = equipment;
                                                isPiPei = true;
                                                break;
                                            }
                                        }
                                        if (!isPiPei) {
                                            createToast(getApplicationContext(), "设备列表中没有此设备", Toast.LENGTH_SHORT);
                                        }
                                    } else {
                                        createToast(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT);
                                    }
                                } else {
//                                    createAlertDialog(C01S018_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                    createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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
//                            createAlertDialog(C01S018_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
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
                            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        }
    }


    /**
     * 点击安上设备下拉框
     */
    private void showPopupWindow() {
        View view = LayoutInflater.from(C01S018_003Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        final PopupWindow popupWindow = new PopupWindow(view, ll01.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv01.setText(productLineEquipmentList.get(i).getName());
                productLineEquipment = productLineEquipmentList.get(i);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //设备的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return productLineEquipmentList.size();
        }

        @Override
        public Object getItem(int i) {
            return productLineEquipmentList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S018_003Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(productLineEquipmentList.get(i).getName());
            return view1;
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
    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();

            Map<String, String> headsMap = new HashMap<>();

            // 授权信息集合
            List<ImpowerRecorder> impowerRecorderList = new ArrayList<>();
            // 授权信息
            ImpowerRecorder impowerRecorder = new ImpowerRecorder();

            try {
                // 需要授权信息
                if (is_need_authorization && authCustomer != null) {
                    //设定用户访问信息
                    @SuppressLint("WrongConstant")
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                    String userInfoJson = sharedPreferences.getString("loginInfo", null);

                    AuthCustomer customer = jsonToObject(userInfoJson, AuthCustomer.class);

                    Set<String> rfids = rfidToMap.keySet();
                    for (String rfid : rfids) {
                        CuttingToolBind cuttingToolBind = rfidToMap.get(rfid);
                        impowerRecorder = new ImpowerRecorder();

                        // ------------ 授权信息 ------------
                        impowerRecorder.setToolCode(cuttingToolBind.getCuttingTool().getBusinessCode());// 合成刀编码
                        impowerRecorder.setRfidLasercode(authCustomer.getRfidContainer().getLaserCode());// rfid标签
                        impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                        impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                        impowerRecorder.setOperatorKey(OperationEnum.Cutting_tool_Inside.getKey().toString());//操作key

                        impowerRecorderList.add(impowerRecorder);
                    }
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                return;
            } catch (IOException e) {
                e.printStackTrace();
//                createAlertDialog(C01S018_003Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                createToast(getApplicationContext(), getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                return;
            }


            inFactoryDTO.setGrindingEquipment(productLineEquipment);

            String jsonStr = objectToJson(inFactoryDTO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> insideGrinding = iRequest.insideGrinding(body, headsMap);

            insideGrinding.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到成功详细页面
                            Intent intent = new Intent(C01S018_003Activity.this, C01S018_004Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
//                            createAlertDialog(C01S018_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
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
//                    createAlertDialog(C01S018_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
        }
    }


}