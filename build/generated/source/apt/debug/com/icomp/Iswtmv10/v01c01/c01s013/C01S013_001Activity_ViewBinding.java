// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s013;

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

public class C01S013_001Activity_ViewBinding<T extends C01S013_001Activity> implements Unbinder {
  protected T target;

  private View view2131558567;

  private View view2131558525;

  private View view2131558583;

  @UiThread
  public C01S013_001Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    target.tv02 = Utils.findRequiredViewAsType(source, R.id.tv_02, "field 'tv02'", TextView.class);
    target.tv03 = Utils.findRequiredViewAsType(source, R.id.tv_03, "field 'tv03'", TextView.class);
    target.tv04 = Utils.findRequiredViewAsType(source, R.id.tv_04, "field 'tv04'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_scan, "field 'btnScan' and method 'onViewClicked'");
    target.btnScan = Utils.castView(view, R.id.btn_scan, "field 'btnScan'", TextView.class);
    view2131558567 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_return, "field 'btnReturn' and method 'onViewClicked'");
    target.btnReturn = Utils.castView(view, R.id.btn_return, "field 'btnReturn'", Button.class);
    view2131558525 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm, "field 'btnConfirm' and method 'onViewClicked'");
    target.btnConfirm = Utils.castView(view, R.id.btn_confirm, "field 'btnConfirm'", Button.class);
    view2131558583 = view;
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

    target.tv01 = null;
    target.tv02 = null;
    target.tv03 = null;
    target.tv04 = null;
    target.btnScan = null;
    target.btnReturn = null;
    target.btnConfirm = null;

    view2131558567.setOnClickListener(null);
    view2131558567 = null;
    view2131558525.setOnClickListener(null);
    view2131558525 = null;
    view2131558583.setOnClickListener(null);
    view2131558583 = null;

    this.target = null;
  }
}
