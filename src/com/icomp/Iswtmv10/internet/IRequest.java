package com.icomp.Iswtmv10.internet;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;


/**
 * Created by Think on 2016/9/12.
 */
public interface IRequest {


    /**
     * 新刀入库-提交入库数量信息
     * @param customerId
     * @param materialNum
     * @param toolsOrdeNO
     * @param storageNum
     * @param toolID
     * @return
     */
    @FormUrlEncoded
    @POST("/dazhong/saveToolInputInfo")
    Call<String> saveToolInputInfo(@Field("customerID") String customerId,
                                   @Field("materialNum") String materialNum,
                                   @Field("toolsOrdeNO") String toolsOrdeNO,
                                   @Field("storageNum") String storageNum,
                                   @Field("toolID") String toolID,
                                   @Field("valType") String valType,
                                   @Field("poItem") String poItem);

    /**
     * 刀具换装-扫码查询合成刀信息
     * @param rfidCode
     * @return
     */
    @FormUrlEncoded
    @POST("/dazhong/getSynthesisToolOneKnifeInfo")
    Call<String> getSynthesisToolOneKnifeInfo(@Field("rfidCode") String rfidCode);

    /**
     * 刀具组装-扫码查询合成刀信息
     * @param rfidCode
     */
    @FormUrlEncoded
    @POST("/dazhong/getSynthesisToolInstall")
    Call<String> getSynthesisToolInstall(@Field("rfidCode") String rfidCode,
                                         @Field("flg") String flg);

    /**
     * 刀具组装-提交组装信息
     * @param synthesisParametersCodes
     * @param rfidContainerIDs
     * @param toolConsumetypes
     * @param customerID
     */
    @FormUrlEncoded
    @POST("/dazhong/saveSynthesisToolInstall")
    Call<String> saveSynthesisToolInstall(@Field("synthesisParametersCodes") String synthesisParametersCodes,
                                        @Field("rfidContainerIDs") String rfidContainerIDs,
                                        @Field("toolConsumetypes") String toolConsumetypes,
                                        @Field("customerID") String customerID,
                                          @Field("installparams") String installparams);

    /**
     * 刀具组装-提交组装信息,组装成新刀
     * rfidCode:RFID标签ID
     toolCode:材料号
     customerID：用户ID
     */
    @FormUrlEncoded
    @POST("/dazhong/saveSynthesisToolInstallNewTool")
    Call<String> saveSynthesisToolInstall(@Field("rfidCode") String rfidCode,
                                          @Field("toolCode") String toolCode,
                                          @Field("customerId") String customerId);


    //C01S018厂内修磨--根据材料号查询一体刀信息
    @FormUrlEncoded
    @POST("/dazhong/getOneKnifeInfo")
    Call<String> getOneKnifeInfo(@Field("rfidCode") String rfidString);

    //C01S018厂内修磨--提交要修磨的一体刀信息
    @FormUrlEncoded
    @POST("/dazhong/saveGrindingOneKnifeInfo")
    Call<String> saveGrindingOneKnifeInfo(@Field("toolCodes") String toolCodes, @Field("rfidContainerIDs") String rfidContainerIDs,
                                          @Field("authorizationFlgs") String authorizationFlgs, @Field("customerID") String customerID, @Field("gruantUserID") String gruantUserID);




    /** 新需求接口请求 **/

    // --------------刀具出库开始--------------
    /**
     * 查询出库单
     * @param info
     * @return
     */
    @POST("/qiMing/getOrders")
    Call<String> getOrders(@Body RequestBody info);

    /**
     * 出库
     * @param info
     * @return
     */
    @POST("/qiMing/outApply")
    Call<String> outApply(@Body RequestBody info);
    // --------------刀具出库结束--------------


    // --------------刀具打码开始--------------未完成
    /**
     * 获取已出库订单
     * @param info
     * @return
     */
    @POST("/cuttingToolBusiness/queryOutOrder")
    Call<String> queryOutOrder(@Body RequestBody info);

    /**
     * 查看刀身码
     * @param info
     * @return
     */
    @POST("/cuttingToolBusiness/queryBladeCodes")
    Call<String> queryBladeCodes(@Body RequestBody info);
    // --------------刀具打码结束--------------


