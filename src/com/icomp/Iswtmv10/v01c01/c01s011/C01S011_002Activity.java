package com.icomp.Iswtmv10.v01c01.c01s011;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 安上设备
 *
 */
public class C01S011_002Activity extends CommonActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.et_00)
    TextView et_00;
    @BindView(R.id.tvScan)
    TextView tvScan;

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;

    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;

    @BindView(R.id.btn_scan)
    TextView btnScan;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private scanThread scanThread;
    //扫描线程
    private scanThread2 scanThread2;

    SynthesisCuttingToolBind synthesisCuttingToolBing = new SynthesisCuttingToolBind();
    // 合成刀标签
    String synthesisCuttingToolBingRFID = "";

    List<ProductLineEquipment> equipmentEntityList = new ArrayList<>();// 设备列表
    Map<String,List<ProductLineAxle>> axleMap = new HashMap<>();// 设备对应轴号Map
    private List<ProductLineAxle> axisList = new ArrayList();// 轴号列表

    ProductLineEquipment productLineEquipment = null;//选中的设备列表项
    ProductLineAxle productLineAxle = null;//选中的轴号列表项

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.c01s011_002activity);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

//        showDialogAlert("合成刀具编码：sadfsdf\n安上设备比编号：爱上QZ01-S1");
    }

    /**
     * 扫描按钮点击
     */
    @OnClick({R.id.ll_01, R.id.ll_02, R.id.btn_scan, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btn_scan:
                if (et_00.getText() != null && !"".equals(et_00.getText().toString().trim())) {
                    scan2();
                } else {
                    createAlertDialog(C01S011_002Activity.this, "请先扫描合成刀具标签", Toast.LENGTH_LONG);
                }
                break;
            case R.id.ll_01:
                if (equipmentEntityList.size() > 0)
                    showPopupWindow();//设备
                break;
            case R.id.ll_02:
                if (axisList.size() > 0)
                    showPopupWindow2();//轴号
                break;
            default:
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
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
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000D6440";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
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
                        tvScan.setClickable(true);
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
                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);

                    QuerySynthesisCuttingToolVO querySynthesisCuttingToolVO = new QuerySynthesisCuttingToolVO();
                    querySynthesisCuttingToolVO.setRfidCode(rfidString);

                    String jsonStr = objectToJson(querySynthesisCuttingToolVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Map<String, String> headsMap = new HashMap<>();
                    headsMap.put("impower", OperationEnum.SynthesisCuttingTool_Install.getKey().toString());

                    Call<String> querySynthesisCuttingTool = iRequest.querySynthesisCuttingTool(body, headsMap);
                    querySynthesisCuttingTool.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                String inpower = response.headers().get("impower");

                                if (response.raw().code() == 200) {
                                    synthesisCuttingToolBing = jsonToObject(response.body(), SynthesisCuttingToolBind.class);
                                    synthesisCuttingToolBingRFID = rfidString;

                                    if (synthesisCuttingToolBing != null) {
                                        tvDesc.setText("请扫描要安上的设备标签");
                                        setTextViewHandler(inpower);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
//                                Message message = new Message();
//                                message.obj = inpower;
//                                setTextViewHandler.sendMessage(message);
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C01S011_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    private void setTextViewHandler(String inpower) {
        Map<String, String> inpowerMap = new HashMap<>();
        try {
            inpowerMap = jsonToObject(inpower, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 判断是否显示提示框
        if ("1".equals(inpowerMap.get("type"))) {
            // 是否需要授权 true为需要授权；false为不需要授权
            is_need_authorization = false;

            et_00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    et_00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
                }

                @Override
                public void cancel() {
                    // 不做任何操作
                }
            });
        } else if ("3".equals(inpowerMap.get("type"))) {
            is_need_authorization = false;
            stopProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    finish();
                }

                @Override
                public void cancel() {
                    // 实际上没有用
                    finish();
                }
            });
        }

    }

