package com.icomp.Iswtmv10.v01c01.c01s018;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c00.c00s000.C00S000_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 厂内修磨页面4
 * Created by FanLL on 2017/7/10.
 */

public class C01S018_004Activity extends CommonActivity {

    @BindView(R.id.btnGoOn)
    Button mBtnGoOn;
    @BindView(R.id.btnComplete)
    Button mBtnComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_004);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnGoOn, R.id.btnComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoOn:
                Intent intent = new Intent(C01S018_004Activity.this, C01S018_002Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnComplete:
                finish();
                break;
            default:
        }
    }

}
