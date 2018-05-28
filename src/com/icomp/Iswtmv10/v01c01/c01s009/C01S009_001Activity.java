package com.icomp.Iswtmv10.v01c01.c01s009;

/**
 * 刀具组装
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.vo.SynthesisCuttingToolInitVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class C01S009_001Activity extends CommonActivity {

    @BindView(R.id.tvScan)
    TextView mTvScan;
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.tvTitle)
    TextView tvTitle;


    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private scanThread scanThread;

    SynthesisCuttingToolConfig synthesisCuttingToolConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01_s009_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();
    }

    @OnClick({R.id.tvScan, R.id.btnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
                finish();
                break;
            default:
        }
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
//        scan();
//    }


    /**
     * 开始扫描
     */
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            mTvScan.setClickable(false);
            mBtnCancel.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread = new scanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 扫描线程,将扫描结果进行网络请求
     */
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
            if ("close".equals(rfidString)) {
                mTvScan.setClickable(true);
                mBtnCancel.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvScan.setClickable(true);
                        mBtnCancel.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                SynthesisCuttingToolInitVO synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
                synthesisCuttingToolInitVO.setRfidCode(rfidString);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(synthesisCuttingToolInitVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Map<String, String> headsMap = new HashMap<>();
                headsMap.put("impower", OperationEnum.SynthesisCuttingTool_Config.getKey().toString());

                Call<String> getSynthesisCuttingConfig = iRequest.getSynthesisCuttingConfig(body, headsMap);
                getSynthesisCuttingConfig.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            String inpower = response.headers().get("impower");

                            if (response.raw().code() == 200) {
                                Gson gson = new Gson();
                                synthesisCuttingToolConfig = gson.fromJson(response.body(), SynthesisCuttingToolConfig.class);

                                Message message = new Message();
                                message.obj = inpower;
                                scanHandler.sendMessage(message);
                            } else {
                                final String errorStr = response.errorBody().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createAlertDialog(C01S009_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S009_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
            }
        }
    }




    //扫描Handler
    @SuppressLint("HandlerLeak")
    Handler scanHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String inpower = msg.obj.toString();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> inpowerMap = new HashMap<>();
            try {
                inpowerMap = mapper.readValue(inpower, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 判断是否显示提示框
            if ("1".equals(inpowerMap.get("type"))) {
                // 是否需要授权 true为需要授权；false为不需要授权
                is_need_authorization = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //跳转到库存盘点刀具信息详细页面
                        Intent intent = new Intent(C01S009_001Activity.this, C01S009_002Activity.class);
                        intent.putExtra(PARAM, synthesisCuttingToolConfig);
                        intent.putExtra("rfidString", rfidString);
                        startActivity(intent);
                        finish();
                    }
                });

            } else if ("2".equals(inpowerMap.get("type"))) {
                is_need_authorization = true;
                exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                    @Override
                    public void confirm() {
                        //跳转到库存盘点刀具信息详细页面
                        Intent intent = new Intent(C01S009_001Activity.this, C01S009_002Activity.class);
                        intent.putExtra(PARAM, synthesisCuttingToolConfig);
                        intent.putExtra("rfidString", rfidString);
                        startActivity(intent);
                        finish();
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
    };




    /**
     * 显示数据提示dialog
     */
    private void showDialogAlert(String content) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog2);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_alert_table, null);
        LinearLayout llContainer = (LinearLayout) view.findViewById(R.id.llContainer);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tv_01 = (TextView) view.findViewById(R.id.tv_01);
        tv_01.setText(content);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                stopScan();
                //
                Intent intent = new Intent(C01S009_001Activity.this, C01S009_002Activity.class);
                intent.putExtra("TAG", "1");//刀具拆分

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

        for (int i = 0; i < 10; i++) {
            addAlertLayout(llContainer);
        }

    }

    /**
     * 添加布局
     */
    private void addAlertLayout(LinearLayout llContainer) {

        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_daojuchaifen_0, null);
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);
        TextView tvHeChengNum = (TextView) mLinearLayout.findViewById(R.id.tvHeChengNum);
        TextView tvDelete = (TextView) mLinearLayout.findViewById(R.id.tvDelete);

        tvNum.setText("1");
        tvHeChengNum.setText("钻头");
        tvDelete.setText("1");

        llContainer.addView(mLinearLayout);
    }

}
