package com.icomp.Iswtmv10.v01c01.c01s009;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s010.c01s010_002Activity;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 刀具组装页面2
 */
public class C01S009_002Activity extends CommonActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tlContainer)
    LinearLayout mTlContainer;
    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnNext)
    Button btnNext;


    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private scanThread scanThread;


    List<UpCuttingToolVO> upCuttingToolVOList = new ArrayList<>();

    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();

    // 刀身码
    String bladeCode = "";
    // 合成刀标签
    String bladeCode_RFID = "";
    // 合成刀标签Code
    String synthesisCuttingToolBind_rfid_code = "";

    // 合成刀配置
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    // 合成刀真实数据
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();

    // 钻头材料号(现在叫物料号)
    Set<String> drillingBitSet = new HashSet<>();
    // 主刀数据的材料号(现在叫物料号)
    Set<String> mainKnifeSet = new HashSet<>();
    // 合成刀换上数据：key材料号(现在叫物料号),value(UpCuttingToolVO)
    Map<String, UpCuttingToolVO> upCuttingToolVOMap = new HashMap<>();
    // 物料号对应的 config：key(材料号,现在叫物料号),value(config)
    Map<String, SynthesisCuttingToolLocationConfig> businessCodeToConfigMap = new HashMap<>();
    // 当前显示的合成刀记录：key(材料号,现在叫物料号),value(显示的值)
    Map<String, String> displaySyntheticKnifeMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01_s009_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            bladeCode = (String) paramMap.get("bladeCode");
            bladeCode_RFID = (String) paramMap.get("bladeCode_RFID");
            synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
            synthesisCuttingToolBind_rfid_code = (String) paramMap.get("synthesisCuttingToolBind_rfid_code");
            synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");


            tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());

            setValue();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
                Intent intent2 = new Intent(C01S009_002Activity.this, C01S009_001Activity.class);
                // 不清空页面之间传递的值
                intent2.putExtra("isClearParamMap", false);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnNext:
                // 检查数据是否正确
                if (!checkData()) {
                    createAlertDialog(C01S009_002Activity.this, "请确认组装数量", Toast.LENGTH_SHORT);
                    return;
                }

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
            default:
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

                //---------------------------------哪个配置信息为真实数据所使用，补充换上数据开始----------------------------------
                // 换上
                UpCuttingToolVO upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setBusinessCode(config.getCuttingTool().getBusinessCode());
                upCuttingToolVO.setUpCode(config.getCuttingTool().getCode());
                upCuttingToolVO.setUpCount(0);


                mainBusinessCode = config.getCuttingTool().getBusinessCode();

                // 是钻头
                if (isDrillingBit) {
                    drillingBitSet.add(mainBusinessCode);
                } else {
                    upCuttingToolVO.setUpCount(config.getCount());
                }

                upCuttingToolVOMap.put(config.getCuttingTool().getBusinessCode(), upCuttingToolVO);
                businessCodeToConfigMap.put(mainBusinessCode, config);
                mainKnifeSet.add(mainBusinessCode);
                displaySyntheticKnifeMap.put(mainBusinessCode, config.getCuttingTool().getBusinessCode());


                // 备用刀1不为空
                if (config.getReplaceCuttingTool1() != null) {
                    replaceBusinessCode1 = config.getReplaceCuttingTool1().getBusinessCode();

                    // 换上
                    upCuttingToolVO = new UpCuttingToolVO();
                    upCuttingToolVO.setBusinessCode(replaceBusinessCode1);
                    upCuttingToolVO.setUpCode(config.getReplaceCuttingTool1().getCode());
                    upCuttingToolVO.setUpCount(0);

                    // 是钻头
                    if (isDrillingBit) {
                        drillingBitSet.add(replaceBusinessCode1);
                    } else {
                        upCuttingToolVO.setUpCount(config.getCount());
                    }

                    upCuttingToolVOMap.put(replaceBusinessCode1, upCuttingToolVO);

                    businessCodeToConfigMap.put(replaceBusinessCode1, config);
                }

                // 备用刀2不为空
                if (config.getReplaceCuttingTool2() != null) {
                    replaceBusinessCode2 = config.getReplaceCuttingTool2().getBusinessCode();

                    // 换上
                    upCuttingToolVO = new UpCuttingToolVO();
                    upCuttingToolVO.setBusinessCode(replaceBusinessCode2);
                    upCuttingToolVO.setUpCode(config.getReplaceCuttingTool2().getCode());
                    upCuttingToolVO.setUpCount(0);

                    // 是钻头
                    if (isDrillingBit) {
                        drillingBitSet.add(replaceBusinessCode1);
                    } else {
                        upCuttingToolVO.setUpCount(config.getCount());
                    }

                    upCuttingToolVOMap.put(replaceBusinessCode2, upCuttingToolVO);

                    businessCodeToConfigMap.put(replaceBusinessCode2, config);
                }

                // 备用刀3不为空
                if (config.getReplaceCuttingTool3() != null) {
                    replaceBusinessCode3 = config.getReplaceCuttingTool3().getBusinessCode();

                    // 换上
                    upCuttingToolVO = new UpCuttingToolVO();
                    upCuttingToolVO.setBusinessCode(replaceBusinessCode3);
                    upCuttingToolVO.setUpCode(config.getReplaceCuttingTool3().getCode());
                    upCuttingToolVO.setUpCount(0);

                    // 是钻头
                    if (isDrillingBit) {
                        drillingBitSet.add(replaceBusinessCode1);
                    } else {
                        upCuttingToolVO.setUpCount(config.getCount());
                    }

                    upCuttingToolVOMap.put(replaceBusinessCode3, upCuttingToolVO);

                    businessCodeToConfigMap.put(replaceBusinessCode3, config);
                }
                //---------------------------------哪个配置信息为真实数据所使用结束----------------------------------

                // 初始化数据
                addLayout(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(C01S009_002Activity.this, C01S009_001Activity.class);
                    startActivity(intent2);
                    finish();
                }
            });
        }
    }

    /**
     * 开始扫描
     */
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnCancel.setClickable(false);
            btnNext.setClickable(false);
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
//            rfidString ="liuzhuan_rfid2";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                btnCancel.setClickable(true);
                btnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScan.setClickable(true);
                        btnCancel.setClickable(true);
                        btnNext.setClickable(true);
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
                                createAlertDialog(C01S009_002Activity.this, "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_LONG);
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
                                                        drillingBitScanSwitch(cuttingToolBind.getCuttingTool().getBusinessCode(), 1, rfidString, cuttingToolBind.getBladeCode());
                                                    }
                                                } else {
                                                    createAlertDialog(C01S009_002Activity.this, "该标签与列表中钻头不匹配", Toast.LENGTH_SHORT);
                                                }
                                            } else {
                                                createAlertDialog(C01S009_002Activity.this, "组装数量已满足", Toast.LENGTH_SHORT);
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        createAlertDialog(C01S009_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                                        createAlertDialog(C01S009_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    /**
     * 检查数据是否正确，符合标准
     *
     * @return true为正确；false为不正确；
     */
    private boolean checkData() {
        List<SynthesisCuttingToolLocationConfig> SynthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

        for (SynthesisCuttingToolLocationConfig config : SynthesisCuttingToolLocationConfigList) {

            // 换上
            UpCuttingToolVO upCuttingToolVO = null;

            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                // 总数
                int count = config.getCount();

                upCuttingToolVO = upCuttingToolVOMap.get(config.getCuttingTool().getBusinessCode());

                if (displaySyntheticKnifeMap.containsKey(config.getCuttingTool().getBusinessCode()) && upCuttingToolVO.getUpCount() != count) {
                    return false;
                }

                if (config.getReplaceCuttingTool1() != null) {
                    upCuttingToolVO = upCuttingToolVOMap.get(config.getReplaceCuttingTool1().getBusinessCode());

                    if (displaySyntheticKnifeMap.containsKey(config.getReplaceCuttingTool1().getBusinessCode()) && upCuttingToolVO.getUpCount() != count) {
                        return false;
                    }
                }

                if (config.getReplaceCuttingTool2() != null) {
                    upCuttingToolVO = upCuttingToolVOMap.get(config.getReplaceCuttingTool2().getBusinessCode());

                    if (displaySyntheticKnifeMap.containsKey(config.getReplaceCuttingTool2().getBusinessCode()) && upCuttingToolVO.getUpCount() != count) {
                        return false;
                    }
                }

                if (config.getReplaceCuttingTool3() != null) {
                    upCuttingToolVO = upCuttingToolVOMap.get(config.getReplaceCuttingTool3().getBusinessCode());

                    if (displaySyntheticKnifeMap.containsKey(config.getReplaceCuttingTool3().getBusinessCode()) && upCuttingToolVO.getUpCount() != count) {
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


    /**
     * 添加布局
     */
    private void addLayout(final SynthesisCuttingToolLocationConfig config) {
        try {
            String daojuType = "";
            // 是否是钻头
            boolean isDrillingBit = false;
            // 是否显示物料号列表 true显示；false不显示；钻头不显示
            boolean isShowBusinessCode = true;

            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(config.getCuttingTool().getConsumeType())) {
                    isDrillingBit = true;
                    isShowBusinessCode = false;
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

            final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.tablerow_c01s009_002, null);

            final TableRow tableRow = (TableRow) mLinearLayout.findViewById(R.id.tableRow);//行
            final TextView cailiaohao = (TextView) mLinearLayout.findViewById(R.id.cailiaohao);//材料号
            TextView daojuleixing = (TextView) mLinearLayout.findViewById(R.id.daojuleixing);//刀具类型
            TextView zongshuliang = (TextView) mLinearLayout.findViewById(R.id.zongshuliang);//总数量
            final TextView huanzhuangshuliang = (TextView) mLinearLayout.findViewById(R.id.huanzhuangshuliang);//换装数量

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

            // 显示物料号列表
            if (isShowBusinessCode) {
                cailiaohao.setClickable(true);
//            cailiaohao.setFocusable(true);
                // 增加TextView的点击事件，单击事件
                cailiaohao.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showPopupWindow(cailiaohao, config, new ShowPopupWindowCallBack() {
                            @Override
                            public void select(String selectBusinessCode) {

                                if (!selectBusinessCode.equals(cailiaohao.getTag().toString())) {

                                    // 主刀物料号
                                    String mainBusinessCode = config.getCuttingTool().getBusinessCode();

                                    String textviewContent = "";
                                    if (mainKnifeSet.contains(selectBusinessCode)) {
                                        textviewContent = selectBusinessCode;
                                    } else {
                                        textviewContent = selectBusinessCode + "(" + mainBusinessCode + ")";
                                    }

                                    // 不是钻头时换上数量为总数量
                                    if (!drillingBitSet.contains(selectBusinessCode)) {
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

    //显示主刀和备用刀列表
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


        View view = LayoutInflater.from(C01S009_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
            View view1 = LayoutInflater.from(C01S009_002Activity.this).inflate(R.layout.item_c03s004_001, null);
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
            TextView huanzhuangshuliang = (TextView) tableRow.findViewById(R.id.huanzhuangshuliang);//换装数量

            cailiaohao.setText(displaySyntheticKnifeMap.get(selectBusinessCode));
            cailiaohao.setTag(selectBusinessCode);
            huanzhuangshuliang.setText("0");

            // 判断是否不是钻头
            if (!drillingBitSet.contains(selectBusinessCode)) {
                huanzhuangshuliang.setText(zongshuliang.getText());
            }

            Map<String, Object> addRfidDataMap = (Map<String, Object>) paramMap.get("addRfidDataMap");
            if (addRfidDataMap != null) {
                String scanBusinessCode = (String) addRfidDataMap.get("selectBusinessCode");
                int num = (Integer) addRfidDataMap.get("num");
                String rfid = (String) addRfidDataMap.get("rfid");
                String bladeCode = (String) addRfidDataMap.get("bladeCode");

                // 给哪个刀具类型增加组装数量，默认只给转头添加组装数量
                addRfidData(scanBusinessCode, num, rfid, bladeCode);
            }
        }
    };


    /**
     * 修改内存中表格
     *
     * @param code 材料号
     * @param num  数量
     * @param rfid 标签
     */
    private void addRfidData(String code, int num, String rfid, String bladeCode) {
        try {
            for (int i = 0; i < mTlContainer.getChildCount(); i++) {
                // 外部行
                TableRow mTableRow = (TableRow) ((LinearLayout) mTlContainer.getChildAt(i)).getChildAt(0);

                TextView cailiaohao = (TextView) mTableRow.findViewById(R.id.cailiaohao);//材料号
                TextView daojuleixing = (TextView) mTableRow.findViewById(R.id.daojuleixing);//刀具类型
                TextView zongshuliang = (TextView) mTableRow.findViewById(R.id.zongshuliang);//总数量
                TextView huanzhuangshuliang = (TextView) mTableRow.findViewById(R.id.huanzhuangshuliang);//换装数量

                if (code.equals(cailiaohao.getTag().toString())) {
                    UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(code);
                    upCuttingToolVO.setUpCount(upCuttingToolVO.getUpCount() + num);
                    upCuttingToolVO.setRfidCode(rfid);
                    upCuttingToolVO.setBladeCode(bladeCode);

                    huanzhuangshuliang.setText(upCuttingToolVO.getUpCount()+"");
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

                View view = LayoutInflater.from(C01S009_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                    createAlertDialog(C01S009_002Activity.this, "请输入刀身码", Toast.LENGTH_LONG);
                } else if (null == tv01.getText().toString().trim() || "".equals(tv01.getText().toString().trim())) {
                    createAlertDialog(C01S009_002Activity.this, "请选择物料号", Toast.LENGTH_LONG);
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
                                        drillingBitScanSwitch(businessCode, 1, rfid, selectBladeCode);
                                    } else {
                                        createAlertDialog(C01S009_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S009_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
            View view1 = LayoutInflater.from(C01S009_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(businessCodeList.get(i));
            return view1;
        }
    }

    /**
     * 钻头扫描自动切换
     * @param scanBusinessCode
     * @param num
     * @param rfid
     * @param bladeCode
     */
    private void drillingBitScanSwitch(String scanBusinessCode, int num, String rfid, String bladeCode) {
        rfidSet.add(rfid);

        // 扫描的标签返回的物料号与当前显示的物料号不一样，需要切换
        if (!displaySyntheticKnifeMap.containsKey(scanBusinessCode)) {
            // 返回钻头所在的行
            TableRow tableRow = getDrillingBitRow();
            TextView cailiaohao = (TextView) tableRow.findViewById(R.id.cailiaohao);//材料号

            // 物料号匹配的 config
            SynthesisCuttingToolLocationConfig config = businessCodeToConfigMap.get(scanBusinessCode);

            // 真实的物料号
            String mainBusinessCode = config.getCuttingTool().getBusinessCode();

            // 显示的物料号名称
            String textviewContent = "";
            if (mainKnifeSet.contains(scanBusinessCode)) {
                textviewContent = scanBusinessCode;
            } else {
                textviewContent = scanBusinessCode + "(" + mainBusinessCode + ")";
            }

            displaySyntheticKnifeMap.remove(cailiaohao.getTag().toString());
            displaySyntheticKnifeMap.put(scanBusinessCode, textviewContent);

            UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(cailiaohao.getTag().toString());
            upCuttingToolVO.setUpCount(0);
            upCuttingToolVO.setRfidCode(null);
            upCuttingToolVO.setBladeCode(null);

            // 换装数据
            Map<String, Object> addRfidDataMap = new HashMap<>();
            addRfidDataMap.put("selectBusinessCode", scanBusinessCode);
            addRfidDataMap.put("num", num);
            addRfidDataMap.put("rfid", rfid);
            addRfidDataMap.put("bladeCode", bladeCode);


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
            addRfidData(scanBusinessCode, num, rfid, bladeCode);
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
    //-----------------------------------------添加刀身码结束--------------------------------------------




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

                    // ------------ 授权信息 ------------
                    impowerRecorder.setToolCode(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(synthesisCuttingToolBind_rfid_code);// rfid标签
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Config.getKey().toString());//操作key

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(C01S009_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                return;
            }

            upCuttingToolVOList = new ArrayList<>();

            Set<String> keys = upCuttingToolVOMap.keySet();
            for (String key : keys) {
                UpCuttingToolVO upCuttingToolVO = upCuttingToolVOMap.get(key);
                if (displaySyntheticKnifeMap.containsKey(upCuttingToolVO.getBusinessCode()) && upCuttingToolVO.getUpCount() > 0) {
                    upCuttingToolVOList.add(upCuttingToolVO);
                }
            }

            RfidContainerVO rfidContainerVO = new RfidContainerVO();
            if (bladeCode != null && !"".equals(bladeCode)) {
                rfidContainerVO.setSynthesisBladeCode(bladeCode);
            }
            if (bladeCode_RFID != null && !"".equals(bladeCode_RFID)) {
                rfidContainerVO.setLaserCode(bladeCode_RFID);
            }

            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
            // 合成刀查询 code
            synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);
            // 合成刀组装信息code编码
            synthesisCuttingToolBindVO.setCode(synthesisCuttingToolBind.getCode());

            ExChangeVO exChangeVO = new ExChangeVO();
            exChangeVO.setSynthesisCuttingToolBindVO(synthesisCuttingToolBindVO);
            exChangeVO.setUpCuttingToolVOS(upCuttingToolVOList);


            String jsonStr = objectToJson(exChangeVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> config = iRequest.config(body, headsMap);

            config.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到库存盘点刀具信息详细页面
                            Intent intent = new Intent(C01S009_002Activity.this, C01S009_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            final String errorStr = response.errorBody().string();

                            createAlertDialog(C01S009_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S009_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
