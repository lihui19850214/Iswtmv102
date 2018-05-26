package com.icomp.Iswtmv10.v01c03.c03s001;

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

import com.apiclient.pojo.CuttingTool;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.pojo.SynthesisCuttingToolLocationConfig;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.GetItemHeight;
import com.icomp.common.utils.SysApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 合成刀具初始化页面2
 */

public class C03S001_002Activity extends CommonActivity {

    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.lv_01)
    ListView lv01;

    //合成刀具初始化参数类
    private SynthesisCuttingToolConfig params = new SynthesisCuttingToolConfig();
    private List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s001_002);
        ButterKnife.bind(this);
        //创建Activity时，添加到List进行管理
        SysApplication.getInstance().addActivity(this);
        //接受上一个页面传递过来的参数
        params = (SynthesisCuttingToolConfig) getIntent().getSerializableExtra(PARAM);
        //将传递过来的合成刀具编码显示在TextView上
        tv01.setText(exChangeBig(params.getSynthesisCuttingTool().getSynthesisCode().trim()));
        synthesisCuttingToolLocationConfigList = params.getSynthesisCuttingToolLocationConfigList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //循环遍历list显示在列表上
        if (null != synthesisCuttingToolLocationConfigList && synthesisCuttingToolLocationConfigList.size() >= 0) {
            for (int i = 0; i < synthesisCuttingToolLocationConfigList.size(); i++) {
                CuttingTool cuttingTool = synthesisCuttingToolLocationConfigList.get(i).getCuttingTool();
                //刀具类型：1钻头、2刀片、3一体刀、4专机、9其他
                if (ONE == cuttingTool.getConsumeType() || cuttingTool.getConsumeType().equals(ONE)) {
                    cuttingTool.setConsumeType("钻头");
                } else if (TWO == cuttingTool.getConsumeType() || cuttingTool.getConsumeType().equals(TWO)) {
                    cuttingTool.setConsumeType("刀片");
                } else if (THREE == cuttingTool.getConsumeType() || cuttingTool.getConsumeType().equals(THREE)) {
                    cuttingTool.setConsumeType("一体刀");
                } else if (FOUR == cuttingTool.getConsumeType() || cuttingTool.getConsumeType().equals(NINE)) {
                    cuttingTool.setConsumeType("专机");
                } else if (NINE == cuttingTool.getConsumeType() || cuttingTool.getConsumeType().equals(NINE)) {
                    cuttingTool.setConsumeType("其他");
                }
            }

            MyAdapter adapter = new MyAdapter(C03S001_002Activity.this, synthesisCuttingToolLocationConfigList);
            lv01.setAdapter(adapter);
        }
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        Intent intent = new Intent(this, C03S001_001Activity.class);
        //合成刀具编码返回上一页面
        intent.putExtra(PARAM1, params.getSynthesisCuttingTool().getSynthesisCode());
        startActivity(intent);
        finish();
    }

    //下一步按钮处理--跳转到下一页面
    public void btnNext(View view) {
        Intent intent = new Intent(this, C03S001_003Activity.class);
        intent.putExtra(PARAM, params);
        startActivity(intent);
        finish();
    }

    class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList;

        public MyAdapter(Context context, List<SynthesisCuttingToolLocationConfig> list) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            synthesisCuttingToolLocationConfigList = list;
        }

        @Override
        public int getCount() {
            if (null != synthesisCuttingToolLocationConfigList) {
                return synthesisCuttingToolLocationConfigList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (null != synthesisCuttingToolLocationConfigList) {
                return synthesisCuttingToolLocationConfigList.get(i);
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
                view = layoutInflater.inflate(R.layout.adapter_c03s001_002, null);
                viewholder = new ViewHolder();
                viewholder.tv01 = (TextView) view.findViewById(R.id.tv_01);
                viewholder.tv02 = (TextView) view.findViewById(R.id.tv_02);
                viewholder.tv03 = (TextView) view.findViewById(R.id.tv_03);
                //设置每条信息所占屏幕百分比
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (GetItemHeight.getScreenHeight(context)*0.07));
                view.setLayoutParams(layoutParams);
                view.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) view.getTag();
            }
            //显示数据
            viewholder.tv01.setText(exChangeBig(synthesisCuttingToolLocationConfigList.get(i).getCuttingTool().getBusinessCode()));//材料号
            //1钻头、2刀片、3一体刀、4专机、9其他
            viewholder.tv02.setText(synthesisCuttingToolLocationConfigList.get(i).getCuttingTool().getConsumeType());//刀具类型
            viewholder.tv03.setText(synthesisCuttingToolLocationConfigList.get(i).getCount().toString());//刀具数量，类型转化为String类型
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_01)
            TextView tv01;
            @BindView(R.id.tv_02)
            TextView tv02;
            @BindView(R.id.tv_03)
            TextView tv03;
        }

    }

}
