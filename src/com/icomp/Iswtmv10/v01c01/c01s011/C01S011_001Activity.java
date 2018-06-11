package com.icomp.Iswtmv10.v01c01.c01s011;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.apiclient.pojo.*;
import com.apiclient.vo.BindEquipmentVO;
import com.apiclient.vo.QueryEquipmentByRfidVO;
import com.apiclient.vo.QuerySynthesisCuttingToolVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 安上设备页面1
 */
public class C01S011_001Activity extends CommonActivity {


//    @BindView(R.id.et_00)
    EditText et00;
//    @BindView(R.id.tvScan)
    TextView tvScan;
//    @BindView(R.id.btn_return)
    Button btnReturn;
//    @BindView(R.id.btn_next)
    Button btnNext;

    //调用接口
    private Retrofit retrofit;


    SynthesisCuttingToolBind synthesisCuttingToolBing = new SynthesisCuttingToolBind();
    // 合成刀标签
    String synthesisCuttingToolBingRFID = "";


    ProductLineEquipment productLineEquipment = null;//选中的设备列表项
    ProductLineAxle productLineAxle = null;//选中的轴号列表项

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.c01s011_001activity);
        setContentView(R.layout.aaaa);
//        ButterKnife.bind(this);

        //调用接口
//        retrofit = RetrofitSingle.newInstance();

    }

    /**
     * 扫描按钮点击
     */
//    @OnClick({R.id.btn_next, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btn_next:
                if (et00.getText() != null && !"".equals(et00.getText().toString().trim())) {

                } else {
                    createAlertDialog(C01S011_001Activity.this, "请先输入合成刀具刀身码", Toast.LENGTH_LONG);
                }
                break;
            default:
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            tvScan.setClickable(false);
            btnReturn.setClickable(false);
            btnNext.setClickable(false);

            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            ScanThread scanThread = new ScanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class ScanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000D6440";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                tvScan.setClickable(true);
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
                        tvScan.setClickable(true);
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
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

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
                                        setTextViewHandler(inpower);
                                    } else {
                                        Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    createAlertDialog(C01S011_001Activity.this, errorStr, Toast.LENGTH_LONG);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
                                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                            } finally {
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
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
                                    createAlertDialog(C01S011_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    private void setTextViewHandler(String inpower) throws IOException {
        Map<String, String> inpowerMap = jsonToObject(inpower, Map.class);

        // 判断是否显示提示框
        if ("1".equals(inpowerMap.get("type"))) {
            // 是否需要授权 true为需要授权；false为不需要授权
            is_need_authorization = false;

            et00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    et00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
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


    /**
     * 下一步
     */
    public void appNext(View view) {

        //if (null != tv01 && !"".equals(tv01.getText().toString().trim()) && null != tv02 && !"".equals(tv02.getText().toString().trim())) {

            authorizationWindow(new AuthorizationWindowCallBack() {
                @Override
                public void success(AuthCustomer authCustomer) {
                    requestData(authCustomer);
                }

                @Override
                public void fail() {

                }
            });
//        } else {
//            createAlertDialog(C01S011_001Activity.this, "请配置生产关联或绑定对应设备标签", Toast.LENGTH_LONG);
//        }

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
                    impowerRecorder.setToolCode(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(synthesisCuttingToolBingRFID);// rfid标签
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
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
                createAlertDialog(C01S011_001Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
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

            String jsonStr = objectToJson(bindEquipmentVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> bindEquipment = iRequest.bindEquipment(body, headsMap);
            bindEquipment.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(C01S011_001Activity.this, C01S011_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S011_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S011_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
