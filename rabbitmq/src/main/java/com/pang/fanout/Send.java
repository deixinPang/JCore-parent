package com.pang.fanout;

import com.pang.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author pangdexin
 * @Date 2023/7/12 14:20
 * @Description:
 * 生产者
 * 1） 声明Exchange，不再声明Queue
 * 2） 发送消息到Exchange，不再发送到Queue
 */

public class Send {
  private final static String EXCHANGE_NAME = "fanout_exchange_test";
  public static void main(String[] args) throws IOException, TimeoutException {
    // 获取到连接
    Connection connection = ConnectionUtil.getConnection();
    // 获取通道
    Channel channel = connection.createChannel();
    // 声明exchange，指定类型为fanout
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

    // 消息内容
    String message = "Hello everyone";
    // 发布消息到Exchange
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    System.out.println(" [生产者] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
}
