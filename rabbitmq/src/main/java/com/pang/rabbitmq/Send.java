package com.pang.rabbitmq;

import com.pang.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author pangdexin
 * @Date 2023/7/11 17:25
 * @Description: TODO
 */
public class Send {

  private final static String QUEUE_NAME="simple_queue";

  public static void main(String[] args) throws IOException, TimeoutException {
    //获取连接以及MQ通道
    Connection connection = ConnectionUtil.getConnection();
    // 轻量级的 Connection，这是完成大部分API的地方
    Channel    channel    = connection.createChannel();

    // 声明（创建）队列，必须声明队列才能够发送消息，我们可以把消息发送到队列中。
    // 声明一个队列是幂等的 - 只有当它不存在时才会被创建
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    // 消息内容
    String message = "Hello World!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    //关闭通道和连接
    channel.close();
    connection.close();
  }
}
