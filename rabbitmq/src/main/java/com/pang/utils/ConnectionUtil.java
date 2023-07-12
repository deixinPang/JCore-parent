package com.pang.utils;

import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Connection;


/**
 * @Author pangdexin
 * @Date 2023/7/11 17:15
 * @Description: TODO
 */
public class ConnectionUtil {
  /**
   * 建立与RabbitMQ连接
   */
  public static Connection getConnection() throws IOException, TimeoutException {
    //定义连接工厂
    ConnectionFactory factory = new ConnectionFactory();
//    //设置服务地址
//    factory.setHost("127.0.0.1");
//    //端口
//    factory.setPort(15672);
    //设置账号信息，用户名、密码、vhost
//    factory.setUsername("guest");
//    factory.setPassword("guest");
//    factory.setVirtualHost("/");
    // 通过工程获取连接
    Connection connection = factory.newConnection();
    return connection;
  }
}
