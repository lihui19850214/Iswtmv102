package com.icomp.Iswtmv10.v01c01.c01s019;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.apiclient.constants.OperationEnum;
import com.apiclient.dto.InFactoryDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.GrindingVO;
import com.apiclient.vo.OutSideVO;
import com.apiclient.vo.SharpenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;

import okhttp3.RequestBody;

import java.io.IOException;
import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 厂外修磨页面2
 */
public class C01S019_002Activity extends CommonActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llContainer)
    LinearLayout mLlContainer;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnNext)
    Button btnNext;


    private Retrofit retrofit;

    InFactoryDTO inFactoryDTO = new InFactoryDTO();
    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();

    private List<GrindingVO> sharpenVOList = new ArrayList<>();
    // 根据物料号对应刀身码
    private Map<String, String> businessCodeToBladeCodeMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s019_002);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap2 = PARAM_MAP.get(2);
            if (paramMap2 != null) {
                inFactoryDTO = (InFactoryDTO) paramMap2.get("inFactoryDTO");
                rfidToMap = (Map<String, CuttingToolBind>) paramMap2.get("rfidToMap");
                sharpenVOList = (List<GrindingVO>) paramMap2.get("sharpenVOList");
                businessCodeToBladeCodeMap = (Map<String, String>) paramMap2.get("businessCodeToBladeCodeMap");

                for (GrindingVO grindingVO : sharpenVOList) {
                    String bl = businessCodeToBladeCodeMap.get(grindingVO.getCuttingTool().getBusinessCode());
                    if (bl == null || "".equals(bl)) {
                        bl = "-";
                    }
                    String num = grindingVO.getGrindingCount().toString();
                    if ("0".equals(num)) {
                        num = "-";
                    }

                    addLayout(grindingVO.getCuttingTool().getBusinessCode(), bl, num);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                Intent intent = new Intent(this, C01S019_001Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:
                authorizationWindow(new AuthorizationWindowCallBack() {
                    @Override
                    public void success(AuthCustomer authCustomer) {
                        requestData(authCustomer);
                    }

                    @Override
                    public void fail() {

                    }
                });
                break;
        }
    }


    /**
     * 添加布局
     * @param cailiao 物料号
     * @param laserCode 刀身码
     * @param num 数量
     */
    private void addLayout(String cailiao, String laserCode, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_changwaixiumo2, null);

        TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);

        tvCaiLiao.setText(cailiao);
        if (laserCode != null && !"".equals(laserCode) && !"-".equals(laserCode) && (laserCode.indexOf("-") >= 0)) {
            tvsingleProductCode.setText(laserCode.split("-")[1]);
        } else {
            tvsingleProductCode.setText(laserCode);
        }
        tvNum.setText(num);

        mLlContainer.addView(mLinearLayout);
    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        Intent intent2 = getIntent();
//        if (intent2 == null) {
//            return;
//        } else {
//            Bundle bundle = intent2.getExtras();
//            if (bundle == null) {
//                return;
//            }
//            boolean isClear = bundle.getBoolean("isClear", false);
//            if (isClear) {
//                mLlContainer.removeAllViews();
//            }
//        }
//    }


    //提交添加场外刃磨
    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();

            Map<String, String> headsMap = new HashMap<>();

            // 授权信息集合
            List<ImpowerRecorder> impowerRecorderList = new ArrayList<>();
            // 授权信息
            ImpowerRecorder impowerRecorder = new ImpowerRecorder();

            try {
                // 需要授权信息
                if (is_need_authorization && authCustomer != null) {
                    //设定用户访问信息
                    @SuppressLint("WrongConstant")
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                    String userInfoJson = sharedPreferences.getString("loginInfo", null);

                    AuthCustomer customer = jsonToObject(userInfoJson, AuthCustomer.class);

                    Set<String> rfids = rfidToMap.keySet();
                    for (String rfid : rfids) {
                        CuttingToolBind cuttingToolBind = rfidToMap.get(rfid);
                        impowerRecorder = new ImpowerRecorder();

                        // ------------ 授权信息 ------------
                        impowerRecorder.setToolCode(cuttingToolBind.getCuttingTool().getBusinessCode());// 合成刀编码
                        impowerRecorder.setRfidLasercode(authCustomer.getRfidContainer().getLaserCode());// rfid标签
                        impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                        impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                        impowerRecorder.setOperatorKey(OperationEnum.Cutting_tool_OutSide.getKey().toString());//操作key

//                impowerRecorder.setOperatorUserName(URLEncoder.encode(authCustomer.getName(),"utf-8"));//操作者姓名
//                impowerRecorder.setImpowerUserName(URLEncoder.encode(authorizationList.get(0).getName(),"utf-8"));//授权人名称
//                impowerRecorder.setOperatorValue(URLEncoder.encode(OperationEnum.SynthesisCuttingTool_Exchange.getName(),"utf-8"));//操作者code

                        impowerRecorderList.add(impowerRecorder);
                    }
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(C01S019_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                return;
            }

            String jsonStr = objectToJson(inFactoryDTO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> outsideGrinding = iRequest.outsideGrinding(body, headsMap);

            outsideGrinding.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到成功详细页面
                            Intent intent = new Intent(C01S019_002Activity.this, C01S019_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S019_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S019_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


}