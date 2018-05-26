package com.icomp.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 作者：Created by Administrator on 2016/4/13 0013.
 */
public class DialogFactory extends Dialog {
    private static int default_width = 200; // 默认宽度
    private static int default_height = 180;// 默认高度

    public DialogFactory(Context context) {
        super(context);
    }

    public DialogFactory(Context context, int layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public DialogFactory(Context context, int width, int height, int layout,
                         int style) {
        super(context, style);
        // 设置内容
        setContentView(layout);
        setCancelable(false);
        // 设置窗口属性
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置宽度、高度、密度、对齐方式
        float density = getDensity(context);
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

    }

    public static void loading() {

    }

    /**
     * 获取显示密度
     *
     * @param context
     * @return
     */
    public float getDensity(Context context) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.density;
    }
}
