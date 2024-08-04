package com.care.GroomHimBack.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
@Getter
@RedisHash(value = "RefreshToken", timeToLive = 86400) // 86400초 = 24시간
public class RefreshToken {

  @Id
  private String  refreshToken;
  private String  username;

  public RefreshToken(String refreshToken, String username) {
    this.refreshToken = refreshToken;
    this.username = username;
  }
}