package com.icomp.Iswtmv10.v01c03.c03s003;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.apiclient.pojo.ProductLineAssemblyline;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.RfidContainer;
import com.apiclient.vo.ProductLineEquipmentVO;
import com.apiclient.vo.ProductLineVO;
import com.apiclient.vo.RfidContainerVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 设备初始化页面1
 */
public class C03S003_001Activity extends CommonActivity {

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;
    @BindView(R.id.btnScan)
    Button btnScan;


    //流水线列表
    private List<ProductLineAssemblyline> lineList = new ArrayList<>();
    //设备列表
    private List<ProductLineEquipment> equipmentEntityList = new ArrayList<>();
    // 设备
    ProductLineEquipment productLineEquipment = new ProductLineEquipment();
    //扫描线程
    private scanThread scanThread;


    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s003_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //启动时获取所有流水线和设备的线程
        findAssemblyLine();
    }

    //返回按钮处理--返回设备初始化菜单页面
    public void btnReturn(View view) {
        finish();
    }

    @OnClick({R.id.ll_01, R.id.ll_02, R.id.btnScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //点击流水线下拉列表处理
            case R.id.ll_01:
                //显示流水线列表
                showPopupWindow();
                break;
            //点击设备下拉列表处理
            case R.id.ll_02:
                //显示流水线对应的设备列表
                showPopupWindow2();
                break;
            //点击扫描按钮处理
            case R.id.btnScan:
                scan();
                break;
            default:
        }
    }

    //获取所有流水线和设备的线程
    private void findAssemblyLine() {
        try {
            loading.show();

            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = "{}";
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> getAssemblylines = iRequest.getAssemblylines(body);

            getAssemblylines.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            lineList = jsonToObject(response.body(), List.class, ProductLineAssemblyline.class);
                            if (lineList == null || lineList.size() == 0) {
                                lineList = new ArrayList<>();
                                createAlertDialog(C03S003_001Activity.this, "无流水线信息", Toast.LENGTH_LONG);
                            }
                        } else {
                            createAlertDialog(C03S003_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C03S003_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

    //显示流水线列表
    private void showPopupWindow() {
        View view = LayoutInflater.from(C03S003_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv01.setText(lineList.get(i).getName());
                popupWindow.dismiss();
                //设置设备下拉列表第一条为空
                tv02.setText("");
                //清空流水线对应的设备列表
                equipmentEntityList.clear();

                //根据流水线ID取出该流水线上的设备
                String assemblyLineCode = lineList.get(i).getCode();

                try {
                    loading.show();

                    IRequest iRequest = retrofit.create(IRequest.class);

                    ProductLineVO plVO = new ProductLineVO();
                    plVO.setAssemblylineCode(assemblyLineCode);

                    String jsonStr = objectToJson(plVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> getEquipmentByAssemblyline = iRequest.getEquipmentByAssemblyline(body);

                    getEquipmentByAssemblyline.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    equipmentEntityList = jsonToObject(response.body(), List.class, ProductLineEquipment.class);
                                    // 未查询到设备信息
                                    if (equipmentEntityList == null || equipmentEntityList.size() == 0) {
                                        equipmentEntityList = new ArrayList<>();
                                        createAlertDialog(C03S003_001Activity.this, "无设备信息", Toast.LENGTH_LONG);
                                    }
                                } else {
                                    createAlertDialog(C03S003_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                            createAlertDialog(C03S003_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
        });
        popupWindow.showAsDropDown(ll01);
    }

    //流水线的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lineList.size();
        }

        @Override
        public Object getItem(int i) {
            return lineList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C03S003_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(lineList.get(i).getName());
            return view1;
        }
    }

    //显示流水线对应的设备列表
    private void showPopupWindow2() {
        View view = LayoutInflater.from(C03S003_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv02.setText(equipmentEntityList.get(i).getName());
                productLineEquipment = equipmentEntityList.get(i);
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    //设备的Adapter
    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return equipmentEntityList.size();
        }

        @Override
        public Object getItem(int i) {
            return equipmentEntityList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C03S003_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(equipmentEntityList.get(i).getName());
            return view1;
        }

    }

    //扫描按钮处理
    private void scan() {
        if ("".equals(tv01.getText().toString().trim())) {
            createAlertDialog(C03S003_001Activity.this, getString(R.string.c03s003_001_003), Toast.LENGTH_LONG);
        }else if ("".equals(tv02.getText().toString().trim())) {
            createAlertDialog(C03S003_001Activity.this, getString(R.string.c03s003_001_004), Toast.LENGTH_LONG);
        } else {
            if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
                //扫描时选择弹框不可用
                ll01.setClickable(false);
                ll02.setClickable(false);
                //点击扫描按钮以后，设置扫描按钮不可用
                btnScan.setClickable(false);
                //显示扫描弹框的方法
                scanPopupWindow();
                //开启扫描线程
                scanThread = new scanThread();
                scanThread.start();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //扫描线程
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            isCanScan = false;
            //单扫方法
            rfidString = singleScan();
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                isCanScan = true;
                ll01.setClickable(true);
                ll02.setClickable(true);
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if(null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnScan.setClickable(true);
                        isCanScan = true;
                        ll01.setClickable(true);
                        ll02.setClickable(true);

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                try {
                    RfidContainerVO rfidContainer = new RfidContainerVO();
                    rfidContainer.setLaserCode(rfidString);

                    ProductLineEquipmentVO productLineEquipmentVO = new ProductLineEquipmentVO();
                    productLineEquipmentVO.setCode(productLineEquipment.getCode());
                    productLineEquipmentVO.setRfidContainerVO(rfidContainer);


                    String jsonStr = objectToJson(productLineEquipmentVO);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    IRequest iRequest = retrofit.create(IRequest.class);
                    Call<String> initEquipment = iRequest.initEquipment(body);
                    initEquipment.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    //跳转加工设备初始化成功页面2
                                    Intent intent = new Intent(C03S003_001Activity.this, C03S003_002Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C03S003_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C03S003_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

}
