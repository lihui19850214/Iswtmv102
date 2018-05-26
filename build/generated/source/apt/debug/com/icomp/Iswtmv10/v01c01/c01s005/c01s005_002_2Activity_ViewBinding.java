// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s005;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class c01s005_002_2Activity_ViewBinding<T extends c01s005_002_2Activity> implements Unbinder {
  protected T target;

  private View view2131558463;

  private View view2131558464;

  private View view2131558467;

  private View view2131558501;

  @UiThread
  public c01s005_002_2Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mLlContainer = Utils.findRequiredViewAsType(source, R.id.llContainer, "field 'mLlContainer'", LinearLayout.class);
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
    view = Utils.findRequiredView(source, R.id.ivAdd, "field 'ivAdd' and method 'onViewClicked'");
    target.ivAdd = Utils.castView(view, R.id.ivAdd, "field 'ivAdd'", ImageView.class);
    view2131558501 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.textView4 = Utils.findRequiredViewAsType(source, R.id.textView4, "field 'textView4'", TextView.class);
    target.activityC01s0050022 = Utils.findRequiredViewAsType(source, R.id.activity_c01s005_002_2, "field 'activityC01s0050022'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mLlContainer = null;
    target.mTvScan = null;
    target.mBtnCancel = null;
    target.mBtnNext = null;
    target.tvTitle = null;
    target.ivAdd = null;
    target.textView4 = null;
    target.activityC01s0050022 = null;

    view2131558463.setOnClickListener(null);
    view2131558463 = null;
    view2131558464.setOnClickListener(null);
    view2131558464 = null;
    view2131558467.setOnClickListener(null);
    view2131558467 = null;
    view2131558501.setOnClickListener(null);
    view2131558501 = null;

    this.target = null;
  }
}
