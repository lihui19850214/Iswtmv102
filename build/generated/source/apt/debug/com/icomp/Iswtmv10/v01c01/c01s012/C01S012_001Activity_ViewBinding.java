// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s012;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S012_001Activity_ViewBinding<T extends C01S012_001Activity> implements Unbinder {
  protected T target;

  @UiThread
  public C01S012_001Activity_ViewBinding(T target, View source) {
    this.target = target;

    target.et01 = Utils.findRequiredViewAsType(source, R.id.et_01, "field 'et01'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.et01 = null;

    this.target = null;
  }
}
