package com.icomp.Iswtmv10.v01c01.c01s025;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.apiclient.pojo.SynthesisBladeCode;
import com.apiclient.vo.SynthesisBladeCodeVO;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 合成刀打码1
 */
public class C01S025_001Activity extends CommonActivity {


    @BindView(R.id.et_t)
    EditText etT;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    //调用接口
    private Retrofit retrofit;


    List<SynthesisBladeCode> synthesisBladeCodeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s025_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //将输入的材料号自动转化为大写
        etT.setTransformationMethod(new AllCapTransformationMethod());
        etT.setText("T");
        //将光标设置在最后
        etT.setSelection(etT.getText().length());
    }

//    @OnClick({R.id.ll_01})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ll_01:
//
//                break;
//            default:
//        }
//    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        //防止点击扫描后点击此按钮
        finish();
    }

    //查询刀具信息
    public void btnSearch(View view) {
        // 判断是否有查询条件
        if (etT.getText().toString() == null || "".equals(etT.getText().toString().trim())) {
            createAlertDialog(C01S025_001Activity.this, "请输入合成刀T号", Toast.LENGTH_LONG);
        } else {
            try {
                // 清空表格数据
                llContainer.removeAllViews();
                loading.show();

                SynthesisBladeCodeVO synthesisBladeCodeVO = new SynthesisBladeCodeVO();
                synthesisBladeCodeVO.setSynthesisCode(etT.getText().toString().trim());

                String jsonStr = objectToJson(synthesisBladeCodeVO);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

                IRequest iRequest = retrofit.create(IRequest.class);
                Call<String> queryBladeCode = iRequest.queryBladeCode(body);

                queryBladeCode.enqueue(new MyCallBack<String>() {
                    @Override
                    public void _onResponse(Response<String> response) {
                        try {
                            if (response.raw().code() == 200) {
                                synthesisBladeCodeList = jsonToObject(response.body(), List.class, SynthesisBladeCode.class);

                                // 判断是否有数据
                                if (synthesisBladeCodeList != null && synthesisBladeCodeList.size() > 0) {

                                    int count = synthesisBladeCodeList.size();
                                    int i = 0;

                                    while (i < count) {
                                        String str1 = "";
                                        String str2 = "";
                                        String str3 = "";

                                        if (i < count) {
                                            str1 = synthesisBladeCodeList.get(i).getBladeCode();
                                        }
                                        i++;

                                        if (i < count) {
                                            str2 = synthesisBladeCodeList.get(i).getBladeCode();
                                        }
                                        i++;

                                        if (i < count) {
                                            str3 = synthesisBladeCodeList.get(i).getBladeCode();
                                        }
                                        i++;

                                        addLayout(str1, str2, str3);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                createAlertDialog(C01S025_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
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
                        createAlertDialog(C01S025_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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


    /**
     * 添加布局
     */
    private void addLayout(String str1, String str2, String str3) {
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_hechengdaodama, null);

        TextView tv1 = (TextView) mLinearLayout.findViewById(R.id.tv1);
        TextView tv2 = (TextView) mLinearLayout.findViewById(R.id.tv2);
        TextView tv3 = (TextView) mLinearLayout.findViewById(R.id.tv3);

        tv1.setText(str1);
        tv2.setText(str2);
        tv3.setText(str3);

        llContainer.addView(mLinearLayout);
        //llContainer.removeView(mLinearLayout);
        //llContainer.removeAllViews();
    }

}
