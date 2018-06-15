package com.icomp.Iswtmv10.v01c03.c03s001;

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
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SynthesisCuttingToolBindVO;
import com.apiclient.vo.SynthesisCuttingToolInitVO;
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
 * 合成刀具初始化页面1
 */
public class C03S001_001Activity extends CommonActivity {

    @BindView(R.id.et_01)
    EditText et01;
    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnReturn)
    Button btnReturn;

    //扫描线程
    private scanThread scanThread;

    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    //合成刀具初始化参数类
    private SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s001_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //接受上一个页面返回的参数
        synthesisCuttingToolBindVO.setSynthesisCode(getIntent().getStringExtra(PARAM1));

        //将输入的材料号自动转化为大写
        et01.setTransformationMethod(new AllCapTransformationMethod());
        //如果材料号不为空，显示在页面上
        if (null != synthesisCuttingToolBindVO.getSynthesisCode()) {
            et01.setText(exChangeBig(synthesisCuttingToolBindVO.getSynthesisCode()));
        } else {
            et01.setText("T");
        }
        //将光标设置在最后
        et01.setSelection(et01.getText().length());
    }

    //返回按钮处理--返回上一页面（刀具初始化菜单页面）
    public void btnReturn(View view) {
        finish();
    }

    @OnClick({R.id.btnScan, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫描按钮处理
            case R.id.btnScan:
                scan();
                break;
            //查询按钮处理
            case R.id.btnSearch:
                synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                synthesisCuttingToolBindVO.setSynthesisCode(et01.getText().toString().trim());
                if ("".equals(synthesisCuttingToolBindVO.getSynthesisCode())) {
                    createAlertDialog(C03S001_001Activity.this, getString(R.string.c03s001_001_002), Toast.LENGTH_LONG);
                } else {
                    //扫描线程
                    scanThread2 scanThread2 = new scanThread2();
                    scanThread2.start();
                }
                break;
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setClickable(false);
            btnSearch.setClickable(false);
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
                btnSearch.setClickable(true);
                btnReturn.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        btnSearch.setClickable(true);
                        btnReturn.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                getSynthesisCuttingConfig();
            }
        }
    }

    //扫描线程
    private class scanThread2 extends Thread {
        @Override
        public void run() {
            super.run();
            getSynthesisCuttingConfig();

        }
    }

    //根据材料号查询合成刀具组成信息
    private void getSynthesisCuttingConfig() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading.show();
            }
        });

        try {
            String jsonStr = objectToJson(synthesisCuttingToolBindVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> getSynthesisCuttingConfig = iRequest.getSynthesisCuttingConfig(body, new HashMap<String, String>());
            getSynthesisCuttingConfig.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            synthesisCuttingToolConfig = jsonToObject(response.body(), SynthesisCuttingToolConfig.class);
                            if (synthesisCuttingToolConfig != null) {
                                // 用于页面之间传值，新方法
                                Map<String, Object> paramMap = new HashMap<>();
                                paramMap.put("synthesisCuttingToolConfig", synthesisCuttingToolConfig);
                                paramMap.put("synthesisCuttingToolBindVO", synthesisCuttingToolBindVO);
                                PARAM_MAP.put(1, paramMap);

                                Intent intent = new Intent(C03S001_001Activity.this, C03S001_002Activity.class);
                                // 不清空页面之间传递的值
                                intent.putExtra("isClearParamMap", false);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C03S001_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C03S001_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
