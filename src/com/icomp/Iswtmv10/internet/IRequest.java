package com.icomp.Iswtmv10.internet;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;


/**
 * Created by Think on 2016/9/12.
 */
public interface IRequest {

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
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/library/getOrders")
    Call<String> getOrders(@Body RequestBody json);

    /**
     * 出库
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/library/outApply")
    Call<String> outApply(@Body RequestBody json);
    // --------------刀具出库结束--------------


    // --------------刀具打码开始--------------未完成
    /**
     * 获取已出库订单
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/queryOutOrder")
    Call<String> queryOutOrder(@Body RequestBody json);

    /**
     * 查看刀身码
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/queryBladeCodes")
    Call<String> queryBladeCodes(@Body RequestBody json);
    // --------------刀具打码结束--------------

    // --------------合成刀具打码开始--------------未完成
    /**
     * 合成刀T号查询刀身码
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/queryBladeCode")
    Call<String> queryBladeCode(@Body RequestBody json);
    // --------------合成刀具打码结束--------------


    // --------------合成刀具初始化开始--------------
    /**
     * 获取合成刀信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/getConfig")
    Call<String> getSynthesisCuttingConfig(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 初始化合成刀具
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/init")
    Call<String> synthesisCuttingInit(@Body RequestBody json);
    // --------------合成刀具初始化结束--------------


    // --------------加工设备初始化开始--------------
    /**
     * 获取流水线
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/productlineBusiness/getAssemblylines")
    Call<String> getAssemblylines(@Body RequestBody json);

    /**
     * 根据流水线获取设备
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/productlineBusiness/getEquipmentByAssemblyline")
    Call<String> getEquipmentByAssemblyline(@Body RequestBody json);

    /**
     * 初始化设备
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/productlineBusiness/initEquipment")
    Call<String> initEquipment(@Body RequestBody json);
    // --------------加工设备初始化结束--------------


    // --------------员工初始化开始--------------
    /**
     * 根据员工号查询
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/auth/query")
    Call<String> queryEmployee(@Body RequestBody json);

    /**
     * 初始化员工
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/auth/init")
    Call<String> initEmployee(@Body RequestBody json);
    // --------------员工始化结束--------------


    // --------------刀具绑定开始--------------
    /**
     * 查询材料刀信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/getUnbind")
    Call<String> getUnbind(@Body RequestBody json);

    /**
     * 刀具绑定
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/bind")
    Call<String> bindUnbind(@Body RequestBody json);
    // --------------刀具绑定结束--------------



    // --------------刀具换装、刀具拆分、刀具组装公用方法开始--------------未完成
    /**
     * 根据刀身码或者rfid标签查询合成刀组装信息接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/getBind")
    Call<String> getBind(@Body RequestBody json, @HeaderMap Map<String, String> headers);
    // --------------刀具拆分和组装公用方法开始--------------未完成


    // --------------刀具换装开始--------------未完成
    /**
     * 根据Rifd获取材料刀信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBind/search")
    Call<String> searchCuttingToolBind(@Body RequestBody json);

    /**
     * 扫描标签查询钻头类刀具信息接口地址
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/queryBindInfo")
    Call<String> queryBindInfo(@Body RequestBody json);

    /**
     * 换装
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/exChange")
    Call<String> exChange(@Body RequestBody json, @HeaderMap Map<String, String> headers);
    // --------------刀具换装结束--------------


    // --------------刀具拆分开始--------------未完成
    /**
     * 提交拆分信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/Unconfig")
    Call<String> unconfig(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 拆分扫描
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/queryRFIDForUnConfig")
    Call<String> queryRFIDForUnConfig(@Body RequestBody json);

    // --------------刀具拆分结束--------------


    // --------------刀具组装开始--------------未完成
    /**
     * 提交组装信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/Config")
    Call<String> config(@Body RequestBody json, @HeaderMap Map<String, String> headers);
    // --------------刀具组装结束--------------


    // --------------安上设备开始--------------未完成
    /**
     * 根据rfid获取合成刀信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/productlineBusiness/querySynthesisCuttingTool")
    Call<String> querySynthesisCuttingTool(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 根据合成刀标签或者刀身码查询信息接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/productlineBusiness/queryForInstall")
    Call<String> queryForInstall(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 查询设备和轴号
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/productlineBusiness/queryEquipmentByRFID")
    Call<String> queryEquipmentByRFID(@Body RequestBody json);



    /**
     * 根据rfid获取设备信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/productLineEquipment/search")
    Call<String> searchProductLineEquipment(@Body RequestBody json);

    /**
     * 扫描获取刃磨设备信息接口地址
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/queryGrindingEquipmentsByRFID")
    Call<String> queryGrindingEquipmentsByRFID(@Body RequestBody json);

    /**
     * 获取刃磨设备列表接口地址
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/queryGrindingEquipments")
    Call<String> queryGrindingEquipments(@Body RequestBody json);

    /**
     * 安上设备接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/productlineBusiness/install")
    Call<String> install(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 按上设备
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/bindEquipment")
    Call<String> bindEquipment(@Body RequestBody json, @HeaderMap Map<String, String> headers);
    // --------------安上设备结束--------------


    // --------------卸下设备开始--------------未完成
    /**
     * 根据rfid获取生产线信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBindleRecords/searchLast")
    Call<String> searchProductLine(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 根据合成刀标签或者刀身码查询信息接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/productlineBusiness/queryForUnInstall")
    Call<String> queryForUnInstall(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 卸下
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBusiness/unBindEquipment")
    Call<String> unBindEquipment(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 卸下设备接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/productlineBusiness/unInstall")
    Call<String> unInstall(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 卸下
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/synthesisCuttingToolBindleRecords/getParts")
    Call<String> getParts(@Body RequestBody json);
    // --------------卸下设备结束--------------


    // --------------场内刃磨开始--------------未完成
    /**
     * 根据RFID获取材料刀信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/getCuttingToolBind")
    Call<String> getInCuttingToolBind(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 根据RFID获取材料刀信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/queryCuttingToolBind")
    Call<String> queryCuttingToolBind(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 根据RFID获取材料刀信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/queryBindInfo")
    Call<String> queryBindInfo(@Body RequestBody json, @HeaderMap Map<String, String> headers);


    /**
     * 根据材料号获取材料刀
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/getCuttingTool")
    Call<String> getInCuttingTool(@Body RequestBody json);

    /**
     * 获取刃磨记录
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/countInsideFactory")
    Call<String> countInsideFactory(@Body RequestBody json);

    /**
     * 补充既往刃磨记录
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/addInsideFactoryHistory")
    Call<String> addInsideFactoryHistory(@Body RequestBody json);

    /**
     * 添加场内刃磨
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/addInsideFactory")
    Call<String> addInsideFactory(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 场内刃磨接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/inFactoryBusiness/insideGrinding")
    Call<String> insideGrinding(@Body RequestBody json, @HeaderMap Map<String, String> headers);
    // --------------场内刃磨结束--------------


    // --------------场外刃磨开始--------------未完成
    /**
     * 根据RFID获取材料刀信息
     * @param json 请求数据
     * @param headers 请求头
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/getCuttingToolBind")
    Call<String> getOutCuttingToolBind(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 根据材料号获取材料刀
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/getCuttingTool")
    Call<String> getOutCuttingTool(@Body RequestBody json);

    /**
     * 输入合成刀T号，查询材料刀具信息接口地址
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/getCuttingToolByTCode")
    Call<String> getCuttingToolByTCode(@Body RequestBody json);

    /**
     * 获取刃磨记录
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/countOutsideFactory")
    Call<String> countOutsideFactory(@Body RequestBody json);

    /**
     * 补充既往刃磨记录
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/addOutsideFactoryHistory")
    Call<String> addOutsideFactoryHistory(@Body RequestBody json);

    /**
     * 添加场外刃磨
     * @param json 请求数据
     * @param headers 请求头
     * @param headers 请求头
     * @return
     */
    @POST("/outFactoryBusiness/addOutsideFactory")
    Call<String> addOutsideFactory(@Body RequestBody json, @HeaderMap Map<String, String> headers);


