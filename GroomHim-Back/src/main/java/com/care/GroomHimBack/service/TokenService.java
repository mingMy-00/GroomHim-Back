package com.care.GroomHimBack.service;

import com.care.GroomHimBack.entity.AccessToken;
import com.care.GroomHimBack.entity.RefreshToken;
import com.care.GroomHimBack.repository.AccessTokenRepository;
import com.care.GroomHimBack.repository.RefreshTokenRepository;
import jakarta.persistence.Access;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private final AccessTokenRepository accessTokenRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  public TokenService(AccessTokenRepository accessTokenRepository, RefreshTokenRepository refreshTokenRepository) {
    this.accessTokenRepository = accessTokenRepository;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public void rotateRefreshToken(String oldToken, String newToken, String username) {
    refreshTokenRepository.deleteById(oldToken);
    // 새로운 RefreshToken 저장
    RefreshToken refreshRedis = new RefreshToken(newToken, username);
    refreshTokenRepository.save(refreshRedis);
  }

  public Boolean findRefreshToken(String token) {
    // Redis에서 RefreshToken이 존재하는지 확인
    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(token);
    return refreshTokenOptional.isPresent();
  }

  public void deleteRefreshToken(String token) {
    refreshTokenRepository.deleteById(token);
  }

  public void deleteAccessToken(String token) {
    accessTokenRepository.deleteById(token);
  }

}
