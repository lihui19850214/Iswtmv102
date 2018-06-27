package com.icomp.Iswtmv10.v01c01.c01s015;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c00.c00s000.C00S000_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 刀具绑定页面2
 */
public class C01S015_003Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s015_003);

    }

    //继续按钮处理--跳转到合成道具初始化页面1
    public void btnKeepOn(View view) {
        Intent intent = new Intent(this, C01S015_001Activity.class);
        startActivity(intent);
        finish();
    }

    //完成按钮处理--跳转到刀具管理菜单页面
    public void btnComplete(View view) {
        finish();
    }

}
