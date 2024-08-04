package com.care.GroomHimBack.jwt;

import com.care.GroomHimBack.repository.RefreshRepository;
import com.care.GroomHimBack.repository.RefreshTokenRepository;
import com.care.GroomHimBack.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.GenericFilterBean;

public class CustomLogoutFilter extends GenericFilterBean {


  private final JWTUtil jwtUtil;
  private final TokenService tokenService;

  public CustomLogoutFilter(JWTUtil jwtUtil, TokenService tokenService) {

    this.jwtUtil = jwtUtil;
    this.tokenService = tokenService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
  }

  private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    //path and method verify
    //로그아웃 경로인지 확인해보기
    String requestUri = request.getRequestURI();
    if (!requestUri.matches("^\\/logout$")) {

      //로그아웃이 아니면 다음 필터로
      filterChain.doFilter(request, response);
      return;
    }

    //로그아웃이더라도 post 요청이 아니면 다음 필터로
    String requestMethod = request.getMethod();
    if (!requestMethod.equals("POST")) {

      filterChain.doFilter(request, response);
      return;
    }

    //get refresh token, refreshToken이 있는지 확인해보기
    String refresh = null;

    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {

      if (cookie.getName().equals("refresh")) {

        refresh = cookie.getValue();
      }
    }

    //refresh null check
    //Refresh Token이 없으면 400응답
    if (refresh == null) {

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    //expired check
    try {
      jwtUtil.isExpired(refresh);
    } catch (ExpiredJwtException e) {

      //response status code
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
    String category = jwtUtil.getCategory(refresh);
    if (!category.equals("refresh")) {

      //response status code
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    //DB에 저장되어 있는지 확인
    Boolean isExist = tokenService.findRefreshToken(refresh);
    if (!isExist) {

      //response status code
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    //로그아웃 진행
    //Refresh 토큰 Redis에서 제거
    tokenService.deleteRefreshToken(refresh);

    //Refresh 토큰 Cookie 값 0
    Cookie cookie = new Cookie("refresh", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");

    response.addCookie(cookie);
    response.setStatus(HttpServletResponse.SC_OK);
  }

  }
