// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s011;

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

public class C01S011_002Activity_ViewBinding<T extends C01S011_002Activity> implements Unbinder {
  protected T target;

  private View view2131558463;

  private View view2131558506;

  private View view2131558489;

  private View view2131558567;

  @UiThread
  public C01S011_002Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
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
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_01, "field 'll01' and method 'onViewClicked'");
    target.ll01 = Utils.castView(view, R.id.ll_01, "field 'll01'", LinearLayout.class);
    view2131558506 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tv02 = Utils.findRequiredViewAsType(source, R.id.tv_02, "field 'tv02'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_02, "field 'll02' and method 'onViewClicked'");
    target.ll02 = Utils.castView(view, R.id.ll_02, "field 'll02'", LinearLayout.class);
    view2131558489 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_scan, "field 'btnScan' and method 'onViewClicked'");
    target.btnScan = Utils.castView(view, R.id.btn_scan, "field 'btnScan'", TextView.class);
    view2131558567 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.btnReturn = Utils.findRequiredViewAsType(source, R.id.btn_return, "field 'btnReturn'", Button.class);
    target.btnNext = Utils.findRequiredViewAsType(source, R.id.btn_next, "field 'btnNext'", Button.class);
    target.tvDesc = Utils.findRequiredViewAsType(source, R.id.tv_desc, "field 'tvDesc'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvTitle = null;
    target.tv00 = null;
    target.tvScan = null;
    target.tv01 = null;
    target.ll01 = null;
    target.tv02 = null;
    target.ll02 = null;
    target.btnScan = null;
    target.btnReturn = null;
    target.btnNext = null;
    target.tvDesc = null;

    view2131558463.setOnClickListener(null);
    view2131558463 = null;
    view2131558506.setOnClickListener(null);
    view2131558506 = null;
    view2131558489.setOnClickListener(null);
    view2131558489 = null;
    view2131558567.setOnClickListener(null);
    view2131558567 = null;

    this.target = null;
  }
}
