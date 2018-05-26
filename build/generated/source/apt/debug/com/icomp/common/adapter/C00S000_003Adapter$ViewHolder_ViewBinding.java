// Generated code from Butter Knife. Do not modify!
package com.icomp.common.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.icomp.Iswtmv10.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class C00S000_003Adapter$ViewHolder_ViewBinding<T extends C00S000_003Adapter.ViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public C00S000_003Adapter$ViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.itemImage = Utils.findRequiredViewAsType(source, R.id.item_image, "field 'itemImage'", ImageView.class);
    target.itemText = Utils.findRequiredViewAsType(source, R.id.item_text, "field 'itemText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.itemImage = null;
    target.itemText = null;

    this.target = null;
  }
}
