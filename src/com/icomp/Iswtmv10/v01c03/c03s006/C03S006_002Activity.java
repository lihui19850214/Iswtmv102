package com.icomp.Iswtmv10.v01c03.c03s006;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;

/**
 * 流转刀具初始化页面2
 */
public class C03S006_002Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s006_002);
    }

    //继续按钮处理--跳转到流转刀具初始化页面1
    public void btnKeepOn(View view) {
        Intent intent = new Intent(this, C03S006_001Activity.class);
        startActivity(intent);
        finish();
    }

    //完成按钮处理--跳转到系统菜单页面
    public void btnComplete(View view) {
        finish();
    }

}
