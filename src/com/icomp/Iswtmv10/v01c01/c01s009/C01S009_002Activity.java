package com.icomp.Iswtmv10.v01c01.c01s009;
/**
 * 刀具组装
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
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
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

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

    List<List<Map<String, Object>>> outsideListData = new ArrayList<>();

    //合成刀具初始化参数类
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();

    List<UpCuttingToolVO> upCuttingToolVOList = new ArrayList<>();

    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();

    // 合成刀标签
    String synthesisCuttingToolConfigRFID = "";

    // 合成刀具编码
    String synthesisCode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01_s009_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        synthesisCuttingToolConfigRFID = (String) paramMap.get("synthesisCuttingToolConfigRFID");
        synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
        synthesisCode = (String) paramMap.get("synthesisCode");

        setValue();
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
            default:
        }
    }

    private void setValue() {
        tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());

        List<SynthesisCuttingToolLocationConfig> SynthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

        for (SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig : SynthesisCuttingToolLocationConfigList) {
            List<Map<String, Object>> insideListDate = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();


            // 换装
            UpCuttingToolVO upCuttingToolVO = new UpCuttingToolVO();
            upCuttingToolVO.setUpCode(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode());
            upCuttingToolVO.setUpCount(0);


            map.put("synthesisCuttingToolLocationConfig", synthesisCuttingToolLocationConfig);
            map.put("upCuttingToolVO", upCuttingToolVO);
//            // 判断是否是钻头
//            if ("1".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//                map.put("isZuanTou", true);
//            }
            // 判断是否是钻头
            // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
            if (CuttingToolTypeEnum.dj.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
                // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                    map.put("isZuanTou", true);
                }
            }
            insideListDate.add(map);


            // 替换刀1
            CuttingTool cuttingTool1 = synthesisCuttingToolLocationConfig.getReplaceCuttingTool1();
            // 替换刀2
            CuttingTool cuttingTool2 = synthesisCuttingToolLocationConfig.getReplaceCuttingTool2();
            // 替换刀3
            CuttingTool cuttingTool3 = synthesisCuttingToolLocationConfig.getReplaceCuttingTool3();

            // 替换刀1
            if (cuttingTool1 != null) {
                upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setUpCode(cuttingTool1.getBusinessCode());
                upCuttingToolVO.setUpCount(0);

                map = new HashMap<>();
                map.put("cuttingTool", cuttingTool1);
                map.put("upCuttingToolVO", upCuttingToolVO);
                insideListDate.add(map);
            }


            // 替换刀2
            if (cuttingTool2 != null) {
                upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setUpCode(cuttingTool2.getBusinessCode());
                upCuttingToolVO.setUpCount(0);


                map = new HashMap<>();
                map.put("cuttingTool", cuttingTool2);
                map.put("upCuttingToolVO", upCuttingToolVO);
                insideListDate.add(map);
            }


            // 替换刀3
            if (cuttingTool3 != null) {
                upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setUpCode(cuttingTool3.getBusinessCode());
                upCuttingToolVO.setUpCount(0);


                map = new HashMap<>();
                map.put("cuttingTool", cuttingTool3);
                map.put("upCuttingToolVO", upCuttingToolVO);
                insideListDate.add(map);
            }

            outsideListData.add(insideListDate);

            // 初始化数据
            addLayout(insideListDate);
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
            rfidString = singleScan();//TODO 生产环境需要解开
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
                            createAlertDialog(C01S009_002Activity.this, "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_LONG);
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.show();
                        }
                    });

                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);

                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
                    cuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(cuttingToolBindVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> searchCuttingToolBind = iRequest.searchCuttingToolBind(body);
                    searchCuttingToolBind.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    Gson gson = new Gson();
                                    CuttingToolBind cuttingToolBind = gson.fromJson(response.body(), CuttingToolBind.class);

                                    if (cuttingToolBind != null) {
                                        if (!checkRfidData(cuttingToolBind.getCuttingTool().getBusinessCode())) {
                                            rfidSet.add(rfidString);
                                            // 给哪个刀具类型增加组装数量，默认只给转头添加组装数量
                                            addRfidData(cuttingToolBind.getCuttingTool().getBusinessCode(), 1, rfidString, cuttingToolBind.getBladeCode());
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    createAlertDialog(C01S009_002Activity.this, "组装数量已满足", Toast.LENGTH_SHORT);
                                                }
                                            });
                                        }
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C01S009_002Activity.this, errorStr, Toast.LENGTH_LONG);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (null != loading && loading.isShowing()) {
                                            loading.dismiss();
                                        }
                                    }
                                });
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

            }
        }
    }

    /**
     * 检查数据是否正确，符合标准
     * @return true为正确；false为不正确；
     */
    private boolean checkData() {
        int zongNum = 0;
        int zuzhuangZongNum = 0;

        for (int i=0; i<outsideListData.size(); i++) {
            List<Map<String, Object>> insideListDate = outsideListData.get(i);

            for (int j=0; j<insideListDate.size(); j++) {
                Map<String, Object> map = insideListDate.get(j);

                // 内部第一行数据为主刀，其他为备用刀
                if (j == 0) {
                    SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig = (SynthesisCuttingToolLocationConfig) map.get("synthesisCuttingToolLocationConfig");
                    zongNum = synthesisCuttingToolLocationConfig.getCount();
                }

                UpCuttingToolVO upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
                zuzhuangZongNum = zuzhuangZongNum + upCuttingToolVO.getUpCount();
            }

            // 总数量 不等于 组装数量 验证
            if (zongNum != zuzhuangZongNum) {
                return false;
            }

            zongNum = 0;
            zuzhuangZongNum = 0;
        }

        return true;
    }

    /**
     * 检查rfid数据是否已满足
     * @param code 材料号
     * @return true为已满足；false为未满足；
     */
    private boolean checkRfidData(String code) {
        int zongNum = 0;
        int zuzhuangZongNum = 0;
        boolean isCaiLiaoHao = false;

        for (int i=0; i<outsideListData.size(); i++) {
            List<Map<String, Object>> insideListDate = outsideListData.get(i);

            Map<String, Object> map = insideListDate.get(0);

            // 内部第一行数据为主刀，有标记是否为转头
            if (map.containsKey("isZuanTou")) {
                SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig = (SynthesisCuttingToolLocationConfig) map.get("synthesisCuttingToolLocationConfig");
                zongNum = synthesisCuttingToolLocationConfig.getCount();

                // 循环获取组装数量集合
                for (int j=0; j<insideListDate.size(); j++) {
                    map = insideListDate.get(j);

                    UpCuttingToolVO upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
                    zuzhuangZongNum = zuzhuangZongNum + upCuttingToolVO.getUpCount();

                    // 找到此材料号标记
                    if (code.equals(upCuttingToolVO.getUpCode())) {
                        isCaiLiaoHao = true;
                    }
                }

                // 如果组装数等于总数，表示已满足组装数量，不需要再扫描
                if (isCaiLiaoHao && (zongNum == zuzhuangZongNum)) {
                    return true;
                }
            }

            zongNum = 0;
            zuzhuangZongNum = 0;
            isCaiLiaoHao = false;
        }

        return false;
    }


    int outsideRowNumber = 0;// 外部行号

    @android.support.annotation.IdRes
    int tvCailiao = 1000;
    int tvDaoJuType = 1001;
    int tvDaoJuNum = 1002;
    int tvZuzhuangNum = 1003;//组装


    /**
     * 添加布局
     */
    private void addLayout(List<Map<String, Object>> insideListDate) {
        SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig = null;
        UpCuttingToolVO upCuttingToolVO = null;

        CuttingTool cuttingTool1 = null;
        UpCuttingToolVO upCuttingToolVO1 = null;

        CuttingTool cuttingTool2 = null;
        UpCuttingToolVO upCuttingToolVO2 = null;

        CuttingTool cuttingTool3 = null;
        UpCuttingToolVO upCuttingToolVO3 = null;


        for (int i=0; i<insideListDate.size(); i++) {
            if (i == 0) {
                Map<String, Object> map = insideListDate.get(i);
                synthesisCuttingToolLocationConfig = (SynthesisCuttingToolLocationConfig) map.get("synthesisCuttingToolLocationConfig");
                upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
            } else if (i == 1) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool1 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO1 = (UpCuttingToolVO) map.get("upCuttingToolVO");
            } else if (i == 2) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool2 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO2 = (UpCuttingToolVO) map.get("upCuttingToolVO");
            } else if (i == 3) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool3 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO3 = (UpCuttingToolVO) map.get("upCuttingToolVO");
            }
        }


        String daojuType = "";
        boolean isZuanTou = false;

