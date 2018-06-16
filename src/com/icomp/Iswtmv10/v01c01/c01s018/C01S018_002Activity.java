package com.icomp.Iswtmv10.v01c01.c01s018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.*;
import android.widget.*;

import com.apiclient.constants.OperationEnum;
import com.apiclient.dto.InFactoryDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;

import java.io.IOException;
import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 厂内修磨页面2
 */
public class C01S018_002Activity extends CommonActivity {

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
//    @BindView(R.id.ivAdd)
//    ImageView ivAdd;
    @BindView(R.id.textView4)
    TextView textView4;

    private int position = 0;

    // 根据 rfid 查询的数据
    Map<String, CuttingToolBind> rfidToMap = new HashMap<>();
    // rfid对应GrindingVO
    Map<String, GrindingVO> rfidToGrindingVOMap = new HashMap<>();
    // 根据物料号对应刀身码
    private Map<String, String> businessCodeToBladeCodeMap = new HashMap<>();

    // 需要授权的标签
    Map<String, Boolean> rfid_authorization_map = new HashMap<>();

    InFactoryDTO inFactoryDTO = new InFactoryDTO();


    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_002);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        if (paramMap != null) {
            try {
                rfidToMap = (Map<String, CuttingToolBind>) paramMap.get("rfidToMap");
                inFactoryDTO = (InFactoryDTO) paramMap.get("inFactoryDTO");
                rfidToGrindingVOMap = (Map<String, GrindingVO>) paramMap.get("rfidToGrindingVOMap");
                rfid_authorization_map = (Map<String, Boolean>) paramMap.get("rfid_authorization_map");
                businessCodeToBladeCodeMap = (Map<String, String>) paramMap.get("businessCodeToBladeCodeMap");

                Set<String> rfids = rfidToGrindingVOMap.keySet();

                for (String rfid : rfids) {
                    GrindingVO grindingVO = rfidToGrindingVOMap.get(rfid);
                    String bladeCode = businessCodeToBladeCodeMap.get(grindingVO.getCuttingTool().getBusinessCode());
                    addLayout(grindingVO.getCuttingTool().getBusinessCode(), bladeCode, rfid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//, R.id.ivAdd
    @OnClick({R.id.tvScan, R.id.btnCancel, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnNext:

                if (rfidToGrindingVOMap != null  && rfidToGrindingVOMap.size() > 0) {
                    List<GrindingVO> grindingVOList = new ArrayList<>();

                    Set<String> rfids = rfidToGrindingVOMap.keySet();
                    for (String rfid : rfids) {
                        grindingVOList.add(rfidToGrindingVOMap.get(rfid));
                    }

                    inFactoryDTO.setGrindingVOS(grindingVOList);

                    if (rfid_authorization_map != null && rfid_authorization_map.size() > 0) {
                        is_need_authorization = true;
                    } else {
                        is_need_authorization = false;
                    }

                    // 用于页面之间传值，新方法
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("rfidToMap", rfidToMap);
                    paramMap.put("inFactoryDTO", inFactoryDTO);
                    paramMap.put("rfidToGrindingVOMap", rfidToGrindingVOMap);
                    paramMap.put("rfid_authorization_map", rfid_authorization_map);
                    paramMap.put("businessCodeToBladeCodeMap", businessCodeToBladeCodeMap);
                    PARAM_MAP.put(1, paramMap);


                    Intent intent2 = new Intent(C01S018_002Activity.this, C01S018_003Activity.class);
                    // 不清空页面之间传递的值
                    intent2.putExtra("isClearParamMap", false);
                    startActivity(intent2);
                    finish();
                } else {
                    createAlertDialog(C01S018_002Activity.this, "请添刀具", Toast.LENGTH_LONG);
                }
                break;
//            case R.id.ivAdd:
//                showDialog();
//                break;
            default:
        }
    }

//    //重写键盘上扫描按键的方法
//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if (isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//
//        scan();
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

    /**
     * 添加布局
     */
    private void addLayout(final String cailiao, String laserCode, final String rfid) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_changneixiumo, null);

        final TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        ImageView tvRemove = (ImageView) mLinearLayout.findViewById(R.id.tvRemove);

        tvCaiLiao.setText(cailiao);
        tvsingleProductCode.setText(laserCode);
//        tvNum.setText(num);

        tvCaiLiao.setTag(position);
        mLinearLayout.setTag(position);

        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rfidToMap.remove(rfid);
                rfid_authorization_map.remove(rfid);
                rfidToGrindingVOMap.remove(rfid);

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
//            ivAdd.setClickable(false);
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
//            rfidString = "18000A00000F045B";
            if ("close".equals(rfidString)) {
                mTvScan.setClickable(true);
//                ivAdd.setClickable(true);
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
//                        ivAdd.setClickable(true);
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
                            createAlertDialog(C01S018_002Activity.this, "已存在", 1);
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

                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
//                    cuttingToolBindVO.setBladeCode();
                    cuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                    String jsonStr = objectToJson(cuttingToolBindVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Map<String, String> headsMap = new HashMap<>();
                    headsMap.put("impower", OperationEnum.Cutting_tool_Inside.getKey().toString());

                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);
                    Call<String> queryCuttingToolBind = iRequest.queryCuttingToolBind(body, headsMap);

                    queryCuttingToolBind.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    CuttingToolBind cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);

                                    if (cuttingToolBind != null) {
                                        isShowExceptionBox(response.headers().get("impower"), rfidString, cuttingToolBind);
                                    } else {
                                        if (null != loading && loading.isShowing()) {
                                            loading.dismiss();
                                        }
                                        Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (null != loading && loading.isShowing()) {
                                        loading.dismiss();
                                    }
                                    createAlertDialog(C01S018_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                            } finally {

                            }
                        }

                        @Override
                        public void _onFailure(Throwable t) {
                            if (null != loading && loading.isShowing()) {
                                loading.dismiss();
                            }
                            createAlertDialog(C01S018_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
    public void isShowExceptionBox(String headers, final String rfid, final CuttingToolBind cuttingToolBind) throws IOException {
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
        businessCodeToBladeCodeMap.put(rfid, cuttingToolBind.getBladeCode());

        GrindingVO grindingVO = new GrindingVO();

        grindingVO.setGrindingCount(1);

        CuttingTool ct = new CuttingTool();
        ct.setBusinessCode(cuttingToolBind.getCuttingTool().getBusinessCode());
        ct.setCode(cuttingToolBind.getCuttingTool().getCode());
        grindingVO.setCuttingTool(ct);

        CuttingToolBind ctb = new CuttingToolBind();
        ctb.setCode(cuttingToolBind.getCode());
        grindingVO.setCuttingToolBind(ctb);

        rfidToGrindingVOMap.put(rfid, grindingVO);

        addLayout(cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind.getBladeCode(), rfid);
    }

//    // 弹窗显示时弹窗外事件不相应
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event){
//        if(addPopupWindow != null && addPopupWindow.isShowing()){
//            return false;
//        }
//        return super.dispatchTouchEvent(event);
//    }

}
