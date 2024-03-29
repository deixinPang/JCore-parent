package com.pang.topic;

import com.pang.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;

/**
 * @Author pangdexin
 * @Date 2023/7/12 16:46
 * @Description: TODO
 */
public class Recv {
  private final static String QUEUE_NAME = "topic_exchange_queue_1";
  private final static String EXCHANGE_NAME = "topic_exchange_test";

  public static void main(String[] args) throws IOException, TimeoutException {
    // 获取到连接
    Connection connection = ConnectionUtil.getConnection();
    // 获取通道
    Channel channel = connection.createChannel();
    // 声明队列-->durable参数为true是将队列持久化
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    // 绑定队列到交换机，同时指定需要订阅的routing key。需要 update、delete
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.update");
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.delete");
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.insert");
    // 定义队列的消费者
    DefaultConsumer consumer = new DefaultConsumer(channel) {
      // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
      @SneakyThrows
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          BasicProperties properties,
          byte[] body) throws IOException {
        // body 即消息体
        String msg = new String(body);
        System.out.println(" [消费者1] received : " + msg + "!");
        Thread.sleep(2000);
      }
    };
    // 监听队列，自动ACK
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}
