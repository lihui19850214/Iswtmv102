package com.icomp.Iswtmv10.v01c01.c01s010;
/**
 * 刀具换装页面2
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.apiclient.constants.CuttingToolConsumeTypeEnum;
import com.apiclient.constants.CuttingToolTypeEnum;
import com.apiclient.pojo.*;
import com.apiclient.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.IRequest;
import com.icomp.Iswtmv10.internet.MyCallBack;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.common.activity.AuthorizationWindowCallBack;
import com.icomp.common.activity.CommonActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c01s010_003Activity extends CommonActivity {

    @BindView(R.id.tlContainer)
    LinearLayout mTlContainer;
    @BindView(R.id.btnReturn)
    Button mBtnReturn;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvShenqingRen)
    TextView tvShenqingRen;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.activity_c01s010_003)
    LinearLayout activityC01s010003;


    private Retrofit retrofit;


    private SynthesisCuttingToolExchange synthesisCuttingToolExchange;

    private List<List<Map<String, Object>>> outsideListData = new ArrayList<>();

    SynthesisCuttingToolConfig synthesisCuttingToolConfig = new SynthesisCuttingToolConfig();
    SynthesisCuttingToolBind synthesisCuttingToolBind = new SynthesisCuttingToolBind();

    // 换装数量
    private List<UpCuttingToolVO> upCuttingToolVOList = new ArrayList<>();
    // 丢刀数量
    private List<DownCuttingToolVO> downCuttingToolVOList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s010_003);
        ButterKnife.bind(this);
        retrofit = RetrofitSingle.newInstance();



        Map<String, Object> paramMap = PARAM_MAP.get(1);
        tv01.setText((String) paramMap.get("title"));// 合成刀具编码
        outsideListData = (List<List<Map<String, Object>>>) paramMap.get("outsideListData");
        synthesisCuttingToolConfig = (SynthesisCuttingToolConfig) paramMap.get("synthesisCuttingToolConfig");
        synthesisCuttingToolBind = (SynthesisCuttingToolBind) paramMap.get("synthesisCuttingToolBind");


        for (int i=0; i<outsideListData.size(); i++) {
            addLayout(outsideListData.get(i));
        }
    }

    @OnClick({R.id.btnReturn, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnReturn:
                Intent intent = new Intent(c01s010_003Activity.this, c01s010_002Activity.class);
                // 不清空页面之间传递的值
                intent.putExtra("isClearParamMap", false);
                startActivity(intent);
                finish();
                break;
            case R.id.btnConfirm:
                authorizationWindow(1, new AuthorizationWindowCallBack() {
                    @Override
                    public void success(List<AuthCustomer> authorizationList) {
                        requestData(authorizationList);
                    }

                    @Override
                    public void fail() {

                    }
                });
                break;
        }
    }



    int outsideRowNumber = 0;// 外部行号

    @android.support.annotation.IdRes
    int tvCailiao = 1000;
    int tvDaoJuType = 1001;
    int tvDaoJuNum = 1002;
    int tvZuzhuangNum = 1003;//组装
    int tvDiudaoNum = 1004;//丢刀

    /**
     * 添加布局
     */
    private void addLayout(List<Map<String, Object>> insideListDate) {
        SynthesisCuttingToolLocationConfig synthesisCuttingToolLocationConfig = null;
        UpCuttingToolVO upCuttingToolVO = null;
        DownCuttingToolVO downCuttingToolVO = null;


        CuttingTool cuttingTool1 = null;
        UpCuttingToolVO upCuttingToolVO1 = null;
        DownCuttingToolVO downCuttingToolVO1 = null;

        CuttingTool cuttingTool2 = null;
        UpCuttingToolVO upCuttingToolVO2 = null;
        DownCuttingToolVO downCuttingToolVO2 = null;

        CuttingTool cuttingTool3 = null;
        UpCuttingToolVO upCuttingToolVO3 = null;
        DownCuttingToolVO downCuttingToolVO3 = null;


        for (int i=0; i<insideListDate.size(); i++) {
            if (i == 0) {
                Map<String, Object> map = insideListDate.get(i);
                synthesisCuttingToolLocationConfig = (SynthesisCuttingToolLocationConfig) map.get("synthesisCuttingToolLocationConfig");
                upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
            } else if (i == 1) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool1 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO1 = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO1 = (DownCuttingToolVO) map.get("downCuttingToolVO");
            } else if (i == 2) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool2 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO2 = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO2 = (DownCuttingToolVO) map.get("downCuttingToolVO");
            } else if (i == 3) {
                Map<String, Object> map = insideListDate.get(i);
                cuttingTool3 = (CuttingTool) map.get("cuttingTool");
                upCuttingToolVO3 = (UpCuttingToolVO) map.get("upCuttingToolVO");
                downCuttingToolVO3 = (DownCuttingToolVO) map.get("downCuttingToolVO");
            }
        }


        String daojuType = "";

