package com.icomp.Iswtmv10.v01c01.c01s010;
/**
 * 刀具换装页面1
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import com.icomp.common.gsonadapter.TimestampTypeAdapter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.sql.Timestamp;
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

    private List<List<Map<String, Object>>> outsideListData = new ArrayList<>();

    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();


    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();

    // 合成刀标签
    String synthesisCuttingToolConfigRFID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s010_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();


        Map<String, Object> paramMap = PARAM_MAP.get(1);
        if (paramMap != null) {
            tv01.setText((String) paramMap.get("title"));// 合成刀具编码
            outsideListData = (List<List<Map<String, Object>>>) paramMap.get("outsideListData");
            synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
            synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");
            rfidSet = (Set<String>) paramMap.get("rfidSet");
            cbDiudao.setChecked((Boolean) paramMap.get("cbDiudao"));
            synthesisCuttingToolConfigRFID = (String) paramMap.get("synthesisCuttingToolConfigRFID");

            for (int i=0; i<outsideListData.size(); i++) {
                addLayout(outsideListData.get(i));
            }
        }
    }

    @OnClick({R.id.btnReturn, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnReturn:
                finish();
                break;
            case R.id.btnNext:
                // 判断是否扫描标签了
                if (tv01.getText().toString() == null || "".equals(tv01.getText().toString())) {
                    createAlertDialog(c01s010_002Activity.this, "请扫描标签", Toast.LENGTH_SHORT);
                    return;
                }

                // 检查数据是否正确
                if (!checkData()) {
                    createAlertDialog(c01s010_002Activity.this, "请确认组装或丢刀数量", Toast.LENGTH_SHORT);
                    return;
                }


                // 用于页面之间传值，新方法
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("title", tv01.getText().toString());
                paramMap.put("outsideListData", outsideListData);
                paramMap.put("synthesisCuttingToolConfig", synthesisCuttingToolConfig);
                paramMap.put("synthesisCuttingToolBind", synthesisCuttingToolBind);
                paramMap.put("rfidSet", rfidSet);
                paramMap.put("cbDiudao", cbDiudao.isChecked());
                paramMap.put("synthesisCuttingToolConfigRFID", synthesisCuttingToolConfigRFID);

                PARAM_MAP.put(1, paramMap);

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

    /**
     * 检查数据是否正确，符合标准
     * @return true为正确；false为不正确；
     */
    private boolean checkData() {
        int zongNum = 0;
        int zuzhuangZongNum = 0;
        int diudaoZongNum = 0;

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

                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                diudaoZongNum = diudaoZongNum + downCuttingToolVO.getDownLostCount();
            }

