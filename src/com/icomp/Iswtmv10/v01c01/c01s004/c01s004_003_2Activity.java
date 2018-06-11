package com.icomp.Iswtmv10.v01c01.c01s004;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.vo.OutApplyVO;
import com.apiclient.vo.SearchOutLiberaryVO;
import com.apiclient.vo.SynthesisCuttingToolInitVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刀具出库页面2-新刀
 */
public class c01s004_003_2Activity extends CommonActivity {


    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;

    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnNext)
    Button btnNext;

    private Retrofit retrofit;

    // 出库订单
    List<SearchOutLiberaryVO> searchOutLiberaryVOList = new ArrayList<>();
    SearchOutLiberaryVO searchOutLiberaryVO = new SearchOutLiberaryVO();
    DjOutapplyAkp djOutapplyAkp = new DjOutapplyAkp();
    OutApplyVO outApplyVO = new OutApplyVO();

    // 授权信息: lingliao:领料签收；kezhang:科长签收；
    Map<String, AuthCustomer> authCustomerMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s004_003_1);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        initView();
        // 出库数量==已扫描数量(标签不需要验证)，如果满足就挤掉第一个
    }

    /**
     * 将上一画面的信息展示到当前画面，进行信息确认
     */
    private void initView() {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        String jsonStr = "{}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getOrders = iRequest.getOrders(body);

        getOrders.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        searchOutLiberaryVOList = jsonToObject(response.body(), List.class, SearchOutLiberaryVO.class);
                        if (searchOutLiberaryVOList == null || searchOutLiberaryVOList.size() == 0) {
                            searchOutLiberaryVOList = new ArrayList<>();
                            Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        createAlertDialog(c01s004_003_2Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                createAlertDialog(c01s004_003_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
    }

    @OnClick({R.id.btnReturn, R.id.btnNext, R.id.ll_02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                finish();
                break;
            case R.id.btnNext:
                //如果取TEXT值则可以直接取:outOrder.getSelectedItem.ToString()或者:((CItem)outOrder.getSelectedItem).getValue();
                String orderText = tv01.getText().toString();

                if (orderText == null || "".equals(orderText)) {
                    createAlertDialog(this, "请选择要出库的单号", Toast.LENGTH_SHORT);
                    return;
                }

                // 需要授权
                is_need_authorization = true;

                authorizationWindow("领料授权签收", new AuthorizationWindowCallBack() {
                    @Override
                    public void success(AuthCustomer authCustomer) {
                        authCustomerMap.put("lingliao", authCustomer);

                        authorizationWindow("科长授权签收", new AuthorizationWindowCallBack() {
                            @Override
                            public void success(AuthCustomer authCustomer) {
                                authCustomerMap.put("kezhang", authCustomer);
                                requestData(authCustomerMap);
                            }

                            @Override
                            public void fail() {}
                        });
                    }

                    @Override
                    public void fail() {}
                });
                break;
            default:
        }
    }

    /**
     * 将出库单号数据提交
     */
    private void requestData(Map<String, AuthCustomer> authCustomerMap) {
        try {
            loading.show();

            if (authCustomerMap != null) {
                AuthCustomer authCustomerLingliao = authCustomerMap.get("lingliao");
                AuthCustomer authCustomerKezhang = authCustomerMap.get("kezhang");
                // 领料
                outApplyVO.setLinglOperatorRfidCode(authCustomerLingliao.getRfidContainer().getLaserCode());
                // 科长
                outApplyVO.setKezhangRfidCode(authCustomerKezhang.getRfidContainer().getLaserCode());
            } else {
                createAlertDialog(c01s004_003_2Activity.this, getString(R.string.authorizedNumberError), Toast.LENGTH_SHORT);
                return;
            }

            try {
                //设定用户访问信息
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                String userInfoJson = sharedPreferences.getString("loginInfo", null);

                AuthCustomer authCustomer = jsonToObject(userInfoJson, AuthCustomer.class);
                outApplyVO.setKuguanOperatorCode(authCustomer.getCode());// 操作者code
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(c01s004_003_2Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            }

            IRequest iRequest = retrofit.create(IRequest.class);

            outApplyVO.setDjOutapplyAkp(djOutapplyAkp);

            String jsonStr = objectToJson(outApplyVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> outApply = iRequest.outApply(body);
            outApply.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(c01s004_003_2Activity.this, c01s004_004Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(c01s004_003_2Activity.this, response.errorBody().string(), Toast.LENGTH_SHORT);
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
                    createAlertDialog(c01s004_003_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    int position = 0;
    /**
     * 添加布局
     */
    private void addLayout(final String cailiao, String laserCode, final String rfid, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_chukubind, null);

        final TextView tvWuliao = (TextView) mLinearLayout.findViewById(R.id.tvWuliao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        TextView tvBind = (TextView) mLinearLayout.findViewById(R.id.tvBind);

        tvWuliao.setText(cailiao);
        tvsingleProductCode.setText(laserCode);

        tvWuliao.setTag(position);
        mLinearLayout.setTag(position);

        tvBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        position++;
        llContainer.addView(mLinearLayout);
    }

    //-----------------------------扫描开始-------------------------------
    /**
     * 开始扫描
     */
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
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

    /**
     * 扫描线程,将扫描结果进行网络请求
     */
    private class ScanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000D3F3C";// TODO 生产环境需要删除
            if ("close".equals(rfidString)) {
                btnReturn.setClickable(true);
                btnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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

                    SynthesisCuttingToolInitVO synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
                    synthesisCuttingToolInitVO.setRfidCode(rfidString);

                    String jsonStr = objectToJson(synthesisCuttingToolInitVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Map<String, String> headsMap = new HashMap<>();
                    headsMap.put("impower", OperationEnum.SynthesisCuttingTool_UnConfig.getKey().toString());

                    Call<String> getBind = iRequest.getBind(body, headsMap);
                    getBind.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                String inpower = response.headers().get("impower");

                                if (response.raw().code() == 200) {
//                                    synthesisCuttingToolBind = jsonToObject(response.body(), SynthesisCuttingToolBind.class);
//                                    synthesisCuttingToolBindRFID = rfidString;
//
//                                    if (synthesisCuttingToolBind != null) {
//                                        setTextViewHandler(inpower);
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
//                                    }
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    createAlertDialog(c01s004_003_2Activity.this, errorStr, Toast.LENGTH_LONG);
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != loading && loading.isShowing()) {
                                        loading.dismiss();
                                    }
                                    createAlertDialog(c01s004_003_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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



    public void setTextViewHandler(String inpower) {
        try {
            Map<String, String> inpowerMap = jsonToObject(inpower, Map.class);

            // 判断是否显示提示框
            if ("1".equals(inpowerMap.get("type"))) {
                // 是否需要授权 true为需要授权；false为不需要授权
                is_need_authorization = false;


            } else if ("2".equals(inpowerMap.get("type"))) {
                is_need_authorization = true;
                exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                    @Override
                    public void confirm() {

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
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //-----------------------------扫描结束-------------------------------

}
