// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s007;

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

public class C01S007_003Activity_ViewBinding<T extends C01S007_003Activity> implements Unbinder {
  protected T target;

  private View view2131558470;

  private View view2131558471;

  @UiThread
  public C01S007_003Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnComplete, "field 'btnComplete' and method 'onViewClicked'");
    target.btnComplete = Utils.castView(view, R.id.btnComplete, "field 'btnComplete'", Button.class);
    view2131558470 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnGoOn, "field 'btnGoOn' and method 'onViewClicked'");
    target.btnGoOn = Utils.castView(view, R.id.btnGoOn, "field 'btnGoOn'", Button.class);
    view2131558471 = view;
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

    target.btnComplete = null;
    target.btnGoOn = null;

    view2131558470.setOnClickListener(null);
    view2131558470 = null;
    view2131558471.setOnClickListener(null);
    view2131558471 = null;

    this.target = null;
  }
}
