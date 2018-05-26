package com.icomp.Iswtmv10.v01c01.c01s013;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C01S003_004Adapter;
import com.icomp.common.utils.SysApplication;
import com.icomp.wsdl.v01c01.c01s013.C01S013Wsdl;
import com.icomp.wsdl.v01c01.c01s013.endpoint.C01S013Request;
import com.icomp.wsdl.v01c01.c01s013.endpoint.C01S013Respons;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 卸下专机
 */

public class C01S013_003Activity extends CommonActivity {

    //加工量
    @BindView(R.id.et_01)
    EditText et_01;
    //卸下数量
    @BindView(R.id.et_02)
    EditText et_02;
    //设备名称
    @BindView(R.id.tv_01)
    TextView tv_01;
    //轴
    @BindView(R.id.tv_02)
    TextView tv_02;
    //合成刀具
    @BindView(R.id.tv_03)
    TextView tv_03;
    //卸下原因
    @BindView(R.id.tv_04)
    TextView tv_04;
    //零部件ID
    @BindView(R.id.tv_05)
    TextView tv_05;
    //零部件名称
    @BindView(R.id.tv_06)
    TextView tv_06;
    //轴
    @BindView(R.id.ll_01)
    LinearLayout ll_01;
    //合成刀具
    @BindView(R.id.ll_02)
    LinearLayout ll_02;
    //卸下原因
    @BindView(R.id.ll_03)
    LinearLayout ll_03;
    //零部件
    @BindView(R.id.ll_04) LinearLayout ll_04;

    private Button btn_scan;
    private boolean type = true;//线程停止条件
    String rfidString;
    private boolean isCanScan = true; //是否可以按键扫描

    private C01S013_Params params;
    private String customerID;//customerID
    private String handsetid;//手持机ID
    private List<String> alxe = new ArrayList<>();//轴列表
    private List<String> synthesisParameters = new ArrayList<>();//合成刀具列表
    private List<Integer> processingCapacity = new ArrayList<>();//加工量列表
    private List<String> removeReasonList = new ArrayList<>();//保存所有卸下原因
    private List<String> lingBuJian = new ArrayList<>();//零部件ID列表
    private List<String> lingBuJianName = new ArrayList<>();//零部件名称列表
    private PopupWindow mPopWindow;//卸下原因下拉列表框PopupWindow

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s013_003activity);
        ButterKnife.bind(this);
        SysApplication.getInstance().addActivity(this);

        //btn_scan = (Button) findViewById(R.id.btn_scan);

//        params = (C01S013_Params) getIntent().getSerializableExtra(PARAM);//上页面取得数据
//        // 设定访问用户信息
//        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
//        customerID = sharedPreferences.getString("customerID", "");
//        handsetid = sharedPreferences.getString("handsetid", "");
//        tv_01.setText(params.getEquipmentName());//设备名称
////        tv_02.setText(params.getSynthesisParametersCodeList().get(0).getAxisID());
//        //将光标设置在最后
//        et_01.setSelection(et_01.getText().length());
//        et_02.setSelection(et_02.getText().length());
//        //1116 修改 遍历所有的轴的方法
//        foreach();
//        //1116 修改 存储所有卸下原因
//        for (int i = 0; i < getResources().getStringArray(R.array.RemoveReason).length; i++) {
//            removeReasonList.add(getResources().getStringArray(R.array.RemoveReason)[i]);
//        }
//        tv_04.setText(removeReasonList.get(0));
    }

    //遍历所有的轴的方法
    private void foreach() {
        alxe.clear();
        for (int i = 0; i < params.getSynthesisParametersCodeList().size(); i++) {
            if (!alxe.contains(params.getSynthesisParametersCodeList().get(i).getAxisID())) {
                alxe.add(params.getSynthesisParametersCodeList().get(i).getAxisID());
            }
        }
    }

    /**
     * 扫描
     */
    public void scan(View view) {
        isCanScan = false;
        showWindow(view, R.layout.c00s000_010activity);
    }
    /**
     * 监听键盘扫描按键
     *
     * @return
     */
