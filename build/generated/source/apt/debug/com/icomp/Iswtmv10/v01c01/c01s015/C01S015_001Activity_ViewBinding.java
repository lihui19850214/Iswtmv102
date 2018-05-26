// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s015;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C01S015_001Activity_ViewBinding<T extends C01S015_001Activity> implements Unbinder {
  protected T target;

  private View view2131558486;

  private View view2131558519;

  private View view2131558506;

  @UiThread
  public C01S015_001Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.et01 = Utils.findRequiredViewAsType(source, R.id.et_01, "field 'et01'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnScan, "field 'btnScan' and method 'onViewClicked'");
    target.btnScan = Utils.castView(view, R.id.btnScan, "field 'btnScan'", Button.class);
    view2131558486 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSearch, "field 'btnSearch' and method 'onViewClicked'");
    target.btnSearch = Utils.castView(view, R.id.btnSearch, "field 'btnSearch'", Button.class);
    view2131558519 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    target.tv02 = Utils.findRequiredViewAsType(source, R.id.tv_02, "field 'tv02'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_01, "field 'll01' and method 'onViewClicked'");
    target.ll01 = Utils.castView(view, R.id.ll_01, "field 'll01'", LinearLayout.class);
    view2131558506 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.btnCancel = Utils.findRequiredViewAsType(source, R.id.btnCancel, "field 'btnCancel'", Button.class);
    target.btnBind = Utils.findRequiredViewAsType(source, R.id.btnBind, "field 'btnBind'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.et01 = null;
    target.btnScan = null;
    target.btnSearch = null;
    target.tv01 = null;
    target.tv02 = null;
    target.ll01 = null;
    target.tvTitle = null;
    target.btnCancel = null;
    target.btnBind = null;

    view2131558486.setOnClickListener(null);
    view2131558486 = null;
    view2131558519.setOnClickListener(null);
    view2131558519 = null;
    view2131558506.setOnClickListener(null);
    view2131558506 = null;

    this.target = null;
  }
}
