package com.icomp.Iswtmv10.v01c01.c01s019;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.AverageProcessingVolume;
import com.apiclient.pojo.CuttingTool;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.vo.*;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s018.C01S018_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;

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
 * 厂外修磨页面1
 */
public class C01S019_001Activity extends CommonActivity {

    @BindView(R.id.llContainer)
    LinearLayout mLlContainer;

    @BindView(R.id.tvScan)
    TextView mTvScan;
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.btnNext)
    Button mBtnNext;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.textView4)
    TextView textView4;

    private int position = 0;

    private List<SharpenVO> sharpenVOList = new ArrayList<>();

    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();
    // 根据物料号查询的数据
    private Map<String, CuttingTool> materialNumToMap = new HashMap<>();

    // 需要授权的标签
    Map<String, Boolean> rfid_authorization_map = new HashMap<>();

    //当前选择的卸下原因，零部件种类在集合中的位置
    int average_processing_volume_posttion;
    // 平均加工量列表
    List<AverageProcessingVolume> averageProcessingVolumeList = new ArrayList<>();

    //调用接口
    private Retrofit retrofit;

    OutSideVO outSideVO = new OutSideVO();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s019_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        for (AverageProcessingVolume averageProcessingVolume : AverageProcessingVolume.values()){
            averageProcessingVolumeList.add(averageProcessingVolume);
        }

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            outSideVO = (OutSideVO) paramMap.get("outSideVO");


            Map<String, Object> paramMap2 = PARAM_MAP.get(2);
            if (paramMap2 != null) {
                outSideVO = (OutSideVO) paramMap2.get("outSideVO");
                rfidToMap = (Map<String, CuttingToolBind>) paramMap2.get("rfidToMap");
                materialNumToMap = (Map<String, CuttingTool>) paramMap2.get("materialNumToMap");
                sharpenVOList = (List<SharpenVO>) paramMap2.get("sharpenVOList");
                rfid_authorization_map = (Map<String, Boolean>) paramMap.get("rfid_authorization_map");

                for (SharpenVO sharpenVO : sharpenVOList) {
                    addLayout(sharpenVO.getCuttingToolBusinessCode(), sharpenVO.getCuttingToolBladeCode(), sharpenVO.getCount().toString());
                }

                PARAM_MAP.remove(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.tvScan, R.id.btnCancel, R.id.btnNext, R.id.ivAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
                Intent intent = new Intent(this, C01S019_000Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:
                if (sharpenVOList != null  && sharpenVOList.size() > 0) {
                    outSideVO.setSharpenVOS(sharpenVOList);

                    if (rfid_authorization_map != null && rfid_authorization_map.size() > 0) {
                        is_need_authorization = true;
                    } else {
                        is_need_authorization = false;
                    }

                    // 用于页面之间传值，新方法
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("rfidToMap", rfidToMap);
                    paramMap.put("materialNumToMap", materialNumToMap);
                    paramMap.put("outSideVO", outSideVO);
                    paramMap.put("sharpenVOList", sharpenVOList);
                    paramMap.put("rfid_authorization_map", rfid_authorization_map);
                    PARAM_MAP.put(2, paramMap);


                    Intent intent2 = new Intent(this, C01S019_002Activity.class);
                    // 不清空页面之间传递的值
                    intent2.putExtra("isClearParamMap", false);
                    startActivity(intent2);
                    finish();
                } else {
                    createAlertDialog(C01S019_001Activity.this, "请添加材料", Toast.LENGTH_LONG);
                }
                break;
            case R.id.ivAdd:
                showDialog();
                break;
            default:
        }
    }

//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if (isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//        mTvScan.setClickable(false);
//        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
//            scan();
//        } else {
//            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
//        }
//    }

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

    //查询弹框
    private PopupWindow addPopupWindow;

    /**
     * 显示数据提示dialog
     */
    //显示材料号和修磨数量的弹框
    private void showDialog() {
        if (null == addPopupWindow || !addPopupWindow.isShowing()) {
            //点击查询按钮以后，设置扫描按钮不可用
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View view = layoutInflater.inflate(R.layout.dialog_c01s018_001, null);
            addPopupWindow = new PopupWindow(view, (int) (screenWidth * 0.8), (int) (screenHeight * 0.4));
            addPopupWindow.setFocusable(true);
            addPopupWindow.setOutsideTouchable(false);
            addPopupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

            final EditText etmaterialNumber = (EditText) view.findViewById(R.id.etmaterialNumber);
            etmaterialNumber.setTransformationMethod(new AllCapTransformationMethod());
            final EditText etgrindingQuantity = (EditText) view.findViewById(R.id.etgrindingQuantity);

            Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
            Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPopupWindow.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null == etmaterialNumber.getText().toString().trim() || "".equals(etmaterialNumber.getText().toString().trim())) {
                        createAlertDialog(C01S019_001Activity.this, "请输入材料号", Toast.LENGTH_LONG);
                    } else if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
                        createAlertDialog(C01S019_001Activity.this, "请输入修磨数量", Toast.LENGTH_LONG);
                    } else {
                        if (Integer.parseInt(etgrindingQuantity.getText().toString()) <= 0) {
                            createAlertDialog(C01S019_001Activity.this, "数量要大于0", 0);
                            return;
                        }
                        if (materialNumToMap.containsKey(etmaterialNumber.getText().toString())) {
                            createAlertDialog(C01S019_001Activity.this, "已存在", Toast.LENGTH_SHORT);
                        } else {
                            search(etmaterialNumber.getText().toString().trim(), etgrindingQuantity.getText().toString().trim());
                        }
                        addPopupWindow.dismiss();
                    }
                }
            });
        }
    }

    //根据材料号查询合成刀具组成信息
    private void search(final String cailiao, final String num) {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            CuttingToolVO cuttingToolVO = new CuttingToolVO();
            cuttingToolVO.setBusinessCode(cailiao);

            String jsonStr = objectToJson(cuttingToolVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> getOutCuttingTool = iRequest.getOutCuttingTool(body);
            getOutCuttingTool.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            CuttingTool cuttingTool = jsonToObject(response.body(), CuttingTool.class);

                            if (cuttingTool != null) {
                                materialNumToMap.put(cailiao, cuttingTool);

                                SharpenVO sharpenVO = new SharpenVO();
                                sharpenVO.setCuttingToolBusinessCode(cailiao);// 材料号
                                sharpenVO.setCuttingToolCode(cuttingTool.getCode());
                                sharpenVO.setCount(Integer.parseInt(num));
                                sharpenVOList.add(sharpenVO);

                                addLayout(cailiao, "-", num);
                            } else {
                                Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C01S019_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                    } finally {
                        loading.dismiss();
                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    loading.dismiss();
                    createAlertDialog(C01S019_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    /**
     * 添加布局
     * @param cailiao 物料号
     * @param laserCode 刀身码
     * @param num 数量
     */
    private void addLayout(final String cailiao, String laserCode, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_changwaixiumo, null);

        final TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);
        ImageView tvRemove = (ImageView) mLinearLayout.findViewById(R.id.tvRemove);

        //将输入的材料号自动转化为大写
        tvCaiLiao.setTransformationMethod(new AllCapTransformationMethod());

        tvCaiLiao.setText(cailiao);
        tvsingleProductCode.setText(laserCode);
        tvNum.setText(num);

        mLinearLayout.setTag(position);


        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialNumToMap.remove(cailiao);


                for (SharpenVO sharpenVO : sharpenVOList) {
                    if (cailiao.equals(sharpenVO.getCuttingToolBusinessCode())) {

                        Set<String> keys = rfidToMap.keySet();
                        for (String key : keys) {
                            CuttingToolBind cb = rfidToMap.get(key);
                            if (sharpenVO.getCuttingToolBusinessCode().equals(cb.getCuttingTool().getBusinessCode())) {
                                rfidToMap.remove(key);
                                rfid_authorization_map.remove(key);
                                break;
                            }
                        }

                        sharpenVOList.remove(sharpenVO);
                        break;
                    }
                }

                mLlContainer.removeView(mLinearLayout);
            }
        });

        position++;
        mLlContainer.addView(mLinearLayout);
    }


    /** ----------------------扫描开始---------------------- **/
    //扫描线程
    private scanThread scanThread;

    /**
     * 扫描
     */
    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            mTvScan.setClickable(false);
            ivAdd.setClickable(false);
            mBtnCancel.setClickable(false);
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
            rfidString = singleScan();//TODO 生产环境需要解开
