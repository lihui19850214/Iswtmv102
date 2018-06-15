package com.icomp.Iswtmv10.v01c03.c03s001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SynthesisCuttingToolBindVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 合成刀具初始化页面3
 */
public class C03S001_003Activity extends CommonActivity {

    @BindView(R.id.et_01)
    EditText tv01;
    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnNext)
    Button btnNext;


    //扫描线程
    private ScanThread scanThread;

    //合成刀具初始化参数类
    private SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s001_003);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
    }

    //返回按钮处理--返回到合成刀具初始化页面1
    public void btnReturn(View view) {
        Intent intent = new Intent(this, C03S001_002Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btnScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫描按钮处理
            case R.id.btnScan:
                //扫描方法
                scan();
                break;
            default:
        }
    }

    /**
     * 扫描方法
     */
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnReturn.setClickable(false);
            btnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread = new ScanThread();
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
            rfidString = singleScan();//TODO 生产环境需要打开
            if ("close".equals(rfidString)) {
                btnReturn.setClickable(true);
                btnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnReturn.setClickable(true);
                        btnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });

                //提交按钮处理方法
                requestData(null, rfidString);
            }
        }
    }


    //点击提交按钮处理方法
    public void next(View view) {
        if (tv01.getText() == null || "".equals(tv01.getText().toString().trim())) {
            createAlertDialog(C03S001_003Activity.this, "请输入合成刀刀身码", Toast.LENGTH_LONG);
        } else {
            requestData(tv01.getText().toString().trim(), null);
        }
    }

    private void requestData(String bladeCode, String rfid) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.show();
                }
            });


            RfidContainerVO rfidContainerVO = new RfidContainerVO();
            if (bladeCode != null) {
                rfidContainerVO.setSynthesisBladeCode(bladeCode);
            }
            if (rfid != null) {
                rfidContainerVO.setLaserCode(rfid);
            }

            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
            synthesisCuttingToolBindVO.setSynthesisCode(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());
            synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

            List<SynthesisCuttingToolBindVO> list = new ArrayList<>();
            list.add(synthesisCuttingToolBindVO);


            String jsonStr = objectToJson(list);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            //调用接口，提交初始化合成刀具RFIDCodeList
            IRequest iRequest = retrofit.create(IRequest.class);
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
                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
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
