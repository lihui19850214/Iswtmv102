// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s002;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class c01s002_002Activity_ViewBinding<T extends c01s002_002Activity> implements Unbinder {
  protected T target;

  private View view2131558486;

  private View view2131558487;

  private View view2131558464;

  private View view2131558467;

  @UiThread
  public c01s002_002Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mTvNum = Utils.findRequiredViewAsType(source, R.id.tvNum, "field 'mTvNum'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnScan, "field 'mBtnScan' and method 'onViewClicked'");
    target.mBtnScan = Utils.castView(view, R.id.btnScan, "field 'mBtnScan'", Button.class);
    view2131558486 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnStop, "field 'mBtnStop' and method 'onViewClicked'");
    target.mBtnStop = Utils.castView(view, R.id.btnStop, "field 'mBtnStop'", Button.class);
    view2131558487 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCancel, "field 'mBtnCancel' and method 'onViewClicked'");
    target.mBtnCancel = Utils.castView(view, R.id.btnCancel, "field 'mBtnCancel'", Button.class);
    view2131558464 = view;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mTvNum = null;
    target.mBtnScan = null;
    target.mBtnStop = null;
    target.mBtnCancel = null;
    target.mBtnNext = null;

    view2131558486.setOnClickListener(null);
    view2131558486 = null;
    view2131558487.setOnClickListener(null);
    view2131558487 = null;
    view2131558464.setOnClickListener(null);
    view2131558464 = null;
    view2131558467.setOnClickListener(null);
    view2131558467 = null;

    this.target = null;
  }
}
