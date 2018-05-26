// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c00.c00s000;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C00S000_004Activity_ViewBinding<T extends C00S000_004Activity> implements Unbinder {
  protected T target;

  private View view2131558525;

  @UiThread
  public C00S000_004Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.vp01 = Utils.findRequiredViewAsType(source, R.id.vp_01, "field 'vp01'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.btn_return, "field 'btnReturn' and method 'onClick'");
    target.btnReturn = Utils.castView(view, R.id.btn_return, "field 'btnReturn'", Button.class);
    view2131558525 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.vp01 = null;
    target.btnReturn = null;

    view2131558525.setOnClickListener(null);
    view2131558525 = null;

    this.target = null;
  }
}