//        //刀具类型(1钻头、2刀片、3一体刀、4专机、9其他)
//        if ("1".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "钻头";
//            isZuanTou = true;
//        } else if ("2".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "刀片";
//        } else if ("3".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "一体刀";
//        } else if ("4".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "专机";
//        } else if ("9".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "其他";
//        }

        // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
        if (CuttingToolTypeEnum.dj.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
            if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                isZuanTou = true;
                daojuType = CuttingToolConsumeTypeEnum.griding_zt.getName();
            } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.griding_dp.getName();
            } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.single_use_dp.getName();
            } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.other.getName();
            }
        } else if (CuttingToolTypeEnum.fj.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.fj.getName();
        } else if (CuttingToolTypeEnum.pt.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.pt.getName();
        } else if (CuttingToolTypeEnum.other.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.other.getName();
        }


        ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams param2 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        TableRow.LayoutParams param3 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1f);


        // 行
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(param);
        tableRow.setBackgroundResource(R.drawable.table_border_c);


        // 内部table1
        TableLayout tableLayout1 = new TableLayout(this);
        tableLayout1.setLayoutParams(param2);
        tableLayout1.addView(getRow(tvCailiao, synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode()));

        if (cuttingTool1 != null) {
            tableLayout1.addView(getRow(tvCailiao, cuttingTool1.getBusinessCode()));
        }

        if (cuttingTool2 != null) {
            tableLayout1.addView(getRow(tvCailiao, cuttingTool2.getBusinessCode()));
        }

        if (cuttingTool3 != null) {
            tableLayout1.addView(getRow(tvCailiao, cuttingTool3.getBusinessCode()));
        }

        // 添加到行中
        tableRow.addView(tableLayout1);
        tableRow.addView(getImage());


        // 内部table2
        TableLayout tableLayout2 = new TableLayout(this);
        tableLayout2.setLayoutParams(param2);
        tableLayout2.addView(getRow(tvDaoJuType, daojuType));

        if (cuttingTool1 != null) {
            tableLayout2.addView(getRow(tvDaoJuType, daojuType));
        }

        if (cuttingTool2 != null) {
            tableLayout2.addView(getRow(tvDaoJuType, daojuType));
        }

        if (cuttingTool3 != null) {
            tableLayout2.addView(getRow(tvDaoJuType, daojuType));
        }

        // 添加到行中
        tableRow.addView(tableLayout2);
        tableRow.addView(getImage());


        TextView tv1 = new TextView(this);
        tv1.setId(tvDaoJuNum);
        tv1.setLayoutParams(param3);
        tv1.setGravity(Gravity.CENTER);
        tv1.setText(synthesisCuttingToolLocationConfig.getCount().toString());//总数量


        // 添加到行中
        tableRow.addView(tv1);
        tableRow.addView(getImage());


        // 内部table3
        TableLayout tableLayout3 = new TableLayout(this);
        tableLayout3.setLayoutParams(param2);
        tableLayout3.addView(getRowEdit(tvZuzhuangNum, String.valueOf(upCuttingToolVO.getUpCount()), isZuanTou, synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode(), outsideRowNumber , 0));

        if (cuttingTool1 != null) {
            tableLayout3.addView(getRowEdit(tvZuzhuangNum, String.valueOf(upCuttingToolVO1.getUpCount()), isZuanTou, cuttingTool1.getBusinessCode(), outsideRowNumber, 1));
        }

        if (cuttingTool2 != null) {
            tableLayout3.addView(getRowEdit(tvZuzhuangNum, String.valueOf(upCuttingToolVO2.getUpCount()), isZuanTou, cuttingTool2.getBusinessCode(), outsideRowNumber, 2));
        }

        if (cuttingTool3 != null) {
            tableLayout3.addView(getRowEdit(tvZuzhuangNum, String.valueOf(upCuttingToolVO3.getUpCount()), isZuanTou, cuttingTool3.getBusinessCode(), outsideRowNumber, 3));
        }

        // 添加到行中
        tableRow.addView(tableLayout3);


        mTlContainer.addView(tableRow);

        // 外部行号+1
        outsideRowNumber++;
    }


    private TableRow getRow(int id, String text) {
        TableRow.LayoutParams param = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams param2 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())), 1f);

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(param);

        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(param2);
        tv1.setGravity(Gravity.CENTER);
        tv1.setId(id);
        tv1.setText(text);

        tableRow.addView(tv1);

        return tableRow;
    }


    /**
     *
     * @param id 组件 ID
     * @param text 显示内容
     * @param isZuanTou 是否是钻头
     * @param cailiao 材料号
     * @param outsideRowNumber 外部行号
     * @param insideRowNumber 内部行号
     * @return
     */
    private TableRow getRowEdit(final int id, String text, boolean isZuanTou, final String cailiao, final int outsideRowNumber, final int insideRowNumber) {
        TableRow.LayoutParams param = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams param2 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())), 1f);

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(param);
        tableRow.setFocusable(true);
        tableRow.setFocusableInTouchMode(true);


        final EditText et1 = new EditText(this);
        et1.setLayoutParams(param2);
        et1.setGravity(Gravity.CENTER);
        et1.setId(id);
        et1.setText(text);
        et1.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (isZuanTou) {
            et1.setFocusable(false);
            et1.setFocusableInTouchMode(false);
        } else {
            et1.addTextChangedListener(new TextWatcher() {
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
                    String num = et1.getText().toString();
                    //输入文字后的状态
                    if (num == null || "".equals(num)) {
                        num = "0";
                    }

                    // 组装数量
                    addZuzhuangData(cailiao, Integer.parseInt(num), outsideRowNumber, insideRowNumber);
                }
            });
        }

        tableRow.addView(et1);

        return tableRow;
    }


    private ImageView getImage() {
        TableRow.LayoutParams param = new TableRow.LayoutParams(
//                getResources().getDimensionPixelOffset(R.dimen.image_height),// 设置1dp宽度
                ((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())),
                ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(param);
        imageView.setBackgroundResource(R.color.baseColor);

        return imageView;
    }


    /**
     * 修改内存中表格
     * @param code 材料号
     * @param num 数量
     * @param rfid 标签
     */
    private void addRfidData(String code, int num, String rfid, String bladeCode) {
        int outsideRowNumber = -1;
        int insideRowNumber = -1;

        for (int i=0; i<outsideListData.size(); i++) {
            List<Map<String, Object>> insideListDate = outsideListData.get(i);

            Map<String, Object> map = insideListDate.get(0);

            if (map.containsKey("isZuanTou")) {
                outsideRowNumber = i+1;
                for (int j=0; j<insideListDate.size(); j++) {
                    map = insideListDate.get(j);

                    UpCuttingToolVO upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");

                    if (code.equals(upCuttingToolVO.getUpCode())) {
                        insideRowNumber = j;
                        upCuttingToolVO.setUpCount((upCuttingToolVO.getUpCount() + num));
                        upCuttingToolVO.setRfidCode(rfid);
                        upCuttingToolVO.setBladeCode(bladeCode);

                        // 外部行
                        TableRow mTableRow = (TableRow) mTlContainer.getChildAt(outsideRowNumber);

                        // 内部行
                        TableLayout mTableLayout = (TableLayout) mTableRow.getChildAt(0);// 材料号
                        TableLayout mTableLayout2 = (TableLayout) mTableRow.getChildAt(2);// 刀具类型
                        TextView mTextView = (TextView) mTableRow.getChildAt(4);// 总数量
                        TableLayout mTableLayout3 = (TableLayout) mTableRow.getChildAt(6);// 组装数量

                        //
                        TextView tvZuzhuangshu = (TextView) ((TableRow) mTableLayout3.getChildAt(insideRowNumber)).getVirtualChildAt(0);

                        int numOld = Integer.parseInt(tvZuzhuangshu.getText().toString());
                        int zong = numOld + num;
                        tvZuzhuangshu.setText(zong+"");

                        return;
                    }
                }
            }
        }
    }

    /**
     * 修改内存中表格组装数据
     * @param code 材料号
     * @param num 数量
     * @param outsideRowNumber 外部行号
     * @param insideRowNumber 内部行号
     */
    private void addZuzhuangData(String code, int num, int outsideRowNumber, int insideRowNumber) {
        List<Map<String, Object>> insideListDate = outsideListData.get(outsideRowNumber);
        Map<String, Object> map = insideListDate.get(insideRowNumber);

        UpCuttingToolVO upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
        upCuttingToolVO.setUpCount(num);
    }


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

                // ------------ 授权信息 ------------
                impowerRecorder.setToolCode(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());// 合成刀编码
                impowerRecorder.setRfidLasercode(synthesisCuttingToolConfigRFID);// rfid标签
                impowerRecorder.setOperatorUserCode(authCustomer.getCode());//操作者code
                impowerRecorder.setImpowerUser(authorizationList.get(0).getCode());//授权人code
                impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Config.getKey().toString());//操作key

