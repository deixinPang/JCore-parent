package com.pang.domain.request;

import lombok.Data;

/**
 * @Author pangdexin
 * @Date 2023/7/14 11:59
 * @Description: TODO
 */
@Data
public class RabbitMessageRequest<T> extends SpringRabbitRequest{

  private String type;

  private T data;
}
