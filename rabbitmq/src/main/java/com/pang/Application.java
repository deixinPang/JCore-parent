package com.pang;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author pangdexin
 * @Date 2023/7/5 13:15
 * @Description: TODO
 */
@SpringBootApplication
@EnableRabbit
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    System.out.println("执行完毕");
  }


}
