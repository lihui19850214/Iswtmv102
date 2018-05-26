package com.apiclient.vo;

import com.apiclient.pojo.ProductLineParts;

/**
 * Created by logan on 2018/4/30.
 */
public class UnBindEquipmentVO {

    private ProductLineVO productLineVO;

    private String unBindReason;

    private ProductLineParts parts;

    private Integer count;

    private String bindRfid;

    public String getBindRfid() {
        return bindRfid;
    }

    public void setBindRfid(String bindRfid) {
        this.bindRfid = bindRfid;
    }

    public ProductLineVO getProductLineVO() {
        return productLineVO;
    }

    public void setProductLineVO(ProductLineVO productLineVO) {
        this.productLineVO = productLineVO;
    }

    public String getUnBindReason() {
        return unBindReason;
    }

    public void setUnBindReason(String unBindReason) {
        this.unBindReason = unBindReason;
    }

    public ProductLineParts getParts() {
        return parts;
    }

    public void setParts(ProductLineParts parts) {
        this.parts = parts;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