    // --------------合成刀具初始化开始--------------
    /**
     * 获取合成刀信息
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/getConfig")
    Call<String> getSynthesisCuttingConfig(@Body RequestBody info);

    /**
     * 初始化合成刀具
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/init")
    Call<String> synthesisCuttingInit(@Body RequestBody info);
    // --------------合成刀具初始化结束--------------


    // --------------加工设备初始化开始--------------
    /**
     * 获取流水线
     * @param info
     * @return
     */
    @POST("/productlineBusiness/getAssemblylines")
    Call<String> getAssemblylines(@Body RequestBody info);

    /**
     * 根据流水线获取设备
     * @param info
     * @return
     */
    @POST("/productlineBusiness/getEquipmentByAssemblyline")
    Call<String> getEquipmentByAssemblyline(@Body RequestBody info);

    /**
     * 初始化设备
     * @param info
     * @return
     */
    @POST("/productlineBusiness/initEquipment")
    Call<String> initEquipment(@Body RequestBody info);
    // --------------加工设备初始化结束--------------


    // --------------员工初始化开始--------------
    /**
     * 根据员工号查询
     * @param info
     * @return
     */
    @POST("/auth/query")
    Call<String> queryEmployee(@Body RequestBody info);

    /**
     * 初始化员工
     * @param info
     * @return
     */
    @POST("/auth/init")
    Call<String> initEmployee(@Body RequestBody info);
    // --------------员工始化结束--------------


    // --------------刀具绑定开始--------------
    /**
     * 查询材料刀信息
     * @param info
     * @return
     */
    @POST("/cuttingToolBusiness/getUnbind")
    Call<String> getUnbind(@Body RequestBody info);

    /**
     * 刀具绑定
     * @param info
     * @return
     */
    @POST("/cuttingToolBusiness/bind")
    Call<String> bindUnbind(@Body RequestBody info);
    // --------------刀具绑定结束--------------



    // --------------刀具换装、刀具拆分、刀具组装公用方法开始--------------未完成
    /**
     * 扫码查询合成刀信息
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/getBind")
    Call<String> getBind(@Body RequestBody info);
    // --------------刀具拆分和组装公用方法开始--------------未完成


    // --------------刀具换装开始--------------未完成
    /**
     * 根据Rifd获取材料刀信息
     * @param info
     * @return
     */
    @POST("/cuttingToolBind/search")
    Call<String> searchCuttingToolBind(@Body RequestBody info);

    /**
     * 换装
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/exChange")
    Call<String> exChange(@Body RequestBody info);
    // --------------刀具换装结束--------------


    // --------------刀具拆分开始--------------未完成
    /**
     * 提交拆分信息
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/breakUp")
    Call<String> breakUp(@Body RequestBody info);
    // --------------刀具拆分结束--------------


    // --------------刀具组装开始--------------未完成
    /**
     * 提交组装信息
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/packageUp")
    Call<String> packageUp(@Body RequestBody info);
    // --------------刀具组装结束--------------


    // --------------安上设备开始--------------未完成
    /**
     * 根据rfid获取合成刀信息
     * @param info
     * @return
     */
    @POST("/productlineBusiness/querySynthesisCuttingTool")
    Call<String> querySynthesisCuttingTool(@Body RequestBody info);


    /**
     * 查询设备和轴号
     * @param info
     * @return
     */
    @POST("/productlineBusiness/queryEquipmentByRFID")
    Call<String> queryEquipmentByRFID(@Body RequestBody info);



    /**
     * 根据rfid获取设备信息
     * @param info
     * @return
     */
    @POST("/productLineEquipment/search")
    Call<String> searchProductLineEquipment(@Body RequestBody info);

    /**
     * 按上设备
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/bindEquipment")
    Call<String> bindEquipment(@Body RequestBody info);
    // --------------安上设备结束--------------


    // --------------卸下设备开始--------------未完成
    /**
     * 根据rfid获取生产线信息
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBindleRecords/searchLast")
    Call<String> searchProductLine(@Body RequestBody info);

    /**
     * 卸下
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBusiness/unBindEquipment")
    Call<String> unBindEquipment(@Body RequestBody info);

    /**
     * 卸下
     * @param info
     * @return
     */
    @POST("/synthesisCuttingToolBindleRecords/getParts")
    Call<String> getParts(@Body RequestBody info);
    // --------------卸下设备结束--------------


