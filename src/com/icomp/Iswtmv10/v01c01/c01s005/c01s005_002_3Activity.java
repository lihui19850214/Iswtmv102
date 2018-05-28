package com.icomp.Iswtmv10.v01c01.c01s005;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolsScrap;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s005.modul.TongDaoModul;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刀具报废页面2
 */
public class c01s005_002_3Activity extends CommonActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llContainer)
    LinearLayout mLlContainer;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.activity_c01s005_002_3)
    LinearLayout activityC01s0050023;

    private int position = 0;
    private List<TongDaoModul> jsonList = new ArrayList<>();


    List<CuttingToolsScrap> cuttingToolsScrap = new ArrayList<>();



    List<String> scrapStatusList = new ArrayList<>();


    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s005_002_3);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        cuttingToolsScrap = (List<CuttingToolsScrap>) paramMap.get("cuttingToolsScrapList");

        for (CuttingToolsScrap cuttingToolsScrap : cuttingToolsScrap) {
            addLayout(cuttingToolsScrap);
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                Intent intent = new Intent(this, c01s005_002_2Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnNext:
                authorizationWindow(1, new AuthorizationWindowCallBack() {
                    @Override
                    public void success(List<AuthCustomer> authorizationList) {
                        requestData(authorizationList);
                    }

                    @Override
                    public void fail() {

                    }
                });
                break;
        }
    }


    /**
     * 添加布局
     * @param cuttingToolsScrap
     */
    private void addLayout(CuttingToolsScrap cuttingToolsScrap) {
        View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_yiti_daojubaofei_static, null);
        TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);


        tvCaiLiao.setText(cuttingToolsScrap.getMaterialNum());
        tvsingleProductCode.setText(cuttingToolsScrap.getCuttingTool().getBusinessCode());
        tvNum.setText(cuttingToolsScrap.getCount());

        mLlContainer.addView(mLinearLayout);
    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        Intent intent2 = getIntent();
//        if (intent2 == null) {
//            return;
//        } else {
//            Bundle bundle = intent2.getExtras();
//            if (bundle == null) {
//                return;
//            }
//            boolean isClear = bundle.getBoolean("isClear", false);
//            if (isClear) {
//                mLlContainer.removeAllViews();
//            }
//        }
//    }

    /**
     * 报废原因下拉框
     */
    public void showPopupWindow() {
        View view = LayoutInflater.from(c01s005_002_3Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        ScrapStatusAdapter myAdapter = new ScrapStatusAdapter();
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
                tv01.setText(scrapStatusList.get(i));
                //TODO 需要获取下拉列表值

                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    class ScrapStatusAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return scrapStatusList.size();
        }

        @Override
        public Object getItem(int i) {
            return scrapStatusList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s005_002_3Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(scrapStatusList.get(i));
            return view1;
        }
    }


    //提交添报废刀具
    private void requestData(List<AuthCustomer> authorizationList) {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        Gson gson = new Gson();

        //TODO 授权信息不知道放哪
//        authorizationList.get(0);

        String jsonStr = gson.toJson(cuttingToolsScrap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> addOutsideFactory = iRequest.addOutsideFactory(body, new HashMap<String, String>());

        addOutsideFactory.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {

                        //跳转到成功详细页面
                        Intent intent = new Intent(c01s005_002_3Activity.this, c01s005_002_4Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        createAlertDialog(c01s005_002_3Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    loading.dismiss();
                }
            }

            @Override
            public void _onFailure(Throwable t) {
                createAlertDialog(c01s005_002_3Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                loading.dismiss();
            }
        });
    }


// --------------------以下代码没用，暂时保留---------------------
    /**
     * 遍历所有数据并转化为json
     */
    private String bianliAndToJson() {
        jsonList.clear();
        if (mLlContainer.getChildCount() == 0) {
            return null;
        }
        for (int k = 0; k < mLlContainer.getChildCount(); k++) {
            LinearLayout mDataLin = (LinearLayout) mLlContainer.getChildAt(k);
            for (int i = 0; i < mDataLin.getChildCount(); i++) {
                View child = mDataLin.getChildAt(i);
                if (child instanceof LinearLayout) {
                    int child2Coutn = ((LinearLayout) child).getChildCount();
                    TongDaoModul c = new TongDaoModul();
                    for (int j = 0; j < child2Coutn; j++) {
                        View child2 = ((LinearLayout) child).getChildAt(j);
                        if (child2 instanceof TextView) {
                            switch (child2.getId()) {
                                case R.id.tvCailiao:
                                    c.setCaiLiao(((TextView) child2).getText().toString());
                                    break;
                                case R.id.tvBaofeishuliang:
                                    c.setGroupNum(((TextView) child2).getText().toString());
                                    break;
                                case R.id.tvsynthesisParametersCode:
                                    c.setSynthesisParametersCode(((TextView) child2).getText().toString());
                                    break;
                                case R.id.tvrFID:
                                    c.setrFID(((TextView) child2).getText().toString());
                                    break;
                            }
                        }
                    }
                    jsonList.add(c);

                }

            }
        }
        Gson gson = new Gson();
        return gson.toJson(jsonList);
    }

    class MyAdapter extends BaseAdapter {
        private List<TongDaoModul> list;

        public MyAdapter(List<TongDaoModul> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(c01s005_002_3Activity.this).inflate(R.layout.item_dialog_list, null);
            CheckBox c = (CheckBox) view.findViewById(R.id.cb);
            TextView tvCaiLiao = (TextView) view.findViewById(R.id.tvCaiLiao);
            TextView tvGroupNum = (TextView) view.findViewById(R.id.tvGroupNum);
            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setCheck(isChecked);
                }
            });
            tvCaiLiao.setText(list.get(position).getCaiLiao());
            tvGroupNum.setText(list.get(position).getGroupNum());
            return view;
        }

        public List<TongDaoModul> getList() {
            return list;
        }
    }
}