//            // 总数量 不等于 组装数量+丢刀数量 验证
//            if (zongNum != (zuzhuangZongNum + diudaoZongNum)) {
//                return false;
//            }

            // 总数量 不等于 组装数量 验证
            if (zongNum != zuzhuangZongNum) {
                return false;
            }

            zongNum = 0;
            zuzhuangZongNum = 0;
            diudaoZongNum = 0;
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

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            mBtnReturn.setClickable(false);
            mBtnNext.setClickable(false);
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
//            rfidString ="18000A00000D6440";
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


                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                // 如果合成刀具编码为空，需要扫描合成刀具标签
                if (tv01.getText().toString() == null || "".equals(tv01.getText().toString())) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.show();
                        }
                    });

                    SynthesisCuttingToolInitVO synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
                    synthesisCuttingToolInitVO.setRfidCode(rfidString);

                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(synthesisCuttingToolInitVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> getSynthesisCuttingConfig = iRequest.getSynthesisCuttingConfig(body, new HashMap<String, String>());
                    getSynthesisCuttingConfig.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    ObjectMapper mapper = new ObjectMapper();

                                    synthesisCuttingToolConfig = mapper.readValue(response.body(), SynthesisCuttingToolConfig.class);
                                    synthesisCuttingToolConfigRFID = rfidString;
                                    if (synthesisCuttingToolConfig != null) {
                                        searchDownCutting(rfidString);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (null != loading && loading.isShowing()) {
                                                    loading.dismiss();
                                                }
                                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (null != loading && loading.isShowing()) {
                                                loading.dismiss();
                                            }
                                            createAlertDialog(c01s010_002Activity.this, errorStr, Toast.LENGTH_LONG);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {

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
                } else {
//                    rfidString="18000A00000EBD58";
                    if (rfidSet.contains(rfidString)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createAlertDialog(c01s010_002Activity.this, "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_LONG);
                            }
                        });
                    }else {
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
                                                        createAlertDialog(c01s010_002Activity.this, "组装数量已满足", Toast.LENGTH_SHORT);
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
                                                createAlertDialog(c01s010_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                        createAlertDialog(c01s010_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                                    }
                                });
                            }
                        });
                    }

                }
            }
        }
    }


    /**
     * 查询配置
     * @param rfidString
     * @return
     */
    private void searchDownCutting(String rfidString) {
        final Map<String, DownCuttingToolVO> downCuttingToolVOMap = new HashMap<>();
        //调用接口，查询合成刀具组成信息
        IRequest iRequest = retrofit.create(IRequest.class);

        SynthesisCuttingToolInitVO synthesisCuttingToolInitVO = new SynthesisCuttingToolInitVO();
        synthesisCuttingToolInitVO.setRfidCode(rfidString);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(synthesisCuttingToolInitVO);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);


        Map<String, String> headsMap = new HashMap<>();
        try {
            headsMap.put("impower", OperationEnum.SynthesisCuttingTool_Exchange.getKey().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<String> getBind = iRequest.getBind(body, headsMap);

        getBind.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        String inpower = response.headers().get("impower");

                        ObjectMapper mapper = new ObjectMapper();
                        synthesisCuttingToolBind = mapper.readValue(response.body(), SynthesisCuttingToolBind.class);

                        List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = synthesisCuttingToolBind.getSynthesisCuttingToolLocationList();

                        for (SynthesisCuttingToolLocation synthesisCuttingToolLocation : synthesisCuttingToolLocationList) {
                            // 卸下
                            DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                            downCuttingToolVO.setDownCode(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                            downCuttingToolVO.setBladeCode(synthesisCuttingToolLocation.getCuttingToolBladeCode());
                            downCuttingToolVO.setDownCount(synthesisCuttingToolLocation.getCount());
                            downCuttingToolVO.setDownLostCount(0);
                            // 卸下数量
                            downCuttingToolVOMap.put(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode(), downCuttingToolVO);
                        }

                        final Map<String, String> inpowerMap = mapper.readValue(inpower, Map.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("1".equals(inpowerMap.get("type"))) {
                                    // 是否需要授权 true为需要授权；false为不需要授权
                                    is_need_authorization = false;
                                    setValue(downCuttingToolVOMap);
                                } else if ("2".equals(inpowerMap.get("type"))) {
                                    is_need_authorization = true;
                                    exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                                        @Override
                                        public void confirm() {
                                            setValue(downCuttingToolVOMap);
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

                                        // 实际上没有用
                                        @Override
                                        public void cancel() {
                                            finish();
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        final String errorStr = response.errorBody().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createAlertDialog(c01s010_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                        createAlertDialog(c01s010_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    }
                });

            }
        });
    }


    private void setValue(Map<String, DownCuttingToolVO> downCuttingToolVOMap) {
        tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode());
        tvShenqingRen.setText("请扫描要换装钻头的刀具盒标签或输入要换装的刀片数量");

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
            if (downCuttingToolVOMap.containsKey(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode())) {
                map.put("downCuttingToolVO", downCuttingToolVOMap.get(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode()));
            } else {
                // 卸下
                DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                downCuttingToolVO.setDownCode(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode());
                downCuttingToolVO.setDownCount(0);
                downCuttingToolVO.setDownLostCount(0);
                map.put("downCuttingToolVO", downCuttingToolVO);
            }

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
                if (downCuttingToolVOMap.containsKey(cuttingTool1.getBusinessCode())) {
                    map.put("downCuttingToolVO", downCuttingToolVOMap.get(cuttingTool1.getBusinessCode()));
                } else {
                    // 卸下
                    DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                    downCuttingToolVO.setDownCode(cuttingTool1.getBusinessCode());
                    downCuttingToolVO.setDownCount(0);
                    downCuttingToolVO.setDownLostCount(0);
                    map.put("downCuttingToolVO", downCuttingToolVO);
                }
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
                if (downCuttingToolVOMap.containsKey(cuttingTool2.getBusinessCode())) {
                    map.put("downCuttingToolVO", downCuttingToolVOMap.get(cuttingTool2.getBusinessCode()));
                } else {
                    // 卸下
                    DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                    downCuttingToolVO.setDownCode(cuttingTool2.getBusinessCode());
                    downCuttingToolVO.setDownCount(0);
                    downCuttingToolVO.setDownLostCount(0);
                    map.put("downCuttingToolVO", downCuttingToolVO);
                }
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
                if (downCuttingToolVOMap.containsKey(cuttingTool3.getBusinessCode())) {
                    map.put("downCuttingToolVO", downCuttingToolVOMap.get(cuttingTool3.getBusinessCode()));
                } else {
                    // 卸下
                    DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                    downCuttingToolVO.setDownCode(cuttingTool3.getBusinessCode());
                    downCuttingToolVO.setDownCount(0);
                    downCuttingToolVO.setDownLostCount(0);
                    map.put("downCuttingToolVO", downCuttingToolVO);
                }
                insideListDate.add(map);
            }

            outsideListData.add(insideListDate);

            // 初始化数据
            addLayout(insideListDate);
        }
    }


    //                Message message = new Message();
