package com.pang.simple;

import com.pang.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author pangdexin
 * @Date 2023/7/11 17:47
 * @Description: 自动ack
 * 自动ack有个问题，如果消费者有异常，消息也会被消费掉。
 * 改为手动并将异常去掉后，消息的状态变成Unacked，关闭这个消费者，状态就变回ready
 */
public class Recv {

  private final static String QUEUE_NAME="simple_queue";

  public static void main(String[] args) throws IOException, TimeoutException {
    //获取连接以及MQ通道
    Connection connection = ConnectionUtil.getConnection();
    // 轻量级的 Connection，这是完成大部分API的地方
    Channel channel = connection.createChannel();

    // 声明（创建）队列，必须声明队列才能够发送消息，我们可以把消息发送到队列中。
    // 声明一个队列是幂等的 - 只有当它不存在时才会被创建
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);

    DefaultConsumer consumer = new DefaultConsumer(channel) {
      // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          BasicProperties properties,byte[] body) throws IOException {
        // body 即消息体
        String msg = new String(body);
//        int i = 1/0;
        System.out.println(" [x] received : " + msg + "!");
      }
    };
    // 监听队列，第二个参数：是否自动进行消息确认，false为手动、true为自动。
    channel.basicConsume(QUEUE_NAME, false, consumer);
  }
}
