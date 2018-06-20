package com.icomp.Iswtmv10.v01c01.c01s007;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c01.c01s005.c01s005_002_2Activity;
import com.icomp.common.activity.CommonActivity;

public class C01S007_002Activity extends CommonActivity {

    @BindView(R.id.btnGoOn)
    Button mBtnGoOn;
    @BindView(R.id.btnComplete)
    Button mBtnComplete;
    @BindView(R.id.tv_00)
    TextView tv00;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s007_002);
        ButterKnife.bind(this);

        tv00.setText(getIntent().getStringExtra("bladeCode"));
    }

    @OnClick({R.id.btnGoOn, R.id.btnComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoOn:
                Intent intent = new Intent(C01S007_002Activity.this, C01S007_001Activity.class);
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
