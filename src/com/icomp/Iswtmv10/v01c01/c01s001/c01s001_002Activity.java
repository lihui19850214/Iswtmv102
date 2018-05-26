package com.icomp.Iswtmv10.v01c01.c01s001;
/**
 * 新刀入库输入入库数量
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s001.modul.JumpModul;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class c01s001_002Activity extends CommonActivity {

    @BindView(R.id.btnReturn)
    Button mBtnReturn;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.tv_01)
    TextView mTv01;
    @BindView(R.id.tvXiaLa)
    TextView mTvXiaLa;
    @BindView(R.id.tv_02)
    TextView mTv02;
    @BindView(R.id.tvMuchStatic)
    TextView mTvMuchStatic;
    @BindView(R.id.etMuch)
    EditText mEtMuch;
    @BindView(R.id.tvMaxSize)
    TextView mTvMaxSize;
    @BindView(R.id.activity_c01s001_002)
    LinearLayout mActivityC01s001002;

//    2018年1月17日15:17:49
    @BindView(R.id.tvPingGuLeiXing)
    TextView tvPingGuLeiXing;
    @BindView(R.id.tvDingDanXuHao)
    TextView tvDingDanXuHao;





    private String json;
    private JumpModul j;

    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s001_002);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = RetrofitSingle.newInstance();
    }

    @OnClick({R.id.tvXiaLa, R.id.btnReturn, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvXiaLa:
//                showPopupWindow();
                break;
            case R.id.btnReturn:
                finish();
                break;
            case R.id.btnConfirm:
                if("".equals(mEtMuch.getText().toString()) || null == mEtMuch.getText().toString()){
                    createAlertDialog(this, "请输入入库数量", Toast.LENGTH_SHORT);
                    return;
                }
                int much = Integer.parseInt(mEtMuch.getText().toString());
                if (much<=0) {
                    createAlertDialog(this, "入库数量要大于0", Toast.LENGTH_SHORT);
                    return;
                }
                if (!"".equals(mTvMaxSize.getText().toString())) {
                    if (Integer.valueOf(mTvMaxSize.getText().toString()) < Integer.valueOf(mEtMuch.getText().toString())) {
                        createAlertDialog(this, "入库数量不能大于可入库数量", Toast.LENGTH_SHORT);
                        return;
                    }
                }
                requestData();

                break;
            default:
        }
    }

    /**
     * 初始化界面，将上一个画面带过来的数据铺到当前画面
     */
    private void initView() {
        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        j = new Gson().fromJson(json, JumpModul.class);
        if (j == null) {
            j = new JumpModul();
        }
        mTvMuchStatic.setText("请输入"+j.getCailiaohao()+"入库数量");
        mTv01.setText(exChangeBig(j.getCailiaohao()));
        mTvXiaLa.setText(j.getDingdanhao());
        mTvMaxSize.setText(j.getMaxSize());
        mTv02.setText(j.getPici());

        tvDingDanXuHao.setText(intent.getStringExtra("DingDanXuHao"));
        tvPingGuLeiXing.setText(intent.getStringExtra("PingGuLeiXing"));

    }

    /**
     *
     * 将对应刀具入库数量提交
     */
    private void requestData() {
        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_APPEND);
        //新建编辑器
        String customerID = sharedPreferences.getString("customerID", "");

        int much = Integer.parseInt(mEtMuch.getText().toString());
        Call<String> saveInfo = iRequest.saveToolInputInfo(customerID,
                mTv01.getText().toString(),
                mTvXiaLa.getText().toString(),
                String.valueOf(much), j.getId(), tvPingGuLeiXing.getText().toString().trim(), tvDingDanXuHao.getText().toString().trim());
        saveInfo.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response response) {
                loading.dismiss();
                    String json = response.body().toString();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getBoolean("success")) {
                            Intent intent = new Intent(c01s001_002Activity.this, c01s001_002Activity.class);

                            startActivity(intent);
                        } else {
                            createAlertDialog(c01s001_002Activity.this,jsonObject.getString("message"), Toast.LENGTH_SHORT);
                        }
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
            }

            @Override
            public void _onFailure(Throwable t) {
                loading.dismiss();
                createAlertDialog(c01s001_002Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
    }

}
