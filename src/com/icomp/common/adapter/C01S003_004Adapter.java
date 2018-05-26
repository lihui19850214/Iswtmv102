package com.icomp.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icomp.Iswtmv10.R;
import com.icomp.common.utils.GetItemHeight;

import java.util.HashMap;
import java.util.List;

public class C01S003_004Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> data;
    ViewHolder viewholder = null;

    public C01S003_004Adapter(Context context, List<String> list) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        data = list;
    }

    @Override
    public int getCount() {
        if (null != data) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (null != data) {
            return data.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_c01s003_003, null);
            viewholder = new ViewHolder();
            viewholder.tv_01 = (TextView) convertView.findViewById(R.id.tv_01);
            convertView.setTag(viewholder);
            // 调整每个Item占屏幕的百分比
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, (int) (GetItemHeight.getScreenHeight(context) * 0.10));
            convertView.setLayoutParams(lp);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        //************************************************控件赋值*********************************************************************
        viewholder.tv_01.setText(data.get(position));


        return convertView;
    }

    static class ViewHolder {
        public TextView tv_01;
    }
}
