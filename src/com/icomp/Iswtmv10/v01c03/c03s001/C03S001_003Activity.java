package com.icomp.Iswtmv10.v01c03.c03s001;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apiclient.pojo.*;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 合成刀具初始化页面3
 */

public class C03S001_003Activity extends CommonActivity {

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.btnStop)
    Button btnStop;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    //扫描数量
    public String scanNumber;
    //群扫存放rfidString的List
    public List<String> rfidList = new ArrayList<>();
    //扫描线程
    private scanThread scanThread;

    //合成刀具初始化参数类
    private SynthesisCuttingToolConfig params = new SynthesisCuttingToolConfig();
    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s001_003);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();
        //接受上一页面传递的参数
        params = (SynthesisCuttingToolConfig) getIntent().getSerializableExtra(PARAM);
        //显示扫描数量
        scanNumber = ZERO;
        tv01.setText(getResources().getString(R.string.c03s001_003_002) + scanNumber);
    }

    //返回按钮处理--返回到合成刀具初始化页面1
    public void btnReturn(View view) {
        Message message = new Message();
        returnHandler.sendMessage(message);
    }

    //提交按钮处理--调用接口，提交初始化合成刀具RFIDCodeList
    public void btnSubmit(View view) {

        if (!isCanScan) {
            stop_scan();
        }

        if (0 == Integer.parseInt(scanNumber)) {
            createAlertDialog(this, getString(R.string.c03s001_003_006), Toast.LENGTH_LONG);
        } else {
            //点击提交按钮处理方法
            next();
        }
    }

    //返回Handler
    @SuppressLint("HandlerLeak")
    Handler returnHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isCanScan) {
                stop_scan();
            }
            new AlertDialog.Builder(C03S001_003Activity.this).
                    setTitle(R.string.prompt).
                    setMessage(R.string.c03s001_003_003).
                    setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(C03S001_003Activity.this, C03S001_001Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
            }).show();
        }
    };

    @OnClick({R.id.btnScan, R.id.btnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫描按钮处理
            case R.id.btnScan:
                //扫描方法
                scan();
                break;
            //停止按钮处理
            case R.id.btnStop:
                stop_scan();
                break;
            default:
        }
    }

    private void stop_scan() {
        scanOrNot = false;
        isCanScan = true;
        close();
        btnScan.setClickable(true);
        btnScan.setText(getString(R.string.scan));
        btnScan.setBackgroundResource(R.drawable.border);
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setText("扫描中");
            btnScan.setClickable(false);
            btnScan.setBackgroundResource(R.color.hintcolor);

            //设置扫描或停止条件为true
            scanOrNot = true;

            // 重复扫描
            Toast.makeText(getApplicationContext(), "开始扫描", Toast.LENGTH_SHORT).show();

            //启动扫描线程
            scanThread = new scanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class scanThread extends Thread{
        @Override
        public void run() {
            super.run();
            //需每次置rfidString为null
            rfidString = null;

            while (null == rfidString && scanOrNot) {
                rfidString = alwaysScan();
            }

            if (null != rfidString) {
                Message message = new Message();
                message.obj = rfidString;
                scanHandler.sendMessage(message);
            }
        }
    }

    //扫描Handler
    @SuppressLint("HandlerLeak")
    Handler scanHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String rfidStr = msg.obj.toString();

            if (null == rfidList) {
                rfidList = new ArrayList<>();
            }

            if (!rfidList.contains(rfidStr)) {
                rfidList.add(rfidStr);
                //扫描数量params.scanNumber+1
                scanNumber = String.valueOf(Integer.parseInt(scanNumber) + 1);
                //显示当前初始化数量
                tv01.setText(getResources().getString(R.string.c03s001_003_002) + scanNumber);
            } else {
                // 重复扫描
//                Toast.makeText(getApplicationContext(), "重复扫描", Toast.LENGTH_SHORT).show();
            }

            //重新启动扫描线程
            scanThread = new scanThread();
            scanThread.start();
        }
    };

    //点击提交按钮处理方法
    private void next() {
        loading.show();

        //调用接口，提交初始化合成刀具RFIDCodeList
        IRequest iRequest = retrofit.create(IRequest.class);

        List<SynthesisCuttingToolBind> list = new ArrayList<>();
        for (int i = 0; i < rfidList.size(); i++) {
            String rfid = rfidList.get(i);

            RfidContainer rfidContainer = new RfidContainer();
            rfidContainer.setLaserCode(rfid);

            List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = new ArrayList<>();

            for (SynthesisCuttingToolLocationConfig stlc : params.getSynthesisCuttingToolLocationConfigList()) {
                SynthesisCuttingToolLocation synthesisCuttingToolLocation = new SynthesisCuttingToolLocation();
                synthesisCuttingToolLocation.setSynthesisCuttingToolCode(stlc.getSynthesisCuttingToolConfig().getSynthesisCuttingToolCode());
                synthesisCuttingToolLocation.setLocation(stlc.getSynthesisCuttingToolConfig().getLocation());
                synthesisCuttingToolLocation.setCount(stlc.getSynthesisCuttingToolConfig().getCount());
                synthesisCuttingToolLocation.setCuttingToolCode(stlc.getCuttingToolCode());
                synthesisCuttingToolLocation.setCount(stlc.getCount());

                synthesisCuttingToolLocationList.add(synthesisCuttingToolLocation);
            }


            SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();
            synthesisCuttingToolBind.setSynthesisCuttingToolCode(params.getSynthesisCuttingToolCode());
            synthesisCuttingToolBind.setRfidContainer(rfidContainer);
            synthesisCuttingToolBind.setSynthesisCode(params.getSynthesisCuttingTool().getSynthesisCode());
            synthesisCuttingToolBind.setSynthesisCuttingToolLocationList(synthesisCuttingToolLocationList);

            list.add(synthesisCuttingToolBind);
        }


        Gson gson = new Gson();

        String jsonStr = gson.toJson(list);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> submitFInitSynthesis = iRequest.synthesisCuttingInit(body);
        submitFInitSynthesis.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response response) {
                try {
                    if (response.raw().code() == 200) {
                        Intent intent = new Intent(C03S001_003Activity.this, C03S001_004Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        createAlertDialog(C03S001_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                createAlertDialog(C03S001_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
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

}