//                impowerRecorder.setOperatorUserName(URLEncoder.encode(authCustomer.getName(),"utf-8"));//操作者姓名
//                impowerRecorder.setImpowerUserName(URLEncoder.encode(authorizationList.get(0).getName(),"utf-8"));//授权人名称
//                impowerRecorder.setOperatorValue(URLEncoder.encode(OperationEnum.SynthesisCuttingTool_Exchange.getName(),"utf-8"));//操作者code

                impowerRecorderList.add(impowerRecorder);
            }
            headsMap.put("impower", mapper.writeValueAsString(impowerRecorderList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //调用接口，查询合成刀具组成信息
        IRequest iRequest = retrofit.create(IRequest.class);


        List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList = new ArrayList<>();
        synthesisCuttingToolConfigList.add(synthesisCuttingToolConfig);

        SynthesisCuttingTool synthesisCuttingTool = new SynthesisCuttingTool();
        synthesisCuttingTool.setSynthesisCuttingToolConfigList(synthesisCuttingToolConfigList);

        SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();
        synthesisCuttingToolBind.setSynthesisCuttingTool(synthesisCuttingTool);
        synthesisCuttingToolBind.setSynthesisCuttingToolCode(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());


        // 循环组装数量
        for (int i=0; i<outsideListData.size(); i++) {
            List<Map<String, Object>> insideListDate = outsideListData.get(i);
            for (int j=0; j<insideListDate.size(); j++) {
                Map<String, Object> map = insideListDate.get(j);

                UpCuttingToolVO upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
                // UpCount 为 0 的数据不要
                if (upCuttingToolVO.getUpCount() > 0) {
                    upCuttingToolVOList.add(upCuttingToolVO);
                }
            }
        }

        PackageUpVO packageUpVO = new PackageUpVO();
        packageUpVO.setUpCuttingToolVOS(upCuttingToolVOList);
        packageUpVO.setSynthesisCuttingToolBind(synthesisCuttingToolBind);


        Gson gson = new Gson();
        String jsonStr = gson.toJson(packageUpVO);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> packageUp = iRequest.packageUp(body, headsMap);
        packageUp.enqueue(new MyCallBack<String>() {
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
    }


}
