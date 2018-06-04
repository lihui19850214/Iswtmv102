package com.icomp.Iswtmv10.v01c01.c01s007;

import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.pojo.DjOutapplyAkp;
import com.apiclient.pojo.QimingRecords;
import com.apiclient.vo.QimingRecordsVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 刀具打码
 * Created by Fanll on 2018/1/16.
 */

public class C01S007_001Activity extends CommonActivity {


    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.ll_01)
    LinearLayout ll01;
    @BindView(R.id.listview)
    ListView listview;

    //调用接口
    private Retrofit retrofit;

    List<DjOutapplyAkp> DjOutapplyAkpList = new ArrayList<>();
    DjOutapplyAkp djOutapplyAkp = new DjOutapplyAkp();

    List<QimingRecords> qimingRecordsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s007_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        searchOutOrder();
    }

    @OnClick({R.id.ll_01})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_01:
                showPopupWindow();
                break;
            default:
        }
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        //防止点击扫描后点击此按钮
        finish();
    }

    //查询刀具信息
    public void btnSearch(View view) {
        if (tv01.getText().toString() == null || "".equals(tv01.getText().toString())) {
            createAlertDialog(C01S007_001Activity.this, "请选择要打码刀具的订单号", Toast.LENGTH_LONG);
            return;
        }

        try {
            loading.show();

            IRequest iRequest = retrofit.create(IRequest.class);

            QimingRecordsVO qimingRecordsVO = new QimingRecordsVO();
            qimingRecordsVO.setApplyNo(djOutapplyAkp.getApplyno());

            Gson gson = new Gson();
            String jsonStr = gson.toJson(qimingRecordsVO);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> queryBladeCodes = iRequest.queryBladeCodes(body);
            queryBladeCodes.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<QimingRecords>>() {
                            }.getType();
                            qimingRecordsList = gson.fromJson(response.body(), type);

                            if (qimingRecordsList == null || qimingRecordsList.size() == 0) {
                                qimingRecordsList = new ArrayList<>();
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            } else {
                                listview.setVisibility(View.VISIBLE);

                                //将数据显示在列表上
                                listview.setAdapter(new QimingRecordsAdapter());
                            }
                        } else {
                            createAlertDialog(C01S007_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S007_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    class QimingRecordsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return qimingRecordsList.size();
        }

        @Override
        public Object getItem(int position) {
            return qimingRecordsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view1 = LayoutInflater.from(C01S007_001Activity.this).inflate(R.layout.item_c01s007_002, null);
            TextView tv_01 = (TextView) view1.findViewById(R.id.tv_01);

            tv_01.setText(qimingRecordsList.get(position).getBladeCode());

            return view1;
        }

    }


    //根据材料号查询合成刀具组成信息
    private void searchOutOrder() {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = "{}";
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> queryOutOrder = iRequest.queryOutOrder(body);

            queryOutOrder.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            ObjectMapper mapper = new ObjectMapper();
                            DjOutapplyAkpList = mapper.readValue(response.body(), getCollectionType(mapper, List.class, DjOutapplyAkp.class));

                            if (DjOutapplyAkpList == null || DjOutapplyAkpList.size() == 0) {
                                DjOutapplyAkpList = new ArrayList<>();
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C01S007_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                    createAlertDialog(C01S007_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
                    loading.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
        }
    }

    //显示流水线列表
    private void showPopupWindow() {
        View view = LayoutInflater.from(C01S007_001Activity.this).inflate(R.layout.spinner_c03s004_001, null);
        ListView listView = (ListView) view.findViewById(R.id.ll_spinner);
        C01S007_001Activity.MyAdapter myAdapter = new C01S007_001Activity.MyAdapter();
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
                tv01.setText(DjOutapplyAkpList.get(i).getName());
                popupWindow.dismiss();

                //刀具
                djOutapplyAkp = DjOutapplyAkpList.get(i);


                // 情况查询列表
                qimingRecordsList = new ArrayList<>();
                //将数据显示在列表上
                listview.setAdapter(new QimingRecordsAdapter());
            }
        });
        popupWindow.showAsDropDown(ll01);
    }

    //流水线的Adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return DjOutapplyAkpList.size();
        }

        @Override
        public Object getItem(int i) {
            return DjOutapplyAkpList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(C01S007_001Activity.this).inflate(R.layout.item_c03s004_001, null);
            TextView textView = (TextView) view1.findViewById(R.id.tv_01);
            textView.setText(DjOutapplyAkpList.get(i).getName());
            return view1;
        }
    }

}
