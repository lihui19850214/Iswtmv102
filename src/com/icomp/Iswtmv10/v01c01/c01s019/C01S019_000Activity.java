package com.icomp.Iswtmv10.v01c01.c01s019;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.DjOwnerAkp;
import com.apiclient.pojo.OutsideFactoryMode;
import com.apiclient.pojo.SharpenProvider;
import com.apiclient.vo.OutSideVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 厂外修磨页面1
 * Created by FanLL on 2017/7/4.
 */

public class C01S019_000Activity extends CommonActivity {

    @BindView(R.id.btn_return)
    Button mBtnCancel;
    @BindView(R.id.btn_next)
    Button mBtnNext;

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
    @BindView(R.id.et_02)
    EditText et02;
    @BindView(R.id.et_03)
    EditText et03;
    @BindView(R.id.et_04)
    EditText et04;


    private int position = 0;

    private Retrofit retrofit;

    // 外委厂家列表
    private List<DjOwnerAkp> sharpenProviderList = new ArrayList<>();
    private DjOwnerAkp sharpenProvider = null;
    // 外委方式列表
    private List<OutsideFactoryMode> outsideFactoryModeList = new ArrayList<>();
    private OutsideFactoryMode outsideFactoryMode = null;

    OutSideVO outSideVO = new OutSideVO();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s019_000);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        // 外委方式列表
        for (OutsideFactoryMode outsideFactoryMode : OutsideFactoryMode.values()){
            outsideFactoryModeList.add(outsideFactoryMode);
        }

        // 外委厂家列表
        getSharpenProvider();

        // 上一个页面传过来的参数
        Map<String, Object> paramMap = PARAM_MAP.get(1);
        if (paramMap != null) {
            outSideVO = (OutSideVO) paramMap.get("outSideVO");
            setViewData(outSideVO);
        }
    }

    private void setViewData(OutSideVO outSideVO) {
        et01.setText(outSideVO.getZcCode());//资材单号
        et02.setText(outSideVO.getOrderNum());//外委单号,出厂单号
        et03.setText(outSideVO.getHandlers());//经手人
        et04.setText(outSideVO.getSender());//邮寄人


        //外委方式
        for (OutsideFactoryMode ofm : outsideFactoryModeList) {
            if (ofm.equals(outSideVO.getOutWay())) {
                outsideFactoryMode = ofm;
                tv02.setText(outsideFactoryMode.getName());
            }
        }

        for (DjOwnerAkp doa : sharpenProviderList) {
            if (doa.getOwnerCode().equals(outSideVO.getSharpenProviderCode())) {
                sharpenProvider = doa;
                tv01.setText(sharpenProvider.getName());
            }
        }

//        outSideVO.setZcCode(et01.getText().toString().trim());//资材单号
//        outSideVO.setOrderNum(et02.getText().toString().trim());//外委单号,出厂单号
//        outSideVO.setHandlers(et03.getText().toString().trim());//经手人
//        outSideVO.setSender(et04.getText().toString().trim());//邮寄人

//        outSideVO.setOutWay(outsideFactoryMode.getKey());//外委方式

//        outSideVO.setSharpenProviderCode(sharpenProvider.getOwnerCode());//外委商,外委厂家
//        outSideVO.setQmSharpenProviderCode(sharpenProvider.getOwnerCode());// 外委厂家code
//        outSideVO.setQmSharpenProviderName(sharpenProvider.getName());// 外委厂家name
    }

    /**
     * 查询外委厂家
     */
    private void getSharpenProvider() {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        String jsonStr = "{}";
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getSharpenProvider = iRequest.getSharpenProvider(body);

        try {
            Response<String> response = getSharpenProvider.execute();

            ObjectMapper mapper = new ObjectMapper();

            if (response.raw().code() == 200) {
                sharpenProviderList = mapper.readValue(response.body(), getCollectionType(mapper, List.class, DjOwnerAkp.class));
            } else {
                createAlertDialog(C01S019_000Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            createAlertDialog(C01S019_000Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
        } finally {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.dismiss();
                }
            });
        }
    }


    @OnClick({R.id.btn_return, R.id.btn_next, R.id.ll_01, R.id.ll_02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_01:
                showPopupWindow();
                break;
            case R.id.ll_02:
                showPopupWindow2();
                break;
            case R.id.btn_return:
                finish();
                break;
            case R.id.btn_next:

                if (et01.getText().toString() == null || "".equals(et01.getText().toString().trim())) {
                    createAlertDialog(C01S019_000Activity.this, "请输入资材单号", Toast.LENGTH_LONG);
                } else if (et02.getText().toString() == null || "".equals(et02.getText().toString().trim())) {
                    createAlertDialog(C01S019_000Activity.this, "请输入出厂单号", Toast.LENGTH_LONG);
                } else if (et03.getText().toString() == null || "".equals(et03.getText().toString().trim())) {
                    createAlertDialog(C01S019_000Activity.this, "请输入经手人", Toast.LENGTH_LONG);
                } else if (et04.getText().toString() == null || "".equals(et04.getText().toString().trim())) {
                    createAlertDialog(C01S019_000Activity.this, "请输入邮寄人", Toast.LENGTH_LONG);
                } else if (sharpenProvider == null) {
                    createAlertDialog(C01S019_000Activity.this, "请选择外委厂家", Toast.LENGTH_LONG);
                } else if (outsideFactoryMode == null) {
                    createAlertDialog(C01S019_000Activity.this, "请选择外委方式", Toast.LENGTH_LONG);
                } else {
                    outSideVO.setZcCode(et01.getText().toString().trim());//资材单号
                    outSideVO.setOrderNum(et02.getText().toString().trim());//外委单号,出厂单号
                    outSideVO.setHandlers(et03.getText().toString().trim());//经手人
                    outSideVO.setSender(et04.getText().toString().trim());//邮寄人

                    outSideVO.setSharpenProviderCode(sharpenProvider.getOwnerCode());//外委商,外委厂家
                    outSideVO.setQmSharpenProviderCode(sharpenProvider.getOwnerCode());// 外委厂家code
                    outSideVO.setQmSharpenProviderName(sharpenProvider.getName());// 外委厂家name

                    outSideVO.setOutWay(outsideFactoryMode.getKey());//外委方式


                    // 用于页面之间传值，新方法
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("outSideVO", outSideVO);
                    PARAM_MAP.put(1, paramMap);


                    Intent intent = new Intent(this, C01S019_001Activity.class);
                    // 不清空页面之间传递的值
                    intent.putExtra("isClearParamMap", false);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
        }
    }



    /**
     * 点击外委厂家下拉框
     */
    private void showPopupWindow() {
        View view = LayoutInflater.from(C01S019_000Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv01.setText(sharpenProviderList.get(i).getName());
                popupWindow.dismiss();

                sharpenProvider = sharpenProviderList.get(i);
//                //设置外委方式下拉列表第一条为空
//                tv02.setText("");
//                //清空外委方式列表
//                outsideFactoryModeList.clear();
//                // 清空保存的外委方式
//                outsideFactoryMode = null;
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //设备的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sharpenProviderList.size();
        }

        @Override
        public Object getItem(int i) {
            return sharpenProviderList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S019_000Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(sharpenProviderList.get(i).getName());
            return view1;
        }
    }

    /**
     * 点击外委方式下拉框
     */
    private void showPopupWindow2() {
        View view = LayoutInflater.from(C01S019_000Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv02.setText(outsideFactoryModeList.get(i).getName());
                //TODO 选择轴下拉列表显示选中名字
                popupWindow.dismiss();

                outsideFactoryMode = outsideFactoryModeList.get(i);
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    //设备的Adapter
    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return outsideFactoryModeList.size();
        }

        @Override
        public Object getItem(int i) {
            return outsideFactoryModeList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S019_000Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(outsideFactoryModeList.get(i).getName());
            //TODO 初始化轴下拉列表名字
            return view1;
        }

    }



}
