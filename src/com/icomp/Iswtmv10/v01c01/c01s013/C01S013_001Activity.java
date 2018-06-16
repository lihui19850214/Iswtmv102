package com.icomp.Iswtmv10.v01c01.c01s013;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.BindRecorderStatusEnum;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.QueryVO;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SynthesisCuttingToolBindVO;
import com.apiclient.vo.SynthesisCuttingToolBindleRecordsVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import com.icomp.entity.base.Assemblyline;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 设备卸下页面1
 */
public class C01S013_001Activity extends CommonActivity {

    @BindView(R.id.et_00)
    EditText et00;

    @BindView(R.id.btn_scan)
    TextView btnScan;

    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;


    //扫描线程
    private ScanThread scanThread;
    //调用接口
    private Retrofit retrofit;

    // 合成刀标签
    String queryVORFID = "";

    // 刀身码
    String bladeCode = "";

    SynthesisCuttingToolBind synthesisCuttingToolBind;
    ProductLineAssemblyline assemblyline;
    ProductLineProcess process;
    ProductLineEquipment equipment;
    List<ProductLine> productLineList;


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
                queryVORFID = (String) paramMap.get("queryVORFID");

                bladeCode = (String) paramMap.get("bladeCode");

                //将输入的材料号自动转化为大写
                et00.setTransformationMethod(new AllCapTransformationMethod());
                //如果材料号不为空，显示在页面上
                if (null != bladeCode && !"".equals(bladeCode)) {
                    et00.setText(exChangeBig(bladeCode));
                }
                //将光标设置在最后
                et00.setSelection(et00.getText().length());
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
                    }
                });

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                queryForUnInstall(synthesisCuttingToolBindVO);
            }
        }
    }

    private void queryForUnInstall(SynthesisCuttingToolBindVO synthesisCuttingToolBindVO) {
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
            headsMap.put("impower", OperationEnum.SynthesisCuttingTool_UnInstall.getKey().toString());

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> queryForUnInstall = iRequest.queryForUnInstall(body, headsMap);

            queryForUnInstall.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        String inpower = response.headers().get("impower");

                        if (response.raw().code() == 200) {
                            QueryVO queryVO = jsonToObject(response.body(), QueryVO.class);
                            queryVORFID = rfidString;

                            if (queryVO != null) {
                                synthesisCuttingToolBind = queryVO.getSynthesisCuttingToolBind();
                                assemblyline = queryVO.getAssemblyline();
                                process = queryVO.getProcess();
                                equipment = queryVO.getEquipment();
                                productLineList = queryVO.getPartsList();

                                setTextViewHandler(inpower);
                            } else {
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C01S013_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
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
                    createAlertDialog(C01S013_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    private void setTextViewHandler(String inpower) throws IOException {
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
        if (et00.getText() == null || "".equals(et00.getText().toString().trim())) {
            createAlertDialog(C01S013_001Activity.this, "请扫输入刀身码", Toast.LENGTH_LONG);
            return;
        } else {
            bladeCode = et00.getText().toString().trim();

            RfidContainerVO rfidContainerVO = new RfidContainerVO();
            rfidContainerVO.setSynthesisBladeCode(et00.getText().toString().trim());

            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
            synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);
            queryForUnInstall(synthesisCuttingToolBindVO);
        }
    }

    private void jumpPage() {
        // 用于页面之间传值，新方法
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("synthesisCuttingToolBind", synthesisCuttingToolBind);
        paramMap.put("assemblyline", assemblyline);
        paramMap.put("process", process);
        paramMap.put("equipment", equipment);
        paramMap.put("productLineList", productLineList);
        paramMap.put("bladeCode", bladeCode);
        paramMap.put("queryVORFID", queryVORFID);
        PARAM_MAP.put(1, paramMap);

        Intent intent = new Intent(C01S013_001Activity.this, C01S013_002Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }

}
