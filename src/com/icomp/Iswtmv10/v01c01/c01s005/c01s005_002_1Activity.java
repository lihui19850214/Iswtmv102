package com.icomp.Iswtmv10.v01c01.c01s005;
/**
 * 单品刀具报废
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.icomp.Iswtmv10.R;
import com.icomp.Iswtmv10.internet.RetrofitSingle;
import com.icomp.Iswtmv10.v01c01.c01s003.ChuKuModul;
import com.icomp.Iswtmv10.v01c01.c01s005.modul.DanPinModul;
import com.icomp.common.activity.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class c01s005_002_1Activity extends CommonActivity {

    @BindView(R.id.ivAdd)
    ImageView mIvAdd;
    @BindView(R.id.llContainer)
    LinearLayout mLlContainer;
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    private List<DanPinModul> mDanPinModulList = new ArrayList<>();
    private int position;
    private boolean isFull = true; //是否有剩余条目
    private List<View> mViewList = new ArrayList<>();
    private boolean isEmpty = true; // 判断容器是否为空
    private Retrofit mRetrofit;
    private String scarpCode;

    //查询弹框
    private PopupWindow addPopupWindow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c01s005_002_1);
        ButterKnife.bind(this);
        initRetrofit();
        Intent intent = getIntent();
        scarpCode = intent.getStringExtra("scrapCode");
    }

    private void initRetrofit() {
        mRetrofit = RetrofitSingle.newInstance();
    }

    @OnClick({R.id.ivAdd, R.id.btnCancel, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAdd:
                checkNull();
                if(isEmpty){
                    addLayout();
                    isEmpty = false;
                }else if(searchLastNum()){
                    addLayout();
                }

                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnNext:
                String jsonData = bianliAndToJson();
                if (jsonData == null || jsonData.equals("")){
                    createAlertDialog(c01s005_002_1Activity.this,"请将空行取消或者输入完整数据",1);
                    return;
                }else{
                    Intent intent = new Intent(this,c01s005_002_1Activity.class);
                    intent.putExtra("json",jsonData);
                    intent.putExtra("scrapCode",scarpCode);
                    startActivity(intent);
                }
                break;
            default:
        }
    }

    /**
     * 添加布局
     */
    private void addLayout() {
        if (!isFull) {
            createAlertDialog(this,"请把数据填写完再加",Toast.LENGTH_LONG);
            return;
        }
        final View mLinearLayout = LayoutInflater.from(this).inflate(R.layout.item_danpin_daojubaofei, null);
        final TextView etCaiLiao = (TextView) mLinearLayout.findViewById(R.id.etCailiao);
        etCaiLiao.setTransformationMethod(new AllCapTransformationMethod());
        TextView etBaofeishuliang = (TextView) mLinearLayout.findViewById(R.id.etBaofeishuliang);
        ImageView ivRemove = (ImageView) mLinearLayout.findViewById(R.id.ivRemove);
        mLinearLayout.setTag(position);
        position++;
        etCaiLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout child = (LinearLayout) mLlContainer.getChildAt(mLlContainer.getChildCount()-1);
                boolean isLast = false;
                if(mLinearLayout.getTag() == child.getTag()){
                    isLast = true;
                }
                if(!isFull && isLast){
                    isFull = true;
                }
                mLlContainer.removeView(mLinearLayout);
            }
        });
        mViewList.add(mLinearLayout);
        mLlContainer.addView(mViewList.get(mViewList.size() - 1));
        isFull = false;
    }

    /**
     * 显示数据提示dialog
     */
    //显示材料号和修磨数量的弹框
    private void showDialog() {
        if (null == addPopupWindow || !addPopupWindow.isShowing()) {
            //点击查询按钮以后，设置扫描按钮不可用
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            final View view = layoutInflater.inflate(R.layout.dialog_c01s019_001, null);
            addPopupWindow = new PopupWindow(view, 350, 450);
            addPopupWindow.setFocusable(true);
            addPopupWindow.setOutsideTouchable(false);
            addPopupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);
            final EditText etmaterialNumber = (EditText) view.findViewById(R.id.etmaterialNumber);
            etmaterialNumber.setTransformationMethod(new AllCapTransformationMethod());
            final EditText etgrindingQuantity = (EditText) view.findViewById(R.id.etgrindingQuantity);
            Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
            Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
            TextView tvNum = (TextView) view.findViewById(R.id.tvNum);
            tvNum.setText("报废数量");
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPopupWindow.dismiss();
                }
            });
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null == etmaterialNumber.getText().toString().trim() || "".equals(etmaterialNumber.getText().toString().trim())) {
                        createAlertDialog(c01s005_002_1Activity.this, "请输入材料号", Toast.LENGTH_LONG);
                    } else if (null == etgrindingQuantity.getText().toString().trim() || "".equals(etgrindingQuantity.getText().toString().trim())) {
                        createAlertDialog(c01s005_002_1Activity.this, "请输入报废数量", Toast.LENGTH_LONG);
                    } else {
                        if(Integer.parseInt(etgrindingQuantity.getText().toString())<=0){
                            createAlertDialog(c01s005_002_1Activity.this,"数量要大于0",0);
                            return;
                        }
                        if(checkIsExit(etmaterialNumber.getText().toString())){
                            createAlertDialog(c01s005_002_1Activity.this,"已存在",Toast.LENGTH_SHORT);
                        }else{
                            addData(etmaterialNumber.getText().toString(),etgrindingQuantity.getText().toString());
                        }
                        addPopupWindow.dismiss();
                    }
                }
            });
        }
    }


    /**
     * 添加数据
     */
    private void addData(String cailiaohao,String num){
        LinearLayout mDataLin = (LinearLayout) mLlContainer.getChildAt(mLlContainer.getChildCount()-1);
        for (int i = 0; i<mDataLin.getChildCount();i++){
            View child = mDataLin.getChildAt(i);
            if(child instanceof LinearLayout){
                int child2Coutn = ((LinearLayout) child).getChildCount();
                for (int j = 0;j<child2Coutn;j++){
                    View child2 = ((LinearLayout) child).getChildAt(j);
                    if(child2 instanceof TextView){
                        switch (child2.getId()){
                            case R.id.etCailiao:
                                ((TextView) child2).setText(cailiaohao);
                                break;
                            case R.id.etBaofeishuliang:
                                ((TextView) child2).setText(num);
                                break;
                            default:
                        }
                    }
                }
            }

        }
    }

    /**
     * 遍历所有数据并转化为json
     */
    private String bianliAndToJson(){
        mDanPinModulList.clear();
        if(mLlContainer.getChildCount() == 0){
            return null;
        }
        for (int k = 0; k<mLlContainer.getChildCount();k++){
            LinearLayout mDataLin = (LinearLayout) mLlContainer.getChildAt(k);
            for (int i = 0; i<mDataLin.getChildCount();i++){
                View child = mDataLin.getChildAt(i);
                if(child instanceof LinearLayout){
                    int child2Coutn = ((LinearLayout) child).getChildCount();
                    DanPinModul c = new DanPinModul();
                    for (int j = 0;j<child2Coutn;j++){
                        View child2 = ((LinearLayout) child).getChildAt(j);
                            switch (child2.getId()){
                                case R.id.etCailiao:
                                    c.setCaiLiao(exChangeBig(((TextView)child2).getText().toString()));
                                    break;
                                case R.id.etBaofeishuliang:
                                    c.setNum(((TextView)child2).getText().toString());
                                    if(c.getNum().equals("") || c.getNum().equals("0")){
                                        return null;
                                    }
                                    break;
                            }

                    }
                    mDanPinModulList.add(c);

                }

            }
        }
        Gson gson = new Gson();
        return gson.toJson(mDanPinModulList);
    }

    /**
     * 判断最后一行是否输入结束
     */
    private boolean searchLastNum(){
        LinearLayout mDataLin = (LinearLayout) mLlContainer.getChildAt(mLlContainer.getChildCount()-1);
        String lastNum = null;
        String lastCailiao = null;
        for (int i = 0; i<mDataLin.getChildCount();i++){
            View child = mDataLin.getChildAt(i);
            if(child instanceof LinearLayout){
                int child2Coutn = ((LinearLayout) child).getChildCount();
                for (int j = 0;j<child2Coutn;j++){
                    View child2 = ((LinearLayout) child).getChildAt(j);
                        switch (child2.getId()){
                            case R.id.etCailiao:
                                lastCailiao = ((TextView) child2).getText().toString();
                                break;
                            case R.id.etBaofeishuliang:
                                lastNum = ((TextView) child2).getText().toString();
                                break;

                        }

                }
            }

        }
        if(lastCailiao == null || lastCailiao.equals("")){
            createAlertDialog(this,"请添加材料号",Toast.LENGTH_SHORT);
            return false;
        }else if(lastNum == null || lastNum.equals("")){
            createAlertDialog(this,"请添加报废数量",Toast.LENGTH_SHORT);
            return false;
        }else{
            isFull = true;
            return true;
        }

    }

    /**
     * 判断容器是否为null
     */
    private void checkNull(){
        LinearLayout mDataLin = (LinearLayout) mLlContainer.getChildAt(mLlContainer.getChildCount()-1);
        if(mDataLin == null){
            isEmpty = true;
        }else{
            isEmpty = false;
        }
    }

    /**
     * 遍历所有数据并转化为json
     */
    private boolean checkIsExit(String code){
        if(mLlContainer.getChildCount() == 0){
            return false;
        }
        for (int k = 0; k<mLlContainer.getChildCount();k++){
            LinearLayout mDataLin = (LinearLayout) mLlContainer.getChildAt(k);
            for (int i = 0; i<mDataLin.getChildCount();i++){
                View child = mDataLin.getChildAt(i);
                if(child instanceof LinearLayout){
                    int child2Coutn = ((LinearLayout) child).getChildCount();
                    for (int j = 0;j<child2Coutn;j++){
                        View child2 = ((LinearLayout) child).getChildAt(j);
                        if(child2 instanceof TextView){
                            switch (child2.getId()){
                                case R.id.etCailiao:
                                    if(((TextView) child2).getText().toString().equals(code)){
                                        return true;
                                    }break;
                            }

                        }
                    }
                }

            }
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Intent intent2 = getIntent();
        if(intent2 == null){
            return;
        }else{
            Bundle bundle = intent2.getExtras();
            if (bundle == null){
                return;
            }
            boolean isClear = bundle.getBoolean("isClear",false);
            if(isClear){
                mLlContainer.removeAllViews();
            }
        }
    }
}
