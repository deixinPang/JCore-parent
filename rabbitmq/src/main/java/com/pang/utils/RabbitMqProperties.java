package com.pang.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author pangdexin
 * @Date 2023/7/13 17:48
 * @Description: TODO
 */
@Data
@Configuration
@ConfigurationProperties("hdw.rabbitmq")
public class RabbitMqProperties {
  /**
   * 通用任务队列
   */
  private Queue commonQueue = new Queue();

  /**
   * 死信队列
   */
  private Queue    deadQueue   = new Queue();

  /**
   * 通用任务交换机
   */
  private Exchange apiExchange = new Exchange();

  /**
   * 重发次数
   */
  private Integer retryLimit;
  /**
   * 重发延时（毫秒）
   */
  private Long    delayTimes;

  public Queue getCommonQueue() {
    return commonQueue;
  }

  public void setCommonQueue(Queue commonQueue) {
    this.commonQueue = commonQueue;
  }

  public Queue getDeadQueue() {
    return deadQueue;
  }

  public void setDeadQueue(Queue deadQueue) {
    this.deadQueue = deadQueue;
  }

  public Exchange getApiExchange() {
    return apiExchange;
  }

  public void setApiExchange(Exchange apiExchange) {
    this.apiExchange = apiExchange;
  }

  public Integer getRetryLimit() {
    return retryLimit;
  }

  public void setRetryLimit(Integer retryLimit) {
    this.retryLimit = retryLimit;
  }

  public Long getDelayTimes() {
    return delayTimes;
  }

  public void setDelayTimes(Long delayTimes) {
    this.delayTimes = delayTimes;
  }

  public class Queue{
    /**
     * 队列名称
     */
    private String name;
    /**
     * 是否持久化
     */
    private Boolean druable;
    /**
     * 队列关键字
     */
    private String routingKey;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Boolean getDruable() {
      return druable;
    }

    public void setDruable(Boolean druable) {
      this.druable = druable;
    }

    public String getRoutingKey() {
      return routingKey;
    }

    public void setRoutingKey(String routingKey) {
      this.routingKey = routingKey;
    }
  }

  public class Exchange {

    /**
     * 交换机名称
     */
    private String  name;
    /**
     * 是否持久化
     */
    private boolean druable;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public boolean isDruable() {
      return druable;
    }

    public void setDruable(boolean druable) {
      this.druable = druable;
    }
  }

}
