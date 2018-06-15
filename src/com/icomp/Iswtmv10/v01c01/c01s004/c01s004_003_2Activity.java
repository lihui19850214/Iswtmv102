package com.icomp.Iswtmv10.v01c01.c01s004;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.pojo.RfidContainer;
import com.apiclient.vo.AuthCustomerVO;
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
import com.icomp.common.utils.FCBCodeHandler;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 刀具出库页面2-新刀
 */
public class c01s004_003_2Activity extends CommonActivity {


    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnNext)
    Button btnNext;
    private Retrofit retrofit;

    // 出库数
    int outageNumber = 0;
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
        setContentView(R.layout.activity_c01s004_003_2);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        // TODO 调试数据
//        bladeCodeNum = 2;
//        rfidSet.add("rfid1");
//        rfidSet.add("rfid2");

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            searchOutLiberaryVOList = (List<SearchOutLiberaryVO>) paramMap.get("searchOutLiberaryVOList");
            searchOutLiberaryVO = (SearchOutLiberaryVO) paramMap.get("searchOutLiberaryVO");
            djOutapplyAkp = (DjOutapplyAkp) paramMap.get("djOutapplyAkp");

            outageNumber = Integer.parseInt(djOutapplyAkp.getUnitqty());
            tv01.setText(outageNumber+"");
            tv02.setText(bladeCodeNum+"");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }

        // 出库数量==已扫描数量(标签不需要验证)，如果满足就挤掉第一个
    }


    @OnClick({R.id.btnReturn, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                Intent intent = new Intent(c01s004_003_2Activity.this, c01s004_003Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:

                if (outageNumber != bladeCodeNum) {
                    createAlertDialog(this, "请继续扫描刀盒上的标签", Toast.LENGTH_SHORT);
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
                            public void fail() {
                            }
                        });
                    }

                    @Override
                    public void fail() {
                    }
                });
                break;
            case R.id.tvScan:
                scan();
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

                llAuthCustomerVO.setCode(authCustomerLingliao.getCode());
                kzAuthCustomerVO.setCode(authCustomerKezhang.getCode());
                // 领料
                outApplyVO.setLlAuthCustomerVO(llAuthCustomerVO);
                // 科长
                outApplyVO.setKzAuthCustomerVO(kzAuthCustomerVO);
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


            List<CuttingToolBind> cuttingToolBindsList = new ArrayList<>();

            for (String rfid : rfidSet) {
                RfidContainer rfidContainer = new RfidContainer();
                rfidContainer.setLaserCode(rfid);

                CuttingToolBind cuttingToolBind = new CuttingToolBind();
                cuttingToolBind.setRfidContainer(rfidContainer);// 标签

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

    //-----------------------------扫描开始-------------------------------

    Set<String> rfidSet = new HashSet<>();
    List<String> rfidList = new ArrayList<>();

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

                        if (rfidSet.contains(rfidString)) {
                            // 重复扫描
                            Toast.makeText(getApplicationContext(), "重复扫描", Toast.LENGTH_SHORT).show();
                        } else {
                            if (bladeCodeNum < Integer.parseInt(djOutapplyAkp.getUnitqty())) {
                                bladeCodeNum++;
                                tv02.setText(bladeCodeNum+"");
                                rfidSet.add(rfidString);
                            } else {
                                rfidSet.remove(rfidList.remove(0));

                                rfidSet.add(rfidString);
                                rfidList.add(rfidString);
                            }
                        }
                    }
                });

            }
        }
    }

    //-----------------------------扫描结束-------------------------------

}
