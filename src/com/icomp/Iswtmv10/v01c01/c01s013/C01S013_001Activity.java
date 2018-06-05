package com.icomp.Iswtmv10.v01c01.c01s013;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.BindRecorderStatusEnum;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.SynthesisCuttingToolBindleRecords;
import com.apiclient.vo.SynthesisCuttingToolBindleRecordsVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 设备卸下1
 *
 * @author WHY
 * @ClassName: C01S013_001Activity
 * @date 2016-3-2 下午6:33:48
 */

public class C01S013_001Activity extends CommonActivity {
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.tv_04)
    TextView tv04;
    @BindView(R.id.btn_scan)
    TextView btnScan;

    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private SynthesisCuttingToolBindleRecords synthesisCuttingToolBindleRecords = new SynthesisCuttingToolBindleRecords();

    //扫描线程
    private scanThread scanThread;
    //调用接口
    private Retrofit retrofit;

    // 合成刀标签
    String synthesisCuttingToolBindleRecordsRFID = "";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s013_001activity);
        ButterKnife.bind(this);
//        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        if (paramMap != null) {
            try {
                synthesisCuttingToolBindleRecords = (SynthesisCuttingToolBindleRecords) paramMap.get("synthesisCuttingToolBindleRecords");
                synthesisCuttingToolBindleRecordsRFID = (String) paramMap.get("synthesisCuttingToolBindleRecordsRFID");

                //TODO 需要检查是否正确
                tv01.setText(synthesisCuttingToolBindleRecords.getSynthesisCuttingTool().getSynthesisCode());
                tv02.setText(synthesisCuttingToolBindleRecords.getProductLineEquipment().getName());
                tv03.setText(synthesisCuttingToolBindleRecords.getProductLineAxle().getCode());
                tv04.setText(synthesisCuttingToolBindleRecords.getProductLineProcess().getName());//对应工序，不知道是哪个字段
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.btn_scan, R.id.btn_return, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫描按钮处理
            case R.id.btn_scan:
                tv01.setText("");
                tv02.setText("");
                tv03.setText("");
                tv04.setText("");
                scan();
                break;
            //返回按钮处理
            case R.id.btn_return:
                appReturn();
                break;
            //确定按钮处理
            case R.id.btn_confirm:
                btnConfirm();
                break;
        }
    }

    /**
     * 扫描方法
     */
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
            btnConfirm.setClickable(false);
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
            rfidString = singleScan();//TODO 生产环境需要打开
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                btnReturn.setClickable(true);
                btnConfirm.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        btnReturn.setClickable(true);
                        btnConfirm.setClickable(true);
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

                    SynthesisCuttingToolBindleRecordsVO synthesisCuttingToolBindleRecordsVO = new SynthesisCuttingToolBindleRecordsVO();
                    synthesisCuttingToolBindleRecordsVO.setBindRfid(rfidString);
                    synthesisCuttingToolBindleRecordsVO.setStatus(BindRecorderStatusEnum.Installed.getKey());


                    String jsonStr = objectToJson(synthesisCuttingToolBindleRecordsVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Map<String, String> headsMap = new HashMap<>();
                    headsMap.put("impower", OperationEnum.SynthesisCuttingTool_UnInstall.getKey().toString());

                    Call<String> searchProductLine = iRequest.searchProductLine(body, headsMap);
                    searchProductLine.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                String inpower = response.headers().get("impower");

                                if (response.raw().code() == 200) {
                                    synthesisCuttingToolBindleRecords = jsonToObject(response.body(), SynthesisCuttingToolBindleRecords.class);
                                    synthesisCuttingToolBindleRecordsRFID = rfidString;

                                    if (synthesisCuttingToolBindleRecords != null) {
                                        setTextViewHandler(inpower);
//                                    Message message = new Message();
//                                    message.obj = inpower;
//                                    setTextViewHandler.sendMessage(message);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (null != loading && loading.isShowing()) {
                                                    loading.dismiss();
                                                }
                                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C01S013_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S013_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

            //TODO 需要检查是否正确
            tv01.setText(synthesisCuttingToolBindleRecords.getSynthesisCuttingTool().getSynthesisCode());
            tv02.setText(synthesisCuttingToolBindleRecords.getProductLineEquipment().getName());
            tv03.setText(synthesisCuttingToolBindleRecords.getProductLineAxle().getCode());
            tv04.setText(synthesisCuttingToolBindleRecords.getProductLineProcess().getName());//对应工序，不知道是哪个字段
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    //TODO 需要检查是否正确
                    tv01.setText(synthesisCuttingToolBindleRecords.getSynthesisCuttingTool().getSynthesisCode());
                    tv02.setText(synthesisCuttingToolBindleRecords.getProductLineEquipment().getName());
                    tv03.setText(synthesisCuttingToolBindleRecords.getProductLineAxle().getCode());
                    tv04.setText(synthesisCuttingToolBindleRecords.getProductLineProcess().getName());//对应工序，不知道是哪个字段
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
//                //TODO 需要检查是否正确
//                tv01.setText(synthesisCuttingToolBindleRecords.getSynthesisCuttingTool().getSynthesisCode());
//                tv02.setText(synthesisCuttingToolBindleRecords.getProductLineEquipment().getName());
//                tv03.setText(synthesisCuttingToolBindleRecords.getProductLineAxle().getCode());
//                tv04.setText(synthesisCuttingToolBindleRecords.getProductLineProcess().getName());//对应工序，不知道是哪个字段
//            } else if ("2".equals(inpowerMap.get("type"))) {
//                is_need_authorization = true;
//                exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
//                    @Override
//                    public void confirm() {
//                        //TODO 需要检查是否正确
//                        tv01.setText(synthesisCuttingToolBindleRecords.getSynthesisCuttingTool().getSynthesisCode());
//                        tv02.setText(synthesisCuttingToolBindleRecords.getProductLineEquipment().getName());
//                        tv03.setText(synthesisCuttingToolBindleRecords.getProductLineAxle().getCode());
//                        tv04.setText(synthesisCuttingToolBindleRecords.getProductLineProcess().getName());//对应工序，不知道是哪个字段
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

    //返回按钮处理
    public void appReturn() {
        finish();
    }

    //提交按钮处理
    public void btnConfirm() {
        if (tv01.getText().toString() == null || "".equals(tv01.getText().toString())) {
            createAlertDialog(C01S013_001Activity.this, "请扫描标签", Toast.LENGTH_LONG);
            return;
        }

        // 用于页面之间传值，新方法
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("synthesisCuttingToolBindleRecords", synthesisCuttingToolBindleRecords);
        paramMap.put("synthesisCuttingToolBindleRecordsRFID", synthesisCuttingToolBindleRecordsRFID);
        PARAM_MAP.put(1, paramMap);

        Intent intent = new Intent(C01S013_001Activity.this, C01S013_002Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }

}
