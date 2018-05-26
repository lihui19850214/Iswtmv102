package com.icomp.Iswtmv10.v01c01.c01s009;
/**
 * 合成刀具组装
 */

import android.content.Intent;
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
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    //合成刀具初始化参数类
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList = new ArrayList<>();

    List<UpCuttingToolVO> upCuttingToolVOList = new ArrayList<>();

    Map<String, Integer[]> zhuantouNumMap = new HashMap<>();

    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();

    String hechengdaoRfidString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01_s009_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        hechengdaoRfidString = getIntent().getStringExtra("rfidString");

        synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) getIntent().getSerializableExtra(PARAM);

        tv01.setText(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());

        synthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

        for (SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig:synthesisCuttingToolLocationConfigList) {
            // 组装
            UpCuttingToolVO upCuttingToolVO = new UpCuttingToolVO();
            upCuttingToolVO.setUpCode(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode());


            // 钻头的"组装数量"根据扫码来的，每次1个
            upCuttingToolVO.setUpCount(0);
            Integer[] zuzhuangnum = new Integer[]{synthesisCuttingToolLocationConfig.getCount(), 0};
            zhuantouNumMap.put(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode(), zuzhuangnum);

            // 换装数量
            upCuttingToolVOList.add(upCuttingToolVO);

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
                // 换装数量
                upCuttingToolVOList.add(upCuttingToolVO);
                // 0：总数；1为组装数；
                Integer[] num = new Integer[]{synthesisCuttingToolLocationConfig.getCount(), 0};
                zhuantouNumMap.put(cuttingTool1.getBusinessCode(), num);
            }
            // 替换刀2
            if (cuttingTool2 != null) {
                upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setUpCode(cuttingTool2.getBusinessCode());
                upCuttingToolVO.setUpCount(0);
                // 换装数量
                upCuttingToolVOList.add(upCuttingToolVO);
                // 0：总数；1为组装数；
                Integer[] num = new Integer[]{synthesisCuttingToolLocationConfig.getCount(), 0};
                zhuantouNumMap.put(cuttingTool2.getBusinessCode(), num);
            }
            // 替换刀3
            if (cuttingTool3 != null) {
                upCuttingToolVO = new UpCuttingToolVO();
                upCuttingToolVO.setUpCode(cuttingTool3.getBusinessCode());
                upCuttingToolVO.setUpCount(0);
                // 换装数量
                upCuttingToolVOList.add(upCuttingToolVO);
                // 0：总数；1为组装数；
                Integer[] num = new Integer[]{synthesisCuttingToolLocationConfig.getCount(), 0};
                zhuantouNumMap.put(cuttingTool3.getBusinessCode(), num);
            }

            addLayout(synthesisCuttingToolLocationConfig);
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
                startActivity(intent2);
                finish();
                break;
            case R.id.btnNext:
                Set<String> keys = zhuantouNumMap.keySet();
                for (String key:keys) {
                    Integer[] zuzhuangNum = zhuantouNumMap.get(key);
                    if (zuzhuangNum[0].intValue() != zuzhuangNum[1].intValue()) {
                        createAlertDialog(C01S009_002Activity.this, "请确认组装数量", Toast.LENGTH_SHORT);
                        return;
                    }
                }

                loading.show();
                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);


                List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList = new ArrayList<>();
                synthesisCuttingToolConfigList.add(synthesisCuttingToolConfig);

                SynthesisCuttingTool synthesisCuttingTool = new SynthesisCuttingTool();
                synthesisCuttingTool.setSynthesisCuttingToolConfigList(synthesisCuttingToolConfigList);

                SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();
                synthesisCuttingToolBind.setSynthesisCuttingTool(synthesisCuttingTool);
                synthesisCuttingToolBind.setSynthesisCuttingToolCode(synthesisCuttingToolConfig.getSynthesisCuttingToolCode());
                RfidContainer rfidContainer = new RfidContainer();
                rfidContainer.setLaserCode(hechengdaoRfidString);
                synthesisCuttingToolBind.setRfidContainer(rfidContainer);

                // 删除 UpCount 为 0 的数据
                for (int i=0; i<upCuttingToolVOList.size();i++) {
                    UpCuttingToolVO upCuttingToolVO = upCuttingToolVOList.get(i);
                    if (upCuttingToolVO.getUpCount() == 0) {
                        upCuttingToolVOList.remove(i);
                        i--;
                    }
                }
                PackageUpVO packageUpVO = new PackageUpVO();
                packageUpVO.setUpCuttingToolVOS(upCuttingToolVOList);
                packageUpVO.setSynthesisCuttingToolBind(synthesisCuttingToolBind);


                Gson gson = new Gson();
                String jsonStr = gson.toJson(packageUpVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> packageUp = iRequest.packageUp(body);
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

                break;
            default:
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

                                    if (zhuantouNumMap.containsKey(cuttingToolBind.getCuttingTool().getBusinessCode())) {
                                        Integer[] zuzhuangNum = zhuantouNumMap.get(cuttingToolBind.getCuttingTool().getBusinessCode());
                                        if (zuzhuangNum[0].intValue() == zuzhuangNum[1].intValue()) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    createAlertDialog(C01S009_002Activity.this, "组装数量已满足", Toast.LENGTH_SHORT);
                                                }
                                            });
                                            return;
                                        }
                                        rfidSet.add(rfidString);
                                        // 给哪个刀具类型增加组装数量，默认只给转头添加组装数量
                                        addData(cuttingToolBind.getCuttingTool().getBusinessCode(), 1, rfidString);
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