//            rfidString="18000A00000D434A";
            if ("close".equals(rfidString)) {
                mTvScan.setClickable(true);
                ivAdd.setClickable(true);
                mBtnCancel.setClickable(true);
                mBtnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvScan.setClickable(true);
                        ivAdd.setClickable(true);
                        mBtnCancel.setClickable(true);
                        mBtnNext.setClickable(true);
                        isCanScan = true;
                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                // 判断是否已经扫描此 rfid
                if (rfidToMap.containsKey(rfidString)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createAlertDialog(C01S019_001Activity.this, "已存在", 1);
                        }
                    });
                    return;
                }

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
                    CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
                    cuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                    String jsonStr = objectToJson(cuttingToolBindVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Map<String, String> headsMap = new HashMap<>();
                    headsMap.put("impower", OperationEnum.Cutting_tool_OutSide.getKey().toString());

                    Call<String> getOutCuttingToolBind = iRequest.getOutCuttingToolBind(body, headsMap);
                    getOutCuttingToolBind.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    CuttingToolBind cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);

                                    if (cuttingToolBind != null) {
                                        isShowExceptionBox(response.headers().get("impower"), rfidString, cuttingToolBind);
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
                                            createAlertDialog(C01S019_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S019_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    /**
     * 是否弹出异常操作框
     * @param headers http响应头，用于判断是否异常操作
     * @param rfid
     * @param cuttingToolBind
     */
    public void isShowExceptionBox(final String headers, final String rfid, final CuttingToolBind cuttingToolBind) throws IOException {
        Map<String, String> inpowerMap = jsonToObject(headers, Map.class);

        if ("1".equals(inpowerMap.get("type"))) {
            // 是否需要授权 true为需要授权；false为不需要授权
            is_need_authorization = false;
            setValue(rfid, cuttingToolBind);
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    setValue(rfid, cuttingToolBind);
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

                @Override
                public void cancel() {
                    // 实际上没有用
                    finish();
                }
            });
        }
    }


    // 设置值
    public void setValue(String rfid, CuttingToolBind cuttingToolBind) {
        if (is_need_authorization) {
            rfid_authorization_map.put(rfid, is_need_authorization);
        }

        rfidToMap.put(rfid, cuttingToolBind);

        SharpenVO sharpenVO = new SharpenVO();
        sharpenVO.setCuttingToolBusinessCode(cuttingToolBind.getCuttingTool().getBusinessCode());// 材料号
        sharpenVO.setCuttingToolBladeCode(cuttingToolBind.getBladeCode());// 刀身码
        sharpenVO.setCuttingToolCode(cuttingToolBind.getCuttingTool().getCode());
        sharpenVO.setCount(1);
        sharpenVOList.add(sharpenVO);


        addLayout(cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind.getBladeCode(), "-");

        searchSharpening(rfid, cuttingToolBind);
    }

    /**
     * 显示数据提示dialog
     */
    private void showDialog(final String name, final String r, final String laserCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this, R.layout.dialog_baofei1_c, null);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvBaoFei = (TextView) view.findViewById(R.id.tvBaofeiName);
        tvBaoFei.setText("报废一体刀" + laserCode);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btnSure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLayout(name, laserCode, "num");
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
//        dialog.getWindow().setLayout(300, 400);
    }

    // ----------------------以下代码根据需求后添加------------------------

    // 查询刃磨记录
    public void searchSharpening(final String rfid, final CuttingToolBind cuttingToolBind) {
        try {
            CuttingToolVO cuttingToolVO = new CuttingToolVO();
            cuttingToolVO.setBusinessCode(cuttingToolBind.getBladeCode());

            InsideFactoryVO insideFactoryVO = new InsideFactoryVO();
            insideFactoryVO.setCuttingToolVO(cuttingToolVO);

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = objectToJson(insideFactoryVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> countInsideFactory = iRequest.countInsideFactory(body);
            countInsideFactory.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Integer sharpeningRecord = jsonToObject(response.body(), Integer.class);
                            // 不是首次刃磨
                            if (sharpeningRecord > 0) {

                            } else {
                                showDialog2(rfid, cuttingToolBind);
                            }
                        } else {
                            createAlertDialog(C01S019_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                        }
                    });
                    createAlertDialog(C01S019_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    /**
     * 显示数据提示dialog
     */
    //显示材料号和修磨数量的弹框
    private void showDialog2(final String rfid, final CuttingToolBind cuttingToolBind) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_c01s018_001_1, null);
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialog).create();
        dialog.setView((this).getLayoutInflater().inflate(R.layout.dialog_c01s018_001_1, null));
        dialog.show();
        dialog.getWindow().setContentView(view);

        final EditText etgrindingQuantity = (EditText) view.findViewById(R.id.etgrindingQuantity);

        final LinearLayout ll01 = (LinearLayout) view.findViewById(R.id.ll_01);
        final TextView tv01 = (TextView) view.findViewById(R.id.tv_01);

        // 报废状态下拉列表
        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 收起软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etgrindingQuantity.getWindowToken(), 0);

                View view = LayoutInflater.from(C01S019_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
                ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
                MyAdapter myAdapter = new MyAdapter();
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
                        tv01.setText(averageProcessingVolumeList.get(i).getName());
                        average_processing_volume_posttion = i;
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
                if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
                    createAlertDialog(C01S019_001Activity.this, "请输入修磨数量", Toast.LENGTH_LONG);
                } else if (Integer.parseInt(etgrindingQuantity.getText().toString()) <= 0) {
                    createAlertDialog(C01S019_001Activity.this, "修磨数量要大于0", Toast.LENGTH_LONG);
                } else if (null == tv01.getText().toString().trim() || "".equals(tv01.getText().toString().trim())) {
                    createAlertDialog(C01S019_001Activity.this, "请选择平均加工量", Toast.LENGTH_LONG);
                } else {
                    try {
                        HistoryVO historyVO = new HistoryVO();
                        historyVO.setAvgProcessCount(Integer.parseInt(averageProcessingVolumeList.get(average_processing_volume_posttion).getKey()));
                        historyVO.setCount(Integer.parseInt(etgrindingQuantity.getText().toString().trim()));
                        historyVO.setCuttingToolBind(cuttingToolBind);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.show();
                            }
                        });

                        //调用接口，查询合成刀具组成信息
                        IRequest iRequest = retrofit.create(IRequest.class);

                        String jsonStr = objectToJson(historyVO);
                        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                        Call<String> addInsideFactoryHistory = iRequest.addInsideFactoryHistory(body);
                        addInsideFactoryHistory.enqueue(new MyCallBack<String>() {
                            @Override
                            public void _onResponse(Response<String> response) {
                                try {
                                    if (response.raw().code() == 200) {
                                        // 保存刃磨记录成功
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        final String errorStr = response.errorBody().string();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                createAlertDialog(C01S019_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                        createAlertDialog(C01S019_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
        });


        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.4));

    }

    //卸下原因下拉框的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return averageProcessingVolumeList.size();
        }

        @Override
        public Object getItem(int i) {
            return averageProcessingVolumeList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S019_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(averageProcessingVolumeList.get(i).getName());
            return view1;
        }
    }

}