//    @SuppressLint("HandlerLeak")
//    Handler setTextViewHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            String inpower = msg.obj.toString();
//
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, String> inpowerMap = new HashMap<>();
//            try {
//                inpowerMap = mapper.readValue(inpower, Map.class);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // 判断是否显示提示框
//            if ("1".equals(inpowerMap.get("type"))) {
//                // 是否需要授权 true为需要授权；false为不需要授权
//                is_need_authorization = false;
//
//                if (!et_00_is_edit) {
//                    et_00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
//                }
//                editTextChangeEditStatus();
//            } else if ("2".equals(inpowerMap.get("type"))) {
//                is_need_authorization = true;
//                exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
//                    @Override
//                    public void confirm() {
//                        if (!et_00_is_edit) {
//                            et_00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
//                        }
//                        editTextChangeEditStatus();
//                    }
//
//                    @Override
//                    public void cancel() {
//                        // 不做任何操作
//                    }
//                });
//            } else if ("3".equals(inpowerMap.get("type"))) {
//                is_need_authorization = false;
//                stopProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
//                    @Override
//                    public void confirm() {
//                        finish();
//                    }
//
//                    @Override
//                    public void cancel() {
//                        // 实际上没有用
//                        finish();
//                    }
//                });
//            }
//
//        }
//    };

    //扫描方法
    private void scan2() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
            btnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread2 = new scanThread2();
            scanThread2.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class scanThread2 extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000FB125";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
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
                        tvScan.setClickable(true);
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


                    QueryEquipmentByRfidVO queryEquipmentByRfidVO = new QueryEquipmentByRfidVO();
                    queryEquipmentByRfidVO.setSynthesisCuttingToolCode(synthesisCuttingToolBing.getSynthesisCuttingTool().getCode());
                    queryEquipmentByRfidVO.setRfidCode(rfidString);


                    String jsonStr = objectToJson(queryEquipmentByRfidVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> queryEquipmentByRFID = iRequest.queryEquipmentByRFID(body);
                    queryEquipmentByRFID.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    QueryEquipmentByRfidVO queryEquipmentByRfidVO = jsonToObject(response.body(), QueryEquipmentByRfidVO.class);
                                    Set<String> equipmentCodeSet = new HashSet<>();

                                    if (queryEquipmentByRfidVO != null && queryEquipmentByRfidVO.getProductLines() != null && queryEquipmentByRfidVO.getProductLines().size() > 0) {
                                        for (ProductLine productLine : queryEquipmentByRfidVO.getProductLines()) {
                                            if (!equipmentCodeSet.contains(productLine.getEquipmentCode())) {
                                                equipmentCodeSet.add(productLine.getEquipmentCode());
                                                equipmentEntityList.add(productLine.getProductLineEquipment());
                                            }
                                        }

                                        for (ProductLineEquipment lineEquipment : equipmentEntityList) {
                                            List<ProductLineAxle> axleItemList = new ArrayList<>();

                                            Set<String> codeSet = new HashSet<>();
                                            for (ProductLine productLine : queryEquipmentByRfidVO.getProductLines()) {
                                                if (lineEquipment.getCode().equals(productLine.getEquipmentCode())) {
                                                    if (!codeSet.contains(productLine.getProductLineAxle().getCode())) {
                                                        codeSet.add(productLine.getProductLineAxle().getCode());
                                                        axleItemList.add(productLine.getProductLineAxle());
                                                    }
                                                }
                                            }
                                            axleMap.put(lineEquipment.getCode(), axleItemList);
                                        }

                                        // 默认选中第一个
                                        if (equipmentEntityList != null && equipmentEntityList.size() > 0) {
                                            tv01.setText(equipmentEntityList.get(0).getName());
                                            productLineEquipment = equipmentEntityList.get(0);

                                            //设置设备下拉列表第一条为空
                                            tv02.setText("");
                                            //清空流水线对应的设备列表
                                            axisList.clear();
                                            productLineAxle = null;

                                            axisList = axleMap.get(equipmentEntityList.get(0).getCode());

                                            if (axisList != null && axisList.size() > 0) {
                                                tv02.setText(axisList.get(0).getName());
                                                productLineAxle = axisList.get(0);
                                            }
                                        }
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C01S011_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
        C01S011_002Activity.MyAdapter myAdapter = new C01S011_002Activity.MyAdapter();
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

                //设置设备下拉列表第一条为空
                tv02.setText("");
                //清空流水线对应的设备列表
                axisList.clear();
                productLineAxle = null;

                axisList = axleMap.get(equipmentEntityList.get(i).getCode());
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
     * 点击轴号下拉框
     */
    private void showPopupWindow2() {
        View view = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter1 myAdapter1 = new MyAdapter1();
        listView.setAdapter(myAdapter1);
        final PopupWindow popupWindow = new PopupWindow(view, ll02.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                tv02.setText(axisList.get(i).getName());
                productLineAxle = axisList.get(i);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    //设备的Adapter
    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return axisList.size();
        }

        @Override
        public Object getItem(int i) {
            return axisList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(axisList.get(i).getName());
            //TODO 初始化轴下拉列表名字
            return view1;
        }

    }


    /**
     * 下一步
     */
    public void appNext(View view) {

        if (null != tv01 && !"".equals(tv01.getText().toString().trim()) && null != tv02 && !"".equals(tv02.getText().toString().trim())) {

            authorizationWindow(1, new AuthorizationWindowCallBack() {
                @Override
                public void success(List<AuthCustomer> authorizationList) {
                    requestData(authorizationList);
                }

                @Override
                public void fail() {

                }
            });
        } else {
            createAlertDialog(C01S011_002Activity.this, "请配置生产关联或绑定对应设备标签", Toast.LENGTH_LONG);
        }

    }


    private void requestData(List<AuthCustomer> authorizationList) {
        try {
            loading.show();

            Map<String, String> headsMap = new HashMap<>();

            // 授权信息集合
            List<ImpowerRecorder> impowerRecorderList = new ArrayList<>();
            // 授权信息
            ImpowerRecorder impowerRecorder = new ImpowerRecorder();

            try {
                // 需要授权信息
                if (is_need_authorization && authorizationList != null) {
                    //设定用户访问信息
                    @SuppressLint("WrongConstant")
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                    String userInfoJson = sharedPreferences.getString("loginInfo", null);

                    AuthCustomer authCustomer = jsonToObject(userInfoJson, AuthCustomer.class);

                    // ------------ 授权信息 ------------
                    impowerRecorder.setToolCode(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(synthesisCuttingToolBingRFID);// rfid标签
                    impowerRecorder.setOperatorUserCode(authCustomer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authorizationList.get(0).getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Install.getKey().toString());//操作key

//                impowerRecorder.setOperatorUserName(URLEncoder.encode(authCustomer.getName(),"utf-8"));//操作者姓名
//                impowerRecorder.setImpowerUserName(URLEncoder.encode(authorizationList.get(0).getName(),"utf-8"));//授权人名称
//                impowerRecorder.setOperatorValue(URLEncoder.encode(OperationEnum.SynthesisCuttingTool_Exchange.getName(),"utf-8"));//操作者code

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(C01S011_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            }


            BindEquipmentVO bindEquipmentVO = new BindEquipmentVO();

            //TODO 提交数据
            bindEquipmentVO.setAxle(productLineAxle);
            bindEquipmentVO.setEquipment(productLineEquipment);
            bindEquipmentVO.setSynthesisCuttingToolBind(synthesisCuttingToolBing);

            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = "";
            try {
                jsonStr = objectToJson(bindEquipmentVO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> bindEquipment = iRequest.bindEquipment(body, headsMap);
            bindEquipment.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(C01S011_002Activity.this, C01S011_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S011_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    /**
     * 返回
     */
    public void appReturn(View view) {
        finish();
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
//                //防止点击扫描后点击此按钮
//                stopScan();
                close();
                Intent intent = new Intent(C01S011_002Activity.this, C01S011_003Activity.class);
                startActivity(intent);
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