//    protected boolean keycodeScanf() {
//        if (popupWindow == null || !popupWindow.isShowing()) {
//            showWindow(null, R.layout.c00s000_010activity);
//        }
//        return true;
//    }

    @Override
    protected void btnScan() {
        super.btnScan();
        if (isCanScan) {
            isCanScan = false;
        }else{
            return;
        }
        if (popupWindow == null || !popupWindow.isShowing()) {
            showWindow(null, R.layout.c00s000_010activity);
        }
    }

    /**
     * 返回
     */
    public void cancel(View view) {
        finish();
        type = false;
    }

    public void btnConfirm(View view) {
        showDialogAlert("加工零部件：齿轮-001\n加工数量：1200\n卸下数量：5");
    }

    /**
     * 打开扫描Rfid页面
     *
     * @param parent
     * @param pageID void
     * @title showWindow
     */
    public void showWindow(View parent, int pageID) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View vPopupWindow = layoutInflater.inflate(pageID, null);
        popupWindow = new PopupWindow(vPopupWindow, 300,240);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(vPopupWindow, Gravity.CENTER_VERTICAL, 0, 0);
        btn_scan.setClickable(false);
        thread = new VisitJniThread();
        thread.start();

    }

    private VisitJniThread thread;

    private class VisitJniThread extends Thread {
        @Override
        public void run() {
            initRFID();// 打开Rfid读头模块
            rfidString = null;
            Date date = new Date();
            do {
                rfidString = singleScan();
                if (new Date().getTime() - date.getTime() >= SCANF_TIME) {
                    rfidString = "close";
                }
            } while (rfidString == null && !"close".equals(rfidString) && type);
            close();
            if (null != rfidString) {
                Message message = new Message();
                message.obj = rfidString;
                scanfmhandler.sendMessage(message);
            }
        }

    }

    @SuppressLint("HandlerLeak")
    Handler scanfmhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            btn_scan.setClickable(true);
            String returnString = msg.obj.toString();
            Log.d("message", "RFID:" + returnString);
            if ("close".equals(returnString)) {
                createAlertDialog(C01S013_003Activity.this, getString(R.string.C01S001001_1), Toast.LENGTH_LONG);
                isCanScan = true;
            } else {
                loading.show();
                outEquThread = new SynthesisToolInfoOutEqu();
                outEquThread.start();
            }
            popupWindow.dismiss();
        }
    };

    /**
     * 线程
     * 取得设备下（砂轮）合成刀具信息
     */
    private SynthesisToolInfoOutEqu outEquThread;

    public class SynthesisToolInfoOutEqu extends Thread {
        @Override
        public void run() {
            C01S013Respons response = SynthesisToolInfoOutEqu(rfidString);
            if (null != response) {
                Message message = new Message();
                message.obj = response;
                SynthesisToolhandler.sendMessage(message);
            } else {
                Message message = new Message();
                internetErrorHandler.sendMessage(message);
            }

        }
    }

    Handler SynthesisToolhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            C01S013Respons respons = (C01S013Respons) msg.obj;

            if ("0".equals(respons.getStateCode())) {
                //**********传送给下一页面数据*********************
                C01S013_Params params = new C01S013_Params();
                params.setProcessID(respons.getProcessID());//对应工序ID
                params.setProcessCode(respons.getProcessCode());//对应工序编码
                params.setAssemblyLineName(respons.getAssemblyLineName());//流水线名称
                params.setAssemblyLineID(respons.getAssemblyLineID());//流水线ID
                params.setEquipmentID(respons.getEquipmentID());//设备ID
                params.setRfidContainerId(respons.getRfidContainerId());//载体id
                params.setSynthesisParametersCodeList(respons.getSynthesisParametersCodeList());//合成刀具编码列表

//                params.setSynthesisParametersCodeList(respons.getSynthesisParametersCodeList());
                //**********************结束***********************
                /*1116 修改*/
                params.setEquipmentName(respons.getEquipmentName());//设备名称

                Intent intent = new Intent(C01S013_003Activity.this, C01S013_004Activity.class);//卸下砂轮页
                intent.putExtra(PARAM, params);
                startActivity(intent);
            } else {
                createAlertDialog(C01S013_003Activity.this, respons.getStateMsg(), Toast.LENGTH_LONG);
            }
            isCanScan = true;
            loading.dismiss();
        }
    };

    /**
     * 方法
     * 取得设备下（砂轮）合成刀具信息
     */
    public C01S013Respons SynthesisToolInfoOutEqu(String rfid) {
        C01S013Wsdl wsdl = new C01S013Wsdl();
        C01S013Request request = new C01S013Request();
        request.setRfidCode(rfid);//RFID
        request.setQueryType("4");//标签类型（0库位标签，1单品刀，2合成刀具，3员工卡，4设备，5容器，6备刀库）
        C01S013Respons respons = new C01S013Respons();
        try {
            respons = wsdl.getSynthesisToolInfoOutWheel(request);//取得设备下（砂轮）合成刀具信息
            if (null == respons) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respons;
    }

    /**
     * 处理网络异常的handler
     */
    @SuppressLint("HandlerLeak")
    Handler internetErrorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isCanScan = true;
            createAlertDialog(null, getString(R.string.netConnection), Toast.LENGTH_LONG);
            loading.dismiss();
        }
    };


    @Nullable
    @OnClick({R.id.tv_03, R.id.tv_04, R.id.et_01, R.id.et_02, R.id.ll_01, R.id.ll_02, R.id.ll_03, R.id.ll_04})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //根据轴和合成刀具去重加工量
            case R.id.tv_03:
                if ("".equals(tv_02.getText().toString().trim())) {
                    createAlertDialog(C01S013_003Activity.this, "请先选择轴", Toast.LENGTH_LONG);
                } else {
                    //显示合成刀具
                    showPopupWindowSynthesis();
                }
                break;
            //根据轴去重合成刀具
            case R.id.ll_01:
                //根据轴遍历合成刀具
                synthesisParameters.clear();
                showPopupWindow();
                break;
            //根据轴和合成刀具去重加工量
            case R.id.ll_02:
                if ("".equals(tv_02.getText().toString().trim())) {
                    createAlertDialog(C01S013_003Activity.this, "请先选择轴", Toast.LENGTH_LONG);
                } else {
                    //显示合成刀具
                    showPopupWindowSynthesis();
                }
                break;
