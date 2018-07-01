package com.icomp.Iswtmv10.v01c01.c01s004;

import android.annotation.SuppressLint;
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
import com.apiclient.constants.OperationEnum;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.vo.AuthCustomerVO;
import com.apiclient.vo.OutApplyVO;
import com.apiclient.vo.SearchOutLiberaryVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.activity.ExceptionProcessCallBack;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

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
    @BindView(R.id.djCode)
    TextView djCode;
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
    // 显示订单
    SearchOutLiberaryVO searchOutLiberaryVO = new SearchOutLiberaryVO();
    // 给服务器传回订单
    DjOutapplyAkp djOutapplyAkp = new DjOutapplyAkp();
    OutApplyVO outApplyVO = new OutApplyVO();

    // 授权信息: lingliao:领料签收；kezhang:科长签收；
    Map<String, AuthCustomer> authCustomerMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s004_003);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        if (paramMap != null) {
            try {
                searchOutLiberaryVOList = (List<SearchOutLiberaryVO>) paramMap.get("searchOutLiberaryVOList");
                searchOutLiberaryVO = (SearchOutLiberaryVO) paramMap.get("searchOutLiberaryVO");
                djOutapplyAkp = (DjOutapplyAkp) paramMap.get("djOutapplyAkp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (searchOutLiberaryVOList != null && searchOutLiberaryVOList.size() > 0) {
            tv01.setText(searchOutLiberaryVO.getName());

            Message message = new Message();
            message.obj = searchOutLiberaryVO;
            //输入授权和扫描授权的handler
            outOrderInfoHandler.sendMessage(message);
        } else {
            initView();
        }
    }

    /**
     * 将上一画面的信息展示到当前画面，进行信息确认
     */
    private void initView() {
        loading.show();

        String jsonStr = "{}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Map<String, String> headsMap = new HashMap<>();
        headsMap.put("impower", OperationEnum.Out_Library.getKey().toString());

        IRequest iRequest = retrofit.create(IRequest.class);
        Call<String> getOrders = iRequest.getOrders(body, headsMap);

        getOrders.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        searchOutLiberaryVOList = jsonToObject(response.body(), List.class, SearchOutLiberaryVO.class);
                        if (searchOutLiberaryVOList == null || searchOutLiberaryVOList.size() == 0) {
                            searchOutLiberaryVOList = new ArrayList<>();
                            createToast(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT);
                        } else {
                            String inpower = response.headers().get("impower");
                            inpowerHandler(inpower);
                        }
                    } else {
//                        createAlertDialog(c01s004_003Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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
//                createAlertDialog(c01s004_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
            }
        });
    }

    public void inpowerHandler(String inpower) throws IOException {
        Map<String, String> inpowerMap = jsonToObject(inpower, Map.class);

        // 判断是否显示提示框
        if ("1".equals(inpowerMap.get("type"))) {
            // 是否需要授权 true为需要授权；false为不需要授权
            is_need_authorization = false;
            setValue();
        } else if ("2".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            exceptionProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    setValue();
                }

                @Override
                public void cancel() {
                    finish();
                }
            });
        } else if ("3".equals(inpowerMap.get("type"))) {
            is_need_authorization = false;
            stopProcessShowDialogAlert(inpowerMap.get("message"), new ExceptionProcessCallBack() {
                @Override
                public void confirm() {
                    finish();
                }

                @Override
                public void cancel() {
                    // 实际上没有用
                    finish();
                }
            });
        } else if ("4".equals(inpowerMap.get("type"))) {
            is_need_authorization = true;
            setValue();
        }
    }

    private void setValue() {
        tv01.setText(searchOutLiberaryVOList.get(0).getName());
        searchOutLiberaryVO = searchOutLiberaryVOList.get(0);

        djOutapplyAkp = searchOutLiberaryVO.getDjOutapplyAkp();

        Message message = new Message();
        message.obj = searchOutLiberaryVO;
        //输入授权和扫描授权的handler
        outOrderInfoHandler.sendMessage(message);
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
//                    createAlertDialog(this, "请选择要出库的单号", Toast.LENGTH_SHORT);
                    createToast(getApplicationContext(), "请选择要出库的单号", Toast.LENGTH_SHORT);
                    return;
                } else {
                    // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                    // 如果是钻头需要走另一个流程
                    if (CuttingToolTypeEnum.dj.getKey().equals(searchOutLiberaryVO.getCuttingToolType()) && CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(searchOutLiberaryVO.getCuttingToolConsumeType())) {
                        char endChar = searchOutLiberaryVO.getMtlno().charAt(searchOutLiberaryVO.getMtlno().length()-1);

                        Intent intent = null;

                        // 最后一位是字母代表是"旧刀"，反之"新刀"
                        if (Pattern.matches("[a-zA-Z]", String.valueOf(endChar))) {
                            intent = new Intent(c01s004_003Activity.this, c01s004_003_1Activity.class);
                        }
                        // "新刀"
                        else {
                            intent = new Intent(c01s004_003Activity.this, c01s004_003_2Activity.class);
                        }

                        // 用于页面之间传值，新方法
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("searchOutLiberaryVOList", searchOutLiberaryVOList);
                        paramMap.put("searchOutLiberaryVO", searchOutLiberaryVO);
                        paramMap.put("djOutapplyAkp", djOutapplyAkp);
                        PARAM_MAP.put(1, paramMap);

                        // 不清空页面之间传递的值
                        intent.putExtra("isClearParamMap", false);
                        startActivity(intent);
                        finish();
                    } else {

                        authorizationWindow("领料授权签收", new AuthorizationWindowCallBack() {
                            @Override
                            public void success(AuthCustomer authCustomer) {
                                authCustomerMap.put("lingliao", authCustomer);

                                authorizationWindow("科长授权签收", new AuthorizationWindowCallBack() {
                                    @Override
                                    public void success(AuthCustomer authCustomer) {
                                        authCustomerMap.put("kezhang", authCustomer);
                                        requestData(authCustomerMap);
                                    }

                                    @Override
                                    public void fail() {}
                                });
                            }

                            @Override
                            public void fail() {}
                        });


                    }
                }
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
                wuliaohao.setText(searchOutLiberaryVO.getDjOutapplyAkp().getMtlno());
                djCode.setText(searchOutLiberaryVO.getDjOutapplyAkp().getDjcode());
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
    private void requestData(Map<String, AuthCustomer> authCustomerMap) {
        try {
            loading.show();

            if (authCustomerMap != null && is_need_authorization) {
                AuthCustomer authCustomerLingliao = authCustomerMap.get("lingliao");
                AuthCustomer authCustomerKezhang = authCustomerMap.get("kezhang");

                AuthCustomerVO llAuthCustomerVO = new AuthCustomerVO();
                AuthCustomerVO kzAuthCustomerVO = new AuthCustomerVO();

                llAuthCustomerVO.setCode(authCustomerLingliao.getCode());
                kzAuthCustomerVO.setCode(authCustomerKezhang.getCode());
                // 领料
                outApplyVO.setLlAuthCustomerVO(llAuthCustomerVO);
                // 科长
                outApplyVO.setKzAuthCustomerVO(kzAuthCustomerVO);
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
//                createAlertDialog(c01s004_003Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                createToast(getApplicationContext(), getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
                return;
            }

            outApplyVO.setApplyno(djOutapplyAkp.getApplyno());//单号
            outApplyVO.setMtlCode(searchOutLiberaryVO.getCuttingtollBusinessCode());//物料号


            String jsonStr = objectToJson(outApplyVO);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
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
//                            createAlertDialog(c01s004_003Activity.this, response.errorBody().string(), Toast.LENGTH_SHORT);
                            createToast(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
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
//                    createAlertDialog(c01s004_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    createToast(getApplicationContext(), getString(R.string.netConnection), Toast.LENGTH_SHORT);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            createToast(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT);
        }
    }

}
