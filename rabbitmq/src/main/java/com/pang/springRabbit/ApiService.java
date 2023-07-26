package com.pang.springRabbit;

import com.alibaba.fastjson.JSON;
import com.pang.domain.enums.SpringAmqpMessageTypeEnum;
import com.pang.domain.request.RabbitMessageRequest;
import com.pang.domain.request.paymentAddRequest;
import com.pang.utils.RabbitMqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author pangdexin
 * @Date 2023/7/26 11:33
 * @Description: TODO
 */
@Service
public class ApiService {

  @Autowired
  private RabbitMqProperties rabbitConfig;
  @Autowired
  private RabbitSender rabbitSender;

  public void add(paymentAddRequest request){
    RabbitMessageRequest messageRequest = new RabbitMessageRequest();
    messageRequest.setType(SpringAmqpMessageTypeEnum.PAYMENT.toString());
    messageRequest.setRetryLimit(rabbitConfig.getRetryLimit());
    messageRequest.setDelayTimes(rabbitConfig.getDelayTimes());
    messageRequest.setRetryCount(0);
    messageRequest.setData(request);

    rabbitSender.send(rabbitConfig.getApiExchange().getName(),rabbitConfig.getCommonQueue().getRoutingKey(),
        JSON.toJSONString(request));
  }

  public void processData(paymentAddRequest request){

  }
}
