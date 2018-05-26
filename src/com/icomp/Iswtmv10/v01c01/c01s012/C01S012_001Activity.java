package com.icomp.Iswtmv10.v01c01.c01s012;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apiclient.vo.ChangeRFIDVO;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c00.c00s000.C00S000_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 标签置换页面1
 */

public class C01S012_001Activity extends CommonActivity {

    @BindView(R.id.et_01)
    EditText et01;

    //调用接口
    private Retrofit retrofit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s012_001);
        ButterKnife.bind(this);

        //调用接口
        retrofit = RetrofitSingle.newInstance();

        //将输入的材料号自动转化为大写
        et01.setTransformationMethod(new CommonActivity.AllCapTransformationMethod());
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        //防止点击扫描后点击此按钮
        finish();
    }

    //置换按钮处理
    public void btnSubmit(View view) {
        String changeRFID = et01.getText().toString().trim();
        if ("".equals(changeRFID)) {
            createAlertDialog(C01S012_001Activity.this, "请输入要置换标签刀具的刀身码", Toast.LENGTH_LONG);
            return;
        }

        loading.show();
        IRequest iRequest = retrofit.create(IRequest.class);

        Gson gson = new Gson();

        ChangeRFIDVO changeRFIDVO = new ChangeRFIDVO();
        changeRFIDVO.setToolCode(changeRFID);
        String jsonStr = gson.toJson(changeRFIDVO);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

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
    }

}
