package com.icomp.Iswtmv10.v01c02.c02s005;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.RfidRead;
import com.umeng.analytics.MobclickAgent;

public class C02S005_001Activity extends CommonActivity {
    private int paramInt = 0;
    byte frequency = '0';

    /**
     * F1按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    @Override
    public boolean keycodeMenu() {
        finish();
        return false;
    }

    /**
     * 取消按钮处理
     */
    @Override
    public void appCancel() {
        finish();
    }

    //取消按钮处理--跳转到系统菜单页面
    public void btnCancel(View view) {
        finish();
    }

    /**
     * Enter按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    public boolean keycodeEnter() {
        if (paramInt == 0) {
            paramInt = frequency;
        }
        // 取得用户已保存的默认语言
        SharedPreferences sharedPreferences = getSharedPreferences("langVaue", CommonActivity.MODE_APPEND);
        // 保存语言信息
        sharedPreferences = getSharedPreferences("rfidFrequency", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();// 获取编辑器
        editor.putString("paramInt", paramInt + "");
        editor.commit();// 提交修改
        createAlertDialog(this, getString(R.string.saveSuccess), Toast.LENGTH_SHORT);
        return false;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c02s005_001activity);
        ButterKnife.bind(this);


//        RfidRead.initRfid(this);
//        final SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
//        // 取得读头模块信号强度
//        //	AppContext.getJni().WIrUHFGetPower(uPower);
//        SharedPreferences preferences = getSharedPreferences("rfidFrequency", CommonActivity.MODE_APPEND);
//        String paramInit = preferences.getString("paramInt", "18");
//        frequency = (byte) Integer.parseInt(paramInit);
//        seekBar1.setMax(30);
//        seekBar1.setProgress(Integer.parseInt(paramInit));
//        TextView textView1 = (TextView) C02S005_001Activity.this.findViewById(R.id.textView1);
//        textView1.setText(getString(R.string.power) + ":" + String.valueOf(frequency) + "dBM");
//        Button btnSave = (Button) findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                keycodeEnter();
//            }
//        });
//
//        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
//        {
//            public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
//                paramInt = progress;
//                TextView textView1 = (TextView) C02S005_001Activity.this.findViewById(R.id.textView1);
//                textView1.setText(getString(R.string.power) + ":" + progress + "dBM");
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        // 取得取消按钮
//        Button cancel = (Button) findViewById(R.id.cancel);
//        if (cancel != null) {
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    appCancel();
//                }
//            });
//        }
//        // 取得返回按钮
//        Button btnReturn = (Button) findViewById(R.id.btnReturn);
//        if (btnReturn != null) {
//            btnReturn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    appCancel();
//                }
//            });
//        }

    }

}
