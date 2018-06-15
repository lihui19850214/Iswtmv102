package com.icomp.Iswtmv10.v01c01.c01s004;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.constants.GrindingEnum;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.pojo.RfidContainer;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.OutApplyVO;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SearchOutLiberaryVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import com.icomp.common.utils.FCBCodeHandler;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 刀具出库页面2-旧刀
 */
public class c01s004_003_1Activity extends CommonActivity {


    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;

    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.tvScan)
    TextView tvScan;

    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnNext)
    Button btnNext;

    private Retrofit retrofit;

    // 输入的刀身码数量
    int bladeCodeNum = 0;

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

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            searchOutLiberaryVOList = (List<SearchOutLiberaryVO>) paramMap.get("searchOutLiberaryVOList");
            searchOutLiberaryVO = (SearchOutLiberaryVO) paramMap.get("searchOutLiberaryVO");
            djOutapplyAkp = (DjOutapplyAkp) paramMap.get("djOutapplyAkp");

            tv01.setText(djOutapplyAkp.getUnitqty());
            tv02.setText(bladeCodeNum+"");

            showDialog();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick({R.id.btnReturn, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                Intent intent = new Intent(c01s004_003_1Activity.this, c01s004_003Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:
                if (Integer.parseInt(djOutapplyAkp.getUnitqty()) != rfidMap.size()) {
                    createAlertDialog(this, "请确认出库数量和刀身码数量", Toast.LENGTH_SHORT);
                } else {

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
                                public void fail() {
                                }
                            });
                        }

                        @Override
                        public void fail() {
                        }
                    });
                }
                break;
            case R.id.tvScan:
                showDialog();
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

                AuthCustomerVO llAuthCustomerVO = new AuthCustomerVO();
                AuthCustomerVO kzAuthCustomerVO = new AuthCustomerVO();

                llAuthCustomerVO.setCode(llAuthCustomerVO.getCode());
                kzAuthCustomerVO.setCode(kzAuthCustomerVO.getCode());
                // 领料
                outApplyVO.setLlAuthCustomerVO(llAuthCustomerVO);
                // 科长
                outApplyVO.setKzAuthCustomerVO(kzAuthCustomerVO);
            } else {
                createAlertDialog(c01s004_003_1Activity.this, getString(R.string.authorizedNumberError), Toast.LENGTH_SHORT);
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
                createAlertDialog(c01s004_003_1Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            }


            List<CuttingToolBind> cuttingToolBindsList = new ArrayList<>();

            Set<String> rfids = rfidMap.keySet();
            for (String rfid : rfids) {
                RfidContainer rfidContainer = new RfidContainer();
                rfidContainer.setLaserCode(rfid);

                CuttingToolBind cuttingToolBind = new CuttingToolBind();
                cuttingToolBind.setRfidContainer(rfidContainer);// 标签
                cuttingToolBind.setBladeCode(rfidMap.get(rfid));// 刀身码

                cuttingToolBindsList.add(cuttingToolBind);
            }


            outApplyVO.setCuttingToolBinds(cuttingToolBindsList);
            outApplyVO.setApplyno(djOutapplyAkp.getApplyno());
            outApplyVO.setMtlCode(FCBCodeHandler.fcbCodeHandler(searchOutLiberaryVO.getCuttingtollBusinessCode()));


            String jsonStr = objectToJson(outApplyVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> outApply = iRequest.outApply(body);
            outApply.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(c01s004_003_1Activity.this, c01s004_004Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(c01s004_003_1Activity.this, response.errorBody().string(), Toast.LENGTH_SHORT);
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
                    createAlertDialog(c01s004_003_1Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    //-----------------------------扫描开始-------------------------------

    /**
     * 开始扫描
     */
    private void scan(String bladeCode) {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnReturn.setClickable(false);
            btnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            ScanThread scanThread = new ScanThread(bladeCode);
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 扫描线程,将扫描结果进行网络请求
     */
    private class ScanThread extends Thread {

        private String bladeCode;

        public ScanThread(String bladeCode) {
            this.bladeCode = bladeCode;
        }

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

                        if (rfidMap.containsKey(rfidString)) {
                            // 重复扫描
                            Toast.makeText(getApplicationContext(), "重复扫描", Toast.LENGTH_SHORT).show();
                        } else {
                            rfidMap.put(rfidString, bladeCode);
                            bladeCodeNum++;
                            addLayout(searchOutLiberaryVO.getCuttingtollBusinessCode(), bladeCode, rfidString);
                        }
                    }
                });
            }
        }
    }
    //-----------------------------扫描结束-------------------------------

    //查询弹框
    private PopupWindow addPopupWindow;

    /**
     * 显示数据提示dialog
     */
    //显示材料号和修磨数量的弹框
    private void showDialog() {
        if (null == addPopupWindow || !addPopupWindow.isShowing()) {
            //点击查询按钮以后，设置扫描按钮不可用
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View view = layoutInflater.inflate(R.layout.dialog_c01s004_003_1, null);
            addPopupWindow = new PopupWindow(view, (int) (screenWidth * 0.8), (int) (screenHeight * 0.4));
            addPopupWindow.setFocusable(true);
            addPopupWindow.setOutsideTouchable(false);
            addPopupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

            final EditText et_bladeCode = (EditText) view.findViewById(R.id.et_bladeCode);
            et_bladeCode.setTransformationMethod(new AllCapTransformationMethod());

            Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
            Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPopupWindow.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null == et_bladeCode.getText().toString().trim() || "".equals(et_bladeCode.getText().toString().trim())) {
                        createAlertDialog(c01s004_003_1Activity.this, "请输入刀身码", Toast.LENGTH_LONG);
                    } else {
                        addPopupWindow.dismiss();
                        scan(et_bladeCode.getText().toString().trim());
                    }
                }
            });
        }
    }


    Map<String, String> rfidMap = new HashMap<>();

    /**
     * 添加布局
     */
    private void addLayout(final String wuliaohao, String bladeCode, final String rfid) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_chuku, null);

        final TextView tvWuliaohao = (TextView) mLinearLayout.findViewById(R.id.tvWuliaohao);//物料号
        TextView tvBladeCode = (TextView) mLinearLayout.findViewById(R.id.tvBladeCode);//刀身码
        TextView tvRfidContain = (TextView) mLinearLayout.findViewById(R.id.tvRfidContain);
        ImageView tvRemove = (ImageView) mLinearLayout.findViewById(R.id.tvRemove);

        tvWuliaohao.setText(wuliaohao);
        tvBladeCode.setText(bladeCode);
        tvRfidContain.setText(rfid);

        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bladeCodeNum--;
                rfidMap.remove(rfid);
                llContainer.removeView(mLinearLayout);
            }
        });

        llContainer.addView(mLinearLayout);


        Message message = new Message();
        //修改刀身码数量的handler
        bladeCodeHandler.sendMessage(message);
    }

    //显示出库单号详细信息
    @SuppressLint("HandlerLeak")
    Handler bladeCodeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tv02.setText(bladeCodeNum+"");
        }
    };



}
