package com.pang.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author pangdexin
 * @Date 2023/7/18 10:35
 * @Description: MQ消息类型枚举类
 */
@Getter
@AllArgsConstructor
public enum  SpringAmqpMessageTypeEnum {
  ORDEROPERATION("下单操作"),
  PAYMENT("支付");
  private String desvription;
}
