package com.icomp.Iswtmv10.v01c01.c01s011;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c03.c03s003.C03S003_001Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.adapter.C01S003_004Adapter;
import com.icomp.common.utils.SysApplication;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 安上设备
 *
 * @author WHY
 * @ClassName: C01S011_002Activity
 * @date 2016-3-1 下午8:54:37
 */

public class C01S011_002Activity extends CommonActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_00)
    TextView tv00;
    @BindView(R.id.tvScan)
    TextView tvScan;

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;

    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.ll_02)
    LinearLayout ll02;

    @BindView(R.id.btn_scan)
    TextView btnScan;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    //调用接口
    private Retrofit retrofit;
    //扫描线程
    private scanThread scanThread;
    //扫描线程
    private scanThread2 scanThread2;

    SynthesisCuttingToolBind synthesisCuttingToolBing = new SynthesisCuttingToolBind();
    String equipmentRfid = "";
    //设备列表

    List<ProductLineEquipment> equipmentEntityList = new ArrayList<>();// 设备列表
    Map<String,List<ProductLineAxle>> axleMap = new HashMap<>();// 设备对应轴号Map
    private List<ProductLineAxle> axisList = new ArrayList();// 轴号列表

    ProductLineEquipment productLineEquipment = null;//选中的设备列表项
    ProductLineAxle productLineAxle = null;//选中的轴号列表项

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.c01s011_002activity);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

