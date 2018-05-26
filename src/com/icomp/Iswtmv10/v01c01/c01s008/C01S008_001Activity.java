package com.icomp.Iswtmv10.v01c01.c01s008;
/**
 * 刀具拆分
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.pojo.SynthesisCuttingToolLocationConfig;
import com.apiclient.vo.SynthesisCuttingToolInitVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.List;

public class C01S008_001Activity extends CommonActivity {

    @BindView(R.id.tvScan)
    TextView mTvScan;
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.activity_c01s008_001)
    LinearLayout activityC01s008001;


    //扫描线程
    private scanThread scanThread;

    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s008_001);
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
//            rfidString = "18000A00000EA015";// TODO 生产环境需要删除
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

                Call<String> getBind = iRequest.getBind(body);
                getBind.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                SynthesisCuttingToolBind synthesisCuttingToolBind = mapper.readValue(response.body(), SynthesisCuttingToolBind.class);

//                                search(synthesisCuttingToolConfig);

                                //跳转到库存盘点刀具信息详细页面
                                Intent intent = new Intent(C01S008_001Activity.this, c01s008_002Activity.class);
                                intent.putExtra(PARAM, synthesisCuttingToolBind);
                                intent.putExtra("rfidString", rfidString);
                                startActivity(intent);
                                finish();
                            } else {
                                final String errorStr = response.errorBody().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createAlertDialog(C01S008_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S008_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
            }
        }
    }



    private void search(SynthesisCuttingToolConfig synthesisCuttingToolConfig) {
        List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();


        for(SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig : synthesisCuttingToolLocationConfigList){
            Log.e("code", synthesisCuttingToolLocationConfig.getCuttingTool().getCode());
        }

        SynthesisCuttingToolInitVO synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
        synthesisCuttingToolInitVO.setSynthesisCode(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());

        //调用接口，查询合成刀具组成信息
        IRequest iRequest = retrofit.create(IRequest.class);


        Gson gson = new Gson();
        String jsonStr = gson.toJson(synthesisCuttingToolInitVO);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getBind = iRequest.getBind(body);
        getBind.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        Gson gson = new Gson();
                        SynthesisCuttingToolConfig synthesisCuttingToolConfig = gson.fromJson(response.body(), SynthesisCuttingToolConfig.class);

                        Log.e("synthesisCutting", synthesisCuttingToolConfig.toString());
                    } else {
                        final String errorStr = response.errorBody().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createAlertDialog(C01S008_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                        createAlertDialog(C01S008_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    }
                });

            }
        });
    }


}
