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

    // 表格行号
    private Integer row = 0;

    private Map<Integer, ScrapVO> scrapVOMap = new HashMap<>();

    // 物料号列表项
    List<CuttingTool> cuttingToolList = new ArrayList<>();
    // 物料号选项
    CuttingTool cuttingTool = new CuttingTool();

    // 根据 rfid 查询的数据
    private Set<String> rfidSet = new HashSet<>();
    // 行号对应的rfid
    private Map<Integer, String> rowTorfidMap = new HashMap<>();
    // 根据行号对应刀身码或状态
    private Map<Integer, String> businessCodeToBladeCodeMap = new HashMap<>();
    // 根据扫描的刀身码，用于验证是否重复
    private Set<String> bladeCodeSet = new HashSet<>();

    // 需要授权的标签
    Set<Integer> rfid_authorization_set = new HashSet<>();


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
                bladeCodeSet = (Set<String>) paramMap.get("bladeCodeSet");
                rfidSet = (Set<String>) paramMap.get("rfidSet");
                rowTorfidMap = (Map<Integer, String>) paramMap.get("rowTorfidMap");
                scrapVOMap = (Map<Integer, ScrapVO>) paramMap.get("scrapVOMap");
                rfid_authorization_set = (Set<Integer>) paramMap.get("rfid_authorization_set");
                businessCodeToBladeCodeMap = (Map<Integer, String>) paramMap.get("businessCodeToBladeCodeMap");

                Set<Integer> rows = scrapVOMap.keySet();
                for (Integer row : rows) {
                    ScrapVO scrapVO = scrapVOMap.get(row);
                    String bc = businessCodeToBladeCodeMap.get(row);

                    addLayout(scrapVO.getCuttingToolVO().getBusinessCode(), bc, scrapVO.getCount().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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

                if (scrapVOMap != null  && scrapVOMap.size() > 0) {

                    if (rfid_authorization_set != null && rfid_authorization_set.size() > 0) {
                        is_need_authorization = true;
                    } else {
                        is_need_authorization = false;
                    }

                    // 用于页面之间传值，新方法
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("bladeCodeSet", bladeCodeSet);
                    paramMap.put("rfidSet", rfidSet);
                    paramMap.put("rowTorfidMap", rowTorfidMap);
                    paramMap.put("scrapVOMap", scrapVOMap);
                    paramMap.put("rfid_authorization_set", rfid_authorization_set);
                    paramMap.put("businessCodeToBladeCodeMap", businessCodeToBladeCodeMap);
                    PARAM_MAP.put(1, paramMap);

                    Intent intent = new Intent(this, c01s005_002_3Activity.class);
                    // 不清空页面之间传递的值
                    intent.putExtra("isClearParamMap", false);
                    startActivity(intent);
                    finish();
                } else {
//                    createAlertDialog(c01s005_002_2Activity.this, "请添加要报废的材料", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请添加要报废的材料", Toast.LENGTH_SHORT);
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

        // 下拉框默认选中第一个
        if (cuttingToolList != null && cuttingToolList.size() > 0) {
            tv01.setText(cuttingToolList.get(0).getBusinessCode());
            cuttingTool = cuttingToolList.get(0);
        }

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

        // 下拉框默认选中第一个
        if (toolBusinessStatusEnumList != null && toolBusinessStatusEnumList.size() > 0) {
            tv02.setText(toolBusinessStatusEnumList.get(0).getName());
            toolBusinessStatusEnum = toolBusinessStatusEnumList.get(0);
        }

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
//                    createAlertDialog(c01s005_002_2Activity.this, "请输入合成刀", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请输入合成刀", Toast.LENGTH_SHORT);
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
//                    createAlertDialog(c01s005_002_2Activity.this, "请选择物料号", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请选择物料号", Toast.LENGTH_SHORT);
                } else if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
//                    createAlertDialog(c01s005_002_2Activity.this, "请输入报废数量", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请输入报废数量", Toast.LENGTH_SHORT);
                } else if (null == tv02.getText() || "".equals(tv02.getText().toString().trim())) {
//                    createAlertDialog(c01s005_002_2Activity.this, "请选择报废状态", Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), "请选择报废状态", Toast.LENGTH_SHORT);
                } else {
                    if (Integer.parseInt(etgrindingQuantity.getText().toString()) <= 0) {
//                        createAlertDialog(c01s005_002_2Activity.this, "数量要大于0", Toast.LENGTH_LONG);
                        createToast(getApplicationContext(), "数量要大于0", Toast.LENGTH_SHORT);
                        return;
                    }


                    String num = etgrindingQuantity.getText().toString().trim();
                    businessCodeToBladeCodeMap.put(row, toolBusinessStatusEnum.getName());


                    ScrapVO scrapVO = new ScrapVO();
                    scrapVO.setStatus(toolBusinessStatusEnum.getKey());
                    scrapVO.setCount(Integer.parseInt(etgrindingQuantity.getText().toString().trim()));

                    CuttingToolVO ctVO = new CuttingToolVO();
                    ctVO.setBusinessCode(cuttingTool.getBusinessCode());
                    ctVO.setCode(cuttingTool.getCode());

                    scrapVO.setCuttingToolVO(ctVO);

                    scrapVOMap.put(row, scrapVO);

                    addLayout(cuttingTool.getBusinessCode(), toolBusinessStatusEnum.getName(), num);

                    dialog.dismiss();
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

            Call<String> getCuttingToolByTCode = iRequest.queryToolForScrap(body);
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
                                createToast(getApplicationContext(), "没有查询到信息", Toast.LENGTH_SHORT);
                            } else {
                                List<CuttingTool> cuttingToolListTemp = new ArrayList<>();
                                // 不要 辅具、配套、其他 项的物料号
                                for (CuttingTool ct : cuttingToolList) {
                                    // 报废的物料号都要，没有过滤条件
//                                    // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
//                                    if (CuttingToolTypeEnum.dj.getKey().equals(ct.getType())) {
//                                        // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
//                                        if (!CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(ct.getConsumeType())) {
                                            cuttingToolListTemp.add(ct);
//                                        }
//                                    }
                                }

                                cuttingToolList = new ArrayList<>(cuttingToolListTemp);
                                if (cuttingToolList.size() > 0) {
                                    tv01.setText(cuttingToolList.get(0).getBusinessCode());
                                    cuttingTool = cuttingToolList.get(0);
                                }
                            }
                        } else {
//                            createAlertDialog(c01s005_002_2Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
                    } finally {
                        loading.dismiss();
                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    loading.dismiss();
//                    createAlertDialog(c01s005_002_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    /**
     * 添加布局
     */
    private void addLayout(final String cailiao, final String bladeCode, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_daojubaofei, null);

        final TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);
        ImageView tvRemove = (ImageView) mLinearLayout.findViewById(R.id.tvRemove);

        //将输入的材料号自动转化为大写
        tvCaiLiao.setTransformationMethod(new AllCapTransformationMethod());

        tvCaiLiao.setText(cailiao);
        if (bladeCode != null && !"".equals(bladeCode) && !"-".equals(bladeCode) && (bladeCode.indexOf("-") >= 0)) {
            tvsingleProductCode.setText(bladeCode.split("-")[1]);
        } else {
            tvsingleProductCode.setText(bladeCode);
        }
        tvNum.setText(num);

        mLinearLayout.setTag(row);

        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bladeCodeSet.remove(bladeCode);
                Integer r = (Integer)mLinearLayout.getTag();

                String rfid = rowTorfidMap.get(r);
                // rfid 不为空时删除相关值
                if (rfid != null && !"".equals(rfid)) {
                    rfidSet.remove(rfid);
                }

                rfid_authorization_set.remove(r);
                scrapVOMap.remove(r);
                businessCodeToBladeCodeMap.remove(r);


                mLlContainer.removeView(mLinearLayout);
            }
        });

        row++;
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
            createToast(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT);
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
                if (rfidSet.contains(rfidString)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            createAlertDialog(c01s005_002_2Activity.this, "当前标签已存在", 1);
                            createToast(getApplicationContext(), "当前标签已存在", Toast.LENGTH_SHORT);
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

                    Call<String> getOutCuttingToolBind = iRequest.getCuttingToolBind(body, headsMap);
                    getOutCuttingToolBind.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    CuttingToolBind cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);

                                    if (cuttingToolBind != null) {
                                        // 判断刀身码是否存在
                                        if (bladeCodeSet.contains(cuttingToolBind.getBladeCode())) {
                                            createToast(getApplicationContext(), "刀身码已存在", Toast.LENGTH_SHORT);
                                        } else {
                                            isShowExceptionBox(response.headers().get("impower"), rfidString, cuttingToolBind);
                                        }
                                    } else {
                                        createToast(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT);
                                    }
                                } else {
//                                    createAlertDialog(c01s005_002_2Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
//                            createAlertDialog(c01s005_002_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
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
            rfid_authorization_set.add(row);
        }

        bladeCodeSet.add(cuttingToolBind.getBladeCode());
        rfidSet.add(rfid);
        rowTorfidMap.put(row, rfid);
        businessCodeToBladeCodeMap.put(row, cuttingToolBind.getBladeCode());

        ScrapVO scrapVO = new ScrapVO();
        scrapVO.setCount(1);

        CuttingToolVO ctVO = new CuttingToolVO();
        ctVO.setBusinessCode(cuttingToolBind.getCuttingTool().getBusinessCode());
        ctVO.setCode(cuttingToolBind.getCuttingTool().getCode());
        scrapVO.setCuttingToolVO(ctVO);

        CuttingToolBindVO ctbVO = new CuttingToolBindVO();
        ctbVO.setCode(cuttingToolBind.getCode());
        scrapVO.setCuttingToolBindVO(ctbVO);

        scrapVOMap.put(row, scrapVO);


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
