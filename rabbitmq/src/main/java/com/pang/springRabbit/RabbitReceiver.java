package com.pang.springRabbit;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pang.domain.enums.SpringAmqpMessageTypeEnum;
import com.pang.domain.request.RabbitMessageRequest;
import com.pang.domain.request.paymentAddRequest;
import com.pang.utils.RabbitMqProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author pangdexin
 * @Date 2023/7/14 13:35
 * @Description: TODO
 */
@Component
public class RabbitReceiver {
  private final static Logger log = LoggerFactory.getLogger(RabbitReceiver.class);
  @Autowired
  private RabbitMqProperties rabbitMqProperties;
  @Autowired
  private RabbitSender rabbitSender;
  @Autowired
  private ApiService apiService;

  /**
   * 正常队列
   */
  @RabbitListener(queues = "${hdw.rabbitmq.common-queue.name}")
  public void receiveQueue(String body){
    log.info(String.format("MQ_Rec -> MessageBody: %s", body));
    if (!body.isEmpty()){
      RabbitMessageRequest messageRequest = null;
      try {
        messageRequest = JSON.parseObject(body, RabbitMessageRequest.class);
        if (ObjectUtil.isNotEmpty(messageRequest.getType())){
          switch (SpringAmqpMessageTypeEnum.valueOf(messageRequest.getType())){
            case ORDEROPERATION:
              //实现-->直接在这个类实现
              addOrder(messageRequest);
              break;
            case PAYMENT:
              //实现-->在其它类实现
              paymentAddRequest paymentAddRequest = JSON
                  .parseObject(JSON.toJSONString(messageRequest.getData()), new TypeReference<paymentAddRequest>() {});
              apiService.processData(paymentAddRequest);
              break;
            default:
              break;
          }
        }
      } catch (Exception ex) {
        //消息为空则封装消息
        if (ObjectUtil.isNull(messageRequest)) {
          messageRequest = new RabbitMessageRequest();
          messageRequest.setData(body);
          messageRequest.setRetryLimit(rabbitMqProperties.getRetryLimit());
          messageRequest.setDelayTimes(rabbitMqProperties.getDelayTimes());
          messageRequest.setRetryCount(0);
        }
        log.error(String.format("MQ_Error: %s", ex.getMessage()), body);
        //重发消息到死信队列
        if (messageRequest.getRetryCount() < messageRequest.getRetryLimit()) {
          messageRequest.setRetryCount(messageRequest.getRetryCount() + 1);
          rabbitSender.sendRetry(messageRequest);
          log.warn(String.format("MQ_Retry -> MessageBody: %s", JSON.toJSONString(messageRequest)));
        }
      }
    }
  }

  /**
   * 重发队列
   */
  @RabbitListener(queues = "${hdw.rabbitmq.retry-queue.name}")
  public void retryQueue(String body){
    if (ObjectUtil.isNotNull(body)){
      try {
        rabbitSender
            .send(rabbitMqProperties.getApiExchange().toString(), rabbitMqProperties.getCommonQueue().getRoutingKey(),
                body);
      } catch (Exception e) {
        log.error(String.format("MQ_Error: %s", e.getMessage()), body);
      }
    }
  }



  /**
   * 下单处理
   */
  public void addOrder(RabbitMessageRequest messageRequest){
    //进行操作
  }
}
