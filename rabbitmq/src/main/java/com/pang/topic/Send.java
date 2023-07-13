package com.pang.topic;

import com.pang.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author pangdexin
 * @Date 2023/7/12 16:40
 * @Description: TODO
 */
public class Send {
  private final static String EXCHANG_NAME = "topic_exchange_test";

  public static void main(String[] args) throws IOException, TimeoutException {
    //获取连接
    Connection connection = ConnectionUtil.getConnection();
    //获取通道
    Channel channel = connection.createChannel();
    //声明Exchange，指定类型为topic  //交换机持久化
    channel.exchangeDeclare(EXCHANG_NAME,"topic",true);
    for (int i=0;i<50;i++){
      //消息内容
      String message = "新增商品 : id ="+ i;
      // 发送消息，并且指定routing key 为：insert ,代表新增商品;MessageProperties.PERSISTENT_TEXT_PLAIN为持久化消息(即DeliveryMode参数为2)
      channel.basicPublish(EXCHANG_NAME, "item.delete", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
      System.out.println(" [商品服务：] Sent '" + message + "'");
    }
    channel.close();
    connection.close();
  }
}
