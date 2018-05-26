// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s009;

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

public class C01S009_001Activity_ViewBinding<T extends C01S009_001Activity> implements Unbinder {
  protected T target;

  private View view2131558463;

  private View view2131558464;

  @UiThread
  public C01S009_001Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tvScan, "field 'mTvScan' and method 'onViewClicked'");
    target.mTvScan = Utils.castView(view, R.id.tvScan, "field 'mTvScan'", TextView.class);
    view2131558463 = view;
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
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mTvScan = null;
    target.mBtnCancel = null;
    target.tvTitle = null;

    view2131558463.setOnClickListener(null);
    view2131558463 = null;
    view2131558464.setOnClickListener(null);
    view2131558464 = null;

    this.target = null;
  }
}
