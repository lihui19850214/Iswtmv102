package com.icomp.Iswtmv10.v01c01.c01s008;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
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
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.entity.base.Rfidcontainer;
import com.ta.utdid2.android.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 刀具拆分页面2
 */
public class c01s008_002Activity extends CommonActivity {

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


    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();
    // 钻头材料号(现在叫物料号)
    Set<String> drillingBitSet = new HashSet<>();

    // 刀身码
    String bladeCode = "";

    // 合成刀标签Code
    String synthesisCuttingToolBind_rfid_code = "";

    // 合成刀标签
    String bladeCode_RFID = "";

    // 合成刀配置
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    // 合成刀真实数据
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();


    List<Map<String, Object>> downCuttingToolVODataList = new ArrayList<>();

    List<DownCuttingToolVO> downCuttingToolVOList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s008_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            bladeCode = (String) paramMap.get("bladeCode");
            bladeCode_RFID = (String) paramMap.get("bladeCode_RFID");
            synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
            synthesisCuttingToolBind_rfid_code = (String) paramMap.get("synthesisCuttingToolBind_rfid_code");
            synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");

            // 合成刀具编码，如果取值不对，使用synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode()
            tv01.setText(synthesisCuttingToolBind.getSynthesisCode());

            setValue();
        } catch (Exception e) {
            e.printStackTrace();
            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
        }
    }


    private void setValue() {
        try {
            List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = synthesisCuttingToolBind.getSynthesisCuttingToolLocationList();

            for (SynthesisCuttingToolLocation synthesisCuttingToolLocation : synthesisCuttingToolLocationList) {
                // 是否时钻头：true为钻头；false为非钻头；
                boolean isDrillingBit = false;
                // 刀具类型
                String daojuType = "";

                // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                if (CuttingToolTypeEnum.dj.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getType())) {
                    // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                    if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                        isDrillingBit = true;
                        daojuType = CuttingToolConsumeTypeEnum.griding_zt.getName();
                    } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                        daojuType = CuttingToolConsumeTypeEnum.griding_dp.getName();
                    } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                        daojuType = CuttingToolConsumeTypeEnum.single_use_dp.getName();
                    } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                        daojuType = CuttingToolConsumeTypeEnum.other.getName();
                    }
                } else {
                    continue;
                }

                Map<String, Object> map = new HashMap<>();

                map.put("count", synthesisCuttingToolLocation.getCount());
                map.put("daojuType", daojuType);

                // 拆分
                DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                downCuttingToolVO.setBladeCode(synthesisCuttingToolLocation.getCuttingToolBladeCode());
                downCuttingToolVO.setBusinessCode(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                downCuttingToolVO.setDownCode(synthesisCuttingToolLocation.getCuttingTool().getCode());
                downCuttingToolVO.setDownCount(synthesisCuttingToolLocation.getCount());
                // 钻头
                if (isDrillingBit) {
                    map.put("isDrillingBit", true);
                    downCuttingToolVO.setDownCount(0);
                    drillingBitSet.add(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                }

                map.put("downCuttingToolVO", downCuttingToolVO);

                downCuttingToolVODataList.add(map);

                addLayout(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                    Intent intent2 = new Intent(c01s008_002Activity.this, C01S008_001Activity.class);
                    startActivity(intent2);
                    finish();
                }
            });
        }
    }

    /**
     * 检查数据是否正确，符合标准
     * @return true为正确；false为不正确；
     */
    private boolean checkData() {

        for (int i=0; i<downCuttingToolVODataList.size(); i++) {
            Map<String, Object> map = downCuttingToolVODataList.get(i);

            Integer count = (Integer)map.get("count");
            boolean isDrillingBit = false;
            if (map.containsKey("isDrillingBit")) {
                isDrillingBit = (Boolean)map.get("isDrillingBit");
            }
            DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");

            // 内部第一行数据为主刀，有标记是否为转头
            if (isDrillingBit) {
                // 如果拆分数不等于总数，表示未满足拆分数量，需要再扫描
                if (count.intValue() != downCuttingToolVO.getDownCount().intValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 检查rfid数据是否已满足
     * @return true为已满足；false为未满足；
     */
    private boolean checkRfidData() {
        for (int i=0; i<downCuttingToolVODataList.size(); i++) {
            Map<String, Object> map = downCuttingToolVODataList.get(i);

            Integer count = (Integer)map.get("count");
            boolean isDrillingBit = false;
            if (map.containsKey("isDrillingBit")) {
                isDrillingBit = (Boolean)map.get("isDrillingBit");
            }
            DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");

            // 转头
            if (isDrillingBit) {
                // 如果拆分数不等于总数，表示未满足拆分数量，需要再扫描
                if (count.intValue() != downCuttingToolVO.getDownCount().intValue()) {
                    return false;
                }
            }
        }

        return true;
    }


    @OnClick({R.id.btnCancel, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
                Intent intent2 = new Intent(c01s008_002Activity.this, C01S008_001Activity.class);
                // 不清空页面之间传递的值
                intent2.putExtra("isClearParamMap", false);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnNext:

                if (!checkData()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            createAlertDialog(c01s008_002Activity.this, "请确认拆分数量", Toast.LENGTH_SHORT);
                            createToast(getApplicationContext(), "请确认拆分数量", Toast.LENGTH_SHORT);
                        }
                    });

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

    /**
     * 开始扫描
     */
    //扫描方法
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
            createToast(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT);
        }
    }

    //扫描线程
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要解开
//            rfidString = "liuzhuan_rfid1";
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

                if (rfidSet.contains(rfidString)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            createAlertDialog(c01s008_002Activity.this, "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_SHORT);
                        }
                    });
                } else if (checkRfidData()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            createAlertDialog(c01s008_002Activity.this, "拆分数量已满足", Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), "拆分数量已满足", Toast.LENGTH_SHORT);
                        }
                    });
                } else {
                    try {
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
                        Call<String> queryBindInfo = iRequest.queryForBind(body);
                        queryBindInfo.enqueue(new MyCallBack<String>() {
                            @Override
                            public void _onResponse(Response<String> response) {
                                try {
                                    if (response.raw().code() == 200) {
                                        CuttingToolBind cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);
                                        if (null == cuttingToolBind){
                                            cuttingToolBind = createBind(rfidString);
                                        }
                                        if (cuttingToolBind != null) {
                                            // 判断根据标签扫得到的钻头是否在配置中存在
                                            if (drillingBitSet.contains(cuttingToolBind.getCuttingTool().getBusinessCode())) {
                                                rfidSet.add(rfidString);
                                                addRfidData(cuttingToolBind.getCuttingTool().getBusinessCode(), rfidString, 1);
                                            } else {
//                                                createAlertDialog(c01s008_002Activity.this, "该标签与列表中钻头不匹配", Toast.LENGTH_SHORT);
                                                createToast(getApplicationContext(), "该标签与列表中钻头不匹配", Toast.LENGTH_SHORT);
                                            }
                                        } else {
                                            createToast(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT);
                                        }
                                    } else {
//                                        createAlertDialog(c01s008_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                        createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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
//                                        createAlertDialog(c01s008_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                                        createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
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
                                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                            }
                        });
                    }
                }
            }
        }
    }


    /**
     * 添加布局
     */
    private void addLayout(Map<String, Object> map) {

        String count = ((Integer)map.get("count"))+"";
        String daojuType = (String) map.get("daojuType");
        boolean isDrillingBit = false;
        if (map.containsKey("isDrillingBit")) {
            isDrillingBit = (Boolean)map.get("isDrillingBit");
        }
        DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");


        View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.tablerow_c01s008_002, null);

        TableRow tableRow = (TableRow) mLinearLayout.findViewById(R.id.tableRow);//行
        TextView cailiaohao = (TextView) mLinearLayout.findViewById(R.id.cailiaohao);//材料号
        TextView daojuleixing = (TextView) mLinearLayout.findViewById(R.id.daojuleixing);//刀具类型
        TextView zongshuliang = (TextView) mLinearLayout.findViewById(R.id.zongshuliang);//总数量
        TextView chaifenshuliang = (TextView) mLinearLayout.findViewById(R.id.chaifenshuliang);//拆分数量

        // 标记是不是钻头
        tableRow.setTag(isDrillingBit);

        cailiaohao.setText(downCuttingToolVO.getBusinessCode());//"显示的材料号"
        cailiaohao.setTag(downCuttingToolVO.getBusinessCode());//"材料号"
        daojuleixing.setText(daojuType);//刀具类型
        zongshuliang.setText(count);//"总数量"
        chaifenshuliang.setText(downCuttingToolVO.getDownCount()+"");//"换装数量"


        mTlContainer.addView(mLinearLayout);
    }


    /**
     * 修改内存中表格
     *
     * @param businessCode 材料号
     * @param rfid 标签
     * @param num  数量
     */
    private void addRfidData(String businessCode, String rfid, int num) {
        try {
            for (int i = 0; i < mTlContainer.getChildCount(); i++) {
                // 外部行
                TableRow mTableRow = (TableRow) ((LinearLayout) mTlContainer.getChildAt(i)).getChildAt(0);

                TextView cailiaohao = (TextView) mTableRow.findViewById(R.id.cailiaohao);//材料号
                TextView daojuleixing = (TextView) mTableRow.findViewById(R.id.daojuleixing);//刀具类型
                TextView zongshuliang = (TextView) mTableRow.findViewById(R.id.zongshuliang);//总数量
                TextView chaifenshuliang = (TextView) mTableRow.findViewById(R.id.chaifenshuliang);//换装数量

                if (businessCode.equals(cailiaohao.getTag().toString())) {
                    chaifenshuliang.setText(num + "");

                    for (int j=0; j<downCuttingToolVODataList.size(); j++) {
                        Map<String, Object> map = downCuttingToolVODataList.get(j);

                        String count = ((Integer)map.get("count"))+"";
                        boolean isDrillingBit = false;
                        if (map.containsKey("isDrillingBit")) {
                            isDrillingBit = (Boolean)map.get("isDrillingBit");
                        }
                        DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");

                        if (businessCode.equals(downCuttingToolVO.getBusinessCode()) && isDrillingBit) {
                            downCuttingToolVO.setDownCount(num);
                            downCuttingToolVO.setDownRfidLaserCode(rfid);

                            break;
                        }
                    }

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                }
            });
        }
    }


    /**
     * 创建绑定信息
     * @param rfid
     * @return
     */
    private CuttingToolBind createBind(String rfid) {
        CuttingToolBind cuttingToolBind = null;
        try {
            RfidContainer rfidContainer = new RfidContainer();
            rfidContainer.setLaserCode(rfid);
            for (int j=0; j<downCuttingToolVODataList.size(); j++) {
                Map<String, Object> map = downCuttingToolVODataList.get(j);
                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                if (null == downCuttingToolVO.getBladeCode() || "".equals(downCuttingToolVO.getBladeCode())){
                    continue;
                }
                if (null!=downCuttingToolVO.getDownRfidLaserCode()&&!"".equals(downCuttingToolVO.getDownRfidLaserCode())){
                    continue;
                }
                downCuttingToolVO.setDownRfidLaserCode(rfid);
                cuttingToolBind = new CuttingToolBind();
                cuttingToolBind.setRfidContainer(rfidContainer);
                CuttingTool cuttingTool = new CuttingTool();
                cuttingTool.setCode(downCuttingToolVO.getDownCode());
                cuttingTool.setBusinessCode(downCuttingToolVO.getBusinessCode());
                cuttingToolBind.setCuttingTool(cuttingTool);
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                }
            });
        }
        return cuttingToolBind;
    }


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
                    impowerRecorder.setToolCode(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                    impowerRecorder.setRfidLasercode(synthesisCuttingToolBind_rfid_code);// rfid标签
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_UnConfig.getKey().toString());//操作key

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                return;
            } catch (IOException e) {
                e.printStackTrace();
//                createAlertDialog(c01s008_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                createToast(getApplicationContext(), getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                return;
            }

            downCuttingToolVOList = new ArrayList<>();

            // 循环拆分数量
            for (int i = 0; i < downCuttingToolVODataList.size(); i++) {
                Map<String, Object> map = downCuttingToolVODataList.get(i);

                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                // DownCount 为 0 的数据不要
                if (downCuttingToolVO.getDownCount() > 0) {
                    downCuttingToolVOList.add(downCuttingToolVO);
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
            exChangeVO.setDownCuttingToolVOS(downCuttingToolVOList);


            String jsonStr = objectToJson(exChangeVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> unconfig = iRequest.unconfig(body, headsMap);

            unconfig.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到库存盘点刀具信息详细页面
                            Intent intent = new Intent(c01s008_002Activity.this, c01s008_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
//                            createAlertDialog(c01s008_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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
//                    createAlertDialog(c01s008_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
        }
    }


}