package com.care.GroomHimBack.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "AccessToken", timeToLive = 600) // 600초 = 10분
public class AccessToken {
  @Id
  private String  accessToken;
  private String  username;

  public AccessToken(String accessToken, String username) {
    this.accessToken = accessToken;
    this.username = username;
  }

}
