package com.icomp.Iswtmv10.v01c01.c01s008;
/**
 * 刀具拆分
 */

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
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

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
    List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = new ArrayList<>();

    List<DownCuttingToolVO> downCuttingToolVOList = new ArrayList<>();

    Map<String, Integer[]> zhuantouNumMap = new HashMap<>();

    // 防止扫描重复标签
    Set<String> rfidSet = new HashSet<>();

    String hechengdaoRfidString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s008_002);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();


        hechengdaoRfidString = getIntent().getStringExtra("rfidString");

        synthesisCuttingToolBind = (SynthesisCuttingToolBind) getIntent().getSerializableExtra(PARAM);

        tv01.setText(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());

        synthesisCuttingToolLocationList = synthesisCuttingToolBind.getSynthesisCuttingToolLocationList();

        for (SynthesisCuttingToolLocation synthesisCuttingToolLocation:synthesisCuttingToolLocationList) {
            if ("1".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
                // 组装
                DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                downCuttingToolVO.setBladeCode(synthesisCuttingToolLocation.getCuttingToolBladeCode());
                downCuttingToolVO.setDownCode(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                // 钻头的"组装数量"根据扫码来的，每次1个
                downCuttingToolVO.setDownCount(0);
                // 换装数量
                downCuttingToolVOList.add(downCuttingToolVO);

                Integer[] zuzhuangnum = new Integer[]{synthesisCuttingToolLocation.getCount(), 0};
                zhuantouNumMap.put(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode(), zuzhuangnum);
            } else {
                // 组装
                DownCuttingToolVO downCuttingToolVO = new DownCuttingToolVO();
                downCuttingToolVO.setBladeCode(synthesisCuttingToolLocation.getCuttingToolBladeCode());
                downCuttingToolVO.setDownCode(synthesisCuttingToolLocation.getCuttingTool().getBusinessCode());
                // 钻头的"组装数量"根据扫码来的，每次1个
                downCuttingToolVO.setDownCount(synthesisCuttingToolLocation.getCount());
                // 换装数量
                downCuttingToolVOList.add(downCuttingToolVO);
            }

            addLayout(synthesisCuttingToolLocation);
        }

    }

    @OnClick({R.id.btnCancel, R.id.btnNext, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
//                stopScan();
                Intent intent2 = new Intent(c01s008_002Activity.this, C01S008_001Activity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnNext:
                Set<String> keys = zhuantouNumMap.keySet();
                for (String key:keys) {
                    Integer[] zuzhuangNum = zhuantouNumMap.get(key);
                    if (zuzhuangNum[0].intValue() != zuzhuangNum[1].intValue()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createAlertDialog(c01s008_002Activity.this, "请确认拆分数量", Toast.LENGTH_SHORT);
                            }
                        });

                        return;
                    }
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
//            rfidString = "18000A00000F3B78";
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
                } else {
                    boolean isUnsatisfied = false;
                    Set<String> keys = zhuantouNumMap.keySet();
                    for (String key : keys) {
                        Integer[] zuzhuangNum = zhuantouNumMap.get(key);
                        if (zuzhuangNum[0] != zuzhuangNum[1]) {
                            isUnsatisfied = true;
                            break;
                        }
                    }

                    if (isUnsatisfied) {
                        rfidSet.add(rfidString);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addData(null,1, rfidString);
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createAlertDialog(c01s008_002Activity.this, "拆分数量已满足", Toast.LENGTH_SHORT);
                            }
                        });
                    }

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
    private void addLayout(SynthesisCuttingToolLocation synthesisCuttingToolLocation) {

        String zuzhuangNum = "0";
        String daojuType = "";
        boolean isZuanTou = false;

        //刀具类型(1钻头、2刀片、3一体刀、4专机、9其他)
        if ("1".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
            daojuType = "钻头";
            isZuanTou = true;
        } else if ("2".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
            daojuType = "刀片";
        } else if ("3".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
            daojuType = "一体刀";
        } else if ("4".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
            daojuType = "专机";
        } else if ("9".equals(synthesisCuttingToolLocation.getCuttingTool().getConsumeType())) {
            zuzhuangNum = synthesisCuttingToolLocation.getCount()+"";
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
        tableLayout3.addView(getRowEdit(tvZuzhuangNum, zuzhuangNum, isZuanTou, synthesisCuttingToolLocation.getCuttingTool().getBusinessCode()));


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
        tv1.setTag("1");//代表钻头

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
            et1.setFocusable(false);
            et1.setFocusableInTouchMode(false);
//            et1.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    //输入文本之前的状态
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    //输入文字中的状态，count是输入字符数
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (et1.getText() != null && !"".equals(et1.getText().toString())) {
//                        //输入文字后的状态
//                        Log.i("ceshi", et1.getText().toString());
//                        addData(cailiao, Integer.parseInt(et1.getText().toString()), null);
//                    }
//                }
//            });
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
        Set<String> keys = zhuantouNumMap.keySet();
        for (String key : keys) {
            Integer[] zuzhuangNum = zhuantouNumMap.get(key);
            if (zuzhuangNum[0] != zuzhuangNum[1]) {
                zuzhuangNum[1] = 1;
                zhuantouNumMap.put(key, zuzhuangNum);
                code = key;
                break;
            }
        }

        if (code == null || "".equals(code)) return;

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

                if (tvCailiaohao.getText().toString().equals(code) ) {

                    int zong = num;
                    // rfid不为空是扫描进来的
                    if (rfid != null) {
                        tvZuzhuangshu.setText("1");

                        // 处理组装
                        for (int j = 0; j < downCuttingToolVOList.size(); j++) {
                            if (downCuttingToolVOList.get(j).getDownCode().equals(code)) {
                                downCuttingToolVOList.get(j).setDownCount(1);
                                downCuttingToolVOList.get(j).setDownRfidCode(rfid);

                                isExist = true;
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            if (isExist) {
                break;
            }

        }
    }



    private void requestData(List<AuthCustomer> authorizationList) {
        loading.show();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> headsMap = new HashMap<>();
        // 需要授权信息
        if (is_need_authorization && authorizationList != null) {
            try {
                //设定用户访问信息
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                String userInfoJson = sharedPreferences.getString("loginInfo", null);

                AuthCustomer authCustomer = mapper.readValue(userInfoJson, AuthCustomer.class);

                // 授权信息
                ImpowerRecorder impowerRecorder = new ImpowerRecorder();
                impowerRecorder.setOperatorUserCode(authCustomer.getCode());//操作者code
                impowerRecorder.setOperatorUserName(authCustomer.getName());//操作者姓名
                impowerRecorder.setImpowerUser(authorizationList.get(0).getCode());//授权人code
                impowerRecorder.setImpowerUserName(authorizationList.get(0).getName());//授权人名称
                impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Exchange.getKey().toString());//操作key
                impowerRecorder.setOperatorValue(OperationEnum.SynthesisCuttingTool_Exchange.getName());//操作者code

                headsMap.put("impower", mapper.writeValueAsString(impowerRecorder));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //调用接口，查询合成刀具组成信息
        IRequest iRequest = retrofit.create(IRequest.class);

        SynthesisCuttingTool synthesisCuttingTool = new SynthesisCuttingTool();
        synthesisCuttingTool.setSynthesisCuttingToolLocationList(synthesisCuttingToolBind.getSynthesisCuttingToolLocationList());


        SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();
        synthesisCuttingToolBind.setSynthesisCuttingTool(synthesisCuttingTool);
        synthesisCuttingToolBind.setSynthesisCuttingToolCode(synthesisCuttingToolBind.getSynthesisCuttingToolCode());
        RfidContainer rfidContainer = new RfidContainer();
        rfidContainer.setLaserCode(hechengdaoRfidString);
        synthesisCuttingToolBind.setRfidContainer(rfidContainer);

        // 删除 UpCount 为 0 的数据
        for (int i=0; i<downCuttingToolVOList.size();i++) {
            DownCuttingToolVO downCuttingToolVO = downCuttingToolVOList.get(i);
            if (downCuttingToolVO.getDownCount() == 0) {
                downCuttingToolVOList.remove(i);
                i--;
            }
        }
        BreakUpVO packageUpVO = new BreakUpVO();
        packageUpVO.setDownCuttingToolVOS(downCuttingToolVOList);
        packageUpVO.setSynthesisCuttingToolBind(synthesisCuttingToolBind);


        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(packageUpVO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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
    }


}