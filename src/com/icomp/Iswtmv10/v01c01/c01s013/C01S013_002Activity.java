package com.icomp.Iswtmv10.v01c01.c01s013;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import com.apiclient.pojo.ProductLine;
import com.apiclient.pojo.ProductLineParts;
import com.apiclient.pojo.SynthesisCuttingToolBindleRecords;
import com.apiclient.pojo.UnInstallReasonEnum;
import com.apiclient.vo.ProductLineVO;
import com.apiclient.vo.UnBindEquipmentVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C01S003_004Adapter;
import com.icomp.wsdl.v01c01.c01s013.endpoint.C01S013Respons;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设备卸下2
 *
 * @author WHY
 * @ClassName: C01S013_002Activity
 * @date 2016-3-2 下午6:47:51
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


    private int remove_reason_posttion, parts_name_posttion;//当前选择的卸下原因，零部件种类在集合中的位置
    private List<UnInstallReasonEnum> removeReasonList = new ArrayList<>();//保存所有卸下原因
    private List<ProductLine> productLineList = new ArrayList<>();//保存所有零部件种类


    UnInstallReasonEnum unInstallReasonEnum;
    ProductLine productLine;

    private SynthesisCuttingToolBindleRecords synthesisCuttingToolBindleRecords;

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s013_002activity);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();


        Map<String, Object> paramMap = PARAM_MAP.get(1);
        synthesisCuttingToolBindleRecords = (SynthesisCuttingToolBindleRecords) paramMap.get("synthesisCuttingToolBindleRecords");

        for (UnInstallReasonEnum unInstallReasonEnum : UnInstallReasonEnum.values()){
            removeReasonList.add(unInstallReasonEnum);
        }

        //查询加工零部件
        getParts();
    }

    private void getParts() {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        Gson gson = new Gson();

        ProductLineVO productLineVO = new ProductLineVO();
        productLineVO.setSynthesisCuttingToolCode(synthesisCuttingToolBindleRecords.getSynthesisCuttingToolCode());//合成刀
        productLineVO.setAxleCode(synthesisCuttingToolBindleRecords.getProductLineAxleCode());//轴ID
        productLineVO.setEquipmentCode(synthesisCuttingToolBindleRecords.getProductLineEquipmentCode());//设备
        productLineVO.setProcessCode(synthesisCuttingToolBindleRecords.getProductLineProcessCode());//工序

        String jsonStr = gson.toJson(productLineVO);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getParts = iRequest.getParts(body);
        getParts.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        ObjectMapper mapper = new ObjectMapper();

                        productLineList = mapper.readValue(response.body(), getCollectionType(mapper, List.class, ProductLine.class));//丢刀
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
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            Gson gson = new Gson();

            UnBindEquipmentVO unBindEquipmentVO = new UnBindEquipmentVO();
            ProductLineVO productLineVO = new ProductLineVO();
            productLineVO.setSynthesisCuttingToolCode(synthesisCuttingToolBindleRecords.getSynthesisCuttingToolCode());
            unBindEquipmentVO.setProductLineVO(productLineVO);
            unBindEquipmentVO.setBindRfid(synthesisCuttingToolBindleRecords.getBindRfid());
            unBindEquipmentVO.setParts(productLine.getProductLineParts());//加工零部件
            unBindEquipmentVO.setUnBindReason(unInstallReasonEnum.getKey());//卸下原因
            unBindEquipmentVO.setCount(Integer.valueOf(et01.getText().toString().trim()));//加工数量

            String jsonStr = gson.toJson(unBindEquipmentVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> unBindEquipment = iRequest.unBindEquipment(body);

            unBindEquipment.enqueue(new MyCallBack<String>() {
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

        }

    }

    /**
     * 点击空白收起键盘
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
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


