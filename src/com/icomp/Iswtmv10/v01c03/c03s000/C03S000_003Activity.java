package com.icomp.Iswtmv10.v01c03.c03s000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c01.c01s018.C01S018_002Activity;
import com.icomp.Iswtmv10.v01c03.c03s003.C03S003_001Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 设备初始化菜单页面
 * Created by FanLL on 2017/6/14.
 */

public class C03S000_003Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s000_003);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
    }

    //加工设备初始化页面
    public void processingEquipmentInit (View view) {
        Intent intent = new Intent(this, C03S003_001Activity.class);
        startActivity(intent);
        finish();
    }

    //修磨设备初始化页面
    public void grindingEquipmentInit (View view) {
        Intent intent = new Intent(this, C01S018_002Activity.class);
        startActivity(intent);
        finish();
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        finish();
    }

    //返回按钮处理--返回上一页面（初始化菜单页面）
    public void btnReturn(View view) {
        Intent intent = new Intent(this, C03S000_001Activity.class);
        startActivity(intent);
        finish();
    }

}
