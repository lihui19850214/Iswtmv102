package com.icomp.Iswtmv10.v01c01.c01s008;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
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
import com.fasterxml.jackson.core.JsonProcessingException;
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

/**
 * 刀具拆分
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
    @BindView(R.id.activity_c01s008_002)
    LinearLayout activityC01s008002;


    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private scanThread scanThread;

    //合成刀具初始化参数类
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();

    List<DownCuttingToolVO> downCuttingToolVOList = new ArrayList<>();

    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();
    // 合成刀标签
    String synthesisCuttingToolBindRFID = "";

    List<Map<String, Object>> outsideListData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s008_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            synthesisCuttingToolBindRFID = (String) paramMap.get("synthesisCuttingToolBindRFID");
            synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");

            setValue();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    private void setValue() {
        try {
            tv01.setText(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());

            List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = synthesisCuttingToolBind.getSynthesisCuttingToolLocationList();

            for (SynthesisCuttingToolLocation synthesisCuttingToolLocation : synthesisCuttingToolLocationList) {
                Map<String, Object> map = new HashMap<>();

                map.put("synthesisCuttingToolLocation", synthesisCuttingToolLocation);

                // 拆分
                DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                downCuttingToolVO.setBladeCode(synthesisCuttingToolLocation.getCuttingToolBladeCode());
                downCuttingToolVO.setDownCode(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                downCuttingToolVO.setDownCount(synthesisCuttingToolLocation.getCount());

                if (CuttingToolTypeEnum.dj.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getType())) {
                    // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                    if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                        map.put("isZuanTou", true);
                        downCuttingToolVO.setDownCount(0);
                    }
                }

                map.put("downCuttingToolVO", downCuttingToolVO);
                outsideListData.add(map);


                addLayout(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
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

        for (int i=0; i<outsideListData.size(); i++) {
            Map<String, Object> map = outsideListData.get(i);

            // 内部第一行数据为主刀，有标记是否为转头
            if (map.containsKey("isZuanTou")) {
                SynthesisCuttingToolLocation synthesisCuttingToolLocation = (SynthesisCuttingToolLocation) map.get("synthesisCuttingToolLocation");
                int zongNum = synthesisCuttingToolLocation.getCount();

                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                int chaifenZongNum = downCuttingToolVO.getDownCount();

                // 如果拆分数不等于总数，表示未满足拆分数量，需要再扫描
                if (zongNum != chaifenZongNum) {
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
        for (int i=0; i<outsideListData.size(); i++) {
            Map<String, Object> map = outsideListData.get(i);

            // 内部第一行数据为主刀，有标记是否为转头
            if (map.containsKey("isZuanTou")) {
                SynthesisCuttingToolLocation synthesisCuttingToolLocation = (SynthesisCuttingToolLocation) map.get("synthesisCuttingToolLocation");
                int zongNum = synthesisCuttingToolLocation.getCount();

                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                int chaifenZongNum = downCuttingToolVO.getDownCount();

                // 如果拆分数不等于总数，表示未满足拆分数量，需要再扫描
                if (zongNum != chaifenZongNum) {
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
                startActivity(intent2);
                finish();
                break;
            case R.id.btnNext:

                if (!checkData()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createAlertDialog(c01s008_002Activity.this, "请确认拆分数量", Toast.LENGTH_SHORT);
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
//            rfidString = "18000A00000F3B78-1";
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
                            createAlertDialog(c01s008_002Activity.this, "此标签已经扫描过，请扫描其他标签", Toast.LENGTH_LONG);
                        }
                    });
                } else if (checkRfidData()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createAlertDialog(c01s008_002Activity.this, "拆分数量已满足", Toast.LENGTH_LONG);
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

                        //调用接口，查询合成刀具组成信息
                        IRequest iRequest = retrofit.create(IRequest.class);

                        RfidContainerVO rfidContainerVO = new RfidContainerVO();
                        rfidContainerVO.setLaserCode(rfidString);

                        String jsonStr = objectToJson(rfidContainerVO);
                        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                        Call<String> searchCuttingToolBind = iRequest.queryRFIDForUnConfig(body);
                        searchCuttingToolBind.enqueue(new MyCallBack<String>() {
                            @Override
                            public void _onResponse(Response<String> response) {
                                try {
                                    if (response.raw().code() == 200) {
                                        rfidSet.add(rfidString);
                                        // 给哪个刀具类型增加组装数量，默认只给转头添加组装数量
                                        addRfidData(1, rfidString);
                                    } else {
                                        final String errorStr = response.errorBody().string();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                createAlertDialog(c01s008_002Activity.this, errorStr, Toast.LENGTH_LONG);
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
                                        createAlertDialog(c01s008_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
                                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }
    }


    @android.support.annotation.IdRes
    int tvCailiao = 1000;
    int tvDaoJuType = 1001;
    int tvDaoJuNum = 1002;
    int tvZuzhuangNum = 1003;

    /**
     * 添加布局
     */
    private void addLayout(Map<String, Object> map) {
        SynthesisCuttingToolLocation synthesisCuttingToolLocation = (SynthesisCuttingToolLocation) map.get("synthesisCuttingToolLocation");

        String chaifenNum = synthesisCuttingToolLocation.getCount()+"";
        String daojuType = "";
        boolean isZuanTou = false;

//        //刀具类型(1钻头、2刀片、3一体刀、4专机、9其他)
//        if ("1".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
//            daojuType = "钻头";
//            isZuanTou = true;
//        } else if ("2".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
//            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
//            daojuType = "刀片";
//        } else if ("3".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
//            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
//            daojuType = "一体刀";
//        } else if ("4".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
//            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
//            daojuType = "专机";
//        } else if ("9".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
//            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
//            daojuType = "其他";
//        }


        // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
        if (CuttingToolTypeEnum.dj.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getType())) {
            // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
            if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                isZuanTou = true;
                daojuType = CuttingToolConsumeTypeEnum.griding_zt.getName();
            } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.griding_dp.getName();
            } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.single_use_dp.getName();
            } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.other.getName();
            }
        } else if (CuttingToolTypeEnum.fj.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.fj.getName();
        } else if (CuttingToolTypeEnum.pt.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.pt.getName();
        } else if (CuttingToolTypeEnum.other.getKey().equals(synthesisCuttingToolLocation.getCuttingTool().getType())) {
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
        tableLayout1.addView(getRow(tvCailiao, synthesisCuttingToolLocation.getCuttingTool().getBusinessCode()));

        // 添加到行中
        tableRow.addView(tableLayout1);
        tableRow.addView(getImage());


        // 内部table2
        TableLayout tableLayout2 = new TableLayout(this);
        tableLayout2.setLayoutParams(param2);
        tableLayout2.addView(getRow(tvDaoJuType, daojuType));

        // 添加到行中
        tableRow.addView(tableLayout2);
        tableRow.addView(getImage());


        TextView tv1 = new TextView(this);
        tv1.setId(tvDaoJuNum);
        tv1.setLayoutParams(param3);
        tv1.setGravity(Gravity.CENTER);
        tv1.setText(synthesisCuttingToolLocation.getCount().toString());//总数量


        // 添加到行中
        tableRow.addView(tv1);
        tableRow.addView(getImage());


        // 内部table3
        TableLayout tableLayout3 = new TableLayout(this);
        tableLayout3.setLayoutParams(param2);
        tableLayout3.addView(getRow(tvZuzhuangNum, chaifenNum));


        // 添加到行中
        tableRow.addView(tableLayout3);


        mTlContainer.addView(tableRow);
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
     * @param num 数量
     * @param rfid 标签
     */
    private void addRfidData(int num, String rfid) {
        int outsideRowNumber = -1;

        for (int i=0; i<outsideListData.size(); i++) {
            Map<String, Object> map = outsideListData.get(i);

            if (map.containsKey("isZuanTou")) {
                outsideRowNumber = i+1;

                SynthesisCuttingToolLocation synthesisCuttingToolLocation = (SynthesisCuttingToolLocation) map.get("synthesisCuttingToolLocation");
                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");

                if (downCuttingToolVO.getDownCount() < synthesisCuttingToolLocation.getCount()) {
                    downCuttingToolVO.setDownCount(num);
                    downCuttingToolVO.setDownRfidLaserCode(rfid);

                    // 外部行
                    TableRow mTableRow = (TableRow) mTlContainer.getChildAt(outsideRowNumber);

                    // 内部行
                    TableLayout mTableLayout = (TableLayout) mTableRow.getChildAt(0);// 材料号
                    TableLayout mTableLayout2 = (TableLayout) mTableRow.getChildAt(2);// 刀具类型
                    TextView mTextView = (TextView) mTableRow.getChildAt(4);// 总数量
                    TableLayout mTableLayout3 = (TableLayout) mTableRow.getChildAt(6);// 拆分数量

                    //
                    TextView tvChaifenshu = (TextView) ((TableRow) mTableLayout3.getChildAt(0)).getVirtualChildAt(0);

                    int numOld = Integer.parseInt(tvChaifenshu.getText().toString());
                    int zong = numOld + num;
                    tvChaifenshu.setText(zong+"");

                    return;
                }
            }
        }
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
                    impowerRecorder.setRfidLasercode(authCustomer.getRfidContainer().getLaserCode());// rfid标签
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_UnConfig.getKey().toString());//操作key

//                impowerRecorder.setOperatorUserName(URLEncoder.encode(authCustomer.getName(),"utf-8"));//操作者姓名
//                impowerRecorder.setImpowerUserName(URLEncoder.encode(authorizationList.get(0).getName(),"utf-8"));//授权人名称
//                impowerRecorder.setOperatorValue(URLEncoder.encode(OperationEnum.SynthesisCuttingTool_Exchange.getName(),"utf-8"));//操作者code

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(c01s008_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            }

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);

            // 循环拆分数量
            for (int i = 0; i < outsideListData.size(); i++) {
                Map<String, Object> map = outsideListData.get(i);

                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                // DownCount 为 0 的数据不要
                if (downCuttingToolVO.getDownCount() > 0) {
                    downCuttingToolVOList.add(downCuttingToolVO);
                }
            }


            BreakUpVO packageUpVO = new BreakUpVO();
            packageUpVO.setDownCuttingToolVOS(downCuttingToolVOList);
            packageUpVO.setSynthesisCuttingToolBind(synthesisCuttingToolBind);


            String jsonStr = objectToJson(packageUpVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> packageUp = iRequest.breakUp(body, headsMap);
            packageUp.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到库存盘点刀具信息详细页面
                            Intent intent = new Intent(c01s008_002Activity.this, c01s008_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            final String errorStr = response.errorBody().string();
                            createAlertDialog(c01s008_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                    createAlertDialog(c01s008_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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