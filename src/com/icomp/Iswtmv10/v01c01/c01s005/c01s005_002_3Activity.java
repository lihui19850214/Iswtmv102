package com.icomp.Iswtmv10.v01c01.c01s005;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.OperationEnum;
import com.apiclient.constants.ScrapReasonEnum;
import com.apiclient.constants.ScrapStateEnum;
import com.apiclient.pojo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s005.modul.TongDaoModul;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.GetItemHeight;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

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

    private List<TongDaoModul> jsonList = new ArrayList<>();


    List<CuttingToolsScrap> cuttingToolsScrapList = new ArrayList<>();


    // 报废原因下拉列表所有数据
    List<ScrapReasonEnum> scrapReasonList = new ArrayList<>();
    // 当前选择的报废原因
    private int scrap_reason_posttion;


    // 报废状态下拉列表所有数据 key=name
    private Map<String, String> scrapStatusMap = new HashMap<>();

    // 根据 rfid 查询的数据
    private Map<String, CuttingToolsScrap> rfidToMap = new HashMap<>();
    // 根据才料号查询的数据
    private Map<String, CuttingToolsScrap> materialNumToMap = new HashMap<>();

    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s005_002_3);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        //存储所有报废状态，key=name
        for (ScrapStateEnum scrapStateEnum : ScrapStateEnum.values()){
            scrapStatusMap.put(scrapStateEnum.getKey(), scrapStateEnum.getName());
        }

        //存储所有报废原因，下拉列表
        for (ScrapReasonEnum scrapReasonEnum : ScrapReasonEnum.values()){
            scrapReasonList.add(scrapReasonEnum);
        }

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            rfidToMap = (Map<String, CuttingToolsScrap>) paramMap.get("rfidToMap");
            materialNumToMap = (Map<String, CuttingToolsScrap>) paramMap.get("materialNumToMap");
            cuttingToolsScrapList = (List<CuttingToolsScrap>) paramMap.get("cuttingToolsScrapList");

            for (CuttingToolsScrap cuttingToolsScrap : cuttingToolsScrapList) {
                CuttingTool cuttingTool = cuttingToolsScrap.getCuttingTool();

                if (cuttingTool.getCuttingToolBindList() == null || cuttingTool.getCuttingToolBindList().size() == 0) {
                    addLayout(cuttingToolsScrap.getMaterialNum(), scrapStatusMap.get(cuttingToolsScrap.getCause()), cuttingToolsScrap.getCount() + "");
                } else {
                    CuttingToolBind cuttingToolBind = cuttingTool.getCuttingToolBindList().get(0);
                    addLayout(cuttingToolBind.getCuttingTool().getBusinessCode(), cuttingToolBind.getBladeCode(), cuttingToolsScrap.getCount() + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
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
                authorizationWindow(new AuthorizationWindowCallBack() {
                    @Override
                    public void success(AuthCustomer authCustomer) {
                        requestData(authCustomer);
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
     */
    private void addLayout(String cailiao, String laserCode, String num) {
        View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_yiti_daojubaofei_static, null);

        TextView tvCaiLiao = (TextView) mLinearLayout.findViewById(R.id.tvCailiao);
        TextView tvsingleProductCode = (TextView) mLinearLayout.findViewById(R.id.tvsingleProductCode);
        TextView tvNum = (TextView) mLinearLayout.findViewById(R.id.tvNum);

        tvCaiLiao.setText(cailiao);
        tvsingleProductCode.setText(laserCode);
        tvNum.setText(num);

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
    public void showPopupWindow(View view2) {
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
                tv01.setText(scrapReasonList.get(i).getName());
                //TODO 需要获取下拉列表值

                popupWindow.dismiss();
            }
        });

//        popupWindow.showAsDropDown(ll01);

        int windowPos[] = calculatePopWindowPos(ll01, view, listView);
        popupWindow.showAtLocation(view, Gravity.TOP, windowPos[0], windowPos[1]);
    }

    class ScrapStatusAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return scrapReasonList.size();
        }

        @Override
        public Object getItem(int i) {
            return scrapReasonList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s005_002_3Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(scrapReasonList.get(i).getName());
            return view1;
        }
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    private static int[] calculatePopWindowPos(final View anchorView, final View contentView, ListView listView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();
        // 获取屏幕的高宽
        final int screenHeight = GetItemHeight.getScreenHeight(anchorView.getContext());
        final int screenWidth = GetItemHeight.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = getTotalHeightofListView(listView);
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = 0;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = 0;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    public static int getTotalHeightofListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
            Log.d("数据" + i, String.valueOf(totalHeight));
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        Log.d("数据", "listview总高度="+ params.height);
        listView.setLayoutParams(params);
        listView.requestLayout();
        return totalHeight;
    }


    //提交添报废刀具
    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();

            Map<String, String> headsMap = new HashMap<>();

            // 授权信息集合
            List<ImpowerRecorder> impowerRecorderList = new ArrayList<>();
            // 授权信息
            ImpowerRecorder impowerRecorder = new ImpowerRecorder();

            try {
                // 需要授权信息
                if (is_need_authorization && authCustomer != null) {
                    //设定用户访问信息
                    @SuppressLint("WrongConstant")
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                    String userInfoJson = sharedPreferences.getString("loginInfo", null);

                    AuthCustomer customer = jsonToObject(userInfoJson, AuthCustomer.class);

                    Set<String> rfids = rfidToMap.keySet();
                    for (String rfid : rfids) {
                        CuttingToolsScrap cuttingToolsScrap = rfidToMap.get(rfid);

                        CuttingToolBind cuttingToolBind = cuttingToolsScrap.getCuttingTool().getCuttingToolBindList().get(0);


                        impowerRecorder = new ImpowerRecorder();

                        // ------------ 授权信息 ------------
                        impowerRecorder.setToolCode(cuttingToolBind.getCuttingTool().getBusinessCode());// 合成刀编码
                        impowerRecorder.setRfidLasercode(authCustomer.getRfidContainer().getLaserCode());// rfid标签
                        impowerRecorder.setOperatorUserCode(customer.getCode());//操作者code
                        impowerRecorder.setImpowerUser(authCustomer.getCode());//授权人code
                        // TODO 缺少报废，暂时先不报错
                        impowerRecorder.setOperatorKey(OperationEnum.Cutting_tool_Bind.getKey().toString());//操作key

//                impowerRecorder.setOperatorUserName(URLEncoder.encode(authCustomer.getName(),"utf-8"));//操作者姓名
//                impowerRecorder.setImpowerUserName(URLEncoder.encode(authorizationList.get(0).getName(),"utf-8"));//授权人名称
//                impowerRecorder.setOperatorValue(URLEncoder.encode(OperationEnum.SynthesisCuttingTool_Exchange.getName(),"utf-8"));//操作者code

                        impowerRecorderList.add(impowerRecorder);
                    }
                }
                headsMap.put("impower", objectToJson(impowerRecorderList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                createAlertDialog(c01s005_002_3Activity.this, getString(R.string.loginInfoError), Toast.LENGTH_SHORT);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
            }

            IRequest iRequest = retrofit.create(IRequest.class);

            for (CuttingToolsScrap cuttingToolsScrap : cuttingToolsScrapList) {
                cuttingToolsScrap.setCause(scrapReasonList.get(scrap_reason_posttion).getKey());
            }

            String jsonStr = objectToJson(cuttingToolsScrapList);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> addScrap = iRequest.addScrap(body, headsMap);

            addScrap.enqueue(new MyCallBack<String>() {
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
        } catch (Exception e) {
            e.printStackTrace();
            if (null != loading && loading.isShowing()) {
                loading.dismiss();
            }
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }


// --------------------以下代码没用，暂时保留---------------------
    /**
     * 遍历所有数据并转化为json
     */
    private String bianliAndToJson() throws JsonProcessingException {
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

        return objectToJson(jsonList);
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
