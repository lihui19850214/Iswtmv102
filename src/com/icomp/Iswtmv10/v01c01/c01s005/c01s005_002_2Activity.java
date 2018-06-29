package com.icomp.Iswtmv10.v01c01.c01s005;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.*;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s019.C01S019_001Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 刀具报废页面1
 */
public class c01s005_002_2Activity extends CommonActivity {

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

    List<ScrapVO> scrapVOList = new ArrayList<>();
    // 物料号列表项
    List<CuttingTool> cuttingToolList = new ArrayList<>();
    // 物料号选项
    CuttingTool cuttingTool = new CuttingTool();

    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();
    // 根据才料号查询的数据
    private Map<String, CuttingTool> materialNumToMap = new HashMap<>();
    // 根据物料号对应刀身码或状态
    private Map<String, String> businessCodeToBladeCodeMap = new HashMap<>();

    // 需要授权的标签
    Map<String, Boolean> rfid_authorization_map = new HashMap<>();

    ScrapBO scrapBO = new ScrapBO();


    // 报废状态下拉列表所有数据
    private List<ToolBusinessStatusEnum> toolBusinessStatusEnumList = new ArrayList<>();
    //当前选择的报废状态
    private ToolBusinessStatusEnum toolBusinessStatusEnum;

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s005_002_2);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //存储所有报废状态，下拉列表
        for (ToolBusinessStatusEnum toolBusinessStatusEnum : ToolBusinessStatusEnum.values()){
            toolBusinessStatusEnumList.add(toolBusinessStatusEnum);
        }

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);

            if (paramMap != null) {
                scrapBO = (ScrapBO) paramMap.get("scrapBO");
                rfidToMap = (Map<String, CuttingToolBind>) paramMap.get("rfidToMap");
                materialNumToMap = (Map<String, CuttingTool>) paramMap.get("materialNumToMap");
                scrapVOList = (List<ScrapVO>) paramMap.get("scrapVOList");
                rfid_authorization_map = (Map<String, Boolean>) paramMap.get("rfid_authorization_map");
                businessCodeToBladeCodeMap = (Map<String, String>) paramMap.get("businessCodeToBladeCodeMap");

                for (ScrapVO scrapVO : scrapVOList) {
                    String bl = businessCodeToBladeCodeMap.get(scrapVO.getCuttingTool().getBusinessCode());

                    addLayout(scrapVO.getCuttingTool().getBusinessCode(), bl, scrapVO.getCount().toString());
                }
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
                finish();
                break;
            case R.id.btnNext:

                if (scrapVOList != null  && scrapVOList.size() > 0) {
                    scrapBO.setScrapVOS(scrapVOList);

                    if (rfid_authorization_map != null && rfid_authorization_map.size() > 0) {
                        is_need_authorization = true;
                    } else {
                        is_need_authorization = false;
                    }

                    // 用于页面之间传值，新方法
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("rfidToMap", rfidToMap);
                    paramMap.put("materialNumToMap", materialNumToMap);
                    paramMap.put("scrapBO", scrapBO);
                    paramMap.put("scrapVOList", scrapVOList);
                    paramMap.put("rfid_authorization_map", rfid_authorization_map);
                    paramMap.put("businessCodeToBladeCodeMap", businessCodeToBladeCodeMap);
                    PARAM_MAP.put(1, paramMap);

                    Intent intent = new Intent(this, c01s005_002_3Activity.class);
                    // 不清空页面之间传递的值
                    intent.putExtra("isClearParamMap", false);
                    startActivity(intent);
                    finish();
                } else {
                    createAlertDialog(c01s005_002_2Activity.this, "请添加要报废的材料", Toast.LENGTH_LONG);
                }
                break;
            case R.id.ivAdd:
                showDialog();
                break;
            default:
        }
    }

    /**
     * 显示数据提示dialog
     */
    //显示物料号和数量的弹框
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_c01s019_001, null);
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialog).create();
        dialog.setView((this).getLayoutInflater().inflate(R.layout.dialog_c01s019_001, null));
        dialog.show();
        dialog.getWindow().setContentView(view);


        final EditText et_t = (EditText) view.findViewById(R.id.et_t);
        //将输入的材料号自动转化为大写
        et_t.setTransformationMethod(new AllCapTransformationMethod());
        //将光标设置在最后
        et_t.setSelection(et_t.getText().length());

        final EditText etgrindingQuantity = (EditText) view.findViewById(R.id.etgrindingQuantity);

        final LinearLayout ll01 = (LinearLayout) view.findViewById(R.id.ll_01);
        final TextView tv01 = (TextView) view.findViewById(R.id.tv_01);

        // 物料号下拉列表
        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 收起软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etgrindingQuantity.getWindowToken(), 0);

                View view = LayoutInflater.from(c01s005_002_2Activity.this).inflate(R.layout.spinner_c03s004_001, null);
                ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
                MyAdapter2 myAdapter2 = new MyAdapter2();
                listView.setAdapter(myAdapter2);

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
                        tv01.setText(cuttingToolList.get(i).getBusinessCode());
                        cuttingTool = cuttingToolList.get(i);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(ll01);
            }
        });

        final LinearLayout ll02 = (LinearLayout) view.findViewById(R.id.ll_02);
        final TextView tv02 = (TextView) view.findViewById(R.id.tv_02);

        // 报废状态下拉列表
        ll02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 收起软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etgrindingQuantity.getWindowToken(), 0);

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.spinner_c03s004_001, null);
                ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
                MyAdapter myAdapter = new MyAdapter();
                listView.setAdapter(myAdapter);

                final PopupWindow popupWindow = new PopupWindow(view, ll02.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
                        tv02.setText(toolBusinessStatusEnumList.get(i).getName());
                        toolBusinessStatusEnum = toolBusinessStatusEnumList.get(i);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(ll02);
            }
        });


        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
        Button btnSearch = (Button) view.findViewById(R.id.btnSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == et_t.getText().toString().trim() || "".equals(et_t.getText().toString().trim())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请输入合成刀", Toast.LENGTH_LONG);
                } else {
                    searchBysynthesisCode(et_t.getText().toString().trim(), tv01);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == tv01.getText() || "".equals(tv01.getText().toString())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请选择物料号", Toast.LENGTH_LONG);
                } else if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请输入报废数量", Toast.LENGTH_LONG);
                } else if (null == tv02.getText() || "".equals(tv02.getText().toString().trim())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请选择报废状态", Toast.LENGTH_LONG);
                } else {
                    if (Integer.parseInt(etgrindingQuantity.getText().toString()) <= 0) {
                        createAlertDialog(c01s005_002_2Activity.this, "数量要大于0", 0);
                        return;
                    }

                    if (materialNumToMap.containsKey(cuttingTool.getBusinessCode())) {
                        createAlertDialog(c01s005_002_2Activity.this, "已存在", Toast.LENGTH_SHORT);
                    } else {
                        String num = etgrindingQuantity.getText().toString().trim();
                        materialNumToMap.put(cuttingTool.getBusinessCode(), cuttingTool);
                        businessCodeToBladeCodeMap.put(cuttingTool.getBusinessCode(), toolBusinessStatusEnum.getName());


                        ScrapVO scrapVO = new ScrapVO();
                        scrapVO.setStatus(toolBusinessStatusEnum.getKey());
                        scrapVO.setCount(Integer.parseInt(etgrindingQuantity.getText().toString().trim()));

                        CuttingTool ct = new CuttingTool();
                        ct.setBusinessCode(cuttingTool.getBusinessCode());
                        ct.setCode(cuttingTool.getCode());

                        scrapVO.setCuttingTool(ct);

                        scrapVOList.add(scrapVO);

                        addLayout(cuttingTool.getBusinessCode(), toolBusinessStatusEnum.getName(), num);

                        dialog.dismiss();
                    }
                }
            }
        });


        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 1), (int) (screenHeight * 0.7));
    }

    //卸下原因下拉框的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return toolBusinessStatusEnumList.size();
        }

        @Override
        public Object getItem(int i) {
            return toolBusinessStatusEnumList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s005_002_2Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(toolBusinessStatusEnumList.get(i).getName());
            return view1;
        }
    }

    //物料号下拉框的Adapter
    class MyAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return cuttingToolList.size();
        }

        @Override
        public Object getItem(int i) {
            return cuttingToolList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s005_002_2Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(cuttingToolList.get(i).getBusinessCode());
            //TODO 需要取值数据
            return view1;
        }
    }

    /**
     * 输入合成刀T号，查询材料刀具信息
     * @param synthesisCode
     */
    private void searchBysynthesisCode(final String synthesisCode, final TextView tv01) {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            SynthesisCuttingToolVO synthesisCuttingToolVO = new SynthesisCuttingToolVO();
            synthesisCuttingToolVO.setSynthesisCode(synthesisCode);

            String jsonStr = objectToJson(synthesisCuttingToolVO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> getCuttingToolByTCode = iRequest.getCuttingToolByTCode(body);
            getCuttingToolByTCode.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            cuttingToolList = jsonToObject(response.body(), List.class, CuttingTool.class);

                            if (cuttingToolList == null || cuttingToolList.size() == 0) {
                                tv01.setText("");
                                cuttingToolList = new ArrayList<>();
                                cuttingTool = null;
                                Toast.makeText(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT).show();
                            } else {
                                List<CuttingTool> cuttingToolListTemp = new ArrayList<>();
                                // 不要 辅具、配套、其他 项的物料号
                                for (CuttingTool ct : cuttingToolList) {
                                    // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                                    if (CuttingToolTypeEnum.dj.getKey().equals(ct.getType())) {
                                        // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                                        if (!CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(ct.getConsumeType())) {
                                            cuttingToolListTemp.add(ct);
                                        }
                                    }
                                }

                                cuttingToolList = new ArrayList<>(cuttingToolListTemp);
                                if (cuttingToolList.size() > 0) {
                                    tv01.setText(cuttingToolList.get(0).getBusinessCode());
                                    cuttingTool = cuttingToolList.get(0);
                                }
                            }
                        } else {
                            createAlertDialog(c01s005_002_2Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(c01s005_002_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
     */
    private void addLayout(final String cailiao, String laserCode, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_daojubaofei, null);

        final TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);
        ImageView tvRemove = (ImageView) mLinearLayout.findViewById(R.id.tvRemove);

        //将输入的材料号自动转化为大写
        tvCaiLiao.setTransformationMethod(new AllCapTransformationMethod());

        tvCaiLiao.setText(cailiao);
        if (laserCode != null && !"".equals(laserCode) && !"-".equals(laserCode) && (laserCode.indexOf("-") >= 0)) {
            tvsingleProductCode.setText(laserCode.split("-")[1]);
        } else {
            tvsingleProductCode.setText(laserCode);
        }
        tvNum.setText(num);

        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialNumToMap.remove(cailiao);

                for (ScrapVO scrapVO : scrapVOList) {
                    if (cailiao.equals(scrapVO.getCuttingTool().getBusinessCode())) {

                        Set<String> keys = rfidToMap.keySet();
                        for (String key : keys) {
                            CuttingToolBind cb = rfidToMap.get(key);
                            if (scrapVO.getCuttingTool().getBusinessCode().equals(cb.getCuttingTool().getBusinessCode())) {
                                rfidToMap.remove(key);
                                rfid_authorization_map.remove(key);
                                break;
                            }
                        }

                        scrapVOList.remove(scrapVO);
                        break;
                    }
                }

                mLlContainer.removeView(mLinearLayout);
            }
        });

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
            rfidString = singleScan();
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
                            createAlertDialog(c01s005_002_2Activity.this, "已存在", 1);
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

                    headsMap.put("impower", OperationEnum.Cutting_tool_scap.getKey().toString());

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
                                        Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    createAlertDialog(c01s005_002_2Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                            createAlertDialog(c01s005_002_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

        rfidToMap.put(rfidString, cuttingToolBind);
        businessCodeToBladeCodeMap.put(cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind.getBladeCode());

        ScrapVO scrapVO = new ScrapVO();
        scrapVO.setCount(1);

        CuttingTool ct = new CuttingTool();
        ct.setBusinessCode(cuttingToolBind.getCuttingTool().getBusinessCode());
        ct.setCode(cuttingToolBind.getCuttingTool().getCode());
        scrapVO.setCuttingTool(ct);

        CuttingToolBind ctb = new CuttingToolBind();
        ctb.setCode(cuttingToolBind.getCode());
        scrapVO.setCuttingToolBind(ctb);

        scrapVOList.add(scrapVO);


        addLayout(cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind.getBladeCode(), "1");
    }




    //    // 按键扫描
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

}
