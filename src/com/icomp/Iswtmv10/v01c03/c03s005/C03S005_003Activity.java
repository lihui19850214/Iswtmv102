package com.icomp.Iswtmv10.v01c03.c03s005;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 员工卡初始化页面3
 * Created by FanLL on 2017/6/14.
 */

public class C03S005_003Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s005_003);

    }

    //继续按钮处理--跳转到员工卡初始化页面1
    public void btnKeepOn(View view) {
        Intent intent = new Intent(this, C03S005_001Activity.class);
        startActivity(intent);
        finish();
    }

    //完成按钮处理--跳转到系统菜单页面
    public void btnComplete(View view) {
        finish();
    }

}
