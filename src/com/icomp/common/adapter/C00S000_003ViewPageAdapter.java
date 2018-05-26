package com.icomp.common.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */

public class C00S000_003ViewPageAdapter extends PagerAdapter {
    /**
     * activity
     */
    private Activity mActivity;
    /**
     * 数据集合
     */
    private List<View> mList;

    public C00S000_003ViewPageAdapter(Activity activity, List<View> list) {
        mActivity = activity;
        mList = list;



    }

    @Override
    public int getCount() {
//        return mList.size();
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        ImageView iv_01 = (ImageView) mList.get(position).findViewById(R.id.iv_01);
//        showImage(mActivity, mImagelist.get(position), iv_01);
        container.addView(mList.get(position), 0);
        return mList.get(position);
    }


}
