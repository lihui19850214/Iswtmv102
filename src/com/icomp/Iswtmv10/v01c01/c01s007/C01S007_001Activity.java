package com.icomp.Iswtmv10.v01c01.c01s007;

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
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.pojo.QimingRecords;
import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.vo.CuttingToolBindVO;
import com.apiclient.vo.QuerySynthesisCuttingToolVO;
import com.apiclient.vo.RfidContainerVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s011.C01S011_002Activity;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刀具打码
 */
public class C01S007_001Activity extends CommonActivity {


    @BindView(R.id.tv_00)
    TextView tv00;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnCode)
    Button btnCode;

    //调用接口
    private Retrofit retrofit;

    CuttingToolBind cuttingToolBind = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s007_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();
    }

    @OnClick({R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            default:
        }
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        //防止点击扫描后点击此按钮
        finish();
    }

    public void btnCode(View view) {
        if (cuttingToolBind == null) {
            createAlertDialog(C01S007_001Activity.this, "请先扫描刀具盒标签", Toast.LENGTH_LONG);
        } else {
            Intent intent = new Intent(C01S007_001Activity.this, C01S007_002Activity.class);
            // 不清空页面之间传递的值
            intent.putExtra("bladeCode", cuttingToolBind.getBladeCode());
            startActivity(intent);
            finish();
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnReturn.setClickable(false);
            btnCode.setClickable(false);

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
//            rfidString = "18000A00000EAA31";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                btnReturn.setClickable(true);
                btnCode.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScan.setClickable(true);
                        btnReturn.setClickable(true);
                        btnCode.setClickable(true);
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

                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
                    cuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                    String jsonStr = objectToJson(cuttingToolBindVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> queryBindInfo = iRequest.queryBindInfo(body);
                    queryBindInfo.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                String inpower = response.headers().get("impower");

                                if (response.raw().code() == 200) {
                                    cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);

                                    if (cuttingToolBind != null) {
                                        tv00.setText(cuttingToolBind.getCuttingTool().getBusinessCode());
                                        tv01.setText(cuttingToolBind.getBladeCode());
                                    } else {
                                        Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    createAlertDialog(C01S007_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S007_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

}
