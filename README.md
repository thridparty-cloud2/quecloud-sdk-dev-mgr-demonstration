## QuecCloud Device Management SDK Demo

This repository provides a demonstration project for the quecloud-sdk-dev-mgr library. It shows how to use the SDK to work with project, product, device, TSL, SN binding, queue, and subscription management APIs.

## Prerequisites

- JDK 1.8
- Maven 3.6.3

## Getting Started

The main sample implementation is located in QueCloudDevMgrDemo.java, which contains example API calls and parameter descriptions for each supported operation.

Before running the demo:

1. Open QueCloudDevMgrDemo.java.
2. Replace the placeholder values for AccessKey, AccessSecret, and endpoint.
3. Replace the remaining placeholder parameters in each example method as needed.
4. Run the relevant example method.

AccessKey and AccessSecret must be created by a user in the device management platform. The endpoint should be set to the correct service address for your environment. For the China production environment, use iot-api.quectelcn.com.

## Multi-tenant Usage

If you need to access data for different users, create separate MgrClient instances with the corresponding AccessKey and AccessSecret for each tenant.

## Support

For communication or support, refer to the following contact image:
<img style="width:20%;" src="/IMG/CBBE3E2F06944E79CF5C87267B909136.png" />