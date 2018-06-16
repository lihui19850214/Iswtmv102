package com.icomp.Iswtmv10.v01c01.c01s013;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.constants.UnInstallReasonEnum;
import com.apiclient.dto.LineDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备卸下页面2
 */
public class C01S013_002Activity extends CommonActivity {

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;

    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;

    @BindView(R.id.et_01)
    EditText et01;

    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_0_0)
    TextView tv00;
    @BindView(R.id.tv_1_0)
    TextView tv10;
    @BindView(R.id.tv_1_1)
    TextView tv11;


    private List<UnInstallReasonEnum> removeReasonList = new ArrayList<>();//保存所有卸下原因
    private List<ProductLine> productLineList = new ArrayList<>();//保存所有零部件种类


    UnInstallReasonEnum unInstallReasonEnum;
    ProductLine productLine;

    // 合成刀标签
    String queryVORFID = "";
    // 刀身码
    String bladeCode = "";

    SynthesisCuttingToolBind synthesisCuttingToolBind;
    ProductLineAssemblyline assemblyline;
    ProductLineProcess process;
    ProductLineEquipment equipment;


    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s013_002activity);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");
            queryVORFID = (String) paramMap.get("queryVORFID");
            assemblyline = (ProductLineAssemblyline) paramMap.get("assemblyline");
            process = (ProductLineProcess) paramMap.get("process");
            equipment = (ProductLineEquipment) paramMap.get("equipment");
            productLineList = (List<ProductLine>) paramMap.get("productLineList");
            bladeCode = (String) paramMap.get("bladeCode");

            tv00.setText(synthesisCuttingToolBind.getSynthesisCuttingTool().getSynthesisCode());
            tv10.setText(process.getName());
            tv11.setText(equipment.getName());

            for (UnInstallReasonEnum unInstallReasonEnum : UnInstallReasonEnum.values()) {
                removeReasonList.add(unInstallReasonEnum);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.ll_01, R.id.ll_02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_01:
                // 卸下原因下拉框
                showPopupWindow();
                break;
            case R.id.ll_02:
                // 零部件种类下拉框
                showPopupWindow2();
                break;
        }
    }

    /**
     * 点击卸下原因下拉框
     */
    public void showPopupWindow() {
        View view = LayoutInflater.from(C01S013_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv01.setText(removeReasonList.get(i).getName());
                unInstallReasonEnum = removeReasonList.get(i);
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //卸下原因下拉框的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return removeReasonList.size();
        }

        @Override
        public Object getItem(int i) {
            return removeReasonList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S013_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(removeReasonList.get(i).getName());
            return view1;
        }
    }


    /**
     * 点击零部件种类下拉框
     */
    public void showPopupWindow2() {
        View view = LayoutInflater.from(C01S013_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter1 myAdapter1 = new MyAdapter1();
        listView.setAdapter(myAdapter1);
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
                tv02.setText(productLineList.get(i).getProductLineParts().getName());
                productLine = productLineList.get(i);
                et01.setText(productLine.getToolDurable()+"");
                //将光标设置在最后
                et01.setSelection(et01.getText().length());
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    //零部件种类下拉框的Adapter
    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return productLineList.size();
        }

        @Override
        public Object getItem(int i) {
            return productLineList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S013_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(productLineList.get(i).getProductLineParts().getName());
            return view1;
        }

    }


    /**
     * 返回
     */
    public void cancel(View view) {
        Intent intent = new Intent(C01S013_002Activity.this, C01S013_001Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }

    /**
     * 确认
     */
    public void appConfirm(View view) {
//        showDialogAlert("加工零部件：齿轮-001\n加工数量：1200");

        if (unInstallReasonEnum == null) {
            createAlertDialog(C01S013_002Activity.this, "请选择卸下原因", Toast.LENGTH_LONG);
        } else if (productLine == null) {
            createAlertDialog(C01S013_002Activity.this, "请选择加工零部件", Toast.LENGTH_LONG);
        } else if ("".equals(et01.getText().toString().trim())) {
            createAlertDialog(C01S013_002Activity.this, "请输入加工量", Toast.LENGTH_LONG);
        } else if (0 == Integer.valueOf(et01.getText().toString().trim())) {
            createAlertDialog(C01S013_002Activity.this, "加工量不能为0", Toast.LENGTH_LONG);
        } else {

            authorizationWindow(new AuthorizationWindowCallBack() {
                @Override
                public void success(AuthCustomer authCustomer) {
                    requestData(authCustomer);
                }

                @Override
                public void fail() {

                }
            });

        }

    }

//    /**
//     * 点击空白收起键盘
//     *
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
//                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.onTouchEvent(event);
//    }

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
                    impowerRecorder.setRfidLasercode(queryVORFID);// rfid标签
                    impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                    impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                    impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_UnInstall.getKey().toString());//操作key

                    impowerRecorderList.add(impowerRecorder);
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(C01S013_002Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            }


            RfidContainerVO RfidContainerVO = new RfidContainerVO();
            if (queryVORFID != null) {
                RfidContainerVO.setLaserCode(queryVORFID);
            }
            if (bladeCode != null) {
                RfidContainerVO.setSynthesisBladeCode(bladeCode);
            }

            SynthesisCuttingToolBindVO synthesisCuttingToolBindVO = new SynthesisCuttingToolBindVO();
            synthesisCuttingToolBindVO.setRfidContainerVO(RfidContainerVO);

            LineDTO lineDTO = new LineDTO();
            lineDTO.setSynthesisCuttingToolBindVO(synthesisCuttingToolBindVO);
            lineDTO.setUnBindReason(unInstallReasonEnum.getKey());


            ProductLinePartsVO productLinePartsVO = new ProductLinePartsVO();
            productLinePartsVO.setCode(productLine.getProductLineParts().getCode());

            lineDTO.setPartsVO(productLinePartsVO);

            lineDTO.setProcessingCount(Integer.valueOf(et01.getText().toString().trim()));

            ProductLineEquipmentVO productLineEquipmentVO = new ProductLineEquipmentVO();
            productLineEquipmentVO.setCode(equipment.getCode());

            lineDTO.setEquipmentVO(productLineEquipmentVO);



            String jsonStr = objectToJson(lineDTO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> unInstall = iRequest.unInstall(body, headsMap);

            unInstall.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到成功详细页面
                            Intent intent = new Intent(C01S013_002Activity.this, C01S013_0021Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S013_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        loading.dismiss();
                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    loading.dismiss();
                    createAlertDialog(C01S013_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
     * 显示数据提示dialog
     */
    private void showDialogAlert(String content) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog2);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_alert, null);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvContent.setText(content);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C01S013_002Activity.this, C01S013_0021Activity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.setContentView(view);
        dialog.getWindow().setLayout((int) (screenWidth * 0.8), (int) (screenHeight * 0.6));
    }
}


