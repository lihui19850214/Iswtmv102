package com.icomp.Iswtmv10.v01c01.c01s011;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.constants.OperationEnum;
import com.apiclient.dto.BindBladeDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 安上设备页面1
 */
public class C01S011_001Activity extends CommonActivity {


    @BindView(R.id.et_00)
    EditText et00;
    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_next)
    Button btnNext;

    //调用接口
    private Retrofit retrofit;


    // 合成刀配置
    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    // 合成刀真实数据
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();
    // 合成刀标签
    String synthesisCuttingToolConfigRFID = "";

    List<ProductLineEquipment> productLineEquipmentList = new ArrayList<>();

    // 刀身码
    String bladeCode = "";

    String rfidCode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s011_001activity);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        if (paramMap != null) {
            try {

                bladeCode = (String) paramMap.get("bladeCode");

                //将输入的材料号自动转化为大写
                et00.setTransformationMethod(new AllCapTransformationMethod());
                //如果材料号不为空，显示在页面上
                if (null != bladeCode && !"".equals(bladeCode)) {
                    et00.setText(exChangeBig(bladeCode));
                }
                //将光标设置在最后
                et00.setSelection(et00.getText().length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 按钮点击
     */
    @OnClick({R.id.btn_next, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                break;
            case R.id.btn_next:
                if (et00.getText() != null && !"".equals(et00.getText().toString().trim())) {
                    // 使用刀身码后清空标签
                    synthesisCuttingToolConfigRFID = "";
                    bladeCode = et00.getText().toString().trim();
                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setSynthesisBladeCode(et00.getText().toString().trim());

                    SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                    synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                    queryForInstall(synthesisCuttingToolBindVO, "");
                } else {
                    createAlertDialog(C01S011_001Activity.this, "请先输入合成刀具刀身码", Toast.LENGTH_LONG);
                }
                break;
            default:
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            tvScan.setClickable(false);
            btnReturn.setClickable(false);
            btnNext.setClickable(false);

            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            ScanThread scanThread = new ScanThread();
            scanThread.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class ScanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
//            rfidString = "18000A00000D6440";
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                tvScan.setClickable(true);
                btnReturn.setClickable(true);
                btnNext.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScan.setClickable(true);
                        tvScan.setClickable(true);
                        btnReturn.setClickable(true);
                        btnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        // 使用标签后清空刀身码
                        et00.setText("");
                    }
                });

                // 使用标签后清空刀身码
                bladeCode = "";

                RfidContainerVO rfidContainerVO = new RfidContainerVO();
                rfidContainerVO.setLaserCode(rfidString);

                SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
                synthesisCuttingToolBindVO.setRfidContainerVO(rfidContainerVO);

                queryForInstall(synthesisCuttingToolBindVO, rfidString);
            }
        }
    }

    /**
     * 根据合成刀标签或者刀身码查询信息
     * @param synthesisCuttingToolBindVO 参数
     * @param rfid 没有传空字符串""
     */
    private void queryForInstall(SynthesisCuttingToolBindVO synthesisCuttingToolBindVO, final String rfid) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.show();
                }
            });

            //调用接口，查询合成刀具组成信息
            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = objectToJson(synthesisCuttingToolBindVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Map<String, String> headsMap = new HashMap<>();
            headsMap.put("impower", OperationEnum.SynthesisCuttingTool_Install.getKey().toString());

            Call<String> queryForInstall = iRequest.queryForInstall(body, headsMap);
            queryForInstall.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        String inpower = response.headers().get("impower");

                        if (response.raw().code() == 200) {
                            QueryVO queryVO = jsonToObject(response.body(), QueryVO.class);

                            if (queryVO != null) {

                                productLineEquipmentList = queryVO.getEquipmentList();
                                synthesisCuttingToolConfig = queryVO.getConfig();
                                synthesisCuttingToolBind = queryVO.getSynthesisCuttingToolBind();
                                synthesisCuttingToolConfigRFID = rfid;
                                rfidCode = synthesisCuttingToolBind.getRfidContainer().getCode();

                                // 生成物料号下拉列表项
                                generateBusinessCodeList();
                                setTextViewHandler(inpower);
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C01S011_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        if (null != loading && loading.isShowing()) {
                            loading.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                    } finally {
                        if (null != loading && loading.isShowing()) {
                            loading.dismiss();
                        }
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
                            createAlertDialog(C01S011_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    /**
     * 检查钻头是否没有刀身码
     * @return true为没有刀身码；false为有刀身码；
     */
    private boolean bladeCodeIsEmpty() {
        boolean isEmpty = false;

        for (String bus : businessCodeList) {
            SynthesisCuttingToolLocation location = configToBindMap.get(bus);
            if (location.getCuttingToolBladeCode() == null || "".equals(location.getCuttingToolBladeCode())) {
                isEmpty = true;
            }
        }

        return isEmpty;
    }

    private void setTextViewHandler(String inpower) throws IOException {
        Map<String, String> inpowerMap = jsonToObject(inpower, Map.class);

        // 判断是否显示提示框
        if ("1".equals(inpowerMap.get("type"))) {
            // 是否需要授权 true为需要授权；false为不需要授权
            is_need_authorization = false;
            // 如果刀身码为空，需要填写刀身码
            if (bladeCodeIsEmpty()) {
                showDialog();
            } else {
                jumpPage();
            }
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    // 如果刀身码为空，需要填写刀身码
                    if (bladeCodeIsEmpty()) {
                        showDialog();
                    } else {
                        jumpPage();
                    }
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

    private void jumpPage() {
        // 用于页面之间传值，新方法
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bladeCode", bladeCode);
        paramMap.put("synthesisCuttingToolConfigRFID", synthesisCuttingToolConfigRFID);
        paramMap.put("synthesisCuttingToolConfig", synthesisCuttingToolConfig);
        paramMap.put("synthesisCuttingToolBind", synthesisCuttingToolBind);
        paramMap.put("productLineEquipmentList", productLineEquipmentList);
        paramMap.put("rfidCode", rfidCode);
        PARAM_MAP.put(1, paramMap);

        Intent intent = new Intent(C01S011_001Activity.this, C01S011_002Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }


    /**
     * 返回
     */
    public void appReturn(View view) {
        finish();
    }

//    //重写键盘上扫描按键的方法
//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if(isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//        //扫描方法
//        scan();
//    }

    // 只有转头的物料号下拉列表
    List<String> businessCodeList = new ArrayList<>();
    // 物料号选项
    String selectBusinessCode = "";
    // 物料号对应 config 信息中CuttingTool的code字段
    Map<String, String> configMap = new HashMap<>();
    // bind 中的物料号
    Map<String, SynthesisCuttingToolLocation> realMap = new HashMap<>();

    // config 的物料号对应 location 的物料号(真实数据)
    Map<String, SynthesisCuttingToolLocation> configToBindMap = new HashMap<>();

    /**
     * 生成物料号下拉列表项
     */
    private void generateBusinessCodeList() {
        // 只有转头的物料号下拉列表
        businessCodeList = new ArrayList<>();
        // 物料号选项
        selectBusinessCode = "";
        // 物料号对应 config 信息中CuttingTool的code字段
        configMap = new HashMap<>();
        // bind 中的物料号
        realMap = new HashMap<>();

        // config 的物料号对应 location 的物料号(真实数据)
        configToBindMap = new HashMap<>();
        try {
            if (synthesisCuttingToolBind.getSynthesisCuttingToolLocationList() != null) {
                List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList = synthesisCuttingToolBind.getSynthesisCuttingToolLocationList();

                for (SynthesisCuttingToolLocation synthesisCuttingToolLocation : synthesisCuttingToolLocationList) {
                    String businessCode = synthesisCuttingToolLocation.getCuttingTool().getBusinessCode();
                    realMap.put(businessCode, synthesisCuttingToolLocation);
                }
            }

            List<SynthesisCuttingToolLocationConfig> SynthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();

            for (SynthesisCuttingToolLocationConfig config : SynthesisCuttingToolLocationConfigList) {

                // 是否时钻头：true为钻头；false为非钻头；
                boolean isDrillingBit = false;
                // 判断是否是钻头
                // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                if (CuttingToolTypeEnum.dj.getKey().equals(config.getCuttingTool().getType())) {
                    // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                    if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(config.getCuttingTool().getConsumeType())) {
                        isDrillingBit = true;
                    }
                } else {
                    continue;
                }


                String mainBusinessCode = null;     // 主刀材料号
                String replaceBusinessCode1 = null; // 备用刀1材料号
                String replaceBusinessCode2 = null; // 备用刀2材料号
                String replaceBusinessCode3 = null; // 备用刀3材料号

                String realBusinessCode = null;     // 有真实数据的材料号

                if (isDrillingBit) {
                    mainBusinessCode = config.getCuttingTool().getBusinessCode();
                    // 真实数据
                    if (realMap.containsKey(mainBusinessCode)) {
                        realBusinessCode = mainBusinessCode;
                    }
                    businessCodeList.add(mainBusinessCode);
                    configMap.put(mainBusinessCode, config.getCuttingTool().getCode());

                    // 备用刀1不为空
                    if (config.getReplaceCuttingTool1() != null) {
                        replaceBusinessCode1 = config.getReplaceCuttingTool1().getBusinessCode();
                        // 真实数据
                        if (realMap.containsKey(replaceBusinessCode1)) {
                            realBusinessCode = replaceBusinessCode1;
                        }
                        businessCodeList.add(replaceBusinessCode1);
                        configMap.put(replaceBusinessCode1, config.getReplaceCuttingTool1().getCode());
                    }

                    // 备用刀2不为空
                    if (config.getReplaceCuttingTool2() != null) {
                        replaceBusinessCode2 = config.getReplaceCuttingTool2().getBusinessCode();
                        // 真实数据
                        if (realMap.containsKey(replaceBusinessCode2)) {
                            realBusinessCode = replaceBusinessCode2;
                        }
                        businessCodeList.add(replaceBusinessCode2);
                        configMap.put(replaceBusinessCode2, config.getReplaceCuttingTool2().getCode());
                    }

                    // 备用刀3不为空
                    if (config.getReplaceCuttingTool3() != null) {
                        replaceBusinessCode3 = config.getReplaceCuttingTool3().getBusinessCode();
                        // 真实数据
                        if (realMap.containsKey(replaceBusinessCode3)) {
                            realBusinessCode = replaceBusinessCode3;
                        }
                        businessCodeList.add(replaceBusinessCode3);
                        configMap.put(replaceBusinessCode3, config.getReplaceCuttingTool3().getCode());
                    }

                    //---------------------------------主刀和备用刀指向同一个真实数据的物料号开始----------------------------------
                    SynthesisCuttingToolLocation location = realMap.get(realBusinessCode);

                    configToBindMap.put(mainBusinessCode, location);

                    if (replaceBusinessCode1 != null) {
                        configToBindMap.put(replaceBusinessCode1, location);
                    }
                    if (replaceBusinessCode2 != null) {
                        configToBindMap.put(replaceBusinessCode2, location);
                    }
                    if (replaceBusinessCode3 != null) {
                        configToBindMap.put(replaceBusinessCode3, location);
                    }
                    //---------------------------------主刀和备用刀指向同一个真实数据的物料号结束----------------------------------
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 显示数据提示dialog
     */
    //显示物料号和刀身码的弹框
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_c01s011_002_1, null);
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialog).create();
        dialog.setView((this).getLayoutInflater().inflate(R.layout.dialog_c01s011_002_1, null));
        dialog.show();
        dialog.getWindow().setContentView(view);


        final EditText etBladeCode = (EditText) view.findViewById(R.id.et_bladeCode);

        final LinearLayout ll01 = (LinearLayout) view.findViewById(R.id.ll_01);
        final TextView tv01 = (TextView) view.findViewById(R.id.tv_01);

        if (businessCodeList != null && businessCodeList.size() > 0) {
            tv01.setText(businessCodeList.get(0));
            selectBusinessCode = businessCodeList.get(0);
        }

        // 物料号下拉列表
        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 收起软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etBladeCode.getWindowToken(), 0);

                View view = LayoutInflater.from(C01S011_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
                ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
                MyAdapter2 myAdapter = new MyAdapter2();
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
                        tv01.setText(businessCodeList.get(i));
                        selectBusinessCode = businessCodeList.get(i);
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
                if (null == etBladeCode.getText() || "".equals(etBladeCode.getText().toString().trim())) {
                    createAlertDialog(C01S011_001Activity.this, "请输入刀身码", Toast.LENGTH_LONG);
                } else if (null == tv01.getText() || "".equals(tv01.getText().toString().trim())) {
                    createAlertDialog(C01S011_001Activity.this, "请选择物料号", Toast.LENGTH_LONG);
                } else {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.show();
                            }
                        });
                        // TODO 确认是否不需要修改 bind
                        SynthesisCuttingToolLocation location = configToBindMap.get(tv01.getText());

                        SynthesisCuttingToolLocation synthesisCuttingToolLocation = new SynthesisCuttingToolLocation();
                        synthesisCuttingToolLocation.setCuttingToolCode(location.getCuttingTool().getCode());
                        synthesisCuttingToolLocation.setId(location.getId());
                        synthesisCuttingToolLocation.setCuttingToolBladeCode(selectBusinessCode + "-" + etBladeCode.getText().toString().trim());


                        BindBladeDTO bindBladeDTO = new BindBladeDTO();
                        bindBladeDTO.setLocation(synthesisCuttingToolLocation);

                        String jsonStr = objectToJson(bindBladeDTO);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                        //调用接口，查询合成刀具组成信息
                        IRequest iRequest = retrofit.create(IRequest.class);
                        Call<String> bindBlade = iRequest.bindBlade(body);

                        bindBlade.enqueue(new MyCallBack<String>() {
                            @Override
                            public void _onResponse(Response<String> response) {
                                try {
                                    if (response.raw().code() == 200) {
                                        jumpPage();
                                    } else {
                                        createAlertDialog(C01S011_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S011_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
        dialog.getWindow().setLayout((int) (screenWidth * 1), (int) (screenHeight * 0.4));

    }

    //物料号下拉框的Adapter
    class MyAdapter2 extends BaseAdapter {

        @Override
        public int getCount() {
            return businessCodeList.size();
        }

        @Override
        public Object getItem(int i) {
            return businessCodeList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S011_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(businessCodeList.get(i).toString());
            //TODO 需要取值数据
            return view1;
        }
    }


}