    /**
     * 厂外刃磨接口地址
     * @param json 请求数据
     * @param headers 请求头
     * @param headers 请求头
     * @return
     */
    @POST("/outFactoryBusiness/outsideGrinding")
    Call<String> outsideGrinding(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 添加场外刃磨服务商
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/getSharpenProvider")
    Call<String> getSharpenProvider(@Body RequestBody json);



    /**
     * 添加场外刃磨服务商
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/outFactoryBusiness/queryForOutGrinding")
    Call<String> queryForOutGrinding(@Body RequestBody json);
    // --------------场外刃磨结束--------------


    // --------------报废开始--------------未完成
    /**
     * 根据RFID获取材料刀信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/ScrapBusiness/getCuttingToolBind")
    Call<String> getCuttingToolBind(@Body RequestBody json, @HeaderMap Map<String, String> headers);

    /**
     * 根据材料号获取材料刀
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/ScrapBusiness/getCuttingTool")
    Call<String> getCuttingTool(@Body RequestBody json);

    /**
     * 报废
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/ScrapBusiness/addScrap")
    Call<String> addScrap(@Body RequestBody json);
    // --------------报废结束--------------


    // --------------标签置换开始--------------未完成
    /**
     * 标签置换
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/SystemBusiness/changeRFIDForToll")
    Call<String> changeRFIDForToll(@Body RequestBody json);
    // --------------标签置换结束--------------


    // --------------清空标签开始--------------未完成
    /**
     * 查询标签绑定信息
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/SystemBusiness/queryByRFID")
    Call<String> queryByRFID(@Body RequestBody json);

    /**
     * 清空标签
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/SystemBusiness/clearRFID")
    Call<String> clearRFID(@Body RequestBody json);
    // --------------清空标签结束--------------


    // --------------登陆开始--------------未完成
    /**
     * 输入登陆
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/auth/loganForPDA")
    Call<String> loganForPDA(@Body RequestBody json);

    /**
     * 扫描登陆
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/authCustomer/search")
    Call<String> logonRfidSearch(@Body RequestBody json);
    // --------------登陆结束--------------

    // --------------权限查询开始--------------
    /**
     * 权限查询
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/power/getPowerForUser")
    Call<String> getPowerForUser(@Body RequestBody json);
    // --------------权限查询结束--------------

    // --------------快速查询开始--------------
    /**
     * 快速查询
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/RFID/fastQuery")
    Call<String> fastQuery(@Body RequestBody json);
    // --------------快速查询结束--------------

    // --------------材料刀刀身码绑定开始--------------
    /**
     * 添加材料刀刀身码接口地址
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/bindBlade")
    Call<String> bindBlade(@Body RequestBody json);
    // --------------材料刀刀身码绑定结束--------------

    // --------------流转刀具开始--------------
    /**
     * 根据合成刀T号查询材料刀接口地址
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/queryByTCode")
    Call<String> queryByTCode(@Body RequestBody json);

    /**
     * 流转刀具绑定
     * @param json 请求数据
     * @return json格式数据
     */
    @POST("/cuttingToolBusiness/bindForRunning")
    Call<String> bindForRunning(@Body RequestBody json);
    // --------------流转刀具结束--------------

}
