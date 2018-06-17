package com.icomp.Iswtmv10.v01c01.c01s010;
/**
 * 刀具换装页面2
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.constants.OperationEnum;
import com.apiclient.dto.BindBladeDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
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

import java.util.*;

public class c01s010_002Activity extends CommonActivity {

    @BindView(R.id.tlContainer)
    LinearLayout mTlContainer;
    @BindView(R.id.btnReturn)
    Button mBtnReturn;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvShenqingRen)
    TextView tvShenqingRen;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.activity_c01s010_002)
    LinearLayout activityC01s010002;
    @BindView(R.id.cbDiudao)
    CheckBox cbDiudao;


    //扫描线程
    private scanThread scanThread;
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
    // 物料号对应的 config：key(材料号,现在叫物料号),value(config)
    Map<String, SynthesisCuttingToolLocationConfig> businessCodeToConfigMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s010_002);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap1 = PARAM_MAP.get(1);
            Map<String, Object> paramMap2 = PARAM_MAP.get(2);

            if (paramMap2 != null) {
                bladeCode = (String) paramMap2.get("bladeCode");
                synthesisCuttingToolConfigRFID = (String) paramMap2.get("synthesisCuttingToolConfigRFID");
                synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap2.get("synthesisCuttingToolConfig");
                synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap2.get("synthesisCuttingToolBind");
                rfidSet = (Set<String>) paramMap2.get("rfidSet");
                cbDiudao.setChecked((paramMap2.get("cbDiudao") == null) ? false : (Boolean) paramMap2.get("cbDiudao"));

                // 合成刀具编码，如果取值不对，使用synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode()
                tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());

                drillingBitSet = (Set<String>) paramMap2.get("drillingBitSet");// 钻头材料号(现在叫物料号)
                realDataSet = (Set<String>) paramMap2.get("realDataSet");// 真实数据的材料号(现在叫物料号)
                downCuttingToolVOMap = (Map<String, DownCuttingToolVO>) paramMap2.get("downCuttingToolVOMap");// 合成刀真实数据：key材料号(现在叫物料号),value(DownCuttingToolVO)
                upCuttingToolVOMap = (Map<String, UpCuttingToolVO>) paramMap2.get("upCuttingToolVOMap");// 合成刀换上数据：key材料号(现在叫物料号),value(UpCuttingToolVO)
                displaySyntheticKnifeMap = (Map<String, String>) paramMap2.get("displaySyntheticKnifeMap");// 当前显示的合成刀记录：key(材料号,现在叫物料号),value(显示的值)

                for (int i = 0; i < synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList().size(); i++) {
                    addLayout(synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList().get(i));
                }
                // 删除当前页面缓存的值
                PARAM_MAP.remove(2);
            } else if (paramMap1 != null) {
                bladeCode = (String) paramMap1.get("bladeCode");
                synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap1.get("synthesisCuttingToolConfig");
                synthesisCuttingToolConfigRFID = (String) paramMap1.get("synthesisCuttingToolConfigRFID");
                synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap1.get("synthesisCuttingToolBind");


                // 合成刀具编码，如果取值不对，使用synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode()
                tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());

                // 查询真实数据
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick({R.id.btnReturn, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnReturn:
                //跳转到库存盘点刀具信息详细页面
                Intent intent2 = new Intent(c01s010_002Activity.this, c01s010_001Activity.class);
                // 不清空页面之间传递的值
                intent2.putExtra("isClearParamMap", false);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnNext:
                // 检查数据是否正确
                if (!checkData()) {
                    createAlertDialog(c01s010_002Activity.this, "请确认组装或丢刀数量", Toast.LENGTH_SHORT);
                    return;
                }

                // 用于页面之间传值，新方法
                Map<String, Object> paramMap = new HashMap<>();

                paramMap.put("bladeCode", bladeCode);
                paramMap.put("synthesisCuttingToolConfigRFID", synthesisCuttingToolConfigRFID);
                paramMap.put("synthesisCuttingToolConfig", synthesisCuttingToolConfig);
                paramMap.put("synthesisCuttingToolBind", synthesisCuttingToolBind);
                paramMap.put("rfidSet", rfidSet);
                paramMap.put("cbDiudao", cbDiudao.isChecked());

                paramMap.put("drillingBitSet", drillingBitSet);// 钻头材料号(现在叫物料号)
                paramMap.put("realDataSet", realDataSet);// 真实数据的材料号(现在叫物料号)
                paramMap.put("downCuttingToolVOMap", downCuttingToolVOMap);// 合成刀真实数据：key材料号(现在叫物料号),value(DownCuttingToolVO)
                paramMap.put("upCuttingToolVOMap", upCuttingToolVOMap);// 合成刀换上数据：key材料号(现在叫物料号),value(UpCuttingToolVO)
                paramMap.put("displaySyntheticKnifeMap", displaySyntheticKnifeMap);// 当前显示的合成刀记录：key(材料号,现在叫物料号),value(显示的值)

                PARAM_MAP.put(2, paramMap);

                //跳转到库存盘点刀具信息详细页面
                Intent intent = new Intent(c01s010_002Activity.this, c01s010_003Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();

                break;
            default:
        }
    }

    private void init() {
        // 不为null继续操作
        if (synthesisCuttingToolBind != null) {
            // 不为null继续操作
            if (synthesisCuttingToolBind.getSynthesisCuttingToolLocationList() != null) {
                List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = synthesisCuttingToolBind.getSynthesisCuttingToolLocationList();

                for (SynthesisCuttingToolLocation synthesisCuttingToolLocation : synthesisCuttingToolLocationList) {
                    // TODO 如果钻头没有刀身码需要弹框输入刀身码，然后存储到起来，不填写刀身码不能往下走
                    // 卸下
                    DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                    downCuttingToolVO.setBusinessCode(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                    downCuttingToolVO.setDownCode(synthesisCuttingToolLocation.getCuttingTool().getCode());
                    downCuttingToolVO.setBladeCode(synthesisCuttingToolLocation.getCuttingToolBladeCode());
                    downCuttingToolVO.setDownCount(synthesisCuttingToolLocation.getCount());
                    downCuttingToolVO.setDownLostCount(0);
                    // 卸下数量
                    downCuttingToolVOMap.put(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode(), downCuttingToolVO);
                    realDataSet.add(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                }
            }

            setValue();
        }
    }

    /**
     * 检查数据是否正确，符合标准
     *
     * @return true为正确；false为不正确；
     */
    private boolean checkData() {
        // 未切换时，如果一个换装数都没改，不能提交数据
        boolean isEmpty = true;
        Set<String> keys = upCuttingToolVOMap.keySet();
        for (String key : keys) {
            if (upCuttingToolVOMap.get(key).getUpCount() > 0) {
                isEmpty = false;
                break;
            }
        }

        if (isEmpty) {
            return false;
        }

        List<SynthesisCuttingToolLocationConfig> SynthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

        for (SynthesisCuttingToolLocationConfig config : SynthesisCuttingToolLocationConfigList) {

            // 换上
            UpCuttingToolVO upCuttingToolVO = null;
            // 真实数据和丢刀数据
            DownCuttingToolVO downCuttingToolVO = null;

            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                // 总数
                int count = config.getCount();

                upCuttingToolVO = upCuttingToolVOMap.get(config.getCuttingTool().getBusinessCode());
                downCuttingToolVO = downCuttingToolVOMap.get(config.getCuttingTool().getBusinessCode());

                if (upCuttingToolVO.getUpCount() > count) {
                    return false;
                }

                if (downCuttingToolVO.getDownLostCount() > count) {
                    return false;
                }


                if (config.getReplaceCuttingTool1() != null) {
                    upCuttingToolVO = upCuttingToolVOMap.get(config.getReplaceCuttingTool1().getBusinessCode());
                    downCuttingToolVO = downCuttingToolVOMap.get(config.getReplaceCuttingTool1().getBusinessCode());

                    if (upCuttingToolVO.getUpCount() > count) {
                        return false;
                    }

                    if (downCuttingToolVO.getDownLostCount() > count) {
                        return false;
                    }
                }

                if (config.getReplaceCuttingTool2() != null) {
                    upCuttingToolVO = upCuttingToolVOMap.get(config.getReplaceCuttingTool2().getBusinessCode());
                    downCuttingToolVO = downCuttingToolVOMap.get(config.getReplaceCuttingTool2().getBusinessCode());

                    if (upCuttingToolVO.getUpCount() > count) {
                        return false;
                    }

                    if (downCuttingToolVO.getDownLostCount() > count) {
                        return false;
                    }
                }

                if (config.getReplaceCuttingTool3() != null) {
                    upCuttingToolVO = upCuttingToolVOMap.get(config.getReplaceCuttingTool3().getBusinessCode());
                    downCuttingToolVO = downCuttingToolVOMap.get(config.getReplaceCuttingTool3().getBusinessCode());

                    if (upCuttingToolVO.getUpCount() > count) {
                        return false;
                    }

                    if (downCuttingToolVO.getDownLostCount() > count) {
                        return false;
                    }
                }
            } else {
                continue;
            }
        }

        return true;
    }

    /**
     * 检查rfid数据是否已满足
     *
     * @param code 材料号
     * @return true为已满足；false为未满足；
     */
    private boolean checkRfidData(String code) {
        List<SynthesisCuttingToolLocationConfig> SynthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

        for (SynthesisCuttingToolLocationConfig config : SynthesisCuttingToolLocationConfigList) {
            // 换上
            UpCuttingToolVO upCuttingToolVO = null;

            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                // 总数
                int count = config.getCount();

                String businessCode = config.getCuttingTool().getBusinessCode();
                upCuttingToolVO = upCuttingToolVOMap.get(businessCode);

                if (code.equals(businessCode) && drillingBitSet.contains(businessCode) && upCuttingToolVO.getUpCount() == count) {
                    return true;
                }

                if (config.getReplaceCuttingTool1() != null) {
                    businessCode = config.getReplaceCuttingTool1().getBusinessCode();
                    upCuttingToolVO = upCuttingToolVOMap.get(businessCode);

                    if (code.equals(businessCode) && drillingBitSet.contains(businessCode) && upCuttingToolVO.getUpCount() == count) {
                        return true;
                    }
                }

                if (config.getReplaceCuttingTool2() != null) {
                    businessCode = config.getReplaceCuttingTool2().getBusinessCode();
                    upCuttingToolVO = upCuttingToolVOMap.get(businessCode);

                    if (code.equals(businessCode) && drillingBitSet.contains(businessCode) && upCuttingToolVO.getUpCount() == count) {
                        return true;
                    }
                }

                if (config.getReplaceCuttingTool3() != null) {
                    businessCode = config.getReplaceCuttingTool3().getBusinessCode();
                    upCuttingToolVO = upCuttingToolVOMap.get(businessCode);

                    if (code.equals(businessCode) && drillingBitSet.contains(businessCode) && upCuttingToolVO.getUpCount() == count) {
                        return true;
                    }
                }
            } else {
                continue;
            }
        }

        return false;
    }

    //扫描方法
    private void scan() {
//        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            mBtnReturn.setClickable(false);
            mBtnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread = new scanThread();
            scanThread.start();
//        } else {
//            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
//        }
    }

    //扫描线程
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
//            rfidString = singleScan();
            rfidString ="18000A00000FA626";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                mBtnReturn.setClickable(true);
                mBtnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScan.setClickable(true);
                        mBtnReturn.setClickable(true);
                        mBtnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });


                try {
                    if (rfidSet.contains(rfidString)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createAlertDialog(c01s010_002Activity.this, "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_LONG);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.show();
                            }
                        });

                        RfidContainerVO rfidContainerVO = new RfidContainerVO();
                        rfidContainerVO.setLaserCode(rfidString);

                        CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
