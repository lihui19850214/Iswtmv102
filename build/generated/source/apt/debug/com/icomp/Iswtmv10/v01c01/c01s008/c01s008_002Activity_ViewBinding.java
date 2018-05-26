// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s008;

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

public class c01s008_002Activity_ViewBinding<T extends c01s008_002Activity> implements Unbinder {
  protected T target;

  private View view2131558463;

  private View view2131558464;

  private View view2131558467;

  @UiThread
  public c01s008_002Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'mTvTitle'", TextView.class);
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    target.mTlContainer = Utils.findRequiredViewAsType(source, R.id.tlContainer, "field 'mTlContainer'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tvScan, "field 'tvScan' and method 'onViewClicked'");
    target.tvScan = Utils.castView(view, R.id.tvScan, "field 'tvScan'", TextView.class);
    view2131558463 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
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
    target.activityC01s008002 = Utils.findRequiredViewAsType(source, R.id.activity_c01s008_002, "field 'activityC01s008002'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mTvTitle = null;
    target.tv01 = null;
    target.mTlContainer = null;
    target.tvScan = null;
    target.btnCancel = null;
    target.btnNext = null;
    target.activityC01s008002 = null;

    view2131558463.setOnClickListener(null);
    view2131558463 = null;
    view2131558464.setOnClickListener(null);
    view2131558464 = null;
    view2131558467.setOnClickListener(null);
    view2131558467 = null;

    this.target = null;
  }
}
