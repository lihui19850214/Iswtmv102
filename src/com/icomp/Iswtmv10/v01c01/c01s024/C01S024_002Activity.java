package com.icomp.Iswtmv10.v01c01.c01s024;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.GetItemHeight;
import com.icomp.common.utils.SysApplication;
import com.icomp.wsdl.v01c01.c01s024.endpoint.TypeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 快速查询页面2
 * Created by FanLL on 2017/6/15.
 */

public class C01S024_002Activity extends CommonActivity {

    @BindView(R.id.lv_01)
    ListView lv01;

    //快速查询参数类
    C01S024Params params = new C01S024Params();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s024_002);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //接受上一个页面传递的参数值
        params = (C01S024Params) getIntent().getSerializableExtra(PARAM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != params.list && params.list.size() > 0) {
            MyAdapter adapter = new MyAdapter(C01S024_002Activity.this, params.list);
            lv01.setAdapter(adapter);
        }
    }

    //继续按钮处理--跳转到快速查询页面1
    public void btnKeepOn(View view) {
        Intent intent = new Intent(this, C01S024_001Activity.class);
        startActivity(intent);
        finish();
    }

    //完成按钮处理--跳转到刀具管理菜单页面
    public void btnComplete(View view) {
        finish();
    }

    class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<TypeEntity> typeEntityList;

        public MyAdapter(Context context, List<TypeEntity> list) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            typeEntityList = list;
        }

        @Override
        public int getCount() {
            if (null != typeEntityList) {
                return typeEntityList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (null != typeEntityList) {
                return typeEntityList.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewholder = null;
            if (null == view) {
                view = layoutInflater.inflate(R.layout.adapter_c01s024_002, null);
                viewholder = new ViewHolder();
                viewholder.tv01 = (TextView) view.findViewById(R.id.tv_01);
                viewholder.tv02 = (TextView) view.findViewById(R.id.tv_02);
                //设置每条信息所占屏幕百分比
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (GetItemHeight.getScreenHeight(context)*0.1));
                view.setLayoutParams(layoutParams);
                view.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) view.getTag();
            }
            //显示数据
            viewholder.tv01.setText(typeEntityList.get(i).getTypeName());
            viewholder.tv02.setText(typeEntityList.get(i).getTypeCount());
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_01)
            TextView tv01;
            @BindView(R.id.tv_02)
            TextView tv02;
        }

    }

}
