package com.quec.demo;

import com.alibaba.fastjson.JSONObject;
import com.quec.client.MgrClient;
import com.quec.config.InitClientProfile;
import com.quec.model.BasicResultResponse;
import com.quec.model.device.request.*;
import com.quec.model.device.response.*;
import com.quec.model.product.request.DetailProductRequest;
import com.quec.model.product.request.ProductListRequest;
import com.quec.model.product.response.ProductInfoResponse;
import com.quec.model.product.response.ProductListResponse;
import com.quec.model.project.request.ProductItemsRequest;
import com.quec.model.project.request.ProjectListRequest;
import com.quec.model.project.response.ProductItemsResponse;
import com.quec.model.project.response.ProjectListResponse;
import com.quec.model.quece.request.*;
import com.quec.model.quece.response.QueceCreateResponse;
import com.quec.model.quece.response.QueceDetailResponse;
import com.quec.model.quece.response.SubscribeCreateResponse;
import com.quec.model.quece.response.SubscribeDetailResponse;
import com.quec.model.snbind.request.*;
import com.quec.model.snbind.response.CreateSNResponse;
import com.quec.model.snbind.response.SNBindDeviceResponse;
import com.quec.model.tsl.request.ObtainTslTslJsonRequest;
import com.quec.model.tsl.response.ObtainTslTslJsonResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QueCloudDevMgrDemo {

    public void mgrDemo() throws Exception {
        // accessKey和accessSecre是DMP平台用户创建accessKey和accessSecret
        InitClientProfile initClientProfile = new InitClientProfile(
                "${accessKey}",
                "${accessSecret}",
                "${endpoint}");
        // 获取MgrClient对象。建议可以使用单例模式。此对象中包含项目、产品、设备相关的sdk
        MgrClient mgrClient = new MgrClient(initClientProfile);

        // 获取项目列表
        ProjectListRequest projectListRequest=new ProjectListRequest();
        ProjectListResponse projectListResponse=mgrClient.getProjectList(projectListRequest);
        log.info("项目列表返回结果:{}",JSONObject.toJSONString(projectListResponse));

        // 产品品类列表
        ProductItemsRequest productItemsRequest=new ProductItemsRequest();
        ProductItemsResponse productItemsResponse=mgrClient.getProductItems(productItemsRequest);
        log.info("产品品类列表返回结果:{}",JSONObject.toJSONString(productItemsResponse));

        // 获取产品详情
        DetailProductRequest detailProductRequest=new DetailProductRequest("${productKey}");
        ProductInfoResponse productInfoResponse=mgrClient.getProductDetail(detailProductRequest);
        log.info("获取产品详情返回结果:{}",JSONObject.toJSONString(productInfoResponse));

        // 获取产品列表
        ProductListRequest productListRequest = new ProductListRequest("${projectId}");
        ProductListResponse productListResponse=mgrClient.getProductList(productListRequest);
        log.info("获取产品列表返回结果:{}",JSONObject.toJSONString(productListResponse));

        // 获取设备列表
        DeviceListRequest deviceListRequest=new DeviceListRequest("${productKey}");
        DeviceListResponse deviceListResponse = mgrClient.getDeviceList(deviceListRequest);
        log.info("获取设备列表返回结果:{}",JSONObject.toJSONString(deviceListResponse));

        // 获取设备详情
        DeviceBasicRequest deviceBasicRequest = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceDetailResponse deviceDetailResponse = mgrClient.getDeviceDetail(deviceBasicRequest);
        log.info("获取设备详情返回结果:{}",JSONObject.toJSONString(deviceDetailResponse));

        // 获取设备资源
        DeviceBasicRequest deviceBasicRequest2 = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceResourceResponse deviceResourceResponse = mgrClient.deviceResource(deviceBasicRequest2);
        log.info("获取设备资源返回结果:{}",JSONObject.toJSONString(deviceResourceResponse));


        // 获取设备历史上下行信息查询
        DeviceDataHistoryRequest deviceDataHistoryRequest = new DeviceDataHistoryRequest("${productKey}","${deviceKey}");
        DeviceDataHistoryResponse deviceDataHistoryResponse = mgrClient.getDeviceDataHistory(deviceDataHistoryRequest);
        log.info("获取设备历史上下行信息查询返回结果:{}",JSONObject.toJSONString(deviceDataHistoryResponse));

        // 获取设备事件日志信息查询
        DeviceEventDataRequest deviceEventDataRequest = new DeviceEventDataRequest("${productKey}","${deviceKey}");
        DeviceEventDataResponse deviceEventDataResponse = mgrClient.getDeviceEventData(deviceEventDataRequest);
        log.info("获取设备事件日志信息查询返回结果:{}",JSONObject.toJSONString(deviceEventDataResponse));

        // 获取设备最新定位数据查询
        DeviceBasicRequest deviceBasicRequest1 = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceLocationLatestResponse deviceLocationLatestResponse = mgrClient.getDeviceLocationLatest(deviceBasicRequest1);
        log.info("获取设备最新定位数据查询返回结果:{}",JSONObject.toJSONString(deviceLocationLatestResponse));

        // 获取物模型
        ObtainTslTslJsonRequest obtainTslTslJsonRequest = new ObtainTslTslJsonRequest("${productKey}");
        ObtainTslTslJsonResponse obtainTslTslJsonResponse = mgrClient.getTslJson(obtainTslTslJsonRequest);
        log.info("获取物模型返回结果:{}",JSONObject.toJSONString(obtainTslTslJsonResponse));

        // 创建sn
        CreateSNRequest createSNRequest = new CreateSNRequest(10000);
        CreateSNResponse createSNResponse=mgrClient.generateSn(createSNRequest);
        log.info("创建sn返回结果:{}",JSONObject.toJSONString(createSNResponse));

        // 批量添加（或覆盖）sn 与 pk/dk 的关系
        SNBindDeviceRequestBody requestBody = new SNBindDeviceRequestBody("${sn}","${productKey}","${deviceKey}");
        List<SNBindDeviceRequestBody> requestBodyLsit=new ArrayList<>();
        requestBodyLsit.add(requestBody);
        CreateSNBindDeviceRequest createSNBindDeviceRequest = new CreateSNBindDeviceRequest("overwritePkDk",requestBodyLsit);
        SNBindDeviceResponse sNBindDeviceResponse = mgrClient.createSnBindDevice(createSNBindDeviceRequest);
        log.info("批量添加（或覆盖）sn 与 pk/dk 的关系返回结果:{}",JSONObject.toJSONString(sNBindDeviceResponse));

        // 批量删除 sn 与 pkdk 的对应关系
        SNDeleteDeviceRequestBody requestBody1 = new SNDeleteDeviceRequestBody();
        requestBody1.setProductKey("${productKey}");
        requestBody1.setDeviceKey("${deviceKey}");
        List<SNDeleteDeviceRequestBody> requestBodyLsit1 = new ArrayList<>();
        requestBodyLsit1.add(requestBody1);
        DeleteSNBindDeviceRequest deleteSNBindDeviceRequest = new DeleteSNBindDeviceRequest(requestBodyLsit1);
        SNBindDeviceResponse result = mgrClient.delSnBindDevice(deleteSNBindDeviceRequest);
        log.info("批量删除 sn 与 pkdk 的对应关系返回结果:{}",JSONObject.toJSONString(result));

        //根据 pk+sn 列表查询对应的 dk 列表
        FindDkRequest requestBody2 = new FindDkRequest();
        List<FindDkRequestInfo> findDkRequest = new ArrayList<>();
        FindDkRequestInfo findDkRequestInfo =new FindDkRequestInfo();
        findDkRequestInfo.setPk("${productKey}");
        findDkRequestInfo.setSn("${sn}");
        findDkRequest.add(findDkRequestInfo);
        requestBody2.setFindDkRequest(findDkRequest);
        SNBindDeviceResponse findDkResponse = mgrClient.findDkList(requestBody2);
        log.info("根据 pk+sn 列表查询对应的 dk 列表返回结果:{}",JSONObject.toJSONString(findDkResponse));

        //根据 pkdk 列表查询对应的 sn 列表
        List<FindSNRequestInfo> findSnRequest = new ArrayList<>();
        FindSNRequestInfo findSNRequestInfo =new FindSNRequestInfo();
        findSNRequestInfo.setPk("${productKey}");
        findSNRequestInfo.setDk("${deviceKey}");
        findSnRequest.add(findSNRequestInfo);
        FindSNRequest requestBody3 = new FindSNRequest(findSnRequest);
        SNBindDeviceResponse findSNResponse = mgrClient.findSnList(requestBody3);
        log.info("根据 pkdk 列表查询对应的 sn 列表返回结果:{}",JSONObject.toJSONString(findSNResponse));


        // 创建队列
        QueceCreateRequest createRequest = new QueceCreateRequest("${queueName}");
        QueceCreateResponse queceCreateResponse = mgrClient.createQuece(createRequest);
        log.info("创建队列返回结果:{}",JSONObject.toJSONString(queceCreateResponse));

        // 队列详情
        QueceDetailRequest queceDetailRequest = new QueceDetailRequest("${queueId}");
        QueceDetailResponse queceDetailResponse = mgrClient.getQueceDetail(queceDetailRequest);
        log.info("队列详情返回结果:{}",JSONObject.toJSONString(queceDetailResponse));

        // 删除队列
        QueceDetailRequest queceDetailRequest1 = new QueceDetailRequest("${queueId}");
        BasicResultResponse basicResultResponse1 = mgrClient.deleteQuece(queceDetailRequest1);
        log.info("删除队列返回结果:{}",JSONObject.toJSONString(basicResultResponse1));

        // 创建订阅
        List<Integer> msgTypes = new ArrayList<Integer>();
        /**
         *  msgTypes 消息类型：
         *
         *  透传产品支持以下类型：
         *  1-设备上下线事件
         *  2-设备和模组状态
         *  3-设备上行数据
         *  4-设备下行数据
         *  5-设备命令响应
         *  11-设备定位下行信息
         *  12-设备定位原始信息
         *  13-设备定位信息
         *  14-设备绑定变更信息
         *  15-设备信息变更
         *
         *  物模型产品支持以下类型：
         *  1-设备上下线事件
         *  2-设备和模组状态
         *  5-设备命令响应
         *  6-物模型属性信息
         *  7-物模型事件上报-信息
         *  8-物模型事件上报-告警
         *  9-物模型事件上报-故障
         *  10-物模型服务调用日志
         *  11-设备定位下行信息
         *  12-设备定位原始信息
         *  13-设备定位信息
         *  14-设备绑定变更信息
         *  15-设备信息变更
         */
        msgTypes.add(1);
        // dataLevel 1产品 2设备
        SubscribeCreateRequest subscribeCreateRequest = new SubscribeCreateRequest("${subscribeName}",1,"${productKey}","${queueName}",msgTypes);
        SubscribeCreateResponse subscribeCreateResponse=mgrClient.createSubscribe(subscribeCreateRequest);
        log.info("创建订阅返回结果:{}",JSONObject.toJSONString(subscribeCreateResponse));

        // 创建企业用户订阅
        List<Integer> msgTypes_EnterpriseUser = new ArrayList<Integer>();
        // msgTypes 消息类型：101-产品信息变更 102-设备信息变更 103-物模型发布信息变更
        msgTypes_EnterpriseUser.add(101);
        EnterpriseUserSubscribeCreateRequest enterpriseUserSubscribeCreateRequest = new EnterpriseUserSubscribeCreateRequest("${subscribeName}", "${queueName}", msgTypes_EnterpriseUser);
        SubscribeCreateResponse enterpriseUser_subscribeCreateResponse=mgrClient.createEnterpriseUserSubscribe(enterpriseUserSubscribeCreateRequest);
        log.info("创建企业用户订阅:{}",JSONObject.toJSONString(enterpriseUser_subscribeCreateResponse));

        // 创建终端用户订阅
        List<Integer> msgTypes_EndUser = new ArrayList<Integer>();
        // msgTypes 消息类型：201-终端用户新增/删除/修改
        msgTypes_EnterpriseUser.add(201);
        EndUserSubscribeCreateRequest endUserSubscribeCreateRequest = new EndUserSubscribeCreateRequest("${subscribeName}", "${queueName}", msgTypes_EnterpriseUser,"${endUserDomain}");
        SubscribeCreateResponse endUser_subscribeCreateResponse=mgrClient.createEndUserSubscribe(endUserSubscribeCreateRequest);
        log.info("创建终端用户订阅:{}",JSONObject.toJSONString(endUser_subscribeCreateResponse));

        //创建SaaS用户订阅
        List<Integer> msgTypes_SaaSUser = new ArrayList<Integer>();
        // msgTypes 消息类型：101-产品信息变更 102-设备信息变更 103-物模型发布信息变更 104-产品授权信息
        msgTypes_SaaSUser.add(101);
        SaasSubscribeCreateRequest saasSubscribeCreateRequest = new SaasSubscribeCreateRequest("${subscribeName}","${queueName}", msgTypes_SaaSUser);
        SubscribeCreateResponse saaSUser_SubscribeCreateResponse = mgrClient.createSaaSSubscribe(saasSubscribeCreateRequest);
        log.info("创建SaaS用户订阅:{}",JSONObject.toJSONString(saaSUser_SubscribeCreateResponse));

        // 订阅详情
        SubscribeIdRequest subscribeIdRequest = new SubscribeIdRequest("${subscribeId}");
        SubscribeDetailResponse subscribeDetailResponse=mgrClient.getSubscribeDetail(subscribeIdRequest);
        log.info("订阅详情返回结果:{}",JSONObject.toJSONString(subscribeDetailResponse));

        // 启动订阅
        SubscribeIdRequest subscribeIdRequest1 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse = mgrClient.startSubscribe(subscribeIdRequest1);
        log.info("启动订阅返回结果:{}",JSONObject.toJSONString(basicResultResponse));

        // 删除订阅
        SubscribeIdRequest subscribeIdRequest3 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse3 = mgrClient.deleteSubscribe(subscribeIdRequest3);
        log.info("删除订阅返回结果:{}",JSONObject.toJSONString(basicResultResponse3));

        // 停止订阅
        SubscribeIdRequest subscribeIdRequest2 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse2 = mgrClient.stopSubscribe(subscribeIdRequest2);
        log.info("停止订阅返回结果:{}",JSONObject.toJSONString(basicResultResponse2));
    }

}
