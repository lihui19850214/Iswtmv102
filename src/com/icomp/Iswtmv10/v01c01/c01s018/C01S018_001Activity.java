package com.icomp.Iswtmv10.v01c01.c01s018;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c00.c00s000.C00S000_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;

/**
 * 厂内修磨页面1
 * Created by FanLL on 2017/7/10.
 */

public class C01S018_001Activity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s018_001);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
    }

    //单品刀按钮处理--跳转到单品刀厂内修磨页面
    public void btnDanKnife(View view) {
        Intent intent = new Intent(this, C01S018_002Activity.class);
        startActivity(intent);
        finish();
    }

    //一体刀按钮处理--跳转到一体刀厂内修磨页面
    public void btnOneKnife(View view) {
        Intent intent = new Intent(this, C01S018_012Activity.class);
        startActivity(intent);
        finish();
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        Intent intent = new Intent(this, C00S000_002Activity.class);
        startActivity(intent);
        finish();
    }

    //返回按钮处理--返回上一页面（刀具管理菜单页面）
    public void btnReturn(View view) {
        finish();
    }

}
