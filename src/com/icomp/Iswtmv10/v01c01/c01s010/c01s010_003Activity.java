package com.icomp.Iswtmv10.v01c01.c01s010;
/**
 * 刀具换装页面3
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

public class c01s010_003Activity extends CommonActivity {

    @BindView(R.id.tlContainer)
    LinearLayout mTlContainer;
    @BindView(R.id.btnReturn)
    Button mBtnReturn;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvShenqingRen)
    TextView tvShenqingRen;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.activity_c01s010_003)
    LinearLayout activityC01s010003;
    @BindView(R.id.tv_02)
    TextView tv02;


    private Retrofit retrofit;


    // 刀身码
    String bladeCode = "";
    // 合成刀标签
    String synthesisCuttingToolConfigRFID = "";
    // 合成刀配置
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    // 合成刀真实数据
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();

    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();

    // 钻头材料号(现在叫物料号)
    Set<String> drillingBitSet = new HashSet<>();
    // 真实数据的材料号(现在叫物料号)
    Set<String> realDataSet = new HashSet<>();
    // 合成刀真实数据：key材料号(现在叫物料号),value(DownCuttingToolVO)
    Map<String, DownCuttingToolVO> downCuttingToolVOMap = new HashMap<>();
    // 合成刀换上数据：key材料号(现在叫物料号),value(UpCuttingToolVO)
    Map<String, UpCuttingToolVO> upCuttingToolVOMap = new HashMap<>();
    // 当前显示的合成刀记录：key(材料号,现在叫物料号),value(显示的值)
    Map<String, String> displaySyntheticKnifeMap = new HashMap<>();

    // 换装数量
    private List<UpCuttingToolVO> upCuttingToolVOList = new ArrayList<>();
    // 丢刀数量
    private List<DownCuttingToolVO> downCuttingToolVOList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s010_003);
        ButterKnife.bind(this);
        retrofit = RetrofitSingle.newInstance();


        Map<String, Object> paramMap = PARAM_MAP.get(2);

        bladeCode = (String) paramMap.get("bladeCode");
        synthesisCuttingToolConfigRFID = (String) paramMap.get("synthesisCuttingToolConfigRFID");
        synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
        synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");
        rfidSet = (Set<String>) paramMap.get("rfidSet");

        // 合成刀具编码，如果取值不对，使用synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode()
        tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());
        // 刀身码
        tv02.setText(bladeCode);

        drillingBitSet = (Set<String>) paramMap.get("drillingBitSet");// 钻头材料号(现在叫物料号)
        realDataSet = (Set<String>) paramMap.get("realDataSet");// 真实数据的材料号(现在叫物料号)
        downCuttingToolVOMap = (Map<String, DownCuttingToolVO>) paramMap.get("downCuttingToolVOMap");// 合成刀真实数据：key材料号(现在叫物料号),value(DownCuttingToolVO)
        upCuttingToolVOMap = (Map<String, UpCuttingToolVO>) paramMap.get("upCuttingToolVOMap");// 合成刀换上数据：key材料号(现在叫物料号),value(UpCuttingToolVO)
        displaySyntheticKnifeMap = (Map<String, String>) paramMap.get("displaySyntheticKnifeMap");// 当前显示的合成刀记录：key(材料号,现在叫物料号),value(显示的值)

        for (int i = 0; i < synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList().size(); i++) {
            addLayout(synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList().get(i));
        }
    }

    @OnClick({R.id.btnReturn, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                Intent intent = new Intent(c01s010_003Activity.this, c01s010_002Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnConfirm:
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


    private void addLayout(final SynthesisCuttingToolLocationConfig config) {
        try {
            String daojuType = "";
            // 是否可编辑 true可编辑；false不可编辑；钻头和辅具都不能编辑
            boolean editable = true;

            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(config.getCuttingTool().getConsumeType())) {
                    editable = false;
                    daojuType = CuttingToolConsumeTypeEnum.griding_zt.getName();
                } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(config.getCuttingTool().getConsumeType())) {
                    daojuType = CuttingToolConsumeTypeEnum.griding_dp.getName();
                } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(config.getCuttingTool().getConsumeType())) {
                    daojuType = CuttingToolConsumeTypeEnum.single_use_dp.getName();
                } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(config.getCuttingTool().getConsumeType())) {
                    daojuType = CuttingToolConsumeTypeEnum.other.getName();
                }
            } else {
                return;
            }

            View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.tablerow_c01s010_003, null);

            TextView cailiaohao = (TextView) mLinearLayout.findViewById(R.id.cailiaohao);//材料号
            TextView daojuleixing = (TextView) mLinearLayout.findViewById(R.id.daojuleixing);//刀具类型
            TextView zongshuliang = (TextView) mLinearLayout.findViewById(R.id.zongshuliang);//总数量
            TextView huanzhuangshuliang = (TextView) mLinearLayout.findViewById(R.id.huanzhuangshuliang);//换装数量
            TextView diudaoshuliang = (TextView) mLinearLayout.findViewById(R.id.diudaoshuliang);//丢刀数量


            String businessCode = "";
            String displayBusinessCode = "";

            if (displaySyntheticKnifeMap.containsKey(config.getCuttingTool().getBusinessCode())) {
                businessCode = config.getCuttingTool().getBusinessCode();
                displayBusinessCode = displaySyntheticKnifeMap.get(config.getCuttingTool().getBusinessCode());
            } else if (config.getReplaceCuttingTool1() != null) {
                if (displaySyntheticKnifeMap.containsKey(config.getReplaceCuttingTool1().getBusinessCode())) {
                    businessCode = config.getReplaceCuttingTool1().getBusinessCode();
                    displayBusinessCode = displaySyntheticKnifeMap.get(config.getReplaceCuttingTool1().getBusinessCode());
                }
            } else if (config.getReplaceCuttingTool2() != null) {
                if (displaySyntheticKnifeMap.containsKey(config.getReplaceCuttingTool2().getBusinessCode())) {
                    businessCode = config.getReplaceCuttingTool2().getBusinessCode();
                    displayBusinessCode = displaySyntheticKnifeMap.get(config.getReplaceCuttingTool2().getBusinessCode());
                }
            } else if (config.getReplaceCuttingTool3() != null) {
                if (displaySyntheticKnifeMap.containsKey(config.getReplaceCuttingTool3().getBusinessCode())) {
                    businessCode = config.getReplaceCuttingTool3().getBusinessCode();
                    displayBusinessCode = displaySyntheticKnifeMap.get(config.getReplaceCuttingTool3().getBusinessCode());
                }
            }


            cailiaohao.setText(displayBusinessCode);//"显示的材料号"
            cailiaohao.setTag(businessCode);//"材料号"
            daojuleixing.setText(daojuType);//刀具类型
            zongshuliang.setText(config.getCount()+"");//"总数量"

            UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(businessCode);
            huanzhuangshuliang.setText(upCuttingToolVO.getUpCount()+"");//"换装数量"
            DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(businessCode);
            diudaoshuliang.setText(downCuttingToolVO.getDownLostCount()+"");//"丢刀数量"


            mTlContainer.addView(mLinearLayout);
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


    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();
            upCuttingToolVOList = new ArrayList<>();
            downCuttingToolVOList = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
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

                    AuthCustomer customer = mapper.readValue(userInfoJson, AuthCustomer.class);

                    // ------------ 授权信息 ------------
                    impowerRecorder.setToolCode(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(synthesisCuttingToolConfigRFID);// 操作信息code
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Exchange.getKey().toString());//操作key

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(c01s010_003Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                return;
            }


            for (String businessCode : realDataSet) {
                DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(businessCode);
                if (downCuttingToolVO.getDownCount() > 0) {
                    downCuttingToolVOList.add(downCuttingToolVO);
                }
            }

            Set<String> keys = upCuttingToolVOMap.keySet();
            for (String key : keys) {
                UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(key);
                if (upCuttingToolVO.getUpCount() > 0) {
                    upCuttingToolVOList.add(upCuttingToolVO);
                }
            }


            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
            // 合成刀组装信息code编码
            synthesisCuttingToolBindVO.setCode(synthesisCuttingToolBind.getCode());

            ExChangeVO exChangeVO = new ExChangeVO();
            exChangeVO.setSynthesisCuttingToolBindVO(synthesisCuttingToolBindVO);
            exChangeVO.setDownCuttingToolVOS(downCuttingToolVOList);
            exChangeVO.setUpCuttingToolVOS(upCuttingToolVOList);

            String jsonStr = objectToJson(exChangeVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> exChange = iRequest.exChange(body, headsMap);

            exChange.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(c01s010_003Activity.this, c01s010_004Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(c01s010_003Activity.this, response.errorBody().string(), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        loading.dismiss();
                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    loading.dismiss();
                    createAlertDialog(c01s010_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    private String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

}
