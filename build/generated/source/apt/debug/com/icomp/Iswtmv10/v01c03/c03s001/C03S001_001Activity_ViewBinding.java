// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c03.c03s001;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C03S001_001Activity_ViewBinding<T extends C03S001_001Activity> implements Unbinder {
  protected T target;

  private View view2131558486;

  private View view2131558519;

  @UiThread
  public C03S001_001Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.et01 = Utils.findRequiredViewAsType(source, R.id.et_01, "field 'et01'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnScan, "field 'btnScan' and method 'onViewClicked'");
    target.btnScan = Utils.castView(view, R.id.btnScan, "field 'btnScan'", Button.class);
    view2131558486 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSearch, "field 'btnSearch' and method 'onViewClicked'");
    target.btnSearch = Utils.castView(view, R.id.btnSearch, "field 'btnSearch'", Button.class);
    view2131558519 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.btnReturn = Utils.findRequiredViewAsType(source, R.id.btnReturn, "field 'btnReturn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.et01 = null;
    target.btnScan = null;
    target.btnSearch = null;
    target.btnReturn = null;

    view2131558486.setOnClickListener(null);
    view2131558486 = null;
    view2131558519.setOnClickListener(null);
    view2131558519 = null;

    this.target = null;
  }
}
