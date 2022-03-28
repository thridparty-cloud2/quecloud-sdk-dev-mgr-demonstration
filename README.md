###### **本demo是针对产品、设备管理的sdk方法介绍(即：quecloud-sdk-dev-mgr jar的介绍)**
# 开发环境
- JDK1.8
- maven 3.6.3
# 方法介绍
- 1.QueCloudDevMgrDemo.java中有各个方法的调用示例及参数说明
- 2.更改QueCloudDevMgrDemo.java 中AccessKey、AccessSecret、endpoint(AccessKey和AccessSecre是设备管理平台用户创建AccessKey和AccessSecret,endpoint是链接地址，国内生成环境值为 iot-gateway.quectel.com)
- 3.根据每个方法的参数说明替换方法中的参数
- 4.执行相应的方法即可
# 多租户
针对需要获取不同用户的数据,根据AccessKey和AccessSecre创建不同MgrClient对象即可