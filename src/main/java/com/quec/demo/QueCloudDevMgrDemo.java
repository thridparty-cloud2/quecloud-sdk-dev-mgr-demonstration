package com.quec.demo;

import com.alibaba.fastjson.JSONObject;
import com.quec.client.MgrClient;
import com.quec.config.InitClientProfile;
import com.quec.model.BasicResultResponse;
import com.quec.model.device.request.DeviceBasicRequest;
import com.quec.model.device.request.DeviceDataHistoryRequest;
import com.quec.model.device.request.DeviceEventDataRequest;
import com.quec.model.device.request.DeviceListRequest;
import com.quec.model.device.response.*;
import com.quec.model.product.request.DetailProductRequest;
import com.quec.model.product.request.ProductListRequest;
import com.quec.model.product.response.ProductInfoResponse;
import com.quec.model.product.response.ProductListResponse;
import com.quec.model.project.request.ProductItemsRequest;
import com.quec.model.project.request.ProjectIdRequest;
import com.quec.model.project.request.ProjectListRequest;
import com.quec.model.project.response.ProductItemsResponse;
import com.quec.model.project.response.ProjectDetailResponse;
import com.quec.model.project.response.ProjectListResponse;
import com.quec.model.quece.request.QueceCreateRequest;
import com.quec.model.quece.request.QueceDetailRequest;
import com.quec.model.quece.request.SubscribeCreateRequest;
import com.quec.model.quece.request.SubscribeIdRequest;
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

        // 获取详情详情
        ProjectIdRequest projectIdRequest=new ProjectIdRequest("${projectId}");
        ProjectDetailResponse projectDetailResponse= mgrClient.getProjectDetail(projectIdRequest);
        log.info("项目详情返回结果:{}",JSONObject.toJSONString(projectDetailResponse));

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
        SNBindDeviceResponse sNBindDeviceResponse=mgrClient.createSnBindDevice(createSNBindDeviceRequest);
        log.info("批量添加（或覆盖）sn 与 pk/dk 的关系返回结果:{}",JSONObject.toJSONString(sNBindDeviceResponse));

        // 批量删除 sn 与 pkdk 的对应关系
        SNDeleteDeviceRequestBody requestBody1 = new SNDeleteDeviceRequestBody();
        requestBody1.setProductKey("${productKey}");
        requestBody1.setDeviceKey("${deviceKey}");
        List<SNDeleteDeviceRequestBody> requestBodyLsit1=new ArrayList<>();
        requestBodyLsit1.add(requestBody1);
        DeleteSNBindDeviceRequest deleteSNBindDeviceRequest = new DeleteSNBindDeviceRequest(requestBodyLsit1);
        SNBindDeviceResponse result=mgrClient.delSnBindDevice(deleteSNBindDeviceRequest);
        log.info("批量删除 sn 与 pkdk 的对应关系返回结果:{}",JSONObject.toJSONString(result));

        // 创建队列
        QueceCreateRequest createRequest = new QueceCreateRequest("${queueName}");
        QueceCreateResponse queceCreateResponse = mgrClient.createQuece(createRequest);
        log.info("创建队列返回结果:{}",JSONObject.toJSONString(queceCreateResponse));

        // 队列详情
        QueceDetailRequest queceDetailRequest = new QueceDetailRequest("${queueId}");
        QueceDetailResponse queceDetailResponse = mgrClient.getQueceDetail(queceDetailRequest);
        log.info("队列详情返回结果:{}",JSONObject.toJSONString(queceDetailResponse));

        // 创建订阅
        List<Integer> msgTypes = new ArrayList<Integer>();
        // msgTypes 消息类型：
        //透传产品支持以下类型：
        //1-设备上下线事件
        //2-设备和模组状态
        //3-设备上行数据
        //4-设备下行数据
        //5-设备命令响应
        //11-设备定位下行信息
        //12-设备定位原始信息
        //13-设备定位信息
        //14-设备绑定变更信息
        //15-设备信息变更
        //物模型产品支持以下类型：
        //1-设备上下线事件
        //2-设备和模组状态
        //5-设备命令响应
        //6-物模型属性信息
        //7-物模型事件上报-信息
        //8-物模型事件上报-告警
        //9-物模型事件上报-故障
        //10-物模型服务调用日志
        //11-设备定位下行信息
        //12-设备定位原始信息
        //13-设备定位信息
        //14-设备绑定变更信息
        //15-设备信息变更
        msgTypes.add(1);
        // dataLevel 1产品 2设备
        SubscribeCreateRequest subscribeCreateRequest = new SubscribeCreateRequest("${subscribeName}",1,"${productKey}","${queueName}",msgTypes);
        SubscribeCreateResponse subscribeCreateResponse=mgrClient.createSubscribe(subscribeCreateRequest);
        log.info("创建订阅返回结果:{}",JSONObject.toJSONString(subscribeCreateResponse));

        // 订阅详情
        SubscribeIdRequest subscribeIdRequest = new SubscribeIdRequest("${subscribeId}");
        SubscribeDetailResponse subscribeDetailResponse=mgrClient.getSubscribeDetail(subscribeIdRequest);
        log.info("订阅详情返回结果:{}",JSONObject.toJSONString(subscribeDetailResponse));

        // 启动订阅
        SubscribeIdRequest subscribeIdRequest1 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse = mgrClient.startSubscribe(subscribeIdRequest1);
        log.info("启动订阅返回结果:{}",JSONObject.toJSONString(basicResultResponse));

        // 停止订阅
        SubscribeIdRequest subscribeIdRequest2 = new SubscribeIdRequest("531");
        BasicResultResponse basicResultResponse2 = mgrClient.stopSubscribe(subscribeIdRequest2);
        log.info("停止订阅返回结果:{}",JSONObject.toJSONString(basicResultResponse2));
    }

}