// Generated code from Butter Knife. Do not modify!
package com.icomp.Iswtmv10.v01c01.c01s024;

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

public class C01S024_001Activity_ViewBinding<T extends C01S024_001Activity> implements Unbinder {
  protected T target;

  private View view2131558486;

  @UiThread
  public C01S024_001Activity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.cailiaoInfo = Utils.findRequiredViewAsType(source, R.id.cailiaoInfo, "field 'cailiaoInfo'", LinearLayout.class);
    target.tvCailiaoInfoMaterialNumber = Utils.findRequiredViewAsType(source, R.id.tv_cailiaoInfo_materialNumber, "field 'tvCailiaoInfoMaterialNumber'", TextView.class);
    target.tvCailiaoInfoFinalExecution = Utils.findRequiredViewAsType(source, R.id.tv_cailiaoInfo_finalExecution, "field 'tvCailiaoInfoFinalExecution'", TextView.class);
    target.tvCailiaoInfoOperator = Utils.findRequiredViewAsType(source, R.id.tv_cailiaoInfo_operator, "field 'tvCailiaoInfoOperator'", TextView.class);
    target.tvCailiaoInfoOperationTime = Utils.findRequiredViewAsType(source, R.id.tv_cailiaoInfo_operationTime, "field 'tvCailiaoInfoOperationTime'", TextView.class);
    target.tvCailiaoInfoGrindingTimes = Utils.findRequiredViewAsType(source, R.id.tv_cailiaoInfo_grindingTimes, "field 'tvCailiaoInfoGrindingTimes'", TextView.class);
    target.tvCailiaoInfoCumulativeAmountOfProcessing = Utils.findRequiredViewAsType(source, R.id.tv_cailiaoInfo_cumulativeAmountOfProcessing, "field 'tvCailiaoInfoCumulativeAmountOfProcessing'", TextView.class);
    target.hechengInfo = Utils.findRequiredViewAsType(source, R.id.hechengInfo, "field 'hechengInfo'", LinearLayout.class);
    target.tvHechengInfoSyntheticToolCode = Utils.findRequiredViewAsType(source, R.id.tv_hechengInfo_syntheticToolCode, "field 'tvHechengInfoSyntheticToolCode'", TextView.class);
    target.tvHechengInfoFinalExecution = Utils.findRequiredViewAsType(source, R.id.tv_hechengInfo_finalExecution, "field 'tvHechengInfoFinalExecution'", TextView.class);
    target.tvHechengInfoOperator = Utils.findRequiredViewAsType(source, R.id.tv_hechengInfo_operator, "field 'tvHechengInfoOperator'", TextView.class);
    target.tvHechengInfoOperationTime = Utils.findRequiredViewAsType(source, R.id.tv_hechengInfo_operationTime, "field 'tvHechengInfoOperationTime'", TextView.class);
    target.tvHechengInfoGrindingTimes = Utils.findRequiredViewAsType(source, R.id.tv_hechengInfo_grindingTimes, "field 'tvHechengInfoGrindingTimes'", TextView.class);
    target.tvHechengInfoCumulativeAmountOfProcessing = Utils.findRequiredViewAsType(source, R.id.tv_hechengInfo_cumulativeAmountOfProcessing, "field 'tvHechengInfoCumulativeAmountOfProcessing'", TextView.class);
    target.equipmentInfo = Utils.findRequiredViewAsType(source, R.id.equipmentInfo, "field 'equipmentInfo'", LinearLayout.class);
    target.tvEquipmentInfoEquipmentName = Utils.findRequiredViewAsType(source, R.id.tv_equipmentInfo_equipmentName, "field 'tvEquipmentInfoEquipmentName'", TextView.class);
    target.personnelInfo = Utils.findRequiredViewAsType(source, R.id.personnelInfo, "field 'personnelInfo'", LinearLayout.class);
    target.tvPersonnelInfoEmployeeNumber = Utils.findRequiredViewAsType(source, R.id.tv_personnelInfo_employeeNumber, "field 'tvPersonnelInfoEmployeeNumber'", TextView.class);
    target.tvPersonnelInfoRealName = Utils.findRequiredViewAsType(source, R.id.tv_personnelInfo_realName, "field 'tvPersonnelInfoRealName'", TextView.class);
    target.tvPersonnelInfoDepartment = Utils.findRequiredViewAsType(source, R.id.tv_personnelInfo_department, "field 'tvPersonnelInfoDepartment'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnScan, "field 'btnScan' and method 'onViewClicked'");
    target.btnScan = Utils.castView(view, R.id.btnScan, "field 'btnScan'", Button.class);
    view2131558486 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.btnCancel = Utils.findRequiredViewAsType(source, R.id.btnCancel, "field 'btnCancel'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.cailiaoInfo = null;
    target.tvCailiaoInfoMaterialNumber = null;
    target.tvCailiaoInfoFinalExecution = null;
    target.tvCailiaoInfoOperator = null;
    target.tvCailiaoInfoOperationTime = null;
    target.tvCailiaoInfoGrindingTimes = null;
    target.tvCailiaoInfoCumulativeAmountOfProcessing = null;
    target.hechengInfo = null;
    target.tvHechengInfoSyntheticToolCode = null;
    target.tvHechengInfoFinalExecution = null;
    target.tvHechengInfoOperator = null;
    target.tvHechengInfoOperationTime = null;
    target.tvHechengInfoGrindingTimes = null;
    target.tvHechengInfoCumulativeAmountOfProcessing = null;
    target.equipmentInfo = null;
    target.tvEquipmentInfoEquipmentName = null;
    target.personnelInfo = null;
    target.tvPersonnelInfoEmployeeNumber = null;
    target.tvPersonnelInfoRealName = null;
    target.tvPersonnelInfoDepartment = null;
    target.btnScan = null;
    target.btnCancel = null;

    view2131558486.setOnClickListener(null);
    view2131558486 = null;

    this.target = null;
  }
}
