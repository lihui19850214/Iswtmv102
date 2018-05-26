package com.icomp.Iswtmv10.v01c03.c03s000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c03.c03s001.C03S001_001Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 刀具初始化菜单页面
 * Created by FanLL on 2017/6/14.
 */

public class C03S000_002Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s000_002);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
    }

    //合成刀具初始化页面
    public void syntheticCutterInit (View view) {
        Intent intent = new Intent(this, C03S001_001Activity.class);
        startActivity(intent);
        finish();
    }

    //筒初始化页面
    public void tubeKnifeInit (View view) {
        Intent intent = new Intent(this, C03S000_001Activity.class);
        startActivity(intent);
        finish();
    }

    //库位标签初始化页面
    public void libraryLabelInit (View view) {
        Intent intent = new Intent(this, C03S000_001Activity.class);
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