//        //刀具类型(1钻头、2刀片、3一体刀、4专机、9其他)
//        if ("1".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "钻头";
//        } else if ("2".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "刀片";
//        } else if ("3".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "一体刀";
//        } else if ("4".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "专机";
//        } else if ("9".equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
//            daojuType = "其他";
//        }

        // dj("1","刀具"),fj("2","辅具"),pt("3","配套"),other("9","其他");
        if (CuttingToolTypeEnum.dj.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            // griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");
            if (CuttingToolConsumeTypeEnum.griding_zt.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.griding_zt.getName();
            } else if (CuttingToolConsumeTypeEnum.griding_dp.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.griding_dp.getName();
            } else if (CuttingToolConsumeTypeEnum.single_use_dp.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.single_use_dp.getName();
            } else if (CuttingToolConsumeTypeEnum.other.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getConsumeType())) {
                daojuType = CuttingToolConsumeTypeEnum.other.getName();
            }
        } else if (CuttingToolTypeEnum.fj.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.fj.getName();
        } else if (CuttingToolTypeEnum.pt.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.pt.getName();
        } else if (CuttingToolTypeEnum.other.getKey().equals(synthesisCuttingToolLocationConfig.getCuttingTool().getType())) {
            daojuType = CuttingToolTypeEnum.other.getName();
        }


        ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams param2 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        TableRow.LayoutParams param3 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1f);


        // 行
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(param);
        tableRow.setBackgroundResource(R.drawable.table_border_c);


        // 内部table1
        TableLayout tableLayout1 = new TableLayout(this);
        tableLayout1.setLayoutParams(param2);
        tableLayout1.addView(getRow(tvCailiao, synthesisCuttingToolLocationConfig.getCuttingTool().getBusinessCode()));

        if (cuttingTool1 != null) {
            tableLayout1.addView(getRow(tvCailiao, cuttingTool1.getBusinessCode()));
        }

        if (cuttingTool2 != null) {
            tableLayout1.addView(getRow(tvCailiao, cuttingTool2.getBusinessCode()));
        }

        if (cuttingTool3 != null) {
            tableLayout1.addView(getRow(tvCailiao, cuttingTool3.getBusinessCode()));
        }

        // 添加到行中
        tableRow.addView(tableLayout1);
        tableRow.addView(getImage());


        // 内部table2
        TableLayout tableLayout2 = new TableLayout(this);
        tableLayout2.setLayoutParams(param2);
        tableLayout2.addView(getRow(tvDaoJuType, daojuType));

        if (cuttingTool1 != null) {
            tableLayout2.addView(getRow(tvDaoJuType, daojuType));
        }

        if (cuttingTool2 != null) {
            tableLayout2.addView(getRow(tvDaoJuType, daojuType));
        }

        if (cuttingTool3 != null) {
            tableLayout2.addView(getRow(tvDaoJuType, daojuType));
        }

        // 添加到行中
        tableRow.addView(tableLayout2);
        tableRow.addView(getImage());


        TextView tv1 = new TextView(this);
        tv1.setId(tvDaoJuNum);
        tv1.setLayoutParams(param3);
        tv1.setGravity(Gravity.CENTER);
        tv1.setText(synthesisCuttingToolLocationConfig.getCount().toString());//总数量


        // 添加到行中
        tableRow.addView(tv1);
        tableRow.addView(getImage());


        // 内部table3
        TableLayout tableLayout3 = new TableLayout(this);
        tableLayout3.setLayoutParams(param2);
        tableLayout3.addView(getRow(tvZuzhuangNum, String.valueOf(upCuttingToolVO.getUpCount())));

        if (cuttingTool1 != null) {
            tableLayout3.addView(getRow(tvZuzhuangNum, String.valueOf(upCuttingToolVO1.getUpCount())));
        }

        if (cuttingTool2 != null) {
            tableLayout3.addView(getRow(tvZuzhuangNum, String.valueOf(upCuttingToolVO2.getUpCount())));
        }

        if (cuttingTool3 != null) {
            tableLayout3.addView(getRow(tvZuzhuangNum, String.valueOf(upCuttingToolVO3.getUpCount())));
        }

        // 添加到行中
        tableRow.addView(tableLayout3);
        tableRow.addView(getImage());

        // 内部table4
        TableLayout tableLayout4 = new TableLayout(this);
        tableLayout4.setLayoutParams(param2);
        tableLayout4.addView(getRow(tvDiudaoNum, String.valueOf(downCuttingToolVO.getDownLostCount())));

        if (cuttingTool1 != null) {
            tableLayout4.addView(getRow(tvDiudaoNum, String.valueOf(downCuttingToolVO1.getDownLostCount())));
        }

        if (cuttingTool2 != null) {
            tableLayout4.addView(getRow(tvDiudaoNum, String.valueOf(downCuttingToolVO2.getDownLostCount())));
        }

        if (cuttingTool3 != null) {
            tableLayout4.addView(getRow(tvDiudaoNum, String.valueOf(downCuttingToolVO3.getDownLostCount())));
        }

        // 添加到行中
        tableRow.addView(tableLayout4);

        mTlContainer.addView(tableRow);

        // 外部行号+1
        outsideRowNumber++;
    }


    private TableRow getRow(int id, String text) {
        TableRow.LayoutParams param = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams param2 = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())), 1f);

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(param);

        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(param2);
        tv1.setGravity(Gravity.CENTER);
        tv1.setId(id);
        tv1.setText(text);

        tableRow.addView(tv1);

        return tableRow;
    }

    private ImageView getImage() {
        TableRow.LayoutParams param = new TableRow.LayoutParams(
//                getResources().getDimensionPixelOffset(R.dimen.image_height),// 设置1dp宽度
                ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())),
                ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(param);
        imageView.setBackgroundResource(R.color.baseColor);

        return imageView;
    }


    private void requestData(List<AuthCustomer> authorizationList) {
        loading.show();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> headsMap = new HashMap<>();
        // 需要授权信息
        if (is_need_authorization && authorizationList != null) {
            try {
                //设定用户访问信息
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
                String userInfoJson = sharedPreferences.getString("loginInfo", null);

                AuthCustomer authCustomer = mapper.readValue(userInfoJson, AuthCustomer.class);

                // 授权信息
                ImpowerRecorder impowerRecorder = new ImpowerRecorder();
                impowerRecorder.setOperatorUserCode(authCustomer.getCode());//操作者code
                impowerRecorder.setOperatorUserName(authCustomer.getName());//操作者姓名
                impowerRecorder.setImpowerUser(authorizationList.get(0).getCode());//授权人code
                impowerRecorder.setImpowerUserName(authorizationList.get(0).getName());//授权人名称
                impowerRecorder.setOperatorKey(OperationEnum.SynthesisCuttingTool_Exchange.getKey().toString());//操作key
                impowerRecorder.setOperatorValue(OperationEnum.SynthesisCuttingTool_Exchange.getName());//操作者code

                headsMap.put("impower", mapper.writeValueAsString(impowerRecorder));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i=0; i<outsideListData.size(); i++) {
            List<Map<String, Object>> insideListDate = outsideListData.get(i);
            for (int j=0; j<insideListDate.size(); j++) {
                Map<String, Object> map = insideListDate.get(j);

                UpCuttingToolVO upCuttingToolVO = (UpCuttingToolVO) map.get("upCuttingToolVO");
                if (upCuttingToolVO.getUpCount() > 0) {
                    upCuttingToolVOList.add(upCuttingToolVO);
                }

                DownCuttingToolVO downCuttingToolVO = (DownCuttingToolVO) map.get("downCuttingToolVO");
                if (downCuttingToolVO.getDownCount() > 0) {
                    downCuttingToolVOList.add(downCuttingToolVO);
                }
            }
        }


        IRequest iRequest = retrofit.create(IRequest.class);


        ExChangeVO exChangeVO = new ExChangeVO();
        exChangeVO.setDownCuttingToolVOS(downCuttingToolVOList);
        exChangeVO.setUpCuttingToolVOS(upCuttingToolVOList);
        exChangeVO.setSynthesisCuttingToolBind(synthesisCuttingToolBind);

        //TODO 不知道参数
//        exChangeVO.setSynthesisCuttingToolExchange();

        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(exChangeVO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);

        Call<String> exChange = iRequest.exChange(body, headsMap);
        exChange.enqueue(new MyCallBack<String>() {
            @Override
            public void _onResponse(Response<String> response) {
                try {
                    if (response.raw().code() == 200) {
                        Intent intent = new Intent(c01s010_003Activity.this, c01s010_004Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        createAlertDialog(c01s010_003Activity.this, response.errorBody().string(), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    loading.dismiss();
                }
            }

            @Override
            public void _onFailure(Throwable t) {
                loading.dismiss();
                createAlertDialog(c01s010_003Activity.this, getString(R.string.netConnection), Toast.LENGTH_LONG);
            }
        });
    }

}
