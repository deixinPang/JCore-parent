package com.pang.domain.request;

import java.io.Serializable;
import lombok.Data;

/**
 * @Author pangdexin
 * @Date 2023/7/14 13:26
 * @Description: mq消息基类
 */
@Data
public class SpringRabbitRequest implements Serializable {

  //todo 重发次数
  private Integer retryCount;

  //todo 重发上限
  private Integer retryLimit;

  //todo 延迟时间(毫秒)
  private long delayTimes;
}
