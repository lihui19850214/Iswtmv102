package com.icomp.Iswtmv10.v01c01.c01s011;

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
import com.apiclient.dto.LineDTO;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.ImpowerRecorder;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.vo.ProductLineEquipmentVO;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SynthesisCuttingToolBindVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
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
 * 安上设备页面2
 */
public class C01S011_002Activity extends CommonActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.et_00)
    TextView et00;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.btn_scan)
    TextView btnScan;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_next)
    Button btnNext;

    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private ScanThread scanThread;


    // 设备列表
    List<ProductLineEquipment> equipmentEntityList = new ArrayList<>();
    //选中的设备列表项
    ProductLineEquipment productLineEquipment = null;

    // 合成刀真实数据
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();
    // 合成刀标签
    String synthesisCuttingToolConfigRFID = null;
    // 刀身码
    String bladeCode = null;

    String rfidCode = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s011_002activity);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        equipmentEntityList = (List<ProductLineEquipment>) paramMap.get("productLineEquipmentList");
        synthesisCuttingToolConfigRFID = (String) paramMap.get("synthesisCuttingToolConfigRFID");
        synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");
        bladeCode = (String) paramMap.get("bladeCode");
        rfidCode = (String) paramMap.get("rfidCode");

        if (equipmentEntityList != null && equipmentEntityList.size() > 0) {
            tv01.setText(equipmentEntityList.get(0).getName());
            productLineEquipment = equipmentEntityList.get(0);
        }

        et00.setText(synthesisCuttingToolBind.getSynthesisCode());
    }

    /**
     * 扫描按钮点击
     */
    @OnClick({R.id.ll_01, R.id.btn_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                scan();
                break;
            case R.id.ll_01:
                if (equipmentEntityList.size() > 0)
                    showPopupWindow();//设备
                break;
            default:
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setClickable(false);
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
            btnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread = new ScanThread();
            scanThread.start();
        } else {
            createToast(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT);
        }
    }

    //扫描线程
    private class ScanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000FB125";
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                btnScan.setClickable(true);
                btnReturn.setClickable(true);
                btnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        btnScan.setClickable(true);
                        btnReturn.setClickable(true);
                        btnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                try {
                    // 查询设备和轴号
                    IRequest iRequest = retrofit.create(IRequest.class);

                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    ProductLineEquipmentVO productLineEquipmentVO = new ProductLineEquipmentVO();
                    productLineEquipmentVO.setRfidContainerVO(rfidContainerVO);

                    String jsonStr = objectToJson(productLineEquipmentVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> searchProductLineEquipment = iRequest.searchProductLineEquipment(body);
                    searchProductLineEquipment.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    ProductLineEquipment equipment = jsonToObject(response.body(), ProductLineEquipment.class);

                                    if (equipment != null) {
                                        // 默认选中第一个
                                        if (equipmentEntityList != null && equipmentEntityList.size() > 0) {
                                            for (int i = 0; i < equipmentEntityList.size(); i++) {
                                                ProductLineEquipment pe = equipmentEntityList.get(i);
                                                if (equipment.getCode().equals(pe.getCode())) {
                                                    tv01.setText(pe.getName());
                                                    productLineEquipment = pe;
                                                }
                                            }
                                        }
                                    } else {
                                        createToast(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT);
                                    }
                                } else {
//                                    createAlertDialog(C01S011_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                    createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
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
//                            createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

//    //重写键盘上扫描按键的方法
//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if(isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//        //扫描方法
//        scan();
//    }

    /**
     * 点击安上设备下拉框
     */
    private void showPopupWindow() {
        View view = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv01.setText(equipmentEntityList.get(i).getName());
                productLineEquipment = equipmentEntityList.get(i);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //设备的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return equipmentEntityList.size();
        }

        @Override
        public Object getItem(int i) {
            return equipmentEntityList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(equipmentEntityList.get(i).getName());
            return view1;
        }
    }


    /**
     * 下一步
     */
    public void appNext(View view) {

        if (null != tv01 && !"".equals(tv01.getText().toString().trim())) {

            authorizationWindow(new AuthorizationWindowCallBack() {
                @Override
                public void success(AuthCustomer authCustomer) {
                    requestData(authCustomer);
                }

                @Override
                public void fail() {

                }
            });
        } else {
//            createAlertDialog(C01S011_002Activity.this, "请配置生产关联或绑定对应设备标签", Toast.LENGTH_LONG);
            createToast(getApplicationContext(), "请配置生产关联或绑定对应设备标签", Toast.LENGTH_SHORT);
        }

    }


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

                    // ------------ 授权信息 ------------
                    impowerRecorder.setToolCode(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(rfidCode);// rfid标签
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Install.getKey().toString());//操作key

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                return;
            } catch (IOException e) {
                e.printStackTrace();
//                createAlertDialog(C01S011_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                createToast(getApplicationContext(), getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                return;
            }


            RfidContainerVO RfidContainerVO = new RfidContainerVO();
            if (synthesisCuttingToolConfigRFID != null && !"".equals(synthesisCuttingToolConfigRFID)) {
                RfidContainerVO.setLaserCode(synthesisCuttingToolConfigRFID);
            }
            if (bladeCode != null && !"".equals(bladeCode)) {
                RfidContainerVO.setSynthesisBladeCode(bladeCode);
            }

            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
            synthesisCuttingToolBindVO.setRfidContainerVO(RfidContainerVO);

            ProductLineEquipmentVO productLineEquipmentVO = new ProductLineEquipmentVO();
            productLineEquipmentVO.setCode(productLineEquipment.getCode());

            LineDTO lineDTO = new LineDTO();
            lineDTO.setSynthesisCuttingToolBindVO(synthesisCuttingToolBindVO);
            lineDTO.setEquipmentVO(productLineEquipmentVO);


            String jsonStr = objectToJson(lineDTO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> install = iRequest.install(body, headsMap);

            install.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(C01S011_002Activity.this, C01S011_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
//                            createAlertDialog(C01S011_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
//                    createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    /**
     * 返回
     */
    public void appReturn(View view) {
        finish();
    }


}