//        showDialogAlert("合成刀具编码：sadfsdf\n安上设备比编号：爱上QZ01-S1");
    }

    /**
     * 扫描按钮点击
     */
    @OnClick({R.id.ll_01, R.id.ll_02, R.id.btn_scan, R.id.tvScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvScan:
                scan();
                tvDesc.setText("请扫描要安上的设备标签");
                break;
            case R.id.btn_scan:
                if (tv00.getText() != null && !"".equals(tv00.getText().toString())) {
                    scan2();
                } else {
                    createAlertDialog(C01S011_002Activity.this, "请先扫描合成刀具标签", Toast.LENGTH_LONG);
                }
                break;
            case R.id.ll_01:
                if (equipmentEntityList.size() > 0)
                    showPopupWindow();//设备
                break;
            case R.id.ll_02:
                if (axisList.size() > 0)
                    showPopupWindow2();//轴号
                break;
            default:
        }
    }

    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
            btnNext.setClickable(false);

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
            rfidString = singleScan();//TODO 生产环境需要
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                btnScan.setClickable(true);
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
                        btnScan.setClickable(true);
                        btnReturn.setClickable(true);
                        btnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });

                //调用接口，查询合成刀具组成信息
                IRequest iRequest = retrofit.create(IRequest.class);

                QuerySynthesisCuttingToolVO querySynthesisCuttingToolVO = new QuerySynthesisCuttingToolVO();
                querySynthesisCuttingToolVO.setRfidCode(rfidString);

                Gson gson = new Gson();
                String jsonStr = gson.toJson(querySynthesisCuttingToolVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> querySynthesisCuttingTool = iRequest.querySynthesisCuttingTool(body);
                querySynthesisCuttingTool.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                synthesisCuttingToolBing = mapper.readValue(response.body(), SynthesisCuttingToolBind.class);


                                //TODO 赋值给 合成刀具标签 TextView，检查是否正确
                                tv00.setText(synthesisCuttingToolBing.getSynthesisCuttingTool().getSynthesisCode());
                            } else {
                                final String errorStr = response.errorBody().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createAlertDialog(C01S011_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
            }
        }
    }

    //扫描方法
    private void scan2() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            tvScan.setClickable(false);
            btnScan.setClickable(false);
            btnReturn.setClickable(false);
            btnNext.setClickable(false);
            //显示扫描弹框的方法
            scanPopupWindow();
            //扫描线程
            scanThread2 = new scanThread2();
            scanThread2.start();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.initFail), Toast.LENGTH_SHORT).show();
        }
    }

    //扫描线程
    private class scanThread2 extends Thread {
        @Override
        public void run() {
            super.run();
            //单扫方法
            rfidString = singleScan();//TODO 生产环境需要
            if ("close".equals(rfidString)) {
                tvScan.setClickable(true);
                btnScan.setClickable(true);
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
                        btnScan.setClickable(true);
                        btnReturn.setClickable(true);
                        btnNext.setClickable(true);
                        isCanScan = true;

                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        loading.show();
                    }
                });


                equipmentRfid = rfidString;

                // 查询设备和轴号
                IRequest iRequest = retrofit.create(IRequest.class);


                QueryEquipmentByRfidVO queryEquipmentByRfidVO = new QueryEquipmentByRfidVO();
                queryEquipmentByRfidVO.setSynthesisCuttingToolCode(synthesisCuttingToolBing.getSynthesisCuttingTool().getCode());
                queryEquipmentByRfidVO.setRfidCode(equipmentRfid);


                Gson gson = new Gson();
                String jsonStr = gson.toJson(queryEquipmentByRfidVO);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

                Call<String> queryEquipmentByRFID = iRequest.queryEquipmentByRFID(body);
                queryEquipmentByRFID.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                Gson gson = new Gson();

                                QueryEquipmentByRfidVO queryEquipmentByRfidVO = gson.fromJson(response.body(), QueryEquipmentByRfidVO.class);

                                for (ProductLine productLine : queryEquipmentByRfidVO.getProductLines()) {
                                    equipmentEntityList.add(productLine.getProductLineEquipment());
                                }

                                for (ProductLineEquipment lineEquipment : equipmentEntityList) {
                                    List<ProductLineAxle> axleItemList = new ArrayList<>();

                                    for (ProductLine productLine : queryEquipmentByRfidVO.getProductLines()) {
                                        if (lineEquipment.getCode().equals(productLine.getEquipmentCode())){
                                            axleItemList.add(productLine.getProductLineAxle());
                                        }

                                    }
                                    axleMap.put(lineEquipment.getCode(), axleItemList);
                                }
                            } else {
                                final String errorStr = response.errorBody().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createAlertDialog(C01S011_002Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
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

    /**
     * 点击安上设备下拉框
     */
    private void showPopupWindow() {
        View view = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        C01S011_002Activity.MyAdapter myAdapter = new C01S011_002Activity.MyAdapter();
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
                tv01.setText(equipmentEntityList.get(i).getName());
                productLineEquipment = equipmentEntityList.get(i);
                popupWindow.dismiss();
                //设置设备下拉列表第一条为空
                tv02.setText("");
                //清空流水线对应的设备列表
                axisList.clear();
                productLineAxle = null;

                axisList = axleMap.get(equipmentEntityList.get(i).getCode());
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //设备的Adapter
    class MyAdapter extends BaseAdapter {

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
            View view1 = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(equipmentEntityList.get(i).getName());
            return view1;
        }
    }

    /**
     * 点击轴号下拉框
     */
    private void showPopupWindow2() {
        View view = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.spinner_c03s004_001, null);
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
                tv02.setText(axisList.get(i).getName());
                productLineAxle = axisList.get(i);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    //设备的Adapter
    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return axisList.size();
        }

        @Override
        public Object getItem(int i) {
            return axisList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S011_002Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(axisList.get(i).getName());
            //TODO 初始化轴下拉列表名字
            return view1;
        }

    }


    /**
     * 下一步
     */
    public void appNext(View view) {

        if (null != tv01 && !"".equals(tv01.getText().toString().trim()) && null != tv02 && !"".equals(tv02.getText().toString().trim())) {

            BindEquipmentVO bindEquipmentVO = new BindEquipmentVO();

            //TODO 提交数据
            bindEquipmentVO.setAxle(productLineAxle);
            bindEquipmentVO.setEquipment(productLineEquipment);
            bindEquipmentVO.setSynthesisCuttingToolBind(synthesisCuttingToolBing);


            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            ObjectMapper mapper = new ObjectMapper();

            String jsonStr = "";
            try {
                jsonStr = mapper.writeValueAsString(bindEquipmentVO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> bindEquipment = iRequest.bindEquipment(body);
            bindEquipment.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Intent intent = new Intent(C01S011_002Activity.this, C01S011_003Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S011_002Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S011_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                }
            });
        } else {
            createAlertDialog(C01S011_002Activity.this, "请配置生产关联或绑定对应设备标签", Toast.LENGTH_LONG);
        }

    }





    /**
     * 返回
     */
    public void appReturn(View view) {
        finish();
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
//                //防止点击扫描后点击此按钮
//                stopScan();
                close();
                Intent intent = new Intent(C01S011_002Activity.this, C01S011_003Activity.class);
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
