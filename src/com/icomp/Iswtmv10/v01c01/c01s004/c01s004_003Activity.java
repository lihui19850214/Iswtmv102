package com.icomp.Iswtmv10.v01c01.c01s004;
/**
 * 刀具出库页面1
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.OutApplyVO;
import com.apiclient.vo.RfidContainerVO;
import com.apiclient.vo.SearchOutLiberaryVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class c01s004_003Activity extends CommonActivity {

    @BindView(R.id.btnReturn)
    Button mBtnReturn;
    @BindView(R.id.btnNext)
    Button mBtnSign;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.wuliaohao)
    TextView wuliaohao;
    @BindView(R.id.cailiaohao)
    TextView cailiaohao;
    @BindView(R.id.xinghaoguige)
    TextView xinghaoguige;
    @BindView(R.id.wuliaomingcheng)
    TextView wuliaomingcheng;
    @BindView(R.id.shengchanxian)
    TextView shengchanxian;
    @BindView(R.id.gongwei)
    TextView gongwei;
    @BindView(R.id.yaohuoshuliang)
    TextView yaohuoshuliang;
    @BindView(R.id.daojuleixing)
    TextView daojuleixing;
    @BindView(R.id.xiumofangshi)
    TextView xiumofangshi;

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_02)
    LinearLayout ll02;


    private Retrofit retrofit;

    // 出库订单
    List<SearchOutLiberaryVO> searchOutLiberaryVOList = new ArrayList<>();
    SearchOutLiberaryVO searchOutLiberaryVO = new SearchOutLiberaryVO();
    DjOutapplyAkp djOutapplyAkp = new DjOutapplyAkp();
    OutApplyVO outApplyVO = new OutApplyVO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s004_003);
        ButterKnife.bind(this);

//        //创建Activity时，添加到List进行管理
//        SysApplication.getInstance().addActivity(this);

        retrofit = RetrofitSingle.newInstance();

        initView();
    }

    /**
     * 将上一画面的信息展示到当前画面，进行信息确认
     */
    private void initView() {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        String jsonStr = "{}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> getOrders = iRequest.getOrders(body);

        getOrders.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<SearchOutLiberaryVO>>() {
                        }.getType();
                        searchOutLiberaryVOList = gson.fromJson(response.body(), type);
                    } else {
                        createAlertDialog(c01s004_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    loading.dismiss();
                }
            }

            @Override
            public void _onFailure(Throwable t) {
                createAlertDialog(c01s004_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                loading.dismiss();
            }
        });
    }

    @OnClick({R.id.btnReturn, R.id.btnNext, R.id.ll_02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                finish();
                break;
            case R.id.btnNext:
                //如果取TEXT值则可以直接取:outOrder.getSelectedItem.ToString()或者:((CItem)outOrder.getSelectedItem).getValue();
                String orderText = tv01.getText().toString();

                if (orderText == null || "".equals(orderText)) {
                    createAlertDialog(this, "请选择要出库的单号", Toast.LENGTH_SHORT);
                    return;
                }

//                showDialogAlert("出库订单：" + orderText);

                outApplyVO = new OutApplyVO();

                authorizationWindow(2, new AuthorizationWindowCallBack() {
                    @Override
                    public void success(List<AuthCustomer> authorizationList) {
                        requestData(authorizationList);
                    }

                    @Override
                    public void fail() {

                    }
                });
                break;
            case R.id.ll_02:

                showPopupWindow();

                break;
            default:
        }
    }


    //显示出库单号列表
    private void showPopupWindow() {
        View view = LayoutInflater.from(c01s004_003Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
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
                tv01.setText(searchOutLiberaryVOList.get(i).getName());
                searchOutLiberaryVO = searchOutLiberaryVOList.get(i);

                djOutapplyAkp = searchOutLiberaryVO.getDjOutapplyAkp();

                Message message = new Message();
                message.obj = searchOutLiberaryVO;
                //输入授权和扫描授权的handler
                outOrderInfoHandler.sendMessage(message);

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll02);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchOutLiberaryVOList.size();
        }

        @Override
        public Object getItem(int i) {
            return searchOutLiberaryVOList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s004_003Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(searchOutLiberaryVOList.get(i).getName());
            return view1;
        }
    }

    //显示出库单号详细信息
    @SuppressLint("HandlerLeak")
    Handler outOrderInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SearchOutLiberaryVO searchOutLiberaryVO = (SearchOutLiberaryVO) msg.obj;

            wuliaohao.setText(searchOutLiberaryVO.getMtlno());
            cailiaohao.setText(searchOutLiberaryVO.getCuttingtollBusinessCode());
            xinghaoguige.setText(searchOutLiberaryVO.getSpecifications());
            wuliaomingcheng.setText(searchOutLiberaryVO.getName());
            shengchanxian.setText(searchOutLiberaryVO.getProductline());
            gongwei.setText(searchOutLiberaryVO.getLocation());
            yaohuoshuliang.setText(searchOutLiberaryVO.getUnitqty());
            daojuleixing.setText(searchOutLiberaryVO.getCuttingToolType());
            xiumofangshi.setText(searchOutLiberaryVO.getGrinding());
        }
    };

    /**
     * 将出库单号数据提交
     */
    private void requestData(List<AuthCustomer> authorizationList) {
        loading.show();
        // 领料
        outApplyVO.setLinglOperatorRfidCode(authorizationList.get(0).getRfidContainer().getLaserCode());
        // 科长
        outApplyVO.setKezhangRfidCode(authorizationList.get(1).getRfidContainer().getLaserCode());


        IRequest iRequest = retrofit.create(IRequest.class);

        outApplyVO.setDjOutapplyAkp(djOutapplyAkp);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(outApplyVO);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> outApply = iRequest.outApply(body);
        outApply.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        Intent intent = new Intent(c01s004_003Activity.this, c01s004_004Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        createAlertDialog(c01s004_003Activity.this, response.errorBody().string(), Toast.LENGTH_SHORT);
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
                createAlertDialog(c01s004_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
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
                outApplyVO = new OutApplyVO();

                authorizationWindow(2, new AuthorizationWindowCallBack() {
                    @Override
                    public void success(List<AuthCustomer> authorizationList) {
                        requestData(authorizationList);
                    }

                    @Override
                    public void fail() {

                    }
                });
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
