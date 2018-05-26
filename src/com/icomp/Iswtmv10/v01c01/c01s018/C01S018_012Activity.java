package com.icomp.Iswtmv10.v01c01.c01s018;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 厂内修磨页面5
 * Created by FanLL on 2017/7/11.
 */

public class C01S018_012Activity extends CommonActivity {

    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.btnScan)
    Button btnScan;

    //存放rfidString的Map的位置
    private int position = 0;
    //存放rfidString的Map
    private HashMap<Integer, String> rfidStringMap = new HashMap<>();
    //扫描线程
    private scanThread scanThread;
    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_012);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();
    }

    //返回按钮处理--跳转到上一页（厂内修磨菜单页面）
    public void btnReturn(View view) {
        //防止点击扫描后点击此按钮
        stopScan();
        Intent intent = new Intent(this, C01S018_001Activity.class);
        startActivity(intent);
        finish();
    }

    //下一步按钮处理--跳转到下一页面
    public void btnNext(View view) {
        //防止点击扫描后点击此按钮
        stopScan();
        if (ll01.getChildCount() == 0) {
            createAlertDialog(C01S018_012Activity.this, getString(R.string.c01s018_002_001), Toast.LENGTH_LONG);
        } else {
            //遍历页面列表数据传递到下一个页面
            List<C01S018_012.DataBean> c01s018012DataBeanList = new ArrayList<>();
            //遍历每一行
            for (int i = 0; i < ll01.getChildCount(); i++) {
                C01S018_012.DataBean c01s018012DataBean = new C01S018_012.DataBean();
                LinearLayout linearLayout = (LinearLayout) ll01.getChildAt(i);
                //遍历每一行中的每一个控件
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View view1 = linearLayout.getChildAt(j);
                    if (view1 instanceof TextView) {
                        switch (view1.getId()) {
                            case R.id.tvmaterialNumber://合成刀具编码
                                c01s018012DataBean.setSynthesisParametersCode(((TextView) view1).getText().toString().trim());
                                break;
                            case R.id.tvRfidContainerID://Rfid载体ID
                                c01s018012DataBean.setRfidContainerID(((TextView) view1).getText().toString().trim());
                                break;
                            case R.id.tvsingleProductCode://单品编码
                                c01s018012DataBean.setLaserCode(((TextView) view1).getText().toString().trim());
                                break;
                            case R.id.tvauthorizationFlgs://是否需要授权标识
                                c01s018012DataBean.setAuthorizationFlgs(((TextView) view1).getText().toString().trim());
                                break;
                            default:
                        }
                    }
                }
                c01s018012DataBeanList.add(c01s018012DataBean);
            }
            Gson gosn = new Gson();
            Intent intent = new Intent(this, C01S018_013Activity.class);
            intent.putExtra(PARAM, gosn.toJson(c01s018012DataBeanList));
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnScan)
    public void onViewClicked() {
        //扫描方法
        scan();
    }

    //扫描方法
    private void scan() {
        btnScan.setClickable(false);
        //显示扫描弹框的方法
        scanPopupWindow();
        //开启扫描线程
        scanThread = new scanThread();
        scanThread.start();
    }

    //扫描线程
    private class scanThread extends Thread {
        @Override
        public void run() {
            super.run();
            //调用单扫方法
            rfidString = singleScan();
            final Message message = new Message();
            if ("close".equals(rfidString)) {
                btnScan.setClickable(true);
                isCanScan = true;
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                //如果rfidString包含在RFIDList列表中，重新启动扫描线程
                if (rfidStringMap.containsValue(rfidString)) {
                    //重复扫描的Handler
                    scanAgainHandler.sendMessage(message);
                } else {
                    //调用接口，扫码查询一体刀信息，判断当前一体刀是否已经修磨
                    IRequest iRequest = retrofit.create(IRequest.class);
                    Call<String> getOneKnifeInfo = iRequest.getOneKnifeInfo(rfidString);
                    getOneKnifeInfo.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response response) {
                            try {
                                popupWindow.dismiss();
                                String json = response.body().toString();
                                JSONObject jsonObject = new JSONObject(json);
                                Gson gson = new Gson();
                                if (jsonObject.getBoolean("success")) {
                                    final C01S018_012 c01s018012 = gson.fromJson(json, C01S018_012.class);
                                    if (ZERO.equals(c01s018012.getData().getCode())) {
                                        //需要授权
                                        AlertDialog.Builder builder = new AlertDialog.Builder(C01S018_012Activity.this);
                                        builder.setTitle(R.string.prompt);
                                        builder.setMessage(R.string.c01s018_012_004);
                                        builder.setCancelable(false);
                                        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                scanAuthorizationAddARow(c01s018012);
                                            }
                                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int a) {
                                                dialogInterface.dismiss();
                                                btnScan.setClickable(true);
                                                isCanScan = true;
                                            }
                                        }).show();
                                    } else {
                                        //将数据放到表格上
                                        scanAddARow(c01s018012);
                                    }
                                } else {
                                    createAlertDialog(C01S018_012Activity.this, jsonObject.getString("message"), Toast.LENGTH_LONG);
                                    btnScan.setClickable(true);
                                    isCanScan = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                if (popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                }
                            }
                        }

                        @Override
                        public void _onFailure(Throwable t) {
                            createAlertDialog(C01S018_012Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                            popupWindow.dismiss();
                            btnScan.setClickable(true);
                            isCanScan = true;
                        }
                    });
                }
            }
        }
    }

    //重复扫描的Handler
    @SuppressLint("HandlerLeak")
    private Handler scanAgainHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            popupWindow.dismiss();
            createAlertDialog(C01S018_012Activity.this, getString(R.string.c01s018_012_002), Toast.LENGTH_LONG);
            btnScan.setClickable(true);
            isCanScan = true;
        }
    };

    //将数据放到表格上
    private void scanAddARow(C01S018_012 c01s018012) {
        //加一行
        final View addRow = LayoutInflater.from(this).inflate(R.layout.item_c01s018_012, null);
        TextView tvmaterialNumber = (TextView) addRow.findViewById(R.id.tvmaterialNumber);//合成刀具编码
        tvmaterialNumber.setText(exChangeBig(c01s018012.getData().getSynthesisParametersCode()));
        TextView tvsingleProductCode = (TextView) addRow.findViewById(R.id.tvsingleProductCode);//单品编码
        tvsingleProductCode.setText(exChangeBig(c01s018012.getData().getLaserCode()));
        TextView tvRfidContainerID = (TextView) addRow.findViewById(R.id.tvRfidContainerID);//Rfid载体ID
        tvRfidContainerID.setText(c01s018012.getData().getRfidContainerID());
        final TextView tvRfidString = (TextView) addRow.findViewById(R.id.tvRfidString);//RfidString
        tvRfidString.setTag(position);
        TextView tvauthorizationFlgs = (TextView) addRow.findViewById(R.id.tvauthorizationFlgs);//是否需要授权标识
        tvauthorizationFlgs.setText(ZERO);
        position++;
        rfidStringMap.put((Integer) tvRfidString.getTag(), rfidString);
        ImageView delete = (ImageView) addRow.findViewById(R.id.iv_01);//减号
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout child = (LinearLayout) ll01.getChildAt(ll01.getChildCount() - 1);
                ll01.removeView(child);
                rfidStringMap.remove(tvRfidString.getTag());
            }
        });
        //将加入条目显示在页面上
        ll01.addView(addRow);
        btnScan.setClickable(true);
        isCanScan = true;
    }

    //将需要授权的一体刀数据放到表格上（再次修磨）
    private void scanAuthorizationAddARow(C01S018_012 c01s018012) {
        //加一行
        final View addRow = LayoutInflater.from(this).inflate(R.layout.item_c01s018_012, null);
        TextView tvmaterialNumber = (TextView) addRow.findViewById(R.id.tvmaterialNumber);//合成刀具编码
        TextView tvsingleProductCode = (TextView) addRow.findViewById(R.id.tvsingleProductCode);//单品编码
        tvsingleProductCode.setText(exChangeBig(c01s018012.getData().getLaserCode()));
        tvmaterialNumber.setText(exChangeBig(c01s018012.getData().getSynthesisParametersCode()));
        TextView tvRfidContainerID = (TextView) addRow.findViewById(R.id.tvRfidContainerID);//Rfid载体ID
        tvRfidContainerID.setText(c01s018012.getData().getRfidContainerID());
        final TextView tvRfidString = (TextView) addRow.findViewById(R.id.tvRfidString);//RfidString
        tvRfidString.setTag(position);
        TextView tvauthorizationFlgs = (TextView) addRow.findViewById(R.id.tvauthorizationFlgs);//是否需要授权标识
        tvauthorizationFlgs.setText(ONE);
        position++;
        rfidStringMap.put((Integer) tvRfidString.getTag(), rfidString);
        ImageView delete = (ImageView) addRow.findViewById(R.id.iv_01);//减号
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout child = (LinearLayout) ll01.getChildAt(ll01.getChildCount() - 1);
                ll01.removeView(child);
                rfidStringMap.remove(tvRfidString.getTag());
            }
        });
        //将加入条目显示在页面上
        ll01.addView(addRow);
        btnScan.setClickable(true);
        isCanScan = true;
    }

    //重写键盘上扫描按键的方法
    @Override
    protected void btnScan() {
        super.btnScan();
        if(isCanScan) {
            isCanScan = false;
        } else {
            return;
        }
        //扫描方法
        scan();
    }

}
