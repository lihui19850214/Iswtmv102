package com.icomp.Iswtmv10.v01c01.c01s012;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 标签置换页面2
 */
public class C01S012_002Activity extends CommonActivity {


    @BindView(R.id.btnGoOn)
    Button mBtnGoOn;
    @BindView(R.id.btnComplete)
    Button mBtnComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s012_002);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnGoOn, R.id.btnComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoOn:
                Intent intent2 = new Intent(C01S012_002Activity.this, C01S012_001Activity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnComplete:
                finish();
                break;
            default:
        }
    }



}