//            case R.id.im_01:
//                if ("".equals(tv_03.getText().toString().trim())) {
//                    createAlertDialog(C01S013_004Activity.this, "请先选择合成刀具", Toast.LENGTH_LONG);
//                } else {
//                    for (int i= 0; i < processingCapacity.size(); i++) {
//                        if (!processingCapacity1.contains(processingCapacity.get(i) )) {
//                            processingCapacity1.add(processingCapacity.get(i));
//                        }
//                    }
//                    //显示加工量
//                    showPopupWindowProcessing();
//                }
//                break;
            case R.id.ll_04:
                if ("".equals(tv_03.getText().toString().trim())) {
                    createAlertDialog(C01S013_003Activity.this, "请先选择合成刀具", Toast.LENGTH_LONG);
                } else {
                    //显示零部件
                    showPopupWindowLing();
                }
                break;
            //卸下原因
            case R.id.ll_03:
                if (removeReasonList.size() > 0) {
                    int tvWidht = ll_03.getWidth();
                    View contentView = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.item_c01s020_001, null);
                    mPopWindow = new PopupWindow(contentView, tvWidht, 200, true);
                    mPopWindow.setBackgroundDrawable(new BitmapDrawable());
                    mPopWindow.showAsDropDown(ll_03);//显示对话框
                    ListView lv_01 = (ListView) contentView.findViewById(R.id.lv_01);
                    C01S003_004Adapter adapter = new C01S003_004Adapter(C01S013_003Activity.this, removeReasonList);
                    lv_01.setAdapter(adapter);
                    lv_01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            tv_04.setText(removeReasonList.get(i));
                            mPopWindow.dismiss();
                        }
                    });
                }
                break;
            default:
        }
    }

    //显示零部件
    private void showPopupWindowLing() {
        View view = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        final ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter3 myAdapter = new MyAdapter3();
        listView.setAdapter(myAdapter);
        final PopupWindow popupWindow = new PopupWindow(view, ll_04.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                tv_06.setText(lingBuJianName.get(i));
                tv_05.setText(lingBuJian.get(i));
                //显示加工量
//                et_01.setText(String.valueOf(processingCapacity.get(i)));
                // TODO: 2018/1/15 修改显示加工量2018年1月15日
                for (int j = 0; i < params.getSynthesisParametersCodeList().size(); j++) {
                    if (params.getSynthesisParametersCodeList().get(j).getAxisID().equals(tv_02.getText().toString().trim())
                            && params.getSynthesisParametersCodeList().get(j).getSynthesisParametersCode().equals(tv_03.getText().toString().trim())
                            && params.getSynthesisParametersCodeList().get(j).getPartsName().equals(tv_06.getText().toString().trim())) {
                        et_01.setText(String.valueOf(params.getSynthesisParametersCodeList().get(j).getToolDurable()));
                        break;
                    }
                }
                //将光标设置在最后
                et_01.setSelection(et_01.getText().length());
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll_04);
    }
    class MyAdapter3 extends BaseAdapter {

        @Override
        public int getCount() {
            return lingBuJianName.size();
        }

        @Override
        public Object getItem(int i) {
            return lingBuJianName.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(lingBuJianName.get(i));
            return view1;
        }

    }
