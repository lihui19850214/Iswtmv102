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
import com.apiclient.constants.ScrapStateEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.CuttingToolBindVO;
import com.apiclient.vo.CuttingToolVO;
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
    @BindView(R.id.activity_c01s005_002_2)
    LinearLayout activityC01s0050022;

    private int position = 0;

    // 根据 rfid 查询的数据
    private Set<String> rfidToSet = new HashSet<>();
    // 根据才料号查询的数据
    private Set<String> materialNumToSet = new HashSet<>();

    //调用接口
    private Retrofit retrofit;

    //当前选择的卸下原因，零部件种类在集合中的位置
    private int scrap_status_posttion;
    // 报废状态下拉列表所有数据
    private List<ScrapStateEnum> scrapStatusList = new ArrayList<>();

    List<CuttingToolsScrap> cuttingToolsScrapList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s005_002_2);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //存储所有报废状态，下拉列表
        for (ScrapStateEnum scrapStateEnum : ScrapStateEnum.values()){
            scrapStatusList.add(scrapStateEnum);
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
                // 用于页面之间传值，新方法
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("cuttingToolsScrapList", cuttingToolsScrapList);
                PARAM_MAP.put(1, paramMap);

                Intent intent = new Intent(this, c01s005_002_3Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                break;
            case R.id.ivAdd:
                showDialog();
                break;
            default:
        }
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



    //根据材料号查询合成刀具组成信息
    private void search(final String cailiao, final String scrapStatus, final String num) {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        CuttingToolVO cuttingToolVO = new CuttingToolVO();
        cuttingToolVO.setBusinessCode(cailiao);
        Gson gson = new Gson();

        String jsonStr = gson.toJson(cuttingToolVO);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getCuttingTool = iRequest.getCuttingTool(body);

        getCuttingTool.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        Gson gson = new Gson();
                        CuttingTool cuttingTool = gson.fromJson(response.body(), CuttingTool.class);

                        if (cuttingTool != null) {
                            // TODO 需要确认
                            CuttingToolsScrap cuttingToolsScrap = new CuttingToolsScrap();
                            cuttingToolsScrap.setCuttingTool(cuttingTool);
                            cuttingToolsScrap.setMaterialNum(cailiao);
                            cuttingToolsScrap.setCause(scrapStatus);
                            cuttingToolsScrap.setCount(Integer.parseInt(num));

                            cuttingToolsScrapList.add(cuttingToolsScrap);

                            materialNumToSet.add(cailiao);

                            addLayout(cailiao, scrapStatus, "", num);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        createAlertDialog(c01s005_002_2Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    loading.dismiss();
                }
            }

            @Override
            public void _onFailure(Throwable t) {
                createAlertDialog(c01s005_002_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                loading.dismiss();
            }
        });
    }

    /**
     * 添加布局
     */
    private void addLayout(final String cailiao, String laserCode, String rfid, String num) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_daojubaofei, null);

        final TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);//单品编码
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);
        TextView tvRfidContain = (TextView) mLinearLayout.findViewById(R.id.tvRfidContain);
        ImageView tvRemove = (ImageView) mLinearLayout.findViewById(R.id.tvRemove);

        tvCaiLiao.setText(cailiao);
        tvsingleProductCode.setText(laserCode);
        tvNum.setText(num);
        tvRfidContain.setText(rfid);


        tvCaiLiao.setTag(position);
        mLinearLayout.setTag(position);

        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlContainer.removeView(mLinearLayout);
                materialNumToSet.remove(cailiao);

                //TODO 需要确认
                for (CuttingToolsScrap cuttingToolsScrap : cuttingToolsScrapList) {
                    if (cailiao.equals(cuttingToolsScrap.getMaterialNum())) {

                        if (cuttingToolsScrap.getCuttingTool().getCuttingToolBindList() != null && cuttingToolsScrap.getCuttingTool().getCuttingToolBindList().size() > 0) {
                            if (cuttingToolsScrap.getCuttingTool().getCuttingToolBindList().get(0).getRfidContainer() != null) {
                                rfidToSet.remove(cuttingToolsScrap.getCuttingTool().getCuttingToolBindList().get(0).getRfidContainer().getLaserCode());
                            }
                        }

                        cuttingToolsScrapList.remove(cuttingToolsScrap);
                        break;
                    }
                }
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
                if (rfidToSet.contains(rfidString)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createAlertDialog(c01s005_002_2Activity.this, "已存在", 1);
                        }
                    });
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.show();
                    }
                });

                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
                cuttingToolBindVO.setRfidContainerCode(rfidString);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(cuttingToolBindVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> getCuttingToolBind = iRequest.getCuttingToolBind(body);
                getCuttingToolBind.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                Gson gson = new Gson();
                                CuttingToolBind cuttingToolBind = gson.fromJson(response.body(), CuttingToolBind.class);

                                if (cuttingToolBind != null) {
                                    //TODO 需要确认
                                    RfidContainer rfidContainer = new RfidContainer();
                                    rfidContainer.setLaserCode(rfidString);
                                    cuttingToolBind.setRfidContainer(rfidContainer);

                                    List<CuttingToolBind> cuttingToolBindList = new ArrayList<>();
                                    cuttingToolBindList.add(cuttingToolBind);

                                    CuttingToolsScrap cuttingToolsScrap = new CuttingToolsScrap();
                                    cuttingToolsScrap.setMaterialNum(cuttingToolBind.getCuttingTool().getBusinessCode());
                                    cuttingToolsScrap.getCuttingTool().setCuttingToolBindList(cuttingToolBindList);
                                    cuttingToolsScrap.setCount(1);

                                    cuttingToolsScrapList.add(cuttingToolsScrap);

                                    rfidToSet.add(rfidString);

                                    addLayout(cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind.getBladeCode(), rfidString, "1");
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
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
                                        createAlertDialog(c01s005_002_2Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                createAlertDialog(c01s005_002_2Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 显示数据提示dialog
     */
    //显示材料号和修磨数量的弹框
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_c01s019_001, null);
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialog).create();
        dialog.setView((this).getLayoutInflater().inflate(R.layout.dialog_c01s019_001, null));
        dialog.show();
        dialog.getWindow().setContentView(view);


        final EditText etmaterialNumber = (EditText) view.findViewById(R.id.etmaterialNumber);
        etmaterialNumber.setTransformationMethod(new AllCapTransformationMethod());
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

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.spinner_c03s004_001, null);
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
                        tv01.setText(scrapStatusList.get(i).getName());
                        scrap_status_posttion = i;
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
                if (null == etmaterialNumber.getText().toString().trim() || "".equals(etmaterialNumber.getText().toString().trim())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请输入材料号", Toast.LENGTH_LONG);
                } else if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请输入报废数量", Toast.LENGTH_LONG);
                } else if (null == tv01.getText().toString().trim() || "".equals(tv01.getText().toString().trim())) {
                    createAlertDialog(c01s005_002_2Activity.this, "请选择报废状态", Toast.LENGTH_LONG);
                } else {
                    if (Integer.parseInt(etgrindingQuantity.getText().toString()) <= 0) {
                        createAlertDialog(c01s005_002_2Activity.this, "数量要大于0", 0);
                        return;
                    }

                    if (materialNumToSet.contains(etmaterialNumber.getText().toString())) {
                        createAlertDialog(c01s005_002_2Activity.this, "已存在", Toast.LENGTH_SHORT);
                    } else {
                        search(etmaterialNumber.getText().toString().trim(), scrapStatusList.get(scrap_status_posttion).getKey(), etgrindingQuantity.getText().toString().trim());
                    }
                    dialog.dismiss();
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
            return scrapStatusList.size();
        }

        @Override
        public Object getItem(int i) {
            return scrapStatusList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s005_002_2Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(scrapStatusList.get(i).getName());
            return view1;
        }
    }




    //-----------------------以下代码没用，暂时保留-------------------------

    //查询弹框
    private PopupWindow addPopupWindow;

    /**
     * 显示数据提示dialog
     */
    //显示材料号和修磨数量的弹框
    private void showDialog2() {
        if (null == addPopupWindow || !addPopupWindow.isShowing()) {
            //点击查询按钮以后，设置扫描按钮不可用
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View view = layoutInflater.inflate(R.layout.dialog_c01s019_001, null);
            addPopupWindow = new PopupWindow(view, (int) (screenWidth * 0.8), (int) (screenHeight * 0.4));
            addPopupWindow.setFocusable(true);
            addPopupWindow.setOutsideTouchable(false);
            addPopupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

            final EditText etmaterialNumber = (EditText) view.findViewById(R.id.etmaterialNumber);
            etmaterialNumber.setTransformationMethod(new AllCapTransformationMethod());
            final EditText etgrindingQuantity = (EditText) view.findViewById(R.id.etgrindingQuantity);

            final LinearLayout ll01 = (LinearLayout) view.findViewById(R.id.ll_01);
            final TextView tv01 = (TextView) view.findViewById(R.id.tv_01);

            // 报废状态下拉列表
            ll01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.spinner_c03s004_001, null);
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
                            tv01.setText(scrapStatusList.get(i).getName());
                            scrap_status_posttion = i;
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
                    addPopupWindow.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null == etmaterialNumber.getText().toString().trim() || "".equals(etmaterialNumber.getText().toString().trim())) {
                        createAlertDialog(c01s005_002_2Activity.this, "请输入材料号", Toast.LENGTH_LONG);
                    } else if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
                        createAlertDialog(c01s005_002_2Activity.this, "请输入报废数量", Toast.LENGTH_LONG);
                    } else if (null == tv01.getText().toString().trim() || "".equals(tv01.getText().toString().trim())) {
                        createAlertDialog(c01s005_002_2Activity.this, "请选择报废状态", Toast.LENGTH_LONG);
                    } else {
                        if (Integer.parseInt(etgrindingQuantity.getText().toString()) <= 0) {
                            createAlertDialog(c01s005_002_2Activity.this, "数量要大于0", 0);
                            return;
                        }

                        if (materialNumToSet.contains(etmaterialNumber.getText().toString())) {
                            createAlertDialog(c01s005_002_2Activity.this, "已存在", Toast.LENGTH_SHORT);
                        } else {
                            search(etmaterialNumber.getText().toString().trim(), scrapStatusList.get(scrap_status_posttion).getKey(), etgrindingQuantity.getText().toString().trim());
                        }
                        addPopupWindow.dismiss();
                    }
                }
            });
        }
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
                addLayout(name, laserCode, "num", r);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
//        dialog.getWindow().setLayout(300, 400);
    }

}
