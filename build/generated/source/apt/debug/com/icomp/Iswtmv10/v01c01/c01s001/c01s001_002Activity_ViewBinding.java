// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s001;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class c01s001_002Activity_ViewBinding<T extends c01s001_002Activity> implements Unbinder {
  protected T target;

  private View view2131558480;

  private View view2131558481;

  private View view2131558473;

  @UiThread
  public c01s001_002Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnReturn, "field 'mBtnReturn' and method 'onViewClicked'");
    target.mBtnReturn = Utils.castView(view, R.id.btnReturn, "field 'mBtnReturn'", Button.class);
    view2131558480 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnConfirm, "field 'mBtnConfirm' and method 'onViewClicked'");
    target.mBtnConfirm = Utils.castView(view, R.id.btnConfirm, "field 'mBtnConfirm'", Button.class);
    view2131558481 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mTv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'mTv01'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvXiaLa, "field 'mTvXiaLa' and method 'onViewClicked'");
    target.mTvXiaLa = Utils.castView(view, R.id.tvXiaLa, "field 'mTvXiaLa'", TextView.class);
    view2131558473 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mTv02 = Utils.findRequiredViewAsType(source, R.id.tv_02, "field 'mTv02'", TextView.class);
    target.mTvMuchStatic = Utils.findRequiredViewAsType(source, R.id.tvMuchStatic, "field 'mTvMuchStatic'", TextView.class);
    target.mEtMuch = Utils.findRequiredViewAsType(source, R.id.etMuch, "field 'mEtMuch'", EditText.class);
    target.mTvMaxSize = Utils.findRequiredViewAsType(source, R.id.tvMaxSize, "field 'mTvMaxSize'", TextView.class);
    target.mActivityC01s001002 = Utils.findRequiredViewAsType(source, R.id.activity_c01s001_002, "field 'mActivityC01s001002'", LinearLayout.class);
    target.tvPingGuLeiXing = Utils.findRequiredViewAsType(source, R.id.tvPingGuLeiXing, "field 'tvPingGuLeiXing'", TextView.class);
    target.tvDingDanXuHao = Utils.findRequiredViewAsType(source, R.id.tvDingDanXuHao, "field 'tvDingDanXuHao'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mBtnReturn = null;
    target.mBtnConfirm = null;
    target.mTv01 = null;
    target.mTvXiaLa = null;
    target.mTv02 = null;
    target.mTvMuchStatic = null;
    target.mEtMuch = null;
    target.mTvMaxSize = null;
    target.mActivityC01s001002 = null;
    target.tvPingGuLeiXing = null;
    target.tvDingDanXuHao = null;

    view2131558480.setOnClickListener(null);
    view2131558480 = null;
    view2131558481.setOnClickListener(null);
    view2131558481 = null;
    view2131558473.setOnClickListener(null);
    view2131558473 = null;

    this.target = null;
  }
}