//
//    //显示加工量
//    private void showPopupWindowProcessing() {
//        View view = LayoutInflater.from(C01S013_004Activity.this).inflate(R.layout.spinner_c03s004_001, null);
//        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
//        MyAdapter2 myAdapter = new MyAdapter2();
//        listView.setAdapter(myAdapter);
//        final PopupWindow popupWindow = new PopupWindow(view, ll_03.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setBackgroundDrawable(new PaintDrawable());
//        popupWindow.setFocusable(true);
//        popupWindow.setTouchable(true);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                et_01.setText(processingCapacity1.get(i).toString());
//                et_01.setSelection(processingCapacity1.get(i).toString().length());
//                popupWindow.dismiss();
//            }
//        });
//        popupWindow.showAsDropDown(et_01);
//    }
//    class MyAdapter2 extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return processingCapacity1.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return processingCapacity1.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View view1 = LayoutInflater.from(C01S013_004Activity.this).inflate(R.layout.item_c03s004_001, null);
//            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
//            textView.setText(processingCapacity1.get(i).toString());
//            return view1;
//        }
//
//    }

    //显示合成刀具
    private void showPopupWindowSynthesis() {
        tv_06.setText("");
        tv_05.setText("");
        et_01.setText("");
        View view = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter1 myAdapter = new MyAdapter1();
        listView.setAdapter(myAdapter);
        final PopupWindow popupWindow = new PopupWindow(view, ll_02.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                tv_03.setText(synthesisParameters.get(i));
                lingBuJian.clear();
                lingBuJianName.clear();
                for (int f = 0; f < params.getSynthesisParametersCodeList().size(); f++) {
                    if (params.getSynthesisParametersCodeList().get(f).getAxisID().equals(tv_02.getText().toString()) &&
                            params.getSynthesisParametersCodeList().get(f).getSynthesisParametersCode().equals(tv_03.getText()) &&
                            !lingBuJian.contains(params.getSynthesisParametersCodeList().get(f).getPartsID())) {
                        lingBuJian.add(params.getSynthesisParametersCodeList().get(f).getPartsID());
                        lingBuJianName.add(params.getSynthesisParametersCodeList().get(f).getPartsName());
                        processingCapacity.add(params.getSynthesisParametersCodeList().get(f).getToolDurable());
                    }
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll_02);

//        tv_03.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                et_01.setText("");
//
//            }
//        });

    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return synthesisParameters.size();
        }

        @Override
        public Object getItem(int i) {
            return synthesisParameters.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(synthesisParameters.get(i));
            return view1;
        }

    }

    //显示轴列表
    private void showPopupWindow() {
        tv_03.setText("");
        tv_06.setText("");
        tv_05.setText("");
        et_01.setText("");

        View view = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        final PopupWindow popupWindow = new PopupWindow(view, ll_01.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                tv_02.setText(alxe.get(i));
                for (int j = 0; j < params.getSynthesisParametersCodeList().size(); j++) {
                    if (params.getSynthesisParametersCodeList().get(j).getAxisID().equals(tv_02.getText().toString().trim()) && !synthesisParameters.contains(params.getSynthesisParametersCodeList().get(j).getSynthesisParametersCode())) {
                        synthesisParameters.add(params.getSynthesisParametersCodeList().get(j).getSynthesisParametersCode());
                    }
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll_01);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return alxe.size();
        }

        @Override
        public Object getItem(int i) {
            return alxe.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S013_003Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(alxe.get(i));
            return view1;
        }

    }

