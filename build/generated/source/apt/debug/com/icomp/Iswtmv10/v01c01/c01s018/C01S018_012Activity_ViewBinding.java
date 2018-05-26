// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s018;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S018_012Activity_ViewBinding<T extends C01S018_012Activity> implements Unbinder {
  protected T target;

  private View view2131558486;

  @UiThread
  public C01S018_012Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.ll01 = Utils.findRequiredViewAsType(source, R.id.ll_01, "field 'll01'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btnScan, "field 'btnScan' and method 'onViewClicked'");
    target.btnScan = Utils.castView(view, R.id.btnScan, "field 'btnScan'", Button.class);
    view2131558486 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ll01 = null;
    target.btnScan = null;

    view2131558486.setOnClickListener(null);
    view2131558486 = null;

    this.target = null;
  }
}
