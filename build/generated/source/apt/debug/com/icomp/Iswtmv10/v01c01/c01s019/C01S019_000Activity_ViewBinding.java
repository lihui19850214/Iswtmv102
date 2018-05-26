// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s019;

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

public class C01S019_000Activity_ViewBinding<T extends C01S019_000Activity> implements Unbinder {
  protected T target;

  private View view2131558525;

  private View view2131558526;

  private View view2131558506;

  private View view2131558489;

  @UiThread
  public C01S019_000Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_return, "field 'mBtnCancel' and method 'onViewClicked'");
    target.mBtnCancel = Utils.castView(view, R.id.btn_return, "field 'mBtnCancel'", Button.class);
    view2131558525 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_next, "field 'mBtnNext' and method 'onViewClicked'");
    target.mBtnNext = Utils.castView(view, R.id.btn_next, "field 'mBtnNext'", Button.class);
    view2131558526 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_01, "field 'll01' and method 'onViewClicked'");
    target.ll01 = Utils.castView(view, R.id.ll_01, "field 'll01'", LinearLayout.class);
    view2131558506 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tv02 = Utils.findRequiredViewAsType(source, R.id.tv_02, "field 'tv02'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_02, "field 'll02' and method 'onViewClicked'");
    target.ll02 = Utils.castView(view, R.id.ll_02, "field 'll02'", LinearLayout.class);
    view2131558489 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.et01 = Utils.findRequiredViewAsType(source, R.id.et_01, "field 'et01'", EditText.class);
    target.et02 = Utils.findRequiredViewAsType(source, R.id.et_02, "field 'et02'", EditText.class);
    target.et03 = Utils.findRequiredViewAsType(source, R.id.et_03, "field 'et03'", EditText.class);
    target.et04 = Utils.findRequiredViewAsType(source, R.id.et_04, "field 'et04'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mBtnCancel = null;
    target.mBtnNext = null;
    target.tv01 = null;
    target.ll01 = null;
    target.tv02 = null;
    target.ll02 = null;
    target.et01 = null;
    target.et02 = null;
    target.et03 = null;
    target.et04 = null;

    view2131558525.setOnClickListener(null);
    view2131558525 = null;
    view2131558526.setOnClickListener(null);
    view2131558526 = null;
    view2131558506.setOnClickListener(null);
    view2131558506 = null;
    view2131558489.setOnClickListener(null);
    view2131558489 = null;

    this.target = null;
  }
}
