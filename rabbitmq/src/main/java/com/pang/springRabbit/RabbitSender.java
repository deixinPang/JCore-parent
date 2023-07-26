package com.pang.springRabbit;

import com.alibaba.fastjson.JSON;
import com.pang.domain.request.RabbitMessageRequest;
import com.pang.utils.RabbitMqProperties;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author pangdexin
 * @Date 2023/7/14 11:41
 * @Description: TODO
 */
@Component
public class RabbitSender {

  @Autowired
  private RabbitMqProperties rabbitMqProperties;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   * 发送MQ消息
   *
   * @param exChangeName 交换机名
   * @param routingKey   指定队列关键词
   * @param request      发送内容（JSON String）
   */
  public void send(String exChangeName, String routingKey, String request){
    rabbitTemplate.convertAndSend(exChangeName, routingKey, request);
  }

  /**
   * 重发消息到死信队列
   *
   * @param request 发送内容（RabbitMessageRequest 对象）
   */
  public void sendRetry(RabbitMessageRequest request) {
    MessagePostProcessor processor = message -> {
      message.getMessageProperties().setExpiration(Long.toString(request.getDelayTimes()));
      return message;
    };
    rabbitTemplate.convertAndSend(rabbitMqProperties.getApiExchange().getName(), rabbitMqProperties.getDeadQueue().getRoutingKey(),
        JSON.toJSONString(request), processor);
  }
}
