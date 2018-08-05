package com.icomp.Iswtmv10.v01c02.c02s005;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;

public class C02S005_001Activity extends CommonActivity {

    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnReturn)
    Button btnReturn;
    @BindView(R.id.tv_power)
    TextView tvPower;

    private int powerValue = 5;

    private int arrPow = 25; //输出功率，分为最大30和24

    private Handler mHandler = new Handler();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c02s005_001activity);
        ButterKnife.bind(this);

        String ver = rfidWithUHF.getHardwareType();

        if (ver != null && ver.contains("RLM")) {
            arrPow = 19;
        }

        // 设置 seekBar 最大值
        seekBar1.setMax(arrPow);

        /*
		开启子线程获取参数，Handler更新UI,防止打开卡顿
		 */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getPower();
            }
        });

//        getPower();

        // 调音监听器
        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
                powerValue = progress + 5;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPower.setText(String.valueOf(powerValue));
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 保存按钮事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setPower();
            }
        });

        // 返回按钮事件
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }

    public void getPower() {
        int iPower = rfidWithUHF.getPower();

        //Log.i("C02S005_001Activity", "getPower() iPower=" + iPower);

        if (iPower > -1) {
            int tempProgress = iPower - 5;
            final int progress;

            if (tempProgress < 5) {
                powerValue = 5;
                progress = 0;
            } else if (tempProgress > seekBar1.getMax()) {
                powerValue = seekBar1.getMax() + 5;
                progress = seekBar1.getMax();
            } else {
                powerValue = tempProgress + 5;
                progress = tempProgress;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvPower.setText(String.valueOf(powerValue));
                    seekBar1.setProgress(progress);
                }
            });
        } else {
            createToast(getApplicationContext(), getString(R.string.uhf_msg_read_power_fail), Toast.LENGTH_SHORT);
        }
    }

    public void setPower() {
        //Log.i("C02S005_001Activity", "setPower() iPower=" + powerValue);

        if (rfidWithUHF.setPower(powerValue)) {
            createToast(getApplicationContext(), getString(R.string.uhf_msg_set_power_succ), Toast.LENGTH_SHORT);
        } else {
            createToast(getApplicationContext(), getString(R.string.uhf_msg_set_power_fail), Toast.LENGTH_SHORT);
        }
    }

}
