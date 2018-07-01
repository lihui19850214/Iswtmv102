package com.icomp.Iswtmv10.v01c01.c01s009;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SynthesisCuttingToolBindVO;
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
 * 刀具组装页面1
 */
public class C01S009_001Activity extends CommonActivity {

    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.et_00)
    EditText et00;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnScan)
    TextView btnScan;


    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private scanThread scanThread;

    // 刀身码
    String bladeCode = "";
    // 合成刀标签
    String bladeCode_RFID = "";
    // 合成刀标签Code
    String synthesisCuttingToolBind_rfid_code = "";

    // 合成刀配置
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    // 合成刀真实数据
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01_s009_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //将输入的材料号自动转化为大写
        et00.setTransformationMethod(new AllCapTransformationMethod());

        Map<String, Object> paramMap = PARAM_MAP.get(1);

        //如果材料号不为空，显示在页面上
        if (null != paramMap) {
            synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
            bladeCode_RFID = (String) paramMap.get("bladeCode_RFID");
            bladeCode = (String) paramMap.get("bladeCode");

            if (bladeCode != null && !"".equals(bladeCode)) {
                et00.setText(exChangeBig(bladeCode));
            }
        }
        //将光标设置在最后
        et00.setSelection(et00.getText().length());
    }

    @OnClick({R.id.btnScan, R.id.btnCancel, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnScan:
                scan();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnSearch:
                if (et00.getText() == null || "".equals(et00.getText().toString().trim())) {
//                    createAlertDialog(C01S009_001Activity.this, "请输入合成刀具编码,再查询", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请输入合成刀具编码,再查询", Toast.LENGTH_SHORT);
                } else {
                    // 使用了 bladeCode，所以清空 bladeCode_RFID
                    bladeCode_RFID = "";
                    bladeCode = et00.getText().toString().trim();

                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setSynthesisBladeCode(bladeCode);

                    SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                    synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                    getSynthesisCuttingConfig(synthesisCuttingToolBindVO, null, bladeCode);
                }
                break;
            default:
        }
    }

    /**
     * 开始扫描
     */
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setClickable(false);
            mBtnCancel.setClickable(false);
            btnSearch.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread = new scanThread();
            scanThread.start();
        } else {
            createToast(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT);
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
//            rfidString = "18000A00000D0157";
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                mBtnCancel.setClickable(true);
                btnSearch.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        mBtnCancel.setClickable(true);
                        btnSearch.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        // 使用了 bladeCode_RFID，所以清空 bladeCode
                        et00.setText("");
                    }
                });

                // 使用了 bladeCode_RFID，所以清空 bladeCode
                bladeCode = "";

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                getSynthesisCuttingConfig(synthesisCuttingToolBindVO, rfidString, null);

            }
        }
    }

    /**
     * 根据刀身码或者rfid标签查询合成刀配置信息
     * @param synthesisCuttingToolBindVO 参数
     */
    private void getSynthesisCuttingConfig(SynthesisCuttingToolBindVO synthesisCuttingToolBindVO, final String rfid, final String bladeCode) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.show();
                }
            });

            String jsonStr = objectToJson(synthesisCuttingToolBindVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Map<String, String> headsMap = new HashMap<>();
            headsMap.put("impower", OperationEnum.SynthesisCuttingTool_Config.getKey().toString());

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> getSynthesisCuttingConfig = iRequest.getSynthesisCuttingConfig(body, headsMap);

            getSynthesisCuttingConfig.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            // 辅具和配套不显示在列表上
                            synthesisCuttingToolConfig = jsonToObject(response.body(), SynthesisCuttingToolConfig.class);

                            if (synthesisCuttingToolConfig != null) {
                                getBind(bladeCode, rfid);
                            } else {
                                createToast(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT);
                            }
                        } else {
//                            createAlertDialog(C01S009_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
//                    createAlertDialog(C01S009_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    /**
     * 根据刀身码或者rfid标签查询合成刀组装信息
     *
     * @param rfid
     * @return
     */
    private void getBind(String bladeCode, final String rfid) {
        try {
            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();

            RfidContainerVO rfidContainerVO = new RfidContainerVO();

            if (bladeCode != null && !"".equals(bladeCode)) {
                rfidContainerVO.setSynthesisBladeCode(bladeCode);
            }

            if (rfid != null && !"".equals(rfid)) {
                rfidContainerVO.setLaserCode(rfid);
            }

            synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

            String jsonStr = objectToJson(synthesisCuttingToolBindVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Map<String, String> headsMap = new HashMap<>();
            headsMap.put("impower", OperationEnum.SynthesisCuttingTool_Config.getKey().toString());

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> getBind = iRequest.getBind(body, headsMap);

            getBind.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            // 真实数据，设别上插了哪些钻头、刀片等
                            synthesisCuttingToolBind = jsonToObject(response.body(), SynthesisCuttingToolBind.class);

                            // 不为null继续操作
                            if (synthesisCuttingToolBind != null) {
                                synthesisCuttingToolBind_rfid_code = synthesisCuttingToolBind.getRfidContainer().getCode();
                                bladeCode_RFID = rfid;
                                String inpower = response.headers().get("impower");
                                inpowerHandler(inpower);
                            } else {
                                createToast(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT);
                            }
                        } else {
//                            createAlertDialog(C01S009_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
//                    createAlertDialog(C01S009_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    public void inpowerHandler(String inpower) throws IOException {
        Map<String, String> inpowerMap = jsonToObject(inpower, Map.class);

        // 判断是否显示提示框
        if ("1".equals(inpowerMap.get("type"))) {
            // 是否需要授权 true为需要授权；false为不需要授权
            is_need_authorization = false;

            jumpPage();
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    jumpPage();
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

    private void jumpPage() {
        // 用于页面之间传值，新方法
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bladeCode", bladeCode);
        paramMap.put("bladeCode_RFID", bladeCode_RFID);
        paramMap.put("synthesisCuttingToolBind_rfid_code", synthesisCuttingToolBind_rfid_code);
        paramMap.put("synthesisCuttingToolConfig", synthesisCuttingToolConfig);
        paramMap.put("synthesisCuttingToolBind", synthesisCuttingToolBind);
        PARAM_MAP.put(1, paramMap);

        Intent intent = new Intent(C01S009_001Activity.this, C01S009_002Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
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

}