//                message.obj = synthesisCuttingToolBindleRecords;
//                setTextViewHandler.sendMessage(message);
    @SuppressLint("HandlerLeak")
    Handler setTextViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {



        }
    };




    int outsideRowNumber = 0;// 外部行号

    @android.support.annotation.IdRes
    int tvCailiao = 1000;
    int tvDaoJuType = 1001;
    int tvDaoJuNum = 1002;
    int tvZuzhuangNum = 1003;//组装
    int tvDiudaoNum = 1004;//丢刀

    /**
     * 添加布局
     */
//    private void addLayout(SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig) {
    private void addLayout(List<Map<String, Object>> insideListDate) {
        SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig = null;
        UpCuttingToolVO upCuttingToolVO = null;
        DownCuttingToolVO downCuttingToolVO = null;


        CuttingTool cuttingTool1 = null;
        UpCuttingToolVO upCuttingToolVO1 = null;
        DownCuttingToolVO downCuttingToolVO1 = null;

        CuttingTool cuttingTool2 = null;
        UpCuttingToolVO upCuttingToolVO2 = null;
        DownCuttingToolVO downCuttingToolVO2 = null;

        CuttingTool cuttingTool3 = null;
        UpCuttingToolVO upCuttingToolVO3 = null;
        DownCuttingToolVO downCuttingToolVO3 = null;


        for (int i=0; i<insideListDate.size(); i++) {
            if (i == 0) {
                Map<String, Object> map = insideListDate.get(i);
                synthesisCuttingToolLocationConfig = (SynthesisCuttingToolLocationConfig) map.get("synthesisCuttingToolLocationConfig");
                upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
            } else if (i == 1) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool1 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO1 = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO1 = (DownCuttingToolVO) map.get("downCuttingToolVO");
            } else if (i == 2) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool2 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO2 = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO2 = (DownCuttingToolVO) map.get("downCuttingToolVO");
            } else if (i == 3) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool3 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO3 = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO3 = (DownCuttingToolVO) map.get("downCuttingToolVO");
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
        tableRow.addView(getImage());

        // 内部table4
        TableLayout tableLayout4 = new TableLayout(this);
        tableLayout4.setLayoutParams(param2);
        tableLayout4.addView(getRowEdit(tvDiudaoNum, String.valueOf(downCuttingToolVO.getDownLostCount()), isZuanTou, synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode(), outsideRowNumber, 0));

        if (cuttingTool1 != null) {
            tableLayout4.addView(getRowEdit(tvDiudaoNum, String.valueOf(downCuttingToolVO1.getDownLostCount()), isZuanTou, cuttingTool1.getBusinessCode(), outsideRowNumber, 1));
        }

        if (cuttingTool2 != null) {
            tableLayout4.addView(getRowEdit(tvDiudaoNum, String.valueOf(downCuttingToolVO2.getDownLostCount()), isZuanTou, cuttingTool2.getBusinessCode(), outsideRowNumber, 2));
        }

        if (cuttingTool3 != null) {
            tableLayout4.addView(getRowEdit(tvDiudaoNum, String.valueOf(downCuttingToolVO3.getDownLostCount()), isZuanTou, cuttingTool3.getBusinessCode(), outsideRowNumber, 3));
        }

        // 添加到行中
        tableRow.addView(tableLayout4);

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

                    // 组装
                    if (id == 1003) {
                        addZuzhuangData(cailiao, Integer.parseInt(num), outsideRowNumber, insideRowNumber);
                    } else if (id == 1004) {
                        addDiudaoData(cailiao, Integer.parseInt(num), outsideRowNumber, insideRowNumber);
                    }
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
                        TableLayout mTableLayout4 = (TableLayout) mTableRow.getChildAt(8);// 丢刀数量

                        //
                        TextView tvZuzhuangshu = (TextView) ((TableRow) mTableLayout3.getChildAt(insideRowNumber)).getVirtualChildAt(0);

                        int numOld = Integer.parseInt(tvZuzhuangshu.getText().toString());
                        int zong = numOld + num;
                        tvZuzhuangshu.setText(zong+"");
                    }
                }

                // 是否选择丢刀
                if (cbDiudao.isChecked()) {
                    for (int j=0; j<insideListDate.size(); j++) {
                        map = insideListDate.get(j);

                        DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");

                        if (code.equals(downCuttingToolVO.getDownCode())) {
                            insideRowNumber = j;
                            downCuttingToolVO.setDownCount((downCuttingToolVO.getDownCount() + num));
//                            downCuttingToolVO.setDownRfidCode(rfid);
//                            downCuttingToolVO.setBladeCode(bladeCode);

                            // 外部行
                            TableRow mTableRow = (TableRow) mTlContainer.getChildAt(outsideRowNumber);

                            // 内部行
                            TableLayout mTableLayout = (TableLayout) mTableRow.getChildAt(0);// 材料号
                            TableLayout mTableLayout2 = (TableLayout) mTableRow.getChildAt(2);// 刀具类型
                            TextView mTextView = (TextView) mTableRow.getChildAt(4);// 总数量
                            TableLayout mTableLayout3 = (TableLayout) mTableRow.getChildAt(6);// 组装数量
                            TableLayout mTableLayout4 = (TableLayout) mTableRow.getChildAt(8);// 丢刀数量

                            //
                            TextView tvDiudao = (TextView) ((TableRow) mTableLayout4.getChildAt(insideRowNumber)).getVirtualChildAt(0);

                            int numOld = Integer.parseInt(tvDiudao.getText().toString());
                            int zong = numOld + num;
                            tvDiudao.setText(zong+"");

                            return;
                        }
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

    /**
     * 修改内存中表格丢刀数据
     * @param code 材料号
     * @param num 数量
     * @param outsideRowNumber 外部行号
     * @param insideRowNumber 内部行号
     */
    private void addDiudaoData(String code, int num, int outsideRowNumber, int insideRowNumber) {
        List<Map<String, Object>> insideListDate = outsideListData.get(outsideRowNumber);
        Map<String, Object> map = insideListDate.get(insideRowNumber);

        DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
        downCuttingToolVO.setDownLostCount(num);
    }




    /**
     * 从前一个页面返回时填充页面数据用的方法
     * @param code
     * @param num
     */
    private void addData2(String code, int num) {

        for (int k = 1; k < mTlContainer.getChildCount(); k++) {

            TableRow mTableRow = (TableRow) mTlContainer.getChildAt(k);

            TableLayout mTableLayout = (TableLayout) mTableRow.getChildAt(0);// 材料号
            TableLayout mTableLayout2 = (TableLayout) mTableRow.getChildAt(2);// 刀具类型
            TextView mTextView = (TextView) mTableRow.getChildAt(4);// 总数量
            TableLayout mTableLayout3 = (TableLayout) mTableRow.getChildAt(6);// 组装数量

            for (int i = 0; i < mTableLayout.getChildCount(); i++) {
                TextView tvCailiaohao = (TextView) ((TableRow) mTableLayout.getChildAt(i)).getVirtualChildAt(0);
                TextView tvZuzhuangshu = (TextView) ((TableRow) mTableLayout3.getChildAt(i)).getVirtualChildAt(0);

                if (tvCailiaohao.getText().toString().equals(code)) {
                    int numOld = Integer.parseInt(tvZuzhuangshu.getText().toString());
                    int zong = numOld + num;
                    tvZuzhuangshu.setText(zong+"");

                    break;
                }
            }

        }
    }


}
