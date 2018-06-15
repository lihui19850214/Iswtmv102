package com.icomp.Iswtmv10.v01c01.c01s011;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c00.c00s000.C00S000_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.SysApplication;
import com.icomp.wsdl.v01c01.c01s011.C01S011Wsdl;
import com.icomp.wsdl.v01c01.c01s011.endpoint.C01S011Request;
import com.icomp.wsdl.v01c01.c01s011.endpoint.C01S011Respons;
import com.umeng.analytics.MobclickAgent;

/**
 * 设备安上3
 *
 * @author WHY
 * @ClassName: C01S011_003Activity
 * @date 2016-3-2 下午5:19:50
 */

public class C01S011_003Activity extends CommonActivity {
    @BindView(R.id.btnGoOn)
    Button mBtnGoOn;
    @BindView(R.id.btnComplete)
    Button mBtnComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c01s011_003activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnGoOn, R.id.btnComplete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoOn:
                Intent intent = new Intent(C01S011_003Activity.this, C01S011_001Activity.class);
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
