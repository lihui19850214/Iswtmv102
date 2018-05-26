package com.icomp.Iswtmv10.v01c02.c02s001;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.v01c00.c00s000.C00S000_002Activity;
import com.icomp.common.activity.CommonActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by user on 2017/11/3.
 * 旧手持机不崩溃版本
 */

public class C02S001_001Activity extends CommonActivity{
    private int paramInt = 0;

    /**
     * F1按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    @Override
    public boolean keycodeMenu() {
        appCancel();
        return false;
    }

    //点击返回按钮
    @Override
    public void appReturn() {
        finish();
    }
    //点击取消按钮
    public void btnCancel(){
        Intent intent = new Intent(this, C00S000_002Activity.class);
        startActivity(intent);
        finish();
    }
    /**
     * Enter按键
     *
     * @return true:事件继续执行,false:事件停止
     */
    @Override
    public boolean keycodeEnter() {
        if (paramInt == 0) {
            paramInt = 10;
        }
        saveScreenBrightness(paramInt);
        setScreenBrightness(paramInt);
        return false;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c02s001_001activity);
        // 屏幕亮度初始化
        int screenBrightness = getScreenBrightness();

        setScreenMode(Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        final SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setMax(2550);
        seekBar1.setProgress(screenBrightness);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (paramInt == 0) {
                    paramInt = 10;
                }
                saveScreenBrightness(paramInt);
                // 获取SharedPreferences对象
                SharedPreferences sp = getSharedPreferences("haha", Activity.MODE_PRIVATE);
                // 获取Editor对象
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("light",paramInt);
                editor.commit();
                setScreenBrightness(paramInt);
            }
        });
        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
        {
            @Override
            public void onProgressChanged(SeekBar arg0, int progress,
                                          boolean fromUser) {
                paramInt = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 取得返回按钮
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        if (btnReturn != null) {
            btnReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    appReturn();
                }
            });
        }

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        if(btnCancel!=null){
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCancel();
                }
            });
        }
    }

//	/**
//	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
//	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
//	 */
//	private int getScreenMode() {
//		int screenMode = 0;
//		try {
//			screenMode = Settings.System.getInt(getContentResolver(),
//					Settings.System.SCREEN_BRIGHTNESS_MODE);
//		} catch (Exception localException) {
//
//		}
//		return screenMode;
//	}
//
    /**
     * 获得当前屏幕亮度值 0--255
     */
    private int getScreenBrightness() {
        int screenBrightness = 2550;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception localException) {}
        return screenBrightness;
    }

    /**
     * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    private void setScreenMode(int paramInt) {
        try {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 设置当前屏幕亮度值 0--255
     */
    private void saveScreenBrightness(int paramInt) {
        try {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    private void setScreenBrightness(int paramInt) {
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow
                .getAttributes();
        float f = paramInt / 2550.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }
    /**
     * 友盟错误统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
//
//
//
//
//package com.icomp.Iswtmv10.v01c02.c02s001;
//
//        import android.os.Bundle;
//        import android.provider.Settings;
//        import android.view.View;
//        import android.view.Window;
//        import android.view.WindowManager;
//        import android.widget.Button;
//        import android.widget.SeekBar;
//        import android.widget.SeekBar.OnSeekBarChangeListener;
//
//        import com.icomp.Iswtmv10.R;
//        import com.icomp.common.activity.CommonActivity;
//        import com.umeng.analytics.MobclickAgent;
//
//public class C02S001_001Activity extends CommonActivity {
//
//    private int paramInt = 0;
//
//    /**
//     * F1按键
//     *
//     * @return true:事件继续执行,false:事件停止
//     */
//    @Override
//    public boolean keycodeMenu() {
//        appCancel();
//        return false;
//    }
//
//    //点击返回按钮
//    public void appReturn() {
//        finish();
//    }
//
//    /**
//     * Enter按键
//     *
//     * @return true:事件继续执行,false:事件停止
//     */
//    public boolean keycodeEnter() {
//        if (paramInt == 0) {
//            paramInt = 10;
//        }
//        saveScreenBrightness(paramInt);
//        setScreenBrightness(paramInt);
//        return false;
//    }
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.c02s001_001activity);
//        // 屏幕亮度初始化
//        int screenBrightness = getScreenBrightness();
//
//        setScreenMode(Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
//        final SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
//        seekBar1.setMax(2550);
//        seekBar1.setProgress(screenBrightness);
//        Button btnSave = (Button) findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                if (paramInt == 0) {
//                    paramInt = 10;
//                }
//                saveScreenBrightness(paramInt);
//                setScreenBrightness(paramInt);
//            }
//        });
//        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() // 调音监听器
//        {
//            public void onProgressChanged(SeekBar arg0, int progress,
//                                          boolean fromUser) {
//                paramInt = progress;
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
//        // 取得返回按钮
//        Button btnReturn = (Button) findViewById(R.id.btnReturn);
//        if (btnReturn != null) {
//            btnReturn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    appReturn();
//                }
//            });
//        }
//    }
//
////	/**
////	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
////	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
////	 */
////	private int getScreenMode() {
////		int screenMode = 0;
////		try {
////			screenMode = Settings.System.getInt(getContentResolver(),
////					Settings.System.SCREEN_BRIGHTNESS_MODE);
////		} catch (Exception localException) {
////
////		}
////		return screenMode;
////	}
//
//    /**
//     * 获得当前屏幕亮度值 0--255
//     */
//    private int getScreenBrightness() {
//        int screenBrightness = 2550;
//        try {
//            screenBrightness = Settings.System.getInt(getContentResolver(),
//                    Settings.System.SCREEN_BRIGHTNESS);
//        } catch (Exception localException) {
//
//        }
//        return screenBrightness;
//    }
//
//    /**
//     * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
//     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
//     */
//    private void setScreenMode(int paramInt) {
//        try {
//            Settings.System.putInt(getContentResolver(),
//                    Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
//        } catch (Exception localException) {
//            localException.printStackTrace();
//        }
//    }
//
//    /**
//     * 设置当前屏幕亮度值 0--255
//     */
//    private void saveScreenBrightness(int paramInt) {
//        try {
//            Settings.System.putInt(getContentResolver(),
//                    Settings.System.SCREEN_BRIGHTNESS, paramInt);
//        } catch (Exception localException) {
//            localException.printStackTrace();
//        }
//    }
//
//    /**
//     * 保存当前的屏幕亮度值，并使之生效
//     */
//    private void setScreenBrightness(int paramInt) {
//        Window localWindow = getWindow();
//        WindowManager.LayoutParams localLayoutParams = localWindow
//                .getAttributes();
//        float f = paramInt / 2550.0F;
//        localLayoutParams.screenBrightness = f;
//        localWindow.setAttributes(localLayoutParams);
//    }
//    /**
//     * 友盟错误统计
//     */
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
//}