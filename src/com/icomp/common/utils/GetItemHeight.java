package com.icomp.common.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.io.Serializable;

/**
 * 作者：Created by Administrator on 2016/4/13 0013.
 */
public class GetItemHeight implements Serializable {
    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
}
