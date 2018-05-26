// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s007;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S007_002Activity_ViewBinding<T extends C01S007_002Activity> implements Unbinder {
  protected T target;

  @UiThread
  public C01S007_002Activity_ViewBinding(T target, View source) {
    this.target = target;

    target.btnScan = Utils.findRequiredViewAsType(source, R.id.btnScan, "field 'btnScan'", Button.class);
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    target.tvXiaLa = Utils.findRequiredViewAsType(source, R.id.tvXiaLa, "field 'tvXiaLa'", TextView.class);
    target.tvNum = Utils.findRequiredViewAsType(source, R.id.tv_num, "field 'tvNum'", EditText.class);
    target.listview = Utils.findRequiredViewAsType(source, R.id.listview, "field 'listview'", ListView.class);
    target.tvTitle02 = Utils.findRequiredViewAsType(source, R.id.tv_title_02, "field 'tvTitle02'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btnScan = null;
    target.tv01 = null;
    target.tvXiaLa = null;
    target.tvNum = null;
    target.listview = null;
    target.tvTitle02 = null;

    this.target = null;
  }
}
