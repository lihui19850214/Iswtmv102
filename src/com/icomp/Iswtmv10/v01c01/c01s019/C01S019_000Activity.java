package com.icomp.Iswtmv10.v01c01.c01s019;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.apiclient.constants.OperationEnum;
import com.apiclient.dto.InFactoryDTO;
import com.apiclient.pojo.*;
import com.apiclient.vo.InOutQueryVO;
import com.apiclient.vo.OutSideVO;
import com.apiclient.vo.QueryVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

/**
 * 厂外修磨页面1
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


    private Retrofit retrofit;

    // 外委厂家列表
    private List<DjOwnerAkp> sharpenProviderList = new ArrayList<>();
    private DjOwnerAkp sharpenProvider = null;
    // 外委方式列表
    private List<OutsideFactoryMode> outsideFactoryModeList = new ArrayList<>();
    private OutsideFactoryMode outsideFactoryMode = null;

    InFactoryDTO inFactoryDTO = new InFactoryDTO();


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
    }

    private void setViewData(InFactoryDTO inFactoryDTO) {
        et01.setText(inFactoryDTO.getZcCode());//资材单号
        et02.setText(inFactoryDTO.getOrderNum());//外委单号,出厂单号
        et03.setText(inFactoryDTO.getHandlers());//经手人
        et04.setText(inFactoryDTO.getSender());//邮寄人

        //外委厂家
        for (DjOwnerAkp doa : sharpenProviderList) {
            if (doa.getOwnerCode().equals(inFactoryDTO.getQmSharpenProviderCode())) {
                sharpenProvider = doa;
                tv01.setText(sharpenProvider.getName());
            }
        }

        //外委方式
        for (OutsideFactoryMode ofm : outsideFactoryModeList) {
            if (ofm.getKey().equals(inFactoryDTO.getOutWay())) {
                outsideFactoryMode = ofm;
                tv02.setText(outsideFactoryMode.getName());
            }
        }
    }

    /**
     * 查询外委厂家和方式
     */
    private void getSharpenProvider() {
        try {
            loading.show();

            String jsonStr = "{}";
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> queryForOutGrinding = iRequest.queryForOutGrinding(body);

            queryForOutGrinding.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {

                            InOutQueryVO InOutQueryVO = jsonToObject(response.body(), InOutQueryVO.class);

                            sharpenProviderList = InOutQueryVO.getDjOwnerAkps();

                            if (sharpenProviderList == null) {
                                sharpenProviderList = new ArrayList<>();
                            }

                            // 上一个页面传过来的参数
                            Map<String, Object> paramMap = PARAM_MAP.get(1);
                            if (paramMap != null) {
                                inFactoryDTO = (InFactoryDTO) paramMap.get("inFactoryDTO");
                                setViewData(inFactoryDTO);
                            } else {
                                //外委单号,出厂单号
                                et02.setText(InOutQueryVO.getWwcode());
                                // 外委厂家列表
                                if (sharpenProviderList.size() > 0) {
                                    tv01.setText(sharpenProviderList.get(0).getName());
                                    sharpenProvider = sharpenProviderList.get(0);
                                }
                                // 外委方式列表
                                if (outsideFactoryModeList.size() > 0) {
                                    tv02.setText(outsideFactoryModeList.get(0).getName());
                                    outsideFactoryMode = outsideFactoryModeList.get(0);
                                }

                                try {
                                    //设定用户访问信息
                                    @SuppressLint("WrongConstant")
                                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                                    String userInfoJson = sharedPreferences.getString("loginInfo", null);

                                    AuthCustomer customer = jsonToObject(userInfoJson, AuthCustomer.class);
                                    et03.setText(customer.getName());
                                    et04.setText(customer.getName());
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                                    return;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    createAlertDialog(C01S019_000Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } else {
                            createAlertDialog(C01S019_000Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S019_000Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
                    inFactoryDTO.setZcCode(et01.getText().toString().trim());//资材单号
                    inFactoryDTO.setOrderNum(et02.getText().toString().trim());//外委单号
                    inFactoryDTO.setHandlers(et03.getText().toString().trim());//经手人
                    inFactoryDTO.setSender(et04.getText().toString().trim());//邮寄人

                    inFactoryDTO.setQmSharpenProviderCode(sharpenProvider.getOwnerCode());//启明外委供应商编码
                    inFactoryDTO.setQmSharpenProviderName(sharpenProvider.getName());//启明外委供应商名称

                    inFactoryDTO.setOutWay(outsideFactoryMode.getKey());//外委方式


                    // 用于页面之间传值，新方法
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("inFactoryDTO", inFactoryDTO);
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
                sharpenProvider = sharpenProviderList.get(i);
                popupWindow.dismiss();

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
                outsideFactoryMode = outsideFactoryModeList.get(i);
                popupWindow.dismiss();
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
            return view1;
        }

    }



}