//    /**
//     * 添加布局
//     */
//    private void addLayout(SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig) {
//
//        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_daojuzuzhuang_1, null);
//        TextView tvCailiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
//        TextView tvDaoJuType = (TextView) mLinearLayout.findViewById(R.id.tvDaoJuType);
//        TextView tvDaoJuNum = (TextView) mLinearLayout.findViewById(R.id.tvDaoJuNum);
//        TextView etZuzhuangNum = (TextView) mLinearLayout.findViewById(R.id.etZuzhuangNum);
//
//        //材料号
//        tvCailiao.setText(synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode());
//
//        //刀具类型
//        //1钻头、2刀片、3一体刀、4专机、9其他
//        if ("1".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            zhuantouNum = synthesisCuttingToolLocationConfig.getCount();
//            tvDaoJuType.setText("钻头");
//            tvDaoJuType.setTag("1");
//            etZuzhuangNum.setText("0");
//        } else if ("2".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            tvDaoJuType.setText("刀片");
//            etZuzhuangNum.setText(synthesisCuttingToolLocationConfig.getCount().toString());
//        } else if ("3".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            tvDaoJuType.setText("一体刀");
//            etZuzhuangNum.setText(synthesisCuttingToolLocationConfig.getCount().toString());
//        } else if ("4".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            tvDaoJuType.setText("专机");
//            etZuzhuangNum.setText(synthesisCuttingToolLocationConfig.getCount().toString());
//        } else if ("9".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            tvDaoJuType.setText("其他");
//            etZuzhuangNum.setText(synthesisCuttingToolLocationConfig.getCount().toString());
//        }
//
//        //总数量
//        tvDaoJuNum.setText(synthesisCuttingToolLocationConfig.getCount().toString());
//        mLlContainer.addView(mLinearLayout);
//    }


    @android.support.annotation.IdRes
    int tvCailiao = 1000;
    int tvDaoJuType = 1001;
    int tvDaoJuNum = 1002;
    int tvZuzhuangNum = 1003;

    /**
     * 添加布局
     */
    private void addLayout(SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig) {
        CuttingTool cuttingTool1 = synthesisCuttingToolLocationConfig.getReplaceCuttingTool1();
        CuttingTool cuttingTool2 = synthesisCuttingToolLocationConfig.getReplaceCuttingTool2();
        CuttingTool cuttingTool3 = synthesisCuttingToolLocationConfig.getReplaceCuttingTool3();
        String zuzhuangNum = "0";
        String daojuType = "";
        boolean isZuanTou = false;

        //刀具类型(1钻头、2刀片、3一体刀、4专机、9其他)
        if ("1".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
            daojuType = "钻头";
            isZuanTou = true;
        } else if ("2".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
            daojuType = "刀片";
        } else if ("3".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
            daojuType = "一体刀";
        } else if ("4".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
            daojuType = "专机";
        } else if ("9".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
            daojuType = "其他";
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
        tableLayout3.addView(getRowEdit(tvZuzhuangNum, zuzhuangNum, isZuanTou, synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode()));
        if (cuttingTool1 != null) {
            tableLayout3.addView(getRowEdit(tvDaoJuType, zuzhuangNum, isZuanTou, cuttingTool1.getBusinessCode()));
        }

        if (cuttingTool2 != null) {
            tableLayout3.addView(getRowEdit(tvDaoJuType, zuzhuangNum, isZuanTou, cuttingTool2.getBusinessCode()));
        }

        if (cuttingTool3 != null) {
            tableLayout3.addView(getRowEdit(tvDaoJuType, zuzhuangNum, isZuanTou, cuttingTool3.getBusinessCode()));
        }

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

    private TableRow getRowEdit(int id, String text, boolean isZuanTou, final String cailiao) {
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
                public void afterTextChanged(Editable s) {
                    if (et1.getText() != null && !"".equals(et1.getText().toString())) {
                        //输入文字后的状态
                        Log.i("ceshi", et1.getText().toString());
                        addData(cailiao, Integer.parseInt(et1.getText().toString()), null);
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
     * 捏合数据
     *
     * @param code
     * @param num
     * @return
     */
    private void addData(String code, int num, String rfid) {
        boolean isExist = false;
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
                    isExist = true;

                    int zong = num;
                    // rfid不为空是扫描进来的
                    if (rfid != null) {
                        int numOld = Integer.parseInt(tvZuzhuangshu.getText().toString());
                        zong = numOld + num;
                        tvZuzhuangshu.setText(zong+"");
                    }


                    // 处理组装
                    for (int j = 0; j < upCuttingToolVOList.size(); j++) {
                        if (upCuttingToolVOList.get(j).getUpCode().equals(code)) {
                            upCuttingToolVOList.get(j).setUpCount(zong);
                            upCuttingToolVOList.get(j).setRfidCode(rfid);
                            break;
                        }
                    }
                    break;
                }
            }

            // 如果材料号已存在，需要更新材料组装数量和替换刀组装数量
            if (isExist) {
                isExist = false;
                int zongNum = 0;
                String[] cailiaohaos = new String[mTableLayout.getChildCount()];
                for (int i = 0; i < mTableLayout.getChildCount(); i++) {
                    TextView tvCailiaohao = (TextView) ((TableRow) mTableLayout.getChildAt(i)).getVirtualChildAt(0);
                    TextView tvZuzhuangshu = (TextView) ((TableRow) mTableLayout3.getChildAt(i)).getVirtualChildAt(0);

                    zongNum = zongNum + Integer.parseInt(tvZuzhuangshu.getText().toString());
                    cailiaohaos[i] = tvCailiaohao.getText().toString();
                }

                for (String cailiaohao:cailiaohaos) {
                    Integer[] zuzhuangNum = zhuantouNumMap.get(cailiaohao);

                    zuzhuangNum[1] = zongNum;

                    zhuantouNumMap.put(cailiaohao, zuzhuangNum);
                }

            }

        }
    }


}
