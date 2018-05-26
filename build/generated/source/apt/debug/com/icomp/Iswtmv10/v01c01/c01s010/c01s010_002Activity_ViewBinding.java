// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s010;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class c01s010_002Activity_ViewBinding<T extends c01s010_002Activity> implements Unbinder {
  protected T target;

  private View view2131558480;

  private View view2131558467;

  private View view2131558463;

  @UiThread
  public c01s010_002Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mTlContainer = Utils.findRequiredViewAsType(source, R.id.tlContainer, "field 'mTlContainer'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnReturn, "field 'mBtnReturn' and method 'onViewClicked'");
    target.mBtnReturn = Utils.castView(view, R.id.btnReturn, "field 'mBtnReturn'", Button.class);
    view2131558480 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnNext, "field 'mBtnNext' and method 'onViewClicked'");
    target.mBtnNext = Utils.castView(view, R.id.btnNext, "field 'mBtnNext'", Button.class);
    view2131558467 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvShenqingRen = Utils.findRequiredViewAsType(source, R.id.tvShenqingRen, "field 'tvShenqingRen'", TextView.class);
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvScan, "field 'tvScan' and method 'onViewClicked'");
    target.tvScan = Utils.castView(view, R.id.tvScan, "field 'tvScan'", TextView.class);
    view2131558463 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.activityC01s010002 = Utils.findRequiredViewAsType(source, R.id.activity_c01s010_002, "field 'activityC01s010002'", LinearLayout.class);
    target.cbDiudao = Utils.findRequiredViewAsType(source, R.id.cbDiudao, "field 'cbDiudao'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mTlContainer = null;
    target.mBtnReturn = null;
    target.mBtnNext = null;
    target.tvTitle = null;
    target.tvShenqingRen = null;
    target.tv01 = null;
    target.tvScan = null;
    target.activityC01s010002 = null;
    target.cbDiudao = null;

    view2131558480.setOnClickListener(null);
    view2131558480 = null;
    view2131558467.setOnClickListener(null);
    view2131558467 = null;
    view2131558463.setOnClickListener(null);
    view2131558463 = null;

    this.target = null;
  }
}
