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
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.vo.SynthesisCuttingToolInitVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.HashMap;

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

    //合成刀具初始化参数类
    private SynthesisCuttingToolInitVO params = new SynthesisCuttingToolInitVO();
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
        params.setSynthesisCode(getIntent().getStringExtra(PARAM1));
        //将输入的材料号自动转化为大写
        et01.setTransformationMethod(new AllCapTransformationMethod());
        //如果材料号不为空，显示在页面上
        if (null != params.getSynthesisCode()) {
            et01.setText(exChangeBig(params.getSynthesisCode()));
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
                params = new SynthesisCuttingToolInitVO();
                params.setSynthesisCode(et01.getText().toString().trim());
                if ("".equals(params.getSynthesisCode())) {
                    createAlertDialog(C03S001_001Activity.this, getString(R.string.c03s001_001_002), Toast.LENGTH_LONG);
                } else {
                    //根据材料号查询合成刀具组成信息
                    search();
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

                        loading.show();
                    }
                });

                try {
                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);
                    params = new SynthesisCuttingToolInitVO();
                    params.setRfidCode(rfidString);

                    String jsonStr = objectToJson(params);
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
                                        Intent intent = new Intent(C03S001_001Activity.this, C03S001_002Activity.class);
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
                                            createAlertDialog(C03S001_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C03S001_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

            String jsonStr = objectToJson(params);
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
                                Intent intent = new Intent(C03S001_001Activity.this, C03S001_002Activity.class);
                                intent.putExtra(PARAM, synthesisCuttingTool);
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
