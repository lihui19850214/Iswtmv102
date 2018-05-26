// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s018;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S018_003Activity_ViewBinding<T extends C01S018_003Activity> implements Unbinder {
  protected T target;

  private View view2131558464;

  private View view2131558467;

  private View view2131558463;

  @UiThread
  public C01S018_003Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.mLlContainer = Utils.findRequiredViewAsType(source, R.id.llContainer, "field 'mLlContainer'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnCancel, "field 'btnCancel' and method 'onViewClicked'");
    target.btnCancel = Utils.castView(view, R.id.btnCancel, "field 'btnCancel'", Button.class);
    view2131558464 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnNext, "field 'btnNext' and method 'onViewClicked'");
    target.btnNext = Utils.castView(view, R.id.btnNext, "field 'btnNext'", Button.class);
    view2131558467 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tv00 = Utils.findRequiredViewAsType(source, R.id.tv_00, "field 'tv00'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvScan, "field 'tvScan' and method 'onViewClicked'");
    target.tvScan = Utils.castView(view, R.id.tvScan, "field 'tvScan'", TextView.class);
    view2131558463 = view;
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

    target.tvTitle = null;
    target.mLlContainer = null;
    target.btnCancel = null;
    target.btnNext = null;
    target.tv00 = null;
    target.tvScan = null;

    view2131558464.setOnClickListener(null);
    view2131558464 = null;
    view2131558467.setOnClickListener(null);
    view2131558467 = null;
    view2131558463.setOnClickListener(null);
    view2131558463 = null;

    this.target = null;
  }
}
