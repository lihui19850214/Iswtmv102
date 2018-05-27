package com.apiclient.vo;

import java.io.Serializable;
import java.util.Date;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjOutapplyAkpVO 
*/
public class DjOutapplyAkpVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName client
    * @fieldType  String
    * @Description  单号‘DJLY’用环境'800'
     */
    private String client;


    /**
     * @fieldName applyno
    * @fieldType  String
    * @Description  单号‘DJLY’
     */
    private String applyno;

    /**
     * @fieldName mtlno
    * @fieldType  String
    * @Description  物料号
     */
    private String mtlno;

    /**
     * @fieldName storagelocation
    * @fieldType  String
    * @Description  存储地点(mtl_wh.PUTAWAYLOC)
     */
    private String storagelocation;

    /**
     * @fieldName unitqty
    * @fieldType  String
    * @Description  数量
     */
    private String unitqty;

    /**
     * @fieldName goodsrecipient
    * @fieldType  String
    * @Description  收货者
     */
    private String goodsrecipient;

    /**
     * @fieldName auditid
    * @fieldType  String
    * @Description  审核标识 0-新建 1-已审核 2-已出库 3-已收货 4-已制签 6-审核失败 7-撤销
     */
    private String auditid;

    /**
     * @fieldName changebefid
    * @fieldType  String
    * @Description  转预留标识
     */
    private String changebefid;

    /**
     * @fieldName mtldocno
    * @fieldType  String
    * @Description  物料凭证号
     */
    private String mtldocno;

    /**
     * @fieldName mtldocitem
    * @fieldType  Integer
    * @Description  物料凭证行项号
     */
    private Integer mtldocitem;

    /**
     * @fieldName outdate
    * @fieldType  Date
    * @Description  读取时间
     */
    private Date outdate;

    /**
     * @fieldName message
    * @fieldType  String
    * @Description  消息
     */
    private String message;

    /**
     * @fieldName dumplogo
    * @fieldType  String
    * @Description  转储标识'N'
     */
    private String dumplogo;

    /**
     * @fieldName externalpurstorage
    * @fieldType  String
    * @Description  收货存储地点
     */
    private String externalpurstorage;

    /**
     * @fieldName location
    * @fieldType  String
    * @Description  工位
     */
    private String location;

    /**
     * @fieldName productline
    * @fieldType  String
    * @Description  生产线
     */
    private String productline;

    /**
     * @fieldName plant
    * @fieldType  String
    * @Description  工厂
     */
    private String plant;

    /**
     * @fieldName whCode
    * @fieldType  String
    * @Description  仓库
     */
    private String whCode;

    /**
     * @fieldName createDate
    * @fieldType  Date
    * @Description  创建时间
     */
    private Date createDate;

    /**
     * @fieldName status
    * @fieldType  String
    * @Description  
     */
    private String status;

    /**
     * @fieldName djqty
    * @fieldType  String
    * @Description  加工寿命
     */
    private String djqty;

    /**
     * @fieldName djcode
    * @fieldType  String
    * @Description  刀具号
     */
    private String djcode;

    /**
     * @fieldName ggxh
    * @fieldType  String
    * @Description  规格型号
     */
    private String ggxh;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  物料名称
     */
    private String name;

    /**
     * @fieldName lltm
    * @fieldType  String
    * @Description  领料条码号
     */
    private String lltm;

    /**
     * @fieldName xiangm
    * @fieldType  String
    * @Description  项目
     */
    private String xiangm;

    /**
     * @fieldName jhj
    * @fieldType  String
    * @Description  计划价
     */
    private String jhj;

    /**
     * @fieldName lastModifyDate
    * @fieldType  Date
    * @Description  
     */
    private Date lastModifyDate;



    /**
     * @fieldName currentPage
    * @fieldType  Integer
    * @Description  当前页码
     */
    private Integer currentPage = 1;

    /**
     * @fieldName totalPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer totalPage;

    /**
     * @fieldName pageSize
    * @fieldType  Integer
    * @Description  每页记录条数
     */
    private Integer pageSize = 10;

    /**
     * @fieldName maxPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer maxPage;

    /**
     * @fieldName startRecord
    * @fieldType  Integer
    * @Description  开始查询记录
     */
    private Integer startRecord;


    /* 单号‘DJLY’用环境'800' */
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno;
    }
    public String getMtlno() {
        return mtlno;
    }

    public void setMtlno(String mtlno) {
        this.mtlno = mtlno;
    }
    public String getStoragelocation() {
        return storagelocation;
    }

    public void setStoragelocation(String storagelocation) {
        this.storagelocation = storagelocation;
    }
    public String getUnitqty() {
        return unitqty;
    }

    public void setUnitqty(String unitqty) {
        this.unitqty = unitqty;
    }
    public String getGoodsrecipient() {
        return goodsrecipient;
    }

    public void setGoodsrecipient(String goodsrecipient) {
        this.goodsrecipient = goodsrecipient;
    }
    public String getAuditid() {
        return auditid;
    }

    public void setAuditid(String auditid) {
        this.auditid = auditid;
    }
    public String getChangebefid() {
        return changebefid;
    }

    public void setChangebefid(String changebefid) {
        this.changebefid = changebefid;
    }
    public String getMtldocno() {
        return mtldocno;
    }

    public void setMtldocno(String mtldocno) {
        this.mtldocno = mtldocno;
    }
    public Integer getMtldocitem() {
        return mtldocitem;
    }

    public void setMtldocitem(Integer mtldocitem) {
        this.mtldocitem = mtldocitem;
    }
    public Date getOutdate() {
        return outdate;
    }

    public void setOutdate(Date outdate) {
        this.outdate = outdate;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDumplogo() {
        return dumplogo;
    }

    public void setDumplogo(String dumplogo) {
        this.dumplogo = dumplogo;
    }

    public String getExternalpurstorage() {
        return externalpurstorage;
    }

    public void setExternalpurstorage(String externalpurstorage) {
        this.externalpurstorage = externalpurstorage;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getProductline() {
        return productline;
    }

    public void setProductline(String productline) {
        this.productline = productline;
    }
    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDjqty() {
        return djqty;
    }

    public void setDjqty(String djqty) {
        this.djqty = djqty;
    }
    public String getDjcode() {
        return djcode;
    }

    public void setDjcode(String djcode) {
        this.djcode = djcode;
    }
    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLltm() {
        return lltm;
    }

    public void setLltm(String lltm) {
        this.lltm = lltm;
    }
    public String getXiangm() {
        return xiangm;
    }

    public void setXiangm(String xiangm) {
        this.xiangm = xiangm;
    }
    public String getJhj() {
        return jhj;
    }

    public void setJhj(String jhj) {
        this.jhj = jhj;
    }
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }



    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startRecord = (this.currentPage-1)*pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        this.maxPage = this.totalPage/this.pageSize+(this.totalPage%this.pageSize)>0?1:0;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(Integer startRecord) {
        this.startRecord = startRecord;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }



}
