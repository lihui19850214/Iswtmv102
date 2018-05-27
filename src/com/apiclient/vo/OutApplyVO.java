package com.apiclient.vo;

import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.DjCircleKanbanAkp;
import com.apiclient.pojo.DjMtlAkp;
import com.apiclient.pojo.DjOutapplyAkp;

import java.util.List;

/**
 * Created by logan on 2018/5/7.
 */
public class OutApplyVO extends  WriteBaseVO{

    private DjOutapplyAkp djOutapplyAkp;

    private DjCircleKanbanAkp djCircleKanbanAkp;

    private DjMtlAkp djMtlAkp;

    private Integer susr20;

    private Integer unitqty;
    //当前登陆
    private String kuguanOperatorCode;
    //刷卡
    private String linglOperatorRfidCode;

    private String kezhangRfidCode;

    private List<CuttingToolBind> cuttingToolBinds;

    public String getKuguanOperatorCode() {
        return kuguanOperatorCode;
    }

    public void setKuguanOperatorCode(String kuguanOperatorCode) {
        this.kuguanOperatorCode = kuguanOperatorCode;
    }

    public String getLinglOperatorRfidCode() {
        return linglOperatorRfidCode;
    }

    public void setLinglOperatorRfidCode(String linglOperatorRfidCode) {
        this.linglOperatorRfidCode = linglOperatorRfidCode;
    }

    public String getKezhangRfidCode() {
        return kezhangRfidCode;
    }

    public void setKezhangRfidCode(String kezhangRfidCode) {
        this.kezhangRfidCode = kezhangRfidCode;
    }

    public DjOutapplyAkp getDjOutapplyAkp() {
        return djOutapplyAkp;
    }

    public void setDjOutapplyAkp(DjOutapplyAkp djOutapplyAkp) {
        this.djOutapplyAkp = djOutapplyAkp;
    }

    public DjCircleKanbanAkp getDjCircleKanbanAkp() {
        return djCircleKanbanAkp;
    }

    public void setDjCircleKanbanAkp(DjCircleKanbanAkp djCircleKanbanAkp) {
        this.djCircleKanbanAkp = djCircleKanbanAkp;
    }

    public DjMtlAkp getDjMtlAkp() {
        return djMtlAkp;
    }

    public void setDjMtlAkp(DjMtlAkp djMtlAkp) {
        this.djMtlAkp = djMtlAkp;
    }

    public Integer getSusr20() {
        return susr20;
    }

    public void setSusr20(Integer susr20) {
        this.susr20 = susr20;
    }

    public Integer getUnitqty() {
        return unitqty;
    }

    public void setUnitqty(Integer unitqty) {
        this.unitqty = unitqty;
    }

    @Override
    public List<CuttingToolBind> getCuttingToolBinds() {
        return cuttingToolBinds;
    }

    @Override
    public void setCuttingToolBinds(List<CuttingToolBind> cuttingToolBinds) {
        this.cuttingToolBinds = cuttingToolBinds;
    }
}
