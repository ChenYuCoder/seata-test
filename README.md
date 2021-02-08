# Seata SpringBoot Http Test
> springBoot项目集成Seata分布式事务，使用Http进行服务间调用
* 安装Seata服务，https://github.com/seata/seata/releases
* 启动 seata/seata/bin/seata-server.sh
* 本地依赖
```
    <dependency>
      <groupId>io.seata</groupId>
      <artifactId>seata-spring-boot-starter</artifactId>
      <version>1.4.0</version>
    </dependency>
```
* 配置文件
```
seata:
  # 事务分组
  tx-service-group: my_test_tx_group
  # 事务ID组成部分
  application-id: order
  # seata服务地址，开启分布式事务，true为关闭
  service:
    grouplist: 
      default: 192.168.17.55:8091
    disable-global-transaction: false
```
* 开启事务
```
  // 注解开启事务
  @GlobalTransactional
  public String createOrder(@RequestBody OrderDO order) {
    String xId = RootContext.getXID();
    logger.info("transactional begin: xId: {}", xId);
    // 主服务在HTTP中增加header，其他服务即可自动绑定事务
    //HttpAutoConfiguration->TransactionPropagationIntercepter
    Map<String, String> header = new HashMap<>(8);
    header.put(RootContext.KEY_XID, xId);

    HttpUtils.syncCall(
      HttpUtils
        .createPostRequest(
          "http://127.0.0.1:8091/costAccountAmount?userId=1&amount=1&xId=" + xId,
          null, header));

    HttpUtils.syncCall(
      HttpUtils
        .createPostRequest("http://127.0.0.1:8093/costStorage?storageId=1&number=1&xId=" + xId,
          null, header));
    repository.save(order);
    throw new RuntimeException("测试");
//    return "success";
  }
```