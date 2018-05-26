package com.icomp.common.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.icomp.Iswtmv10.R;
import com.icomp.common.constat.Constat;
import com.icomp.common.control.ScrollLayout;
import com.icomp.common.utils.GetItemHeight;

import butterknife.BindView;
import butterknife.ButterKnife;

public class C00S000_003Adapter extends BaseAdapter {
    private int[] mList;
    private Context mContext;
    private String[] nameList;
    private ViewPager scrollLayout;

    public C00S000_003Adapter(Context context, int[] menu_image_array, int page, int pageCount, String[] menu_capName_array, ViewPager scrollLayout) {
        this.scrollLayout = scrollLayout;
        mContext = context;
        if (page < pageCount - 1) {
            mList = new int[Constat.MENU_SIZE];
            nameList = new String[Constat.MENU_SIZE];
        } else if (menu_image_array.length % Constat.MENU_SIZE == 0) {
            mList = new int[Constat.MENU_SIZE];
            nameList = new String[Constat.MENU_SIZE];

        } else {
            mList = new int[menu_image_array.length % Constat.MENU_SIZE];
            nameList = new String[menu_image_array.length % Constat.MENU_SIZE];
        }

        int i = page * Constat.MENU_SIZE;
        int iEnd = i + Constat.MENU_SIZE;
        int j = 0;
        while ((i < menu_image_array.length) && (i < iEnd)) {
            mList[j] = menu_image_array[i];
            nameList[j] = menu_capName_array[i];
            i++;
            j++;
        }
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_c00s000_003, null);
            viewHolder = new ViewHolder(convertView);
            // 调整每个Item占屏幕的百分比
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(scrollLayout.getWidth() / 3, scrollLayout.getHeight() / 3);
            convertView.setLayoutParams(lp);
            //调整ImageView大小
            ViewGroup.LayoutParams ip_photo = viewHolder.itemImage.getLayoutParams();
            ip_photo.height = (int) (GetItemHeight.getScreenHeight(mContext) * 0.17);
            ip_photo.width = (int) (GetItemHeight.getScreenHeight(mContext) * 0.17);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(scrollLayout.getWidth() / 3, scrollLayout.getHeight() / 3);
//            convertView.setLayoutParams(lp);
        }
        // set the icon
        viewHolder.itemImage.setBackgroundResource(mList[position]);
        viewHolder.itemText.setText(nameList[position]);
        convertView.setBackgroundColor(Color.parseColor("#00000000")); //设置背景颜色
        return convertView;
    }


    /**
     * 每个应用显示的内容，包括图标和名称
     *
     * @author Yao.GUET
     */
    static class ViewHolder {
        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_text)
        TextView itemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }

}
