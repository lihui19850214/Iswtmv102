package com.icomp.Iswtmv10.v01c03.c03s001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
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

    //继续按钮处理--跳转到合成刀具初始化页面1
    public void btnKeepOn(View view) {
        Intent intent = new Intent(this, C03S001_003Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }

    //完成按钮处理--跳转到系统菜单页面
    public void btnComplete(View view) {
        finish();
    }

}
