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

import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.pojo.CuttingTool;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolConfig;
import com.apiclient.pojo.SynthesisCuttingToolLocationConfig;
import com.icomp.Iswtmv10.R;
import com.icomp.common.activity.CommonActivity;
import com.icomp.common.utils.GetItemHeight;
import com.icomp.common.utils.SysApplication;

import java.util.List;
import java.util.Map;

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
    private SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    private List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c03s001_002);
        ButterKnife.bind(this);

        Map<String, Object> paramMap = PARAM_MAP.get(1);
        synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");

        //将传递过来的合成刀具编码显示在TextView上
        tv01.setText(exChangeBig(synthesisCuttingToolConfig.getSynthesisCuttingTool().getSynthesisCode()));
        synthesisCuttingToolLocationConfigList = synthesisCuttingToolConfig.getSynthesisCuttingToolLocationConfigList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //循环遍历list显示在列表上
        if (null != synthesisCuttingToolLocationConfigList && synthesisCuttingToolLocationConfigList.size() >= 0) {
            for (int i = 0; i < synthesisCuttingToolLocationConfigList.size(); i++) {
                CuttingTool cuttingTool = synthesisCuttingToolLocationConfigList.get(i).getCuttingTool();

                // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
                if (CuttingToolTypeEnum.dj.getKey().equals(cuttingTool.getType())) {
                    // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
                    if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(cuttingTool.getConsumeType())) {
                        cuttingTool.setConsumeType(CuttingToolConsumeTypeEnum.griding_zt.getName());
                    } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(cuttingTool.getConsumeType())) {
                        cuttingTool.setConsumeType(CuttingToolConsumeTypeEnum.griding_dp.getName());
                    } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(cuttingTool.getConsumeType())) {
                        cuttingTool.setConsumeType(CuttingToolConsumeTypeEnum.single_use_dp.getName());
                    } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(cuttingTool.getConsumeType())) {
                        cuttingTool.setConsumeType(CuttingToolConsumeTypeEnum.other.getName());
                    }
                } else if (CuttingToolTypeEnum.fj.getKey().equals(cuttingTool.getType())) {
                    cuttingTool.setConsumeType(CuttingToolTypeEnum.fj.getName());
                } else if (CuttingToolTypeEnum.pt.getKey().equals(cuttingTool.getType())) {
                    cuttingTool.setConsumeType(CuttingToolTypeEnum.pt.getName());
                } else if (CuttingToolTypeEnum.other.getKey().equals(cuttingTool.getType())) {
                    cuttingTool.setConsumeType(CuttingToolTypeEnum.other.getName());
                }
            }

            MyAdapter adapter = new MyAdapter(C03S001_002Activity.this, synthesisCuttingToolLocationConfigList);
            lv01.setAdapter(adapter);
        }
    }

    //返回按钮处理--返回上一页面
    public void btnReturn(View view) {
        Intent intent = new Intent(this, C03S001_001Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
        startActivity(intent);
        finish();
    }

    //下一步按钮处理--跳转到下一页面
    public void btnNext(View view) {
        Intent intent = new Intent(this, C03S001_003Activity.class);
        // 不清空页面之间传递的值
        intent.putExtra("isClearParamMap", false);
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
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (GetItemHeight.getScreenHeight(context)*0.09));
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
