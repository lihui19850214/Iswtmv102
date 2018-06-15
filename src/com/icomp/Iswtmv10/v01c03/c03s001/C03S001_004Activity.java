package com.icomp.Iswtmv10.v01c03.c03s001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c01.c01s005.c01s005_002_2Activity;
import com.icomp.Iswtmv10.v01c01.c01s005.c01s005_002_4Activity;
import com.icomp.common.activity.CommonActivity;

/**
 * 合成刀具初始化页面4
 */

public class C03S001_004Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s001_004);
    }

    @OnClick({R.id.btnKeepOn, R.id.btnComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnKeepOn:
                Intent intent = new Intent(this, C03S001_003Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
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