//                        if (drillingBitSet != null && drillingBitSet.size() > 0) {
//                            Iterator<String> it = drillingBitSet.iterator();
//                            it.hasNext();
//                            String bc = it.next();
//
//                            // TODO 是否需要刀身码，需要确定
//
//                            cuttingToolBindVO.getBladeCode(bladeCode);
//                        }
                        cuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                        String jsonStr = objectToJson(cuttingToolBindVO);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                        //调用接口，查询合成刀具组成信息
                        IRequest iRequest = retrofit.create(IRequest.class);
                        Call<String> queryBindInfo = iRequest.queryBindInfo(body);
                        queryBindInfo.enqueue(new MyCallBack<String>() {
                            @Override
                            public void _onResponse(Response<String> response) {
                                try {
                                    if (response.raw().code() == 200) {
                                        CuttingToolBind cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);

                                        if (cuttingToolBind != null) {
                                            if (!checkRfidData(cuttingToolBind.getCuttingTool().getBusinessCode())) {
                                                // 判断根据标签扫得到的钻头是否在配置中存在
                                                if (drillingBitSet.contains(cuttingToolBind.getCuttingTool().getBusinessCode())) {
                                                    // 钻头未绑定刀身码
                                                    if (cuttingToolBind.getBladeCode() == null || "".equals(cuttingToolBind.getBladeCode())) {
                                                        businessCodeList.add(cuttingToolBind.getCuttingTool().getBusinessCode());
                                                        showDialog(rfidString, cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind);
                                                    } else {
                                                        drillingBitScanSwitch(cuttingToolBind.getCuttingTool().getBusinessCode(), 1, rfidString, cuttingToolBind.getBladeCode(), 0);
                                                    }
                                                } else {
                                                    createAlertDialog(c01s010_002Activity.this, "没有匹配的配置信息", Toast.LENGTH_SHORT);
                                                }
                                            } else {
                                                createAlertDialog(c01s010_002Activity.this, "组装数量已满足", Toast.LENGTH_SHORT);
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        createAlertDialog(c01s010_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                                        createAlertDialog(c01s010_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                                    }
                                });
                            }
                        });
                    }
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


    private void setValue() {
        try {
            List<SynthesisCuttingToolLocationConfig> SynthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

            for (SynthesisCuttingToolLocationConfig config : SynthesisCuttingToolLocationConfigList) {

                // 是否时钻头：true为钻头；false为非钻头；
                boolean isDrillingBit = false;
                // 判断是否是钻头
                // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                    // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                    if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(config.getCuttingTool().getConsumeType())) {
                        isDrillingBit = true;
                    }
                } else {
                    continue;
                }

                String mainBusinessCode = null;     // 主刀材料号
                String replaceBusinessCode1 = null; // 备用刀1材料号
                String replaceBusinessCode2 = null; // 备用刀2材料号
                String replaceBusinessCode3 = null; // 备用刀3材料号

                String realBusinessCode = null;     // 有真实数据的材料号
                //---------------------------------哪个配置信息为真实数据所使用，补充换上数据开始----------------------------------
                // 换上
                UpCuttingToolVO upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setBladeCode(config.getCuttingTool().getBusinessCode());
                upCuttingToolVO.setUpCode(config.getCuttingTool().getCode());
                upCuttingToolVO.setUpCount(0);
                upCuttingToolVOMap.put(config.getCuttingTool().getBusinessCode(), upCuttingToolVO);

                mainBusinessCode = config.getCuttingTool().getBusinessCode();

                businessCodeToConfigMap.put(mainBusinessCode, config);

                // 是钻头
                if (isDrillingBit) {
                    drillingBitSet.add(mainBusinessCode);
                }

                // 真实数据
                if (realDataSet.contains(mainBusinessCode)) {
                    realBusinessCode = mainBusinessCode;
                    displaySyntheticKnifeMap.put(mainBusinessCode, config.getCuttingTool().getBusinessCode());
                }

                // 备用刀1不为空
                if (config.getReplaceCuttingTool1() != null) {
                    replaceBusinessCode1 = config.getReplaceCuttingTool1().getBusinessCode();

                    // 是钻头
                    if (isDrillingBit) {
                        drillingBitSet.add(replaceBusinessCode1);
                    }

                    // 换上
                    upCuttingToolVO = new UpCuttingToolVO();
                    upCuttingToolVO.setBusinessCode(replaceBusinessCode1);
                    upCuttingToolVO.setUpCode(config.getReplaceCuttingTool1().getCode());
                    upCuttingToolVO.setUpCount(0);
                    upCuttingToolVOMap.put(replaceBusinessCode1, upCuttingToolVO);

                    // 真实数据
                    if (realDataSet.contains(replaceBusinessCode1)) {
                        realBusinessCode = replaceBusinessCode1;
                        displaySyntheticKnifeMap.put(replaceBusinessCode1, config.getReplaceCuttingTool1().getBusinessCode());
                    }

                    businessCodeToConfigMap.put(replaceBusinessCode1, config);
                }

                // 备用刀2不为空
                if (config.getReplaceCuttingTool2() != null) {
                    replaceBusinessCode2 = config.getReplaceCuttingTool2().getBusinessCode();

                    // 是钻头
                    if (isDrillingBit) {
                        drillingBitSet.add(replaceBusinessCode2);
                    }

                    // 换上
                    upCuttingToolVO = new UpCuttingToolVO();
                    upCuttingToolVO.setBusinessCode(replaceBusinessCode2);
                    upCuttingToolVO.setUpCode(config.getReplaceCuttingTool2().getCode());
                    upCuttingToolVO.setUpCount(0);
                    upCuttingToolVOMap.put(replaceBusinessCode2, upCuttingToolVO);

                    // 真实数据
                    if (realDataSet.contains(replaceBusinessCode2)) {
                        realBusinessCode = replaceBusinessCode2;
                        displaySyntheticKnifeMap.put(replaceBusinessCode2, config.getReplaceCuttingTool2().getBusinessCode());
                    }

                    businessCodeToConfigMap.put(replaceBusinessCode2, config);
                }

                // 备用刀3不为空
                if (config.getReplaceCuttingTool3() != null) {
                    replaceBusinessCode3 = config.getReplaceCuttingTool3().getBusinessCode();

                    // 是钻头
                    if (isDrillingBit) {
                        drillingBitSet.add(replaceBusinessCode3);
                    }

                    // 换上
                    upCuttingToolVO = new UpCuttingToolVO();
                    upCuttingToolVO.setBusinessCode(replaceBusinessCode3);
                    upCuttingToolVO.setUpCode(config.getReplaceCuttingTool3().getCode());
                    upCuttingToolVO.setUpCount(0);
                    upCuttingToolVOMap.put(replaceBusinessCode3, upCuttingToolVO);

                    // 真实数据
                    if (realDataSet.contains(replaceBusinessCode3)) {
                        realBusinessCode = replaceBusinessCode3;
                        displaySyntheticKnifeMap.put(replaceBusinessCode3, config.getReplaceCuttingTool3().getBusinessCode());
                    }

                    businessCodeToConfigMap.put(replaceBusinessCode3, config);
                }
                //---------------------------------哪个配置信息为真实数据所使用结束----------------------------------

                //---------------------------------主刀和备用刀指向同一个真实数据的引用开始----------------------------------
                DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(realBusinessCode);

                downCuttingToolVOMap.put(mainBusinessCode, downCuttingToolVO);

                if (replaceBusinessCode1 != null) {
                    downCuttingToolVOMap.put(replaceBusinessCode1, downCuttingToolVO);
                }
                if (replaceBusinessCode2 != null) {
                    downCuttingToolVOMap.put(replaceBusinessCode2, downCuttingToolVO);
                }
                if (replaceBusinessCode3 != null) {
                    downCuttingToolVOMap.put(replaceBusinessCode3, downCuttingToolVO);
                }
                //---------------------------------主刀和备用刀指向同一个真实数据的引用结束----------------------------------

                // 初始化数据
                addLayout(config);
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


    /**
     * 修改内存中表格
     *
     * @param code 材料号
     * @param num  数量
     * @param rfid 标签
     */
    private void addRfidData(String code, int num, String rfid, String bladeCode, Integer needBind) {
        try {
            for (int i = 0; i < mTlContainer.getChildCount(); i++) {
                // 外部行
                TableRow mTableRow = (TableRow) ((LinearLayout) mTlContainer.getChildAt(i)).getChildAt(0);

                TextView cailiaohao = (TextView) mTableRow.findViewById(R.id.cailiaohao);//材料号
                TextView daojuleixing = (TextView) mTableRow.findViewById(R.id.daojuleixing);//刀具类型
                TextView zongshuliang = (TextView) mTableRow.findViewById(R.id.zongshuliang);//总数量
                EditText huanzhuangshuliang = (EditText) mTableRow.findViewById(R.id.huanzhuangshuliang);//换装数量
                EditText diudaoshuliang = (EditText) mTableRow.findViewById(R.id.diudaoshuliang);//丢刀数量

                if (code.equals(cailiaohao.getTag().toString())) {
                    UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(code);
                    upCuttingToolVO.setUpCount(upCuttingToolVO.getUpCount() + num);
                    upCuttingToolVO.setRfidCode(rfid);
                    upCuttingToolVO.setBladeCode(bladeCode);

                    huanzhuangshuliang.setText(upCuttingToolVO.getUpCount()+"");


                    DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(code);
                    downCuttingToolVO.setDownRfidLaserCode(rfid);
                    if (needBind == 1) {
                        downCuttingToolVO.setNeedBind(needBind);
                    }


                    // 是否选择丢刀
                    if (cbDiudao.isChecked()) {
                        downCuttingToolVO.setDownLostCount(downCuttingToolVO.getDownLostCount() + num);

                        diudaoshuliang.setText(downCuttingToolVO.getDownLostCount()+"");
                    }
                }
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


    /**
     * 修改内存中表格组装数据
     *
     * @param code 材料号
     * @param num  数量
     */
    private void addZuzhuangData(String code, int num) {
        UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(code);
        upCuttingToolVO.setUpCount(num);
    }

    /**
     * 修改内存中表格丢刀数据
     *
     * @param code 材料号
     * @param num  数量
     */
    private void addDiudaoData(String code, int num) {
        DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(code);
        downCuttingToolVO.setDownLostCount(num);
    }


    private void addLayout(final SynthesisCuttingToolLocationConfig config) {
        try {
            String daojuType = "";
            // 是否可编辑 true可编辑；false不可编辑；钻头和辅具都不能编辑
            boolean editable = true;
            // 是否是钻头
            boolean isDrillingBit = false;

            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(config.getCuttingTool().getConsumeType())) {
                    editable = false;
                    isDrillingBit = true;
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

            // 判断是钻头
            if (drillingBitSet.contains(config.getCuttingTool().getBusinessCode())) {
                isDrillingBit = true;
            }

            final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.tablerow_c01s010_002, null);

            final TableRow tableRow = (TableRow) mLinearLayout.findViewById(R.id.tableRow);//行
            final TextView cailiaohao = (TextView) mLinearLayout.findViewById(R.id.cailiaohao);//材料号
            TextView daojuleixing = (TextView) mLinearLayout.findViewById(R.id.daojuleixing);//刀具类型
            TextView zongshuliang = (TextView) mLinearLayout.findViewById(R.id.zongshuliang);//总数量
            final EditText huanzhuangshuliang = (EditText) mLinearLayout.findViewById(R.id.huanzhuangshuliang);//换装数量
            final EditText diudaoshuliang = (EditText) mLinearLayout.findViewById(R.id.diudaoshuliang);//丢刀数量

            // 标记是不是钻头
            tableRow.setTag(isDrillingBit);

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
            //将光标设置在最后
            huanzhuangshuliang.setSelection(huanzhuangshuliang.getText().length());
            DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(businessCode);
            diudaoshuliang.setText(downCuttingToolVO.getDownLostCount()+"");//"丢刀数量"
            //将光标设置在最后
            diudaoshuliang.setSelection(diudaoshuliang.getText().length());


            cailiaohao.setClickable(true);
//            cailiaohao.setFocusable(true);
            // 增加TextView的点击事件，单击事件
            cailiaohao.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showPopupWindow(cailiaohao, config, new ShowPopupWindowCallBack() {
                        @Override
                        public void select(String selectBusinessCode) {

                            if (!selectBusinessCode.equals(cailiaohao.getTag().toString())) {

                                String realBusinessCode = getRealBusinessCode(config);

                                String textviewContent = "";
                                if (realDataSet.contains(selectBusinessCode)) {
                                    textviewContent = selectBusinessCode;
                                } else {
                                    textviewContent = selectBusinessCode + "(" + realBusinessCode + ")";
                                }

                                // 不是钻头 and 不是真实数据时换上数量为总数量
                                if (!drillingBitSet.contains(selectBusinessCode) && !realDataSet.contains(selectBusinessCode)) {
                                    UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(selectBusinessCode);
                                    upCuttingToolVO.setUpCount(config.getCount());
                                    upCuttingToolVO.setRfidCode(null);
                                    upCuttingToolVO.setBladeCode(null);
                                }

                                displaySyntheticKnifeMap.remove(cailiaohao.getTag().toString());
                                displaySyntheticKnifeMap.put(selectBusinessCode, textviewContent);

                                UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(cailiaohao.getTag().toString());
                                upCuttingToolVO.setUpCount(0);
                                upCuttingToolVO.setRfidCode(null);
                                upCuttingToolVO.setBladeCode(null);

                                DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(cailiaohao.getTag().toString());
                                downCuttingToolVO.setDownLostCount(0);
                                downCuttingToolVO.setDownRfidLaserCode(null);

                                //
                                Map<String, Object> paramMap = new HashMap<>();
                                paramMap.put("tableRow", tableRow);
                                paramMap.put("selectBusinessCode", selectBusinessCode);

                                Message message = new Message();
                                message.obj = paramMap;
                                cailiaohaoHandler.sendMessage(message);
                            }
                        }
                    });
                }
            });

            if (editable) {
                // 换装添加事件
                huanzhuangshuliang.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //输入文本之前的状态
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //输入文字中的状态，count是输入字符数
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String num = huanzhuangshuliang.getText().toString();
                        //输入文字后的状态
                        if (num == null || "".equals(num)) {
                            num = "0";
                        }

                        addZuzhuangData((String) cailiaohao.getTag(), Integer.parseInt(num));
                    }
                });
            } else {
                huanzhuangshuliang.setFocusable(false);
                huanzhuangshuliang.setFocusableInTouchMode(false);
            }

            if (editable) {
                // 丢刀添加事件
                diudaoshuliang.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //输入文本之前的状态
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //输入文字中的状态，count是输入字符数
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //                    Log.i("ceshi", et1.getText().toString()+"=="+editable.toString());
                        String num = diudaoshuliang.getText().toString();
                        //输入文字后的状态
                        if (num == null || "".equals(num)) {
                            num = "0";
                        }

                        addDiudaoData((String) cailiaohao.getTag(), Integer.parseInt(num));
                    }
                });
            } else {
                diudaoshuliang.setFocusable(false);
                diudaoshuliang.setFocusableInTouchMode(false);
            }

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

    //显示出库单号列表
    private void showPopupWindow(TextView tv, final SynthesisCuttingToolLocationConfig config, final ShowPopupWindowCallBack callBack) {
        // 材料号(现在叫物料号)列表数据
        final List<String> businessCodeList = new ArrayList<>();
        businessCodeList.add(config.getCuttingTool().getBusinessCode());
        if (config.getReplaceCuttingTool1() != null) {
            businessCodeList.add(config.getReplaceCuttingTool1().getBusinessCode());
        }

        if (config.getReplaceCuttingTool2() != null) {
            businessCodeList.add(config.getReplaceCuttingTool2().getBusinessCode());
        }

        if (config.getReplaceCuttingTool3() != null) {
            businessCodeList.add(config.getReplaceCuttingTool3().getBusinessCode());
        }


        View view = LayoutInflater.from(c01s010_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter myAdapter = new MyAdapter(businessCodeList);
        listView.setAdapter(myAdapter);

        int width = ((tv.getWidth()*3) > screenWidth) ? screenWidth : (tv.getWidth()*3);

        final PopupWindow popupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callBack.select(businessCodeList.get(i));

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(tv);
    }

    class MyAdapter extends BaseAdapter {

        List<String> businessCodeList;

        public MyAdapter(final List<String> businessCodeList) {
            this.businessCodeList = businessCodeList;
        }

        @Override
        public int getCount() {
            return this.businessCodeList.size();
        }

        @Override
        public Object getItem(int i) {
            return this.businessCodeList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s010_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(this.businessCodeList.get(i));
            return view1;
        }
    }

    interface ShowPopupWindowCallBack {
        public void select(String selectBusinessCode);
    }


    //修改材料号信息
    @SuppressLint("HandlerLeak")
    Handler cailiaohaoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Map<String, Object> paramMap = (Map<String, Object>) msg.obj;
            TableRow tableRow = (TableRow) paramMap.get("tableRow");
            String selectBusinessCode = (String) paramMap.get("selectBusinessCode");


            TextView cailiaohao = (TextView) tableRow.findViewById(R.id.cailiaohao);//材料号
            TextView daojuleixing = (TextView) tableRow.findViewById(R.id.daojuleixing);//刀具类型
            TextView zongshuliang = (TextView) tableRow.findViewById(R.id.zongshuliang);//总数量
            EditText huanzhuangshuliang = (EditText) tableRow.findViewById(R.id.huanzhuangshuliang);//换装数量
            EditText diudaoshuliang = (EditText) tableRow.findViewById(R.id.diudaoshuliang);//丢刀数量

            cailiaohao.setText(displaySyntheticKnifeMap.get(selectBusinessCode));
            cailiaohao.setTag(selectBusinessCode);
            huanzhuangshuliang.setText("0");
            diudaoshuliang.setText("0");

            // 判断是否不是钻头
            if (!drillingBitSet.contains(selectBusinessCode)) {
                // 判断是否是真实数据
                if (realDataSet.contains(selectBusinessCode)) {
                    huanzhuangshuliang.setFocusable(true);
                    huanzhuangshuliang.setFocusableInTouchMode(true);
                } else {
                    huanzhuangshuliang.setText(zongshuliang.getText());

                    huanzhuangshuliang.setFocusable(false);
                    huanzhuangshuliang.setFocusableInTouchMode(false);
                }
            }

            Map<String, Object> addRfidDataMap = (Map<String, Object>) paramMap.get("addRfidDataMap");
            if (addRfidDataMap != null) {
                String scanBusinessCode = (String) addRfidDataMap.get("selectBusinessCode");
                int num = (Integer) addRfidDataMap.get("num");
                String rfid = (String) addRfidDataMap.get("rfid");
                String bladeCode = (String) addRfidDataMap.get("bladeCode");
                int needBind = (Integer) addRfidDataMap.get("needBind");

                // 给哪个刀具类型增加组装数量，默认只给转头添加组装数量
                addRfidData(scanBusinessCode, num, rfid, bladeCode, needBind);
            }
        }
    };

    /**
     * 钻头扫描自动切换
     * @param scanBusinessCode
     * @param num
     * @param rfid
     * @param bladeCode
     * @param needBind
     */
    private void drillingBitScanSwitch(String scanBusinessCode, int num, String rfid, String bladeCode, int needBind) {
        rfidSet.add(rfid);

        // 扫描的标签返回的物料号与当前显示的物料号不一样，需要切换
        if (!displaySyntheticKnifeMap.containsKey(scanBusinessCode)) {
            // 返回钻头所在的行
            TableRow tableRow = getDrillingBitRow();
            TextView cailiaohao = (TextView) tableRow.findViewById(R.id.cailiaohao);//材料号

            // 物料号匹配的 config
            SynthesisCuttingToolLocationConfig config = businessCodeToConfigMap.get(scanBusinessCode);

            // 真实的物料号
            String realBusinessCode = getRealBusinessCode(config);

            // 显示的物料号名称
            String textviewContent = "";
            if (realDataSet.contains(scanBusinessCode)) {
                textviewContent = scanBusinessCode;
            } else {
                textviewContent = scanBusinessCode + "(" + realBusinessCode + ")";
            }

            displaySyntheticKnifeMap.remove(cailiaohao.getTag().toString());
            displaySyntheticKnifeMap.put(scanBusinessCode, textviewContent);

            UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(cailiaohao.getTag().toString());
            upCuttingToolVO.setUpCount(0);
            upCuttingToolVO.setRfidCode(null);
            upCuttingToolVO.setBladeCode(null);

            DownCuttingToolVO downCuttingToolVO = downCuttingToolVOMap.get(cailiaohao.getTag().toString());
            downCuttingToolVO.setDownLostCount(0);
            downCuttingToolVO.setDownRfidLaserCode(null);


            // 换装数据
            Map<String, Object> addRfidDataMap = new HashMap<>();
            addRfidDataMap.put("selectBusinessCode", scanBusinessCode);
            addRfidDataMap.put("num", num);
            addRfidDataMap.put("rfid", rfid);
            addRfidDataMap.put("bladeCode", bladeCode);
            addRfidDataMap.put("needBind", needBind);


            // 修改换装显示数据
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("tableRow", tableRow);
            paramMap.put("selectBusinessCode", scanBusinessCode);
            paramMap.put("addRfidDataMap", addRfidDataMap);


            Message message = new Message();
            message.obj = paramMap;
            cailiaohaoHandler.sendMessage(message);
        } else {
            // 给哪个刀具类型增加组装数量，默认只给转头添加组装数量
            addRfidData(scanBusinessCode, num, rfid, bladeCode, needBind);
        }
    }

    /**
     * 根据材料号返回当前显示的钻头行
     * @return
     */
    private TableRow getDrillingBitRow() {
        try {
            for (int i = 0; i < mTlContainer.getChildCount(); i++) {
                // 外部行
                TableRow mTableRow = (TableRow) ((LinearLayout) mTlContainer.getChildAt(i)).getChildAt(0);

                if ((Boolean) mTableRow.getTag()) {
                    return mTableRow;
                }
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

        return null;
    }

    /**
     * 获取真实数据的物料号
     * @param config
     * @return
     */
    private String getRealBusinessCode(SynthesisCuttingToolLocationConfig config) {
        String realBusinessCode = "";
        if (realDataSet.contains(config.getCuttingTool().getBusinessCode())) {
            realBusinessCode = config.getCuttingTool().getBusinessCode();
        }

        if (config.getReplaceCuttingTool1() != null && realDataSet.contains(config.getReplaceCuttingTool1().getBusinessCode())) {
            realBusinessCode = config.getReplaceCuttingTool1().getBusinessCode();
        }

        if (config.getReplaceCuttingTool2() != null && realDataSet.contains(config.getReplaceCuttingTool2().getBusinessCode())) {
            realBusinessCode = config.getReplaceCuttingTool2().getBusinessCode();
        }

        if (config.getReplaceCuttingTool3() != null && realDataSet.contains(config.getReplaceCuttingTool3().getBusinessCode())) {
            realBusinessCode = config.getReplaceCuttingTool3().getBusinessCode();
        }

        return realBusinessCode;
    }


    //-----------------------------------------添加刀身码开始--------------------------------------------
    // 只有转头的物料号下拉列表
    List<String> businessCodeList = new ArrayList<>();
    // 物料号选项
    String selectBusinessCode = "";

    /**
     * 显示数据提示dialog
     */
    //显示物料号和刀身码的弹框
    private void showDialog(final String rfid, final String businessCode, final CuttingToolBind cuttingToolBind) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_c01s011_002_1, null);
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialog).create();
        dialog.setView((this).getLayoutInflater().inflate(R.layout.dialog_c01s011_002_1, null));
        dialog.show();
        dialog.getWindow().setContentView(view);


        final EditText etBladeCode = (EditText) view.findViewById(R.id.et_bladeCode);

        final LinearLayout ll01 = (LinearLayout) view.findViewById(R.id.ll_01);
        final TextView tv01 = (TextView) view.findViewById(R.id.tv_01);

        if (businessCodeList != null && businessCodeList.size() > 0) {
            tv01.setText(businessCodeList.get(0));
            selectBusinessCode = businessCodeList.get(0);
        }

        // 物料号下拉列表
        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 收起软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etBladeCode.getWindowToken(), 0);

                View view = LayoutInflater.from(c01s010_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
                ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
                MyAdapter2 myAdapter = new MyAdapter2();
                listView.setAdapter(myAdapter);

                final PopupWindow popupWindow = new PopupWindow(view, ll01.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(new PaintDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);

                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            popupWindow.dismiss();
                            return true;
                        }
                        return false;
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        tv01.setText(businessCodeList.get(i));
                        selectBusinessCode = businessCodeList.get(i);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(ll01);
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == etBladeCode.getText() || "".equals(etBladeCode.getText().toString().trim())) {
                    createAlertDialog(c01s010_002Activity.this, "请输入刀身码", Toast.LENGTH_LONG);
                } else if (null == tv01.getText().toString().trim() || "".equals(tv01.getText().toString().trim())) {
                    createAlertDialog(c01s010_002Activity.this, "请选择物料号", Toast.LENGTH_LONG);
                } else {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.show();
                            }
                        });
                        // TODO 确认是否不需要修改 location
                        // 生成的刀身码
                        final String selectBladeCode = (selectBusinessCode + "-" + etBladeCode.getText().toString().trim());

                        CuttingToolBind bind = new CuttingToolBind();
                        bind.setCode(cuttingToolBind.getCode());
                        bind.setCuttingToolCode(cuttingToolBind.getCuttingToolCode());
                        bind.setBladeCode(selectBladeCode);


                        BindBladeDTO bindBladeDTO = new BindBladeDTO();
                        bindBladeDTO.setCuttingToolBind(bind);

                        String jsonStr = objectToJson(bindBladeDTO);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                        //调用接口，查询合成刀具组成信息
                        IRequest iRequest = retrofit.create(IRequest.class);
                        Call<String> bindBlade = iRequest.bindBlade(body);

                        bindBlade.enqueue(new MyCallBack<String>() {
                            @Override
                            public void _onResponse(Response<String> response) {
                                try {
                                    if (response.raw().code() == 200) {
                                        drillingBitScanSwitch(businessCode, 1, rfid, selectBladeCode, 1);
                                    } else {
                                        createAlertDialog(c01s010_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                                } finally {
                                    if (null != loading && loading.isShowing()) {
                                        loading.dismiss();
                                    }
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void _onFailure(Throwable t) {
                                if (null != loading && loading.isShowing()) {
                                    loading.dismiss();
                                }
                                dialog.dismiss();
                                createAlertDialog(c01s010_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });


        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.4));

    }

    //物料号下拉框的Adapter
    class MyAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return businessCodeList.size();
        }

        @Override
        public Object getItem(int i) {
            return businessCodeList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s010_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(businessCodeList.get(i));
            return view1;
        }
    }

}
