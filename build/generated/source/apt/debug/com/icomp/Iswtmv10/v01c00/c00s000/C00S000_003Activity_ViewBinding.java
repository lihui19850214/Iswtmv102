// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c00.c00s000;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C00S000_003Activity_ViewBinding<T extends C00S000_003Activity> implements Unbinder {
  protected T target;

  @UiThread
  public C00S000_003Activity_ViewBinding(T target, View source) {
    this.target = target;

    target.mScrollLayout = Utils.findRequiredViewAsType(source, R.id.ScrollLayout, "field 'mScrollLayout'", ViewPager.class);
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mScrollLayout = null;
    target.tv01 = null;

    this.target = null;
  }
}
