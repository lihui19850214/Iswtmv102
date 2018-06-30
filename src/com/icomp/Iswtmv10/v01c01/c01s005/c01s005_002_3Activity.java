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
import com.apiclient.constants.ScrapCaseEnum;
import com.apiclient.constants.ScrapReasonEnum;
import com.apiclient.constants.ScrapStateEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.ScrapVO;
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


    // 报废原因下拉列表所有数据
    List<ScrapCaseEnum> scrapCaseEnumList = new ArrayList<>();
    // 当前选择的报废原因
    ScrapCaseEnum scrapCaseEnum = null;


    // 根据 rfid 查询的数据
    private Map<String, CuttingToolBind> rfidToMap = new HashMap<>();
    // 根据物料号对应刀身码或状态
    private Map<String, String> businessCodeToBladeCodeMap = new HashMap<>();

    List<ScrapVO> scrapVOList = new ArrayList<>();

    ScrapBO scrapBO = new ScrapBO();

    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s005_002_3);
        ButterKnife.bind(this);

        retrofit = RetrofitSingle.newInstance();

        //存储所有报废原因，下拉列表
        for (ScrapCaseEnum scrapCaseEnum : ScrapCaseEnum.values()){
            scrapCaseEnumList.add(scrapCaseEnum);
        }

        try {
            Map<String, Object> paramMap = PARAM_MAP.get(1);
            scrapBO = (ScrapBO) paramMap.get("scrapBO");
            rfidToMap = (Map<String, CuttingToolBind>) paramMap.get("rfidToMap");
            scrapVOList = (List<ScrapVO>) paramMap.get("scrapVOList");
            businessCodeToBladeCodeMap = (Map<String, String>) paramMap.get("businessCodeToBladeCodeMap");

            for (ScrapVO scrapVO : scrapVOList) {
                String bl = businessCodeToBladeCodeMap.get(scrapVO.getCuttingToolVO().getBusinessCode());

                addLayout(scrapVO.getCuttingToolVO().getBusinessCode(), bl, scrapVO.getCount().toString());
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
                if (scrapCaseEnum != null) {
                    authorizationWindow(new AuthorizationWindowCallBack() {
                        @Override
                        public void success(AuthCustomer authCustomer) {
                            requestData(authCustomer);
                        }

                        @Override
                        public void fail() {

                        }
                    });
                } else {
                    createAlertDialog(c01s005_002_3Activity.this, "请选择报废原因", Toast.LENGTH_LONG);
                }
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
        if (laserCode != null && !"".equals(laserCode) && !"-".equals(laserCode) && (laserCode.indexOf("-") >= 0)) {
            tvsingleProductCode.setText(laserCode.split("-")[1]);
        } else {
            tvsingleProductCode.setText(laserCode);
        }
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
        ScrapStatusAdapter scrapStatusAdapter = new ScrapStatusAdapter();
        listView.setAdapter(scrapStatusAdapter);
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
                tv01.setText(scrapCaseEnumList.get(i).getName());
                scrapCaseEnum = scrapCaseEnumList.get(i);

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
            return scrapCaseEnumList.size();
        }

        @Override
        public Object getItem(int i) {
            return scrapCaseEnumList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(c01s005_002_3Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(scrapCaseEnumList.get(i).getName());
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
            //Log.d("数据" + i, String.valueOf(totalHeight));
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        //Log.d("数据", "listview总高度="+ params.height);
        listView.setLayoutParams(params);
        listView.requestLayout();
        return totalHeight;
    }


    //提交添报废刀具
    private void requestData(AuthCustomer authCustomer) {
        try {
            loading.show();

            // 需要授权信息
            if (is_need_authorization && authCustomer != null) {
                scrapBO.setAuthCustomer(authCustomer);
            }

            scrapBO.setReason(scrapCaseEnum.getKey());

            //地址 /ScrapBusiness/addScrap
            //参数ScrapBO
            String jsonStr = objectToJson(scrapBO);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            IRequest iRequest = retrofit.create(IRequest.class);
            Call<String> addScrap = iRequest.addScrap(body);

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

}
