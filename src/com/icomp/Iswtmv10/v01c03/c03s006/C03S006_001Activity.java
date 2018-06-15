package com.icomp.Iswtmv10.v01c03.c03s006;

import android.content.Intent;
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
import com.apiclient.constants.ScrapStateEnum;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.vo.BindEquipmentVO;
import com.apiclient.vo.SynthesisCuttingToolInitVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s011.C01S011_003Activity;
import com.icomp.Iswtmv10.v01c03.c03s001.C03S001_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.DialogAlertCallBack;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 流转刀具初始化页面1
 */
public class C03S006_001Activity extends CommonActivity {

    @BindView(R.id.syntheticKnife)
    EditText syntheticKnife;
    @BindView(R.id.tvSerach)
    TextView tvSerach;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.et_bladeCode)
    EditText etBladeCode;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.btn_scan)
    TextView btnScan;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    //扫描线程
    private scanThread scanThread;

    //合成刀具初始化参数类
    private SynthesisCuttingToolInitVO synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
    //调用接口
    private Retrofit retrofit;

    // 物料号列表
    List<Object> wuliaohaoList = new ArrayList<>();
    // 物料号选项
    Object obj = null;
    // 刀具状态列表
    List<ScrapStateEnum> scrapStateList = new ArrayList<>();
    // 刀具状态选项
    ScrapStateEnum scrapStateEnum = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s006_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //接受上一个页面返回的参数
        synthesisCuttingToolInitVO.setSynthesisCode(getIntent().getStringExtra(PARAM1));
        //将输入的材料号自动转化为大写
        syntheticKnife.setTransformationMethod(new AllCapTransformationMethod());
        //如果材料号不为空，显示在页面上
        if (null != synthesisCuttingToolInitVO.getSynthesisCode()) {
            syntheticKnife.setText(exChangeBig(synthesisCuttingToolInitVO.getSynthesisCode()));
        } else {
            syntheticKnife.setText("T");
        }
        //将光标设置在最后
        syntheticKnife.setSelection(syntheticKnife.getText().length());

        // 生成刀具状态列表数据
        for (ScrapStateEnum scrapStateEnum : ScrapStateEnum.values()) {
            if (!ScrapStateEnum.scrapState002.getKey().equals(scrapStateEnum.getKey())) {
                scrapStateList.add(scrapStateEnum);
            }
        }

        // 刀具状态下拉列表默认选中第一个
        if (scrapStateList.size() > 0) {
            tv02.setText(scrapStateList.get(0).getName());
            scrapStateEnum = scrapStateList.get(0);
        }
    }

    @OnClick({R.id.ll_01, R.id.ll_02, R.id.btnScan, R.id.tvSerach, R.id.btnReturn, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                //返回按钮处理--菜单页面
                finish();
                break;
            case R.id.btnConfirm:
                // 判断合成刀是否为空
                if (syntheticKnife.getText() == null || "".equals(syntheticKnife.getText().toString().trim())) {
                    createAlertDialog(C03S006_001Activity.this, "请输入合成刀", Toast.LENGTH_LONG);
                    return;
                } else if (obj == null) {
                    createAlertDialog(C03S006_001Activity.this, "请输入物料号", Toast.LENGTH_LONG);
                    return;
                } else if (etBladeCode.getText() == null || "".equals(etBladeCode.getText().toString().trim())) {
                    createAlertDialog(C03S006_001Activity.this, "请输入刀身码", Toast.LENGTH_LONG);
                    return;
                } else if (scrapStateEnum == null) {
                    createAlertDialog(C03S006_001Activity.this, "请输入刀具状态", Toast.LENGTH_LONG);
                    return;
                }

                StringBuffer sb = new StringBuffer();
                sb.append("物料号：").append(obj.toString()).append("\n");// TODO 取值不对
                sb.append("刀身码：").append(etBladeCode.getText().toString().trim()).append("\n");
                sb.append("刀具状态：").append(scrapStateEnum.getName());

                showDialogAlertContent(sb.toString(), new DialogAlertCallBack() {
                    @Override
                    public void confirm() {
                        requestData();
                    }

                    @Override
                    public void cancel() {

                    }
                });
                break;
            //扫描按钮处理
            case R.id.btnScan:
                scan();
                break;
            //查询按钮处理
            case R.id.tvSerach:
                synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
                synthesisCuttingToolInitVO.setSynthesisCode(syntheticKnife.getText().toString().trim());
                if ("".equals(synthesisCuttingToolInitVO.getSynthesisCode())) {
                    createAlertDialog(C03S006_001Activity.this, getString(R.string.c03s006_006_003), Toast.LENGTH_LONG);
                } else {
                    //根据合成刀查询信息
                    search();
                }
                break;
            case R.id.ll_01:
                if (wuliaohaoList.size() > 0)
                    showPopupWindow();//设备
                break;
            case R.id.ll_02:
                if (scrapStateList.size() > 0)
                    showPopupWindow2();//刀具状态
                break;
        }
    }

    /**
     * 点击物料号下拉框
     */
    private void showPopupWindow() {
        View view = LayoutInflater.from(C03S006_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv01.setText(wuliaohaoList.get(i).toString());
                // TODO 未完成，显示数据不对，获取数据不对
                obj = wuliaohaoList.get(i);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //设备的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return wuliaohaoList.size();
        }

        @Override
        public Object getItem(int i) {
            return wuliaohaoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C03S006_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(wuliaohaoList.get(i).toString());
            // TODO 未完成，显示数据不对
            return view1;
        }
    }

    /**
     * 点击刀具状态下拉框
     */
    private void showPopupWindow2() {
        View view = LayoutInflater.from(C03S006_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv02.setText(scrapStateList.get(i).getName());
                scrapStateEnum = scrapStateList.get(i);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    //设备的Adapter
    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return scrapStateList.size();
        }

        @Override
        public Object getItem(int i) {
            return scrapStateList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C03S006_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(scrapStateList.get(i).getName());
            return view1;
        }

    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setClickable(false);
            tvSerach.setClickable(false);
            btnConfirm.setClickable(false);
            btnReturn.setClickable(false);
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
            rfidString = singleScan();
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                tvSerach.setClickable(true);
                btnConfirm.setClickable(true);
                btnReturn.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        tvSerach.setClickable(true);
                        btnConfirm.setClickable(true);
                        btnReturn.setClickable(true);
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
                    synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
                    synthesisCuttingToolInitVO.setRfidCode(rfidString);

                    String jsonStr = objectToJson(synthesisCuttingToolInitVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> getSynthesisCuttingConfig = iRequest.getSynthesisCuttingConfig(body, new HashMap<String, String>());
                    getSynthesisCuttingConfig.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    SynthesisCuttingToolConfig synthesisCuttingTool = jsonToObject(response.body(), SynthesisCuttingToolConfig.class);
                                    if (synthesisCuttingTool != null) {
                                        //跳转到库存盘点刀具信息详细页面
                                        Intent intent = new Intent(C03S006_001Activity.this, C03S001_002Activity.class);
                                        intent.putExtra(PARAM, synthesisCuttingTool);
                                        startActivity(intent);
                                        finish();
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
                                            createAlertDialog(C03S006_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C03S006_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    //根据材料号查询合成刀具组成信息
    private void search() {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = objectToJson(synthesisCuttingToolInitVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> getSynthesisCuttingConfig = iRequest.getSynthesisCuttingConfig(body, new HashMap<String, String>());

            getSynthesisCuttingConfig.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            SynthesisCuttingToolConfig synthesisCuttingTool = jsonToObject(response.body(), SynthesisCuttingToolConfig.class);
                            if (synthesisCuttingTool != null) {
                                //跳转到库存盘点刀具信息详细页面
                                Intent intent = new Intent(C03S006_001Activity.this, C03S006_002Activity.class);
                                intent.putExtra(PARAM, synthesisCuttingTool);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C03S006_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C03S006_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    loading.dismiss();
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

    private void requestData() {
        try {
            loading.show();

            BindEquipmentVO bindEquipmentVO = new BindEquipmentVO();
            //TODO 提交数据

            //syntheticKnife.getText().toString().trim() 合成刀
            //obj 物料号选项
            //etBladeCode.getText().toString().trim() 刀身码
            //scrapStateEnum 刀具状态选项
            //rfidString rfid标签


            String jsonStr = objectToJson(bindEquipmentVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);


            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> bindEquipment = iRequest.bindEquipment(body, new HashMap<String, String>());
            bindEquipment.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(C03S006_001Activity.this, C03S006_002Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C03S006_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C03S006_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

//    //重写键盘上扫描按键的方法
//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if(isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
//            scan();
//        } else {
//            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
//        }
//    }

}
