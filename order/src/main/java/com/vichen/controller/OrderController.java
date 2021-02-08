package com.vichen.controller;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyu
 * @date 2021/2/1
 */
@RestController
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final OrderRepository repository;

  public OrderController(OrderRepository repository) {
    this.repository = repository;
  }

  @PostMapping("createOrder")
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

}
