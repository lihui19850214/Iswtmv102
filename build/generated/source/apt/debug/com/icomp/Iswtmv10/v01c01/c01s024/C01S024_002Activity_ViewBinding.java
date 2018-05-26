// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s024;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S024_002Activity_ViewBinding<T extends C01S024_002Activity> implements Unbinder {
  protected T target;

  @UiThread
  public C01S024_002Activity_ViewBinding(T target, View source) {
    this.target = target;

    target.lv01 = Utils.findRequiredViewAsType(source, R.id.lv_01, "field 'lv01'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.lv01 = null;

    this.target = null;
  }
}
