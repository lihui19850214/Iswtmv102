package com.icomp.Iswtmv10.v01c01.c01s013;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;

/**
 * 设备卸下页面3
 */
public class C01S013_0021Activity extends CommonActivity {

    @BindView(R.id.btnGoOn)
    Button mBtnGoOn;
    @BindView(R.id.btnComplete)
    Button mBtnComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s013_0021activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnGoOn, R.id.btnComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoOn:
                Intent intent2 = new Intent(C01S013_0021Activity.this, C01S013_001Activity.class);
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
