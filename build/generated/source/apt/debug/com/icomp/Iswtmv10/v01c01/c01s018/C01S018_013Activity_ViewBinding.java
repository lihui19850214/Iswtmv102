// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s018;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S018_013Activity_ViewBinding<T extends C01S018_013Activity> implements Unbinder {
  protected T target;

  @UiThread
  public C01S018_013Activity_ViewBinding(T target, View source) {
    this.target = target;

    target.ll01 = Utils.findRequiredViewAsType(source, R.id.ll_01, "field 'll01'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.ll01 = null;

    this.target = null;
  }
}
