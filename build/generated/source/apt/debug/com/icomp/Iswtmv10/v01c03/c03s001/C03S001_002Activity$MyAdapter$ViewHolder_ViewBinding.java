// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c03.c03s001;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C03S001_002Activity$MyAdapter$ViewHolder_ViewBinding<T extends C03S001_002Activity.MyAdapter.ViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public C03S001_002Activity$MyAdapter$ViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    target.tv02 = Utils.findRequiredViewAsType(source, R.id.tv_02, "field 'tv02'", TextView.class);
    target.tv03 = Utils.findRequiredViewAsType(source, R.id.tv_03, "field 'tv03'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv01 = null;
    target.tv02 = null;
    target.tv03 = null;

    this.target = null;
  }
}