    // --------------场内刃磨开始--------------未完成
    /**
     * 根据RFID获取材料刀信息
     * @param info
     * @return
     */
    @POST("/inFactoryBusiness/getCuttingToolBind")
    Call<String> getInCuttingToolBind(@Body RequestBody info);

    /**
     * 根据材料号获取材料刀
     * @param info
     * @return
     */
    @POST("/inFactoryBusiness/getCuttingTool")
    Call<String> getInCuttingTool(@Body RequestBody info);

    /**
     * 获取刃磨记录
     * @param info
     * @return
     */
    @POST("/inFactoryBusiness/countInsideFactory")
    Call<String> countInsideFactory(@Body RequestBody info);

    /**
     * 补充既往刃磨记录
     * @param info
     * @return
     */
    @POST("/inFactoryBusiness/addInsideFactoryHistory")
    Call<String> addInsideFactoryHistory(@Body RequestBody info);

    /**
     * 添加场内刃磨
     * @param info
     * @return
     */
    @POST("/inFactoryBusiness/addInsideFactory")
    Call<String> addInsideFactory(@Body RequestBody info);
    // --------------场内刃磨结束--------------


    // --------------场外刃磨开始--------------未完成
    /**
     * 根据RFID获取材料刀信息
     * @param info
     * @return
     */
    @POST("/outFactoryBusiness/getCuttingToolBind")
    Call<String> getOutCuttingToolBind(@Body RequestBody info);

    /**
     * 根据材料号获取材料刀
     * @param info
     * @return
     */
    @POST("/outFactoryBusiness/getCuttingTool")
    Call<String> getOutCuttingTool(@Body RequestBody info);

    /**
     * 获取刃磨记录
     * @param info
     * @return
     */
    @POST("/outFactoryBusiness/countOutsideFactory")
    Call<String> countOutsideFactory(@Body RequestBody info);

    /**
     * 补充既往刃磨记录
     * @param info
     * @return
     */
    @POST("/outFactoryBusiness/addOutsideFactoryHistory")
    Call<String> addOutsideFactoryHistory(@Body RequestBody info);

    /**
     * 添加场外刃磨
     * @param info
     * @return
     */
    @POST("/outFactoryBusiness/addOutsideFactory")
    Call<String> addOutsideFactory(@Body RequestBody info);

    /**
     * 添加场外刃磨服务商
     * @param info
     * @return
     */
    @POST("/outFactoryBusiness/getSharpenProvider")
    Call<String> getSharpenProvider(@Body RequestBody info);
    // --------------场外刃磨结束--------------


    // --------------报废开始--------------未完成
    /**
     * 根据RFID获取材料刀信息
     * @param info
     * @return
     */
    @POST("/ScrapBusiness/getCuttingToolBind")
    Call<String> getCuttingToolBind(@Body RequestBody info);

    /**
     * 根据材料号获取材料刀
     * @param info
     * @return
     */
    @POST("/ScrapBusiness/getCuttingTool")
    Call<String> getCuttingTool(@Body RequestBody info);

    /**
     * 报废
     * @param info
     * @return
     */
    @POST("/ScrapBusiness/addScrap")
    Call<String> addScrap(@Body RequestBody info);
    // --------------报废结束--------------


    // --------------标签置换开始--------------未完成
    /**
     * 标签置换
     * @param info
     * @return
     */
    @POST("/SystemBusiness/changeRFIDForToll")
    Call<String> changeRFIDForToll(@Body RequestBody info);
    // --------------标签置换结束--------------


    // --------------清空标签开始--------------未完成
    /**
     * 查询标签绑定信息
     * @param info
     * @return
     */
    @POST("/SystemBusiness/queryByRFID")
    Call<String> queryByRFID(@Body RequestBody info);

    /**
     * 清空标签
     * @param info
     * @return
     */
    @POST("/SystemBusiness/clearRFID")
    Call<String> clearRFID(@Body RequestBody info);
    // --------------清空标签结束--------------


    // --------------登陆开始--------------未完成
    /**
     * 输入登陆
     * @param info
     * @return
     */
    @POST("/auth/loganForPDA")
    Call<String> loganForPDA(@Body RequestBody info);

    /**
     * 扫描登陆
     * @param info
     * @return
     */
    @POST("/authCustomer/search")
    Call<String> logonRfidSearch(@Body RequestBody info);
    // --------------登陆结束--------------
}
