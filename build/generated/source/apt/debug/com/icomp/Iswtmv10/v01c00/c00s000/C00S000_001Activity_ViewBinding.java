// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c00.c00s000;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C00S000_001Activity_ViewBinding<T extends C00S000_001Activity> implements Unbinder {
  protected T target;

  private View view2131558460;

  private View view2131558461;

  @UiThread
  public C00S000_001Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnInputLogin, "field 'btnInputLogin' and method 'onViewClicked'");
    target.btnInputLogin = Utils.castView(view, R.id.btnInputLogin, "field 'btnInputLogin'", Button.class);
    view2131558460 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnScanLogin, "field 'btnScanLogin' and method 'onViewClicked'");
    target.btnScanLogin = Utils.castView(view, R.id.btnScanLogin, "field 'btnScanLogin'", Button.class);
    view2131558461 = view;
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

    target.btnInputLogin = null;
    target.btnScanLogin = null;

    view2131558460.setOnClickListener(null);
    view2131558460 = null;
    view2131558461.setOnClickListener(null);
    view2131558461 = null;

    this.target = null;
  }
}
