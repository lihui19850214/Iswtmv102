package com.icomp.Iswtmv10.v01c01.c01s012;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.vo.ChangeRFIDVO;
import com.apiclient.vo.CuttingToolBindVO;
import com.apiclient.vo.RfidContainerVO;
import com.google.gson.Gson;
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


/**
 * 标签置换页面1
 */
public class C01S012_001Activity extends CommonActivity {

//    @BindView(R.id.et_01)
    EditText et01;
//    @BindView(R.id.btnReturn)
    Button btnReturn;
//    @BindView(R.id.btnSearch)
    Button btnSearch;

    //调用接口
    private Retrofit retrofit;

    // 刀身码
    String toolCode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_c01s012_001);
        setContentView(R.layout.aaaa);
//        ButterKnife.bind(this);

        //调用接口
//        retrofit = RetrofitSingle.newInstance();

        //将输入的材料号自动转化为大写
//        et01.setTransformationMethod(new AllCapTransformationMethod());
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        //防止点击扫描后点击此按钮
        finish();
    }

    /**
     * 根据刀身码查询信息
     * @param view
     */
    public void btnSearch(View view) {
        String changeRFID = et01.getText().toString().trim();
        if ("".equals(changeRFID)) {
            createAlertDialog(C01S012_001Activity.this, "请输入要置换标签刀具的刀身码", Toast.LENGTH_LONG);
            return;
        } else {
            try {
                loading.show();
                IRequest iRequest = retrofit.create(IRequest.class);

                // TODO 参数是什么
                CuttingToolBindVO cuttingToolBindVO = new CuttingToolBindVO();
                cuttingToolBindVO.setBladeCode(changeRFID);


                String jsonStr = objectToJson(cuttingToolBindVO);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                // TODO 接口是什么
                Call<String> changeRFIDForToll = iRequest.changeRFIDForToll(body);

                changeRFIDForToll.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                // TODO 返回值是什么
                                CuttingToolBind cuttingToolBind = jsonToObject(response.body(), CuttingToolBind.class);
                                if (cuttingToolBind != null) {
                                    toolCode = cuttingToolBind.getBladeCode();
                                    showDialogAlert("若为 " + cuttingToolBind.getBladeCode() + " 置换标签，请点击确定按钮并扫描一个标签");
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                createAlertDialog(C01S012_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                        createAlertDialog(C01S012_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
    }

    //置换按钮处理
    public void btnSubmit() {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            ChangeRFIDVO changeRFIDVO = new ChangeRFIDVO();
            changeRFIDVO.setRfidCode(rfidString);
            changeRFIDVO.setToolCode(toolCode);

            String jsonStr = objectToJson(changeRFIDVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> changeRFIDForToll = iRequest.changeRFIDForToll(body);

            changeRFIDForToll.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            //跳转到成功详细页面
                            Intent intent = new Intent(C01S012_001Activity.this, C01S012_002Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            createAlertDialog(C01S012_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S012_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
                scan();
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

    /**
     * ----------------------扫描开始----------------------
     **/
    //扫描线程
    private scanThread scanThread;

    /**
     * 扫描
     */
    //扫描方法
    private void scan() {
        if (rfidWithUHF.startInventoryTag((byte) 0, (byte) 0)) {
            isCanScan = false;
            btnReturn.setClickable(false);
            btnSearch.setClickable(false);
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
                btnReturn.setClickable(true);
                btnSearch.setClickable(true);
                isCanScan = true;
                Message message = new Message();
                overtimeHandler.sendMessage(message);
            } else if (null != rfidString && !"close".equals(rfidString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnReturn.setClickable(true);
                        btnSearch.setClickable(true);
                        isCanScan = true;
                        if (null != popupWindow && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        loading.show();
                    }
                });

                try {
                    //调用接口，查询合成刀具组成信息
                    IRequest iRequest = retrofit.create(IRequest.class);

                    RfidContainerVO rfidContainerVO = new RfidContainerVO();
                    rfidContainerVO.setLaserCode(rfidString);

                    String jsonStr = objectToJson(rfidContainerVO);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                    Call<String> queryRFIDForUnConfig = iRequest.queryRFIDForUnConfig(body);
                    queryRFIDForUnConfig.enqueue(new MyCallBack<String>() {
                        @Override
                        public void _onResponse(Response<String> response) {
                            try {
                                if (response.raw().code() == 200) {
                                    //TODO 业务处理
                                    btnSubmit();
                                } else {
                                    final String errorStr = response.errorBody().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAlertDialog(C01S012_001Activity.this, errorStr, Toast.LENGTH_LONG);
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
                                    createAlertDialog(C01S012_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

}
