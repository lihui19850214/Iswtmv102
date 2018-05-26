package com.icomp.Iswtmv10.v01c03.c03s000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c03.c03s003.C03S003_001Activity;
import com.icomp.Iswtmv10.v01c03.c03s005.C03S005_001Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 初始化菜单页面
 * Created by FanLL on 2017/6/14.
 */

public class C03S000_001Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s000_001);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
    }

    //刀具初始化菜单页面
    public void toolsInit (View view) {
        Intent intent = new Intent(this, C03S000_002Activity.class);
        startActivity(intent);
        finish();
    }

    //设备初始化菜单页面
    public void equipmentInit (View view) {
        Intent intent = new Intent(this, C03S003_001Activity.class);
        startActivity(intent);
        finish();
    }

    //员工卡初始化页面
    public void employeeCardInit (View view) {
        Intent intent = new Intent(this, C03S005_001Activity.class);
        startActivity(intent);
        finish();
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        finish();
    }

    //返回按钮处理--返回上一页面（系统菜单页面）
    public void btnReturn(View view) {
        finish();
    }

}
