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
        // accessKey and accessSecret are created by users on the DMP platform.
        InitClientProfile initClientProfile = new InitClientProfile(
                "${accessKey}",
                "${accessSecret}",
                "${endpoint}");
        // Get the MgrClient instance. A singleton pattern is recommended. This object provides project, product, and device related SDK APIs.
        MgrClient mgrClient = new MgrClient(initClientProfile);

        // Get project list
        ProjectListRequest projectListRequest=new ProjectListRequest();
        ProjectListResponse projectListResponse=mgrClient.getProjectList(projectListRequest);
        log.info("Project list response:{}",JSONObject.toJSONString(projectListResponse));

        // Get product category list
        ProductItemsRequest productItemsRequest=new ProductItemsRequest();
        ProductItemsResponse productItemsResponse=mgrClient.getProductItems(productItemsRequest);
        log.info("Product category list response:{}",JSONObject.toJSONString(productItemsResponse));

        // Get product details
        DetailProductRequest detailProductRequest=new DetailProductRequest("${productKey}");
        ProductInfoResponse productInfoResponse=mgrClient.getProductDetail(detailProductRequest);
        log.info("Product details response:{}",JSONObject.toJSONString(productInfoResponse));

        // Get product list
        ProductListRequest productListRequest = new ProductListRequest("${projectId}");
        ProductListResponse productListResponse=mgrClient.getProductList(productListRequest);
        log.info("Product list response:{}",JSONObject.toJSONString(productListResponse));

        // Get device list
        DeviceListRequest deviceListRequest=new DeviceListRequest("${productKey}");
        DeviceListResponse deviceListResponse = mgrClient.getDeviceList(deviceListRequest);
        log.info("Device list response:{}",JSONObject.toJSONString(deviceListResponse));

        // Get device details
        DeviceBasicRequest deviceBasicRequest = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceDetailResponse deviceDetailResponse = mgrClient.getDeviceDetail(deviceBasicRequest);
        log.info("Device details response:{}",JSONObject.toJSONString(deviceDetailResponse));

        // Get device resources
        DeviceBasicRequest deviceBasicRequest2 = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceResourceResponse deviceResourceResponse = mgrClient.deviceResource(deviceBasicRequest2);
        log.info("Device resource response:{}",JSONObject.toJSONString(deviceResourceResponse));


        // Query historical device uplink and downlink data
        DeviceDataHistoryRequest deviceDataHistoryRequest = new DeviceDataHistoryRequest("${productKey}","${deviceKey}");
        DeviceDataHistoryResponse deviceDataHistoryResponse = mgrClient.getDeviceDataHistory(deviceDataHistoryRequest);
        log.info("Historical device data response:{}",JSONObject.toJSONString(deviceDataHistoryResponse));

        // Query device event log data
        DeviceEventDataRequest deviceEventDataRequest = new DeviceEventDataRequest("${productKey}","${deviceKey}");
        DeviceEventDataResponse deviceEventDataResponse = mgrClient.getDeviceEventData(deviceEventDataRequest);
        log.info("Device event log response:{}",JSONObject.toJSONString(deviceEventDataResponse));

        // Query latest device location data
        DeviceBasicRequest deviceBasicRequest1 = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceLocationLatestResponse deviceLocationLatestResponse = mgrClient.getDeviceLocationLatest(deviceBasicRequest1);
        log.info("Latest device location response:{}",JSONObject.toJSONString(deviceLocationLatestResponse));

        // Get TSL model
        ObtainTslTslJsonRequest obtainTslTslJsonRequest = new ObtainTslTslJsonRequest("${productKey}");
        ObtainTslTslJsonResponse obtainTslTslJsonResponse = mgrClient.getTslJson(obtainTslTslJsonRequest);
        log.info("TSL model response:{}",JSONObject.toJSONString(obtainTslTslJsonResponse));

        // Create SN
        CreateSNRequest createSNRequest = new CreateSNRequest(10000);
        CreateSNResponse createSNResponse=mgrClient.generateSn(createSNRequest);
        log.info("Create SN response:{}",JSONObject.toJSONString(createSNResponse));

        // Batch add or overwrite SN to PK/DK mappings
        SNBindDeviceRequestBody requestBody = new SNBindDeviceRequestBody("${sn}","${productKey}","${deviceKey}");
        List<SNBindDeviceRequestBody> requestBodyLsit=new ArrayList<>();
        requestBodyLsit.add(requestBody);
        CreateSNBindDeviceRequest createSNBindDeviceRequest = new CreateSNBindDeviceRequest("overwritePkDk",requestBodyLsit);
        SNBindDeviceResponse sNBindDeviceResponse = mgrClient.createSnBindDevice(createSNBindDeviceRequest);
        log.info("Batch add or overwrite SN mapping response:{}",JSONObject.toJSONString(sNBindDeviceResponse));

        // Batch delete SN to PK/DK mappings
        SNDeleteDeviceRequestBody requestBody1 = new SNDeleteDeviceRequestBody();
        requestBody1.setProductKey("${productKey}");
        requestBody1.setDeviceKey("${deviceKey}");
        List<SNDeleteDeviceRequestBody> requestBodyLsit1 = new ArrayList<>();
        requestBodyLsit1.add(requestBody1);
        DeleteSNBindDeviceRequest deleteSNBindDeviceRequest = new DeleteSNBindDeviceRequest(requestBodyLsit1);
        SNBindDeviceResponse result = mgrClient.delSnBindDevice(deleteSNBindDeviceRequest);
        log.info("Batch delete SN mapping response:{}",JSONObject.toJSONString(result));

        // Query the corresponding DK list by PK and SN list
        FindDkRequest requestBody2 = new FindDkRequest();
        List<FindDkRequestInfo> findDkRequest = new ArrayList<>();
        FindDkRequestInfo findDkRequestInfo =new FindDkRequestInfo();
        findDkRequestInfo.setPk("${productKey}");
        findDkRequestInfo.setSn("${sn}");
        findDkRequest.add(findDkRequestInfo);
        requestBody2.setFindDkRequest(findDkRequest);
        SNBindDeviceResponse findDkResponse = mgrClient.findDkList(requestBody2);
        log.info("DK list by PK and SN response:{}",JSONObject.toJSONString(findDkResponse));

        // Query the corresponding SN list by PK and DK list
        List<FindSNRequestInfo> findSnRequest = new ArrayList<>();
        FindSNRequestInfo findSNRequestInfo =new FindSNRequestInfo();
        findSNRequestInfo.setPk("${productKey}");
        findSNRequestInfo.setDk("${deviceKey}");
        findSnRequest.add(findSNRequestInfo);
        FindSNRequest requestBody3 = new FindSNRequest(findSnRequest);
        SNBindDeviceResponse findSNResponse = mgrClient.findSnList(requestBody3);
        log.info("SN list by PK and DK response:{}",JSONObject.toJSONString(findSNResponse));


        // Create queue
        QueceCreateRequest createRequest = new QueceCreateRequest("${queueName}");
        QueceCreateResponse queceCreateResponse = mgrClient.createQuece(createRequest);
        log.info("Create queue response:{}",JSONObject.toJSONString(queceCreateResponse));

        // Get queue details
        QueceDetailRequest queceDetailRequest = new QueceDetailRequest("${queueId}");
        QueceDetailResponse queceDetailResponse = mgrClient.getQueceDetail(queceDetailRequest);
        log.info("Queue details response:{}",JSONObject.toJSONString(queceDetailResponse));

        // Delete queue
        QueceDetailRequest queceDetailRequest1 = new QueceDetailRequest("${queueId}");
        BasicResultResponse basicResultResponse1 = mgrClient.deleteQuece(queceDetailRequest1);
        log.info("Delete queue response:{}",JSONObject.toJSONString(basicResultResponse1));

        // Create subscription
        List<Integer> msgTypes = new ArrayList<Integer>();
        /**
         *  msgTypes message types:
         *
         *  Pass-through products support the following types:
         *  1 - Device online/offline event
         *  2 - Device and module status
         *  3 - Device uplink data
         *  4 - Device downlink data
         *  5 - Device command response
         *  11 - Device location downlink information
         *  12 - Device raw location information
         *  13 - Device location information
         *  14 - Device binding change information
         *  15 - Device information change
         *
         *  TSL products support the following types:
         *  1 - Device online/offline event
         *  2 - Device and module status
         *  5 - Device command response
         *  6 - TSL property information
         *  7 - TSL event report - info
         *  8 - TSL event report - alarm
         *  9 - TSL event report - fault
         *  10 - TSL service invocation log
         *  11 - Device location downlink information
         *  12 - Device raw location information
         *  13 - Device location information
         *  14 - Device binding change information
         *  15 - Device information change
         */
        msgTypes.add(1);
        // dataLevel: 1 for product, 2 for device
        SubscribeCreateRequest subscribeCreateRequest = new SubscribeCreateRequest("${subscribeName}",1,"${productKey}","${queueName}",msgTypes);
        SubscribeCreateResponse subscribeCreateResponse=mgrClient.createSubscribe(subscribeCreateRequest);
        log.info("Create subscription response:{}",JSONObject.toJSONString(subscribeCreateResponse));

        // Create enterprise user subscription
        List<Integer> msgTypes_EnterpriseUser = new ArrayList<Integer>();
        // msgTypes message types: 101 - Product information change, 102 - Device information change, 103 - TSL release information change
        msgTypes_EnterpriseUser.add(101);
        EnterpriseUserSubscribeCreateRequest enterpriseUserSubscribeCreateRequest = new EnterpriseUserSubscribeCreateRequest("${subscribeName}", "${queueName}", msgTypes_EnterpriseUser);
        SubscribeCreateResponse enterpriseUser_subscribeCreateResponse=mgrClient.createEnterpriseUserSubscribe(enterpriseUserSubscribeCreateRequest);
        log.info("Create enterprise user subscription response:{}",JSONObject.toJSONString(enterpriseUser_subscribeCreateResponse));

        // Create end user subscription
        List<Integer> msgTypes_EndUser = new ArrayList<Integer>();
        // msgTypes message types: 201 - End user create/delete/update
        msgTypes_EnterpriseUser.add(201);
        EndUserSubscribeCreateRequest endUserSubscribeCreateRequest = new EndUserSubscribeCreateRequest("${subscribeName}", "${queueName}", msgTypes_EnterpriseUser,"${endUserDomain}");
        SubscribeCreateResponse endUser_subscribeCreateResponse=mgrClient.createEndUserSubscribe(endUserSubscribeCreateRequest);
        log.info("Create end user subscription response:{}",JSONObject.toJSONString(endUser_subscribeCreateResponse));

        // Create SaaS user subscription
        List<Integer> msgTypes_SaaSUser = new ArrayList<Integer>();
        // msgTypes message types: 101 - Product information change, 102 - Device information change, 103 - TSL release information change, 104 - Product authorization information
        msgTypes_SaaSUser.add(101);
        SaasSubscribeCreateRequest saasSubscribeCreateRequest = new SaasSubscribeCreateRequest("${subscribeName}","${queueName}", msgTypes_SaaSUser);
        SubscribeCreateResponse saaSUser_SubscribeCreateResponse = mgrClient.createSaaSSubscribe(saasSubscribeCreateRequest);
        log.info("Create SaaS user subscription response:{}",JSONObject.toJSONString(saaSUser_SubscribeCreateResponse));

        // Get subscription details
        SubscribeIdRequest subscribeIdRequest = new SubscribeIdRequest("${subscribeId}");
        SubscribeDetailResponse subscribeDetailResponse=mgrClient.getSubscribeDetail(subscribeIdRequest);
        log.info("Subscription details response:{}",JSONObject.toJSONString(subscribeDetailResponse));

        // Start subscription
        SubscribeIdRequest subscribeIdRequest1 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse = mgrClient.startSubscribe(subscribeIdRequest1);
        log.info("Start subscription response:{}",JSONObject.toJSONString(basicResultResponse));

        // Delete subscription
        SubscribeIdRequest subscribeIdRequest3 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse3 = mgrClient.deleteSubscribe(subscribeIdRequest3);
        log.info("Delete subscription response:{}",JSONObject.toJSONString(basicResultResponse3));

        // Stop subscription
        SubscribeIdRequest subscribeIdRequest2 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse2 = mgrClient.stopSubscribe(subscribeIdRequest2);
        log.info("Stop subscription response:{}",JSONObject.toJSONString(basicResultResponse2));
    }

}
