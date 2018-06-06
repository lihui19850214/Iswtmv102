package com.icomp.Iswtmv10.v01c01.c01s015;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.RfidContainer;
import com.apiclient.vo.CuttingToolBindVO;
import com.apiclient.vo.CuttingToolVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * 刀具绑定页面1
 */
public class C01S015_001Activity extends CommonActivity {

    @BindView(R.id.et_01)
    EditText et01;
    @BindView(R.id.btnScan)
    Button btnScan;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnBind)
    Button btnBind;

    //扫描线程
    private scanThread scanThread;

    //库房盘点参数类
    private CuttingToolBindVO params = new CuttingToolBindVO();

    //调用接口
    private Retrofit retrofit;

    List<CuttingToolBind> cuttingToolBindList = new ArrayList<>();

    CuttingToolBind cuttingToolBind = new CuttingToolBind();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s015_001);
        ButterKnife.bind(this);
//        //创建Activity时，添加到List进行管理
//        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();
        //将输入的材料号自动转化为大写
        et01.setTransformationMethod(new AllCapTransformationMethod());
        params.setCuttingToolVO(new CuttingToolVO());
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        finish();
    }

    //绑定按钮处理
    public void btnBind(View view) {

        if (cuttingToolBind.getRfidContainer() == null || cuttingToolBind.getRfidContainer().getLaserCode() == null || "".equals(cuttingToolBind.getRfidContainer().getLaserCode())) {
            createAlertDialog(C01S015_001Activity.this, "请扫描 RFID 标签", Toast.LENGTH_LONG);
        } else {
            try {
                loading.show();

                IRequest iRequest = retrofit.create(IRequest.class);

                String jsonStr = objectToJson(cuttingToolBind);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> bindUnbind = iRequest.bindUnbind(body);

                bindUnbind.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                Intent intent = new Intent(C01S015_001Activity.this, C01S015_003Activity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                createAlertDialog(C01S015_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                        createAlertDialog(C01S015_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (null != loading && loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            }
//            showDialogAlert("绑定刀具的刀身码是：" + tv01.getText().toString().trim());
        }
    }

    @OnClick({R.id.btnScan, R.id.btnSearch, R.id.ll_01})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫描按钮
            case R.id.btnScan:
                if (tv01.getText().toString().trim() == null || "".equals(tv01.getText().toString().trim())) {
                    createAlertDialog(this, "请输入刀身码", Toast.LENGTH_LONG);
                    return;
                }

                //扫描方法
                scan();
                break;
            case R.id.btnSearch:
                params.getCuttingToolVO().setBusinessCode(et01.getText().toString().trim());

                if ("".equals(params.getCuttingToolVO().getBusinessCode())) {
                    createAlertDialog(C01S015_001Activity.this, "请输入材料号,再查询", Toast.LENGTH_LONG);
                } else {
                    tvTitle.setText(R.string.c01s015_001_002_title);

                    // 重置下拉列表，默认选中第一个
                    tv01.setText("");
                    //刀具
                    cuttingToolBind = new CuttingToolBind();
                    // 下拉列表置空
                    cuttingToolBindList = new ArrayList<>();

                    search();
                }
                break;
            case R.id.ll_01:
                showPopupWindow();
                break;
            default:
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnScan.setClickable(false);
            btnSearch.setClickable(false);
            btnCancel.setClickable(false);
            btnBind.setClickable(false);
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
            //调用单扫方法
            rfidString = singleScan();
            if ("close".equals(rfidString)) {
                Message message = new Message();
                overtimeHandler.sendMessage(message);
                btnSearch.setClickable(true);
                btnScan.setClickable(true);
                btnCancel.setClickable(true);
                btnBind.setClickable(true);
                isCanScan = true;
            } else if (null != rfidString) {
                Message message = new Message();
                scanHandler.sendMessage(message);
            }
        }
    }

    //扫描的Handler
    @SuppressLint("HandlerLeak")
    public Handler scanHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            RfidContainer rfidContainer = new RfidContainer();
            rfidContainer.setLaserCode(rfidString);
            cuttingToolBind.setRfidContainer(rfidContainer);

            popupWindow.dismiss();
            btnSearch.setClickable(true);
            btnScan.setClickable(true);
            btnCancel.setClickable(true);
            btnBind.setClickable(true);
            isCanScan = true;
        }
    };

    //查询线程
    private void search() {
        try {
            loading.show();

            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = objectToJson(params);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> getUnbind = iRequest.getUnbind(body);

            getUnbind.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            cuttingToolBindList = jsonToObject(response.body(), List.class, CuttingToolBind.class);

                            if (cuttingToolBindList == null || cuttingToolBindList.size() == 0) {
                                cuttingToolBindList = new ArrayList<>();
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            } else {
                                // 重置下拉列表，默认选中第一个
                                tv01.setText(cuttingToolBindList.get(0).getBladeCode());
                                //刀具
                                cuttingToolBind = cuttingToolBindList.get(0);
                            }
                        } else {
                            createAlertDialog(C01S015_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S015_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

//    //重写键盘上扫描按键的方法
//    @Override
//    protected void btnScan() {
//        super.btnScan();
//        if (isCanScan) {
//            isCanScan = false;
//        } else {
//            return;
//        }
//        //扫描方法
//        scan();
//    }


    //显示刀身码列表
    private void showPopupWindow() {
        View view = LayoutInflater.from(C01S015_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv01.setText(cuttingToolBindList.get(i).getBladeCode());
                popupWindow.dismiss();

                //刀具
                cuttingToolBind = cuttingToolBindList.get(i);
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //流水线的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cuttingToolBindList.size();
        }

        @Override
        public Object getItem(int i) {
            return cuttingToolBindList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S015_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(cuttingToolBindList.get(i).getBladeCode());
            return view1;
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
                try {
                    IRequest iRequest = retrofit.create(IRequest.class);

                    String jsonStr = objectToJson(cuttingToolBind);

                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> bindUnbind = iRequest.bindUnbind(body);

                    bindUnbind.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    Intent intent = new Intent(C01S015_001Activity.this, C01S015_003Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    createAlertDialog(C01S015_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void _onFailure(Throwable t) {
                            dialog.dismiss();
                            createAlertDialog(C01S015_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                }
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