//    /**
//     * 下拉框点击事件
//     */
//    public void dropdown1(View view) {
//        if (params.getSynthesisParametersCodeList().size() > 0) {
//            int tvWidht = ll_0111.getWidth();
//            View contentView = LayoutInflater.from(C01S013_004Activity.this).inflate(R.layout.item_c01s020_001, null);
//            mPopWindow1 = new PopupWindow(contentView, tvWidht, 200, true);
//            mPopWindow1.setBackgroundDrawable(new BitmapDrawable());
//            mPopWindow1.showAsDropDown(ll_0111);//显示对话框
//            ListView lv_01 = (ListView) contentView.findViewById(R.id.lv_01);
//            C01S003_004Adapter adapter = new C01S003_004Adapter(C01S013_004Activity.this, params.getSynthesisParametersCodeList());
//            lv_01.setAdapter(adapter);
//            lv_01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    tv_0111.setText(params.getSynthesisParametersCodeList().get(i));
//                    mPopWindow1.dismiss();
//                    position = i;
//                }
//            });
//        }
//    }


    /**
     * 提交
     */
    public void appConfirm(View view) {
        if ("".equals(tv_02.getText().toString().trim())) {
            createAlertDialog(C01S013_003Activity.this, "请选择轴", Toast.LENGTH_LONG);
        } else if ("".equals(tv_03.getText().toString().trim())) {
            createAlertDialog(C01S013_003Activity.this, "请选择合成刀具", Toast.LENGTH_LONG);
        } else if ("".equals(et_01.getText().toString().trim())) {
            createAlertDialog(C01S013_003Activity.this, "请选择或输入加工量", Toast.LENGTH_LONG);
        } else if ("".equals(tv_04.getText().toString().trim())) {
            createAlertDialog(C01S013_003Activity.this, "请选择卸下原因", Toast.LENGTH_LONG);
        } else if ("".equals(et_02.getText().toString().trim())) {
            createAlertDialog(C01S013_003Activity.this, "请输入卸下数量", Toast.LENGTH_LONG);
        } else if ("".equals(tv_05.getText().toString().trim())) {
            createAlertDialog(C01S013_003Activity.this, "请选择加工零部件", Toast.LENGTH_LONG);
        }else {
            if (Integer.parseInt(et_02.getText().toString().trim()) > 25 ) {
                new AlertDialog.Builder(C01S013_003Activity.this).
                        setTitle(R.string.prompt).
                        setMessage("卸下刀具数量不能大于25").
                        setCancelable(false).
                        setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            } else {
                new AlertDialog.Builder(C01S013_003Activity.this).
                        setTitle(R.string.prompt).
                        setMessage("卸下" + tv_03.getText().toString().trim() +"共" + et_02.getText().toString().trim() + "把").
                        setCancelable(false).
                        setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                loading.show();
                                goSubmitThread = new goSubmit();
                                goSubmitThread.start();
                            }
                        }).
                        setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int a) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }

        }
