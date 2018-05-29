package com.icomp.Iswtmv10.v01c01.c01s019;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.OutSideVO;
import com.apiclient.vo.SharpenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
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
 * Created by FanLL on 2017/7/4.
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

    OutSideVO outSideVO = new OutSideVO();
    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s019_002);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();


        Map<String, Object> paramMap2 = PARAM_MAP.get(2);
        if (paramMap2 != null) {
            outSideVO = (OutSideVO) paramMap2.get("outSideVO");
            rfidToMap = (Map<String, CuttingToolBind>) paramMap2.get("rfidToMap");

            for (SharpenVO sharpenVO : outSideVO.getSharpenVOS()) {
                addLayout(sharpenVO.getCuttingToolBusinessCode(), sharpenVO.getCuttingToolBladeCode(), sharpenVO.getCount().toString());
            }
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
                authorizationWindow(1, new AuthorizationWindowCallBack() {
                    @Override
                    public void success(List<AuthCustomer> authorizationList) {
                        requestData(authorizationList);
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
        if (laserCode == null || "".equals(laserCode)) {
            tvsingleProductCode.setText("-");
            tvNum.setText(num);
        } else {
            tvsingleProductCode.setText(laserCode);
            tvNum.setText("-");
        }

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
    private void requestData(List<AuthCustomer> authorizationList) {
        loading.show();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> headsMap = new HashMap<>();

        // 授权信息集合
        List<ImpowerRecorder> impowerRecorderList = new ArrayList<>();
        // 授权信息
        ImpowerRecorder impowerRecorder = new ImpowerRecorder();

        try {
            // 需要授权信息
            if (is_need_authorization && authorizationList != null) {
                //设定用户访问信息
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                String userInfoJson = sharedPreferences.getString("loginInfo", null);

                AuthCustomer authCustomer = mapper.readValue(userInfoJson, AuthCustomer.class);

                Set<String> rfids = rfidToMap.keySet();
                for (String rfid : rfids) {
                    CuttingToolBind cuttingToolBind = rfidToMap.get(rfid);
                    impowerRecorder = new ImpowerRecorder();

                    // ------------ 授权信息 ------------
                    impowerRecorder.setToolCode(cuttingToolBind.getCuttingTool().getBusinessCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(rfid);// rfid标签
                    impowerRecorder.setOperatorUserCode(authCustomer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authorizationList.get(0).getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.Cutting_tool_OutSide.getKey().toString());//操作key

//                impowerRecorder.setOperatorUserName(URLEncoder.encode(authCustomer.getName(),"utf-8"));//操作者姓名
//                impowerRecorder.setImpowerUserName(URLEncoder.encode(authorizationList.get(0).getName(),"utf-8"));//授权人名称
//                impowerRecorder.setOperatorValue(URLEncoder.encode(OperationEnum.SynthesisCuttingTool_Exchange.getName(),"utf-8"));//操作者code

                    impowerRecorderList.add(impowerRecorder);
                }
            }
            headsMap.put("impower", mapper.writeValueAsString(impowerRecorderList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRequest iRequest = retrofit.create(IRequest.class);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(outSideVO);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> addOutsideFactory = iRequest.addOutsideFactory(body, headsMap);

        addOutsideFactory.enqueue(new MyCallBack<String>() {
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
    }


}