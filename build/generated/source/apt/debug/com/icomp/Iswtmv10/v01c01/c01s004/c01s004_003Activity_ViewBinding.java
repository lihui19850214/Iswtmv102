// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s004;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class c01s004_003Activity_ViewBinding<T extends c01s004_003Activity> implements Unbinder {
  protected T target;

  private View view2131558480;

  private View view2131558467;

  private View view2131558489;

  @UiThread
  public c01s004_003Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnReturn, "field 'mBtnReturn' and method 'onViewClicked'");
    target.mBtnReturn = Utils.castView(view, R.id.btnReturn, "field 'mBtnReturn'", Button.class);
    view2131558480 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnNext, "field 'mBtnSign' and method 'onViewClicked'");
    target.mBtnSign = Utils.castView(view, R.id.btnNext, "field 'mBtnSign'", Button.class);
    view2131558467 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.wuliaohao = Utils.findRequiredViewAsType(source, R.id.wuliaohao, "field 'wuliaohao'", TextView.class);
    target.cailiaohao = Utils.findRequiredViewAsType(source, R.id.cailiaohao, "field 'cailiaohao'", TextView.class);
    target.xinghaoguige = Utils.findRequiredViewAsType(source, R.id.xinghaoguige, "field 'xinghaoguige'", TextView.class);
    target.wuliaomingcheng = Utils.findRequiredViewAsType(source, R.id.wuliaomingcheng, "field 'wuliaomingcheng'", TextView.class);
    target.shengchanxian = Utils.findRequiredViewAsType(source, R.id.shengchanxian, "field 'shengchanxian'", TextView.class);
    target.gongwei = Utils.findRequiredViewAsType(source, R.id.gongwei, "field 'gongwei'", TextView.class);
    target.yaohuoshuliang = Utils.findRequiredViewAsType(source, R.id.yaohuoshuliang, "field 'yaohuoshuliang'", TextView.class);
    target.daojuleixing = Utils.findRequiredViewAsType(source, R.id.daojuleixing, "field 'daojuleixing'", TextView.class);
    target.xiumofangshi = Utils.findRequiredViewAsType(source, R.id.xiumofangshi, "field 'xiumofangshi'", TextView.class);
    target.tv01 = Utils.findRequiredViewAsType(source, R.id.tv_01, "field 'tv01'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_02, "field 'll02' and method 'onViewClicked'");
    target.ll02 = Utils.castView(view, R.id.ll_02, "field 'll02'", LinearLayout.class);
    view2131558489 = view;
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

    target.mBtnReturn = null;
    target.mBtnSign = null;
    target.tvTitle = null;
    target.wuliaohao = null;
    target.cailiaohao = null;
    target.xinghaoguige = null;
    target.wuliaomingcheng = null;
    target.shengchanxian = null;
    target.gongwei = null;
    target.yaohuoshuliang = null;
    target.daojuleixing = null;
    target.xiumofangshi = null;
    target.tv01 = null;
    target.ll02 = null;

    view2131558480.setOnClickListener(null);
    view2131558480 = null;
    view2131558467.setOnClickListener(null);
    view2131558467 = null;
    view2131558489.setOnClickListener(null);
    view2131558489 = null;

    this.target = null;
  }
}