//        if (!"".equals(et_01.getText().toString().trim())) {
//            int number = Integer.valueOf(et_01.getText().toString().trim());
//            int number1 = 1;
//            if (!"".equals(et_02.getText().toString().trim())) {
//                //卸下数量
//                number1 = Integer.valueOf(et_02.getText().toString().trim());
//            }
//            params.removeReason = number1;
//            if (number == 0) {
//                createAlertDialog(C01S013_004Activity.this, "请确认加工量", Toast.LENGTH_LONG);
//            } else {
//                loading.show();
//                goSubmitThread = new goSubmit();
//                goSubmitThread.start();
//            }
//        } else {
//            createAlertDialog(C01S013_004Activity.this, "请确认加工量", Toast.LENGTH_LONG);
//        }
    }

    private goSubmit goSubmitThread;

    /**
     * 提交数据线程
     */
    public class goSubmit extends Thread {
        @Override
        public void run() {
            C01S013Respons respons = submitSynthetic();
            if (null != respons) {
                Message message = new Message();
                message.obj = respons;
                handler.sendMessage(message);
            } else {
                Message message = new Message();
                internetErrorHandler.sendMessage(message);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            C01S013Respons respons = (C01S013Respons) msg.obj;
            if ("0".equals(respons.getStateCode())) {
                alertDialog(respons.getStateMsg());
            } else {
                createAlertDialog(null, respons.getStateMsg(), Toast.LENGTH_LONG);
            }
            loading.dismiss();
        }
    };

    /**
     * 提交数据方法
     */
    public C01S013Respons submitSynthetic() {
//        String parting = new String();
//        for (int i = 0; i < params.getSynthesisParametersCodeList().size(); i++) {
//            if (params.getSynthesisParametersCodeList().get(i).getAxisID().equals(tv_02.getText().toString().trim()) &&
//                    params.getSynthesisParametersCodeList().get(i).getSynthesisParametersCode().equals(tv_03.getText().toString().trim()) &&
//                    params.getSynthesisParametersCodeList().get(i).getToolDurable().equals(et_01.getText().toString().trim())) {
//                parting = params.getSynthesisParametersCodeList().get(i).getPartsID();
//            }
//        }
        C01S013Wsdl wsdl = new C01S013Wsdl();
        C01S013Request request = new C01S013Request();
        //卸下原因
        if (tv_04.getText().toString().trim().equals("正常卸下")) {
            request.setRemoveReason("0");
        } else if (tv_04.getText().toString().trim().equals("加工尺寸不满足")) {
            request.setRemoveReason("1");
        } else if (tv_04.getText().toString().trim().equals("表面质量不满足")) {
            request.setRemoveReason("2");
        } else if (tv_04.getText().toString().trim().equals("机床原因")) {
            request.setRemoveReason("3");
        }  else if (tv_04.getText().toString().trim().equals("其他")) {
            request.setRemoveReason("4");
        }
        request.setCustomerID(customerID);//用户ID
        request.setHandSetId(handsetid);//手持机ID
        request.setProcessAmount(Integer.valueOf(et_01.getText().toString().trim()));//加工量
        request.setAssemblyLineID(params.getAssemblyLineID());//流水线ID
        request.setProcessID(params.getProcessID());//对应工序ID
        request.setSynthesisParametersCode(tv_03.getText().toString().trim());//合成刀具编码
        request.setEquipmentID(params.getEquipmentID());//设备ID
        request.setRfidContainerId(params.getRfidContainerId());//载体id
        request.setAxisID(tv_02.getText().toString().trim());//轴ID
        request.setPartsID(tv_05.getText().toString().trim());//零部件ID
        request.setRemoveNum(Integer.parseInt(et_02.getText().toString().trim()));//卸下数量
        try {
            C01S013Respons respons = wsdl.submitSyntheticUnloadWheelInfo(request);
            if (null != respons) {
                return respons;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交数据成功对话框
     */
    public void alertDialog(String contry) {
        // 创建一个AlertDialog的构建者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(C01S013_003Activity.this);
        builder.setTitle(R.string.infoMsg);// 设置标题
        builder.setMessage(contry);// 提示信息
        builder.setCancelable(false);// 设置对话框不能被取消
        // 设置正面的按钮
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(C01S013_004Activity.this, C00S000_003Activity.class);
//                startActivity(intent);
//                finish();
                SysApplication.getInstance().exit();
            }
        });
        builder.show();  // 显示对话框
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
                Intent intent = new Intent(C01S013_003Activity.this, C01S013_004Activity.class);
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
