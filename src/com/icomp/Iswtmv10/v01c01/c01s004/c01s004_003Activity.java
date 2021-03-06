package com.icomp.Iswtmv10.v01c01.c01s004;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.constants.GrindingEnum;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.vo.OutApplyVO;
import com.apiclient.vo.SearchOutLiberaryVO;
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
import java.util.*;

/**
 * 刀具出库页面1
 */
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
                        searchOutLiberaryVOList = jsonToObject(response.body(), List.class, SearchOutLiberaryVO.class);
                        if (searchOutLiberaryVOList == null || searchOutLiberaryVOList.size() == 0) {
                            searchOutLiberaryVOList = new ArrayList<>();
                            Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        createAlertDialog(c01s004_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                createAlertDialog(c01s004_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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

                // 需要授权
                is_need_authorization = true;

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

            // 不等于 null 再赋值
            if (searchOutLiberaryVO != null) {
                wuliaohao.setText(searchOutLiberaryVO.getMtlno());
                cailiaohao.setText(searchOutLiberaryVO.getCuttingtollBusinessCode());
                xinghaoguige.setText(searchOutLiberaryVO.getSpecifications());
                wuliaomingcheng.setText(searchOutLiberaryVO.getName());
                shengchanxian.setText(searchOutLiberaryVO.getProductline());
                gongwei.setText(searchOutLiberaryVO.getLocation());
                yaohuoshuliang.setText(searchOutLiberaryVO.getUnitqty());

                String type = "";
                // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                if (CuttingToolTypeEnum.dj.getKey().equals(searchOutLiberaryVO.getCuttingToolType())) {
                    if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(searchOutLiberaryVO.getCuttingToolConsumeType())) {
                        type = CuttingToolConsumeTypeEnum.griding_zt.getName();
                    } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(searchOutLiberaryVO.getCuttingToolConsumeType())) {
                        type = CuttingToolConsumeTypeEnum.griding_dp.getName();
                    } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(searchOutLiberaryVO.getCuttingToolConsumeType())) {
                        type = CuttingToolConsumeTypeEnum.single_use_dp.getName();
                    } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(searchOutLiberaryVO.getCuttingToolConsumeType())) {
                        type = CuttingToolConsumeTypeEnum.other.getName();
                    }
                } else if (CuttingToolTypeEnum.fj.getKey().equals(searchOutLiberaryVO.getCuttingToolType())) {
                    type = CuttingToolTypeEnum.fj.getName();
                } else if (CuttingToolTypeEnum.pt.getKey().equals(searchOutLiberaryVO.getCuttingToolType())) {
                    type = CuttingToolTypeEnum.pt.getName();
                } else if (CuttingToolTypeEnum.other.getKey().equals(searchOutLiberaryVO.getCuttingToolType())) {
                    type = CuttingToolTypeEnum.other.getName();
                }
                daojuleixing.setText(type);

                String grinding = "";
                if (GrindingEnum.inside.getKey().equals(searchOutLiberaryVO.getGrinding())) {
                    grinding = GrindingEnum.inside.getName();
                } else if (GrindingEnum.outside.getKey().equals(searchOutLiberaryVO.getGrinding())) {
                    grinding = GrindingEnum.outside.getName();
                } else if (GrindingEnum.outside_tuceng.getKey().equals(searchOutLiberaryVO.getGrinding())) {
                    grinding = GrindingEnum.outside_tuceng.getName();
                }
                xiumofangshi.setText(grinding);
            }
        }
    };

    /**
     * 将出库单号数据提交
     */
    private void requestData(List<AuthCustomer> authorizationList) {
        try {
            loading.show();

            if (authorizationList != null && authorizationList.size() > 1) {
                // 领料
                outApplyVO.setLinglOperatorRfidCode(authorizationList.get(0).getRfidContainer().getLaserCode());
                // 科长
                outApplyVO.setKezhangRfidCode(authorizationList.get(1).getRfidContainer().getLaserCode());
            } else {
                createAlertDialog(c01s004_003Activity.this, getString(R.string.authorizedNumberError), Toast.LENGTH_SHORT);
                return;
            }

            try {
                //设定用户访问信息
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                String userInfoJson = sharedPreferences.getString("loginInfo", null);

                AuthCustomer authCustomer = jsonToObject(userInfoJson, AuthCustomer.class);
                outApplyVO.setKuguanOperatorCode(authCustomer.getCode());// 操作者code
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(c01s004_003Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            }

            IRequest iRequest = retrofit.create(IRequest.class);

            outApplyVO.setDjOutapplyAkp(djOutapplyAkp);

            String jsonStr = objectToJson(outApplyVO);
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
                    createAlertDialog(c01s004_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
                is_need_authorization = true;
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
