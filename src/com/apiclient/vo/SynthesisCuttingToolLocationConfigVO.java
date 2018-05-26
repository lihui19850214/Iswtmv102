package com.apiclient.vo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolLocationConfigVO 
*/
public class SynthesisCuttingToolLocationConfigVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;


    /**
     * @fieldName location
    * @fieldType  Integer
    * @Description  位置号
     */
    private Integer location;

    /**
     * @fieldName count
    * @fieldType  Integer
    * @Description  材料刀数量
     */
    private Integer count;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName replaceCuttingToolCode1
    * @fieldType  String
    * @Description  
     */
    private String replaceCuttingToolCode1;

    /**
     * @fieldName replaceCuttingToolCode2
    * @fieldType  String
    * @Description  
     */
    private String replaceCuttingToolCode2;

    /**
     * @fieldName replaceCuttingToolCode3
    * @fieldType  String
    * @Description  
     */
    private String replaceCuttingToolCode3;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀
     */
    private String cuttingToolCode;

    /**
     * @fieldName synthesisCuttingToolConfigId
    * @fieldType  Integer
    * @Description  
     */
    private Integer synthesisCuttingToolConfigId;

    /**
     * @fieldName type
    * @fieldType  String
    * @Description  材料刀类型
     */
    private String type;

    /**
     * @fieldName replaceCuttingToolCode1
    * @fieldType  
    * @Description  
     */
    private CuttingToolVO replaceCuttingTool1VO;
    /**
     * @fieldName replaceCuttingToolCode2
    * @fieldType  
    * @Description  
     */
    private CuttingToolVO replaceCuttingTool2VO;
    /**
     * @fieldName replaceCuttingToolCode3
    * @fieldType  
    * @Description  
     */
    private CuttingToolVO replaceCuttingTool3VO;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  材料刀
     */
    private CuttingToolVO cuttingToolVO;
    /**
     * @fieldName synthesisCuttingToolConfigId
    * @fieldType  
    * @Description  
     */
    private SynthesisCuttingToolConfigVO synthesisCuttingToolConfigVO;


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


    /* 标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getReplaceCuttingToolCode1() {
        return replaceCuttingToolCode1;
    }

    public void setReplaceCuttingToolCode1(String replaceCuttingToolCode1) {
        this.replaceCuttingToolCode1 = replaceCuttingToolCode1;
    }
    public String getReplaceCuttingToolCode2() {
        return replaceCuttingToolCode2;
    }

    public void setReplaceCuttingToolCode2(String replaceCuttingToolCode2) {
        this.replaceCuttingToolCode2 = replaceCuttingToolCode2;
    }
    public String getReplaceCuttingToolCode3() {
        return replaceCuttingToolCode3;
    }

    public void setReplaceCuttingToolCode3(String replaceCuttingToolCode3) {
        this.replaceCuttingToolCode3 = replaceCuttingToolCode3;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public Integer getSynthesisCuttingToolConfigId() {
        return synthesisCuttingToolConfigId;
    }

    public void setSynthesisCuttingToolConfigId(Integer synthesisCuttingToolConfigId) {
        this.synthesisCuttingToolConfigId = synthesisCuttingToolConfigId;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SynthesisCuttingToolConfigVO getSynthesisCuttingToolConfigVO() {
        return synthesisCuttingToolConfigVO;
    }

    public void setSynthesisCuttingToolConfigVO(SynthesisCuttingToolConfigVO synthesisCuttingToolConfigVO) {
        this.synthesisCuttingToolConfigVO = synthesisCuttingToolConfigVO;
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

    public CuttingToolVO getReplaceCuttingTool1VO() {
        return replaceCuttingTool1VO;
    }

    public void setReplaceCuttingTool1VO(CuttingToolVO replaceCuttingTool1VO) {
        this.replaceCuttingTool1VO = replaceCuttingTool1VO;
    }

    public CuttingToolVO getReplaceCuttingTool2VO() {
        return replaceCuttingTool2VO;
    }

    public void setReplaceCuttingTool2VO(CuttingToolVO replaceCuttingTool2VO) {
        this.replaceCuttingTool2VO = replaceCuttingTool2VO;
    }

    public CuttingToolVO getReplaceCuttingTool3VO() {
        return replaceCuttingTool3VO;
    }

    public void setReplaceCuttingTool3VO(CuttingToolVO replaceCuttingTool3VO) {
        this.replaceCuttingTool3VO = replaceCuttingTool3VO;
    }

    public CuttingToolVO getCuttingToolVO() {
        return cuttingToolVO;
    }

    public void setCuttingToolVO(CuttingToolVO cuttingToolVO) {
        this.cuttingToolVO = cuttingToolVO;
    }
}
