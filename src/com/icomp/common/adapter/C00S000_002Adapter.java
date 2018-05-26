package com.icomp.common.adapter;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.icomp.Iswtmv10.R;
import com.icomp.common.utils.GetItemHeight;

public class C00S000_002Adapter extends BaseAdapter {

    private int[] mList;
    private String[] nameList;
    private Context mContext;
    public static final int APP_PAGE_SIZE = 18;
    private PackageManager pm;
    int width;
    int height;

    public C00S000_002Adapter(Context context, int[] menu_image_array, String[] menu_capName_array) {
        mContext = context;
        pm = context.getPackageManager();
        mList = new int[menu_image_array.length];
        nameList = new String[menu_image_array.length];
        int i = 0;
        int iEnd = i + APP_PAGE_SIZE;
        while ((i < menu_image_array.length) && (i < iEnd)) {
            mList[i] = menu_image_array[i];
            nameList[i] = menu_capName_array[i];
            i++;
        }

        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    public int getCount() {
        return mList.length;
    }

    public Object getItem(int position) {
        return mList[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int imgMenu = mList[position];
        AppItem appItem;
        if (convertView == null) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_c00s000_002, null);

            appItem = new AppItem();
            appItem.mAppIcon = (ImageView) v.findViewById(R.id.item_image);
            appItem.mAppTitle = (TextView) v.findViewById(R.id.item_text);
            v.setTag(appItem);

            // 调整每个Item占屏幕的百分比
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams((int) (width * 0.5), (int) (height * 0.4));
            v.setLayoutParams(lp);
            v.setTag(appItem);
            convertView = v;
            //调整ImageView大小
            LayoutParams ip_photo = appItem.mAppIcon.getLayoutParams();
            ip_photo.height = (int) (GetItemHeight.getScreenHeight(mContext) * 0.28);
            ip_photo.width = (int) (GetItemHeight.getScreenWidth(mContext) * 0.45);
//            appItem.mAppIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            appItem = (AppItem) convertView.getTag();
        }
        // set the icon
        appItem.mAppIcon.setBackgroundResource(imgMenu);
        appItem.mAppTitle.setText(nameList[position]);
        convertView.setBackgroundColor(Color.parseColor("#00000000")); //设置背景颜色
        return convertView;
    }

    /**
     * 每个应用显示的内容，包括图标和名称
     *
     * @author Yao.GUET
     */
    class AppItem {
        ImageView mAppIcon;
        TextView mAppTitle;
    }

}
