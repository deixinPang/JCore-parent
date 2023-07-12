package com.pang.workqueues;

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
 * @Date 2023/7/12 13:35
 * @Description: TODO
 */
public class Recver1 {

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionUtil.getConnection();
    Channel    channel    = connection.createChannel();

    String QUEUE_NAME = "work_queue";
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    //设置每个消费者同时只能处理一条消息
    channel.basicQos(1);
    //接收消息
    DefaultConsumer consumer = new DefaultConsumer(channel){
      //获取消息且处理，这个方法类似事件监听，监听到消息时会被自动调用
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
          throws IOException {
        try {
          String msg = new String(body);
          System.out.println("Recver1：" + msg);
          channel.basicAck(envelope.getDeliveryTag(),false);

          Thread.sleep(2000);
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    };
    channel.basicConsume(QUEUE_NAME,false,consumer);
  }
}
