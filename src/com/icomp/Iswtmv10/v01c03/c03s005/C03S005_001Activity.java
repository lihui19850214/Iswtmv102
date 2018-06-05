package com.icomp.Iswtmv10.v01c03.c03s005;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.vo.AuthCustomerVO;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 员工卡初始化页面1
 */
public class C03S005_001Activity extends CommonActivity {

    @BindView(R.id.et_01)
    EditText et01;
    @BindView(R.id.btnSearch)
    Button btnSearch;

    //员工初始化参数类
    private AuthCustomerVO params = new AuthCustomerVO();

    //调用接口
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s005_001);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //调用接口
        retrofit = RetrofitSingle.newInstance();
        //将输入的材料号自动转化为大写
        et01.setTransformationMethod(new AllCapTransformationMethod());
        //接受下一个页面点击返回按钮时返回的员工号
        params.setEmployeeCode(getIntent().getStringExtra(PARAM1));
        //显示员工卡号
        if (null != params.getEmployeeCode()) {
            et01.setText(params.getEmployeeCode());
        }
        //将光标设置在最后
        et01.setSelection(et01.getText().length());
    }

    //返回按钮处理--返回初始化菜单页面
    public void btnReturn(View view) {
        finish();
    }

    //查询按钮处理
    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        btnSearch.setClickable(false);
        params.setEmployeeCode(et01.getText().toString().trim());
        if ("".equals(params.getEmployeeCode())) {
            createAlertDialog(C03S005_001Activity.this, getString(R.string.c03s005_001_002), Toast.LENGTH_LONG);
            btnSearch.setClickable(true);
        } else {
            search();
        }
    }


    //根据材料号查询合成刀具组成信息
    private void search() {
        try {
            loading.show();
            IRequest iRequest = retrofit.create(IRequest.class);

            String jsonStr = objectToJson(params);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);

            Call<String> queryEmployee = iRequest.queryEmployee(body);

            queryEmployee.enqueue(new MyCallBack<String>() {
                @Override
                public void _onResponse(Response<String> response) {
                    try {
                        if (response.raw().code() == 200) {
                            AuthCustomer authCustomer = jsonToObject(response.body(), AuthCustomer.class);
                            if (authCustomer != null) {
                                //跳转到员工初始化页面2
                                Intent intent = new Intent(C03S005_001Activity.this, C03S005_002Activity.class);
                                intent.putExtra(PARAM, authCustomer);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.queryNoMessage), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createAlertDialog(C03S005_001Activity.this, response.errorBody().string(), Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                    } finally {
                        loading.dismiss();
                        btnSearch.setClickable(true);
                    }
                }

                @Override
                public void _onFailure(Throwable t) {
                    loading.dismiss();
                    btnSearch.setClickable(true);
                    createAlertDialog(C03S005_001Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
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
