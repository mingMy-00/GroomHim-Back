package com.care.GroomHimBack.jwt;

import com.care.GroomHimBack.entity.AccessToken;
import com.care.GroomHimBack.entity.RefreshEntity;
import com.care.GroomHimBack.entity.RefreshToken;
import com.care.GroomHimBack.repository.AccessTokenRepository;
import com.care.GroomHimBack.repository.RefreshRepository;
import com.care.GroomHimBack.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil , RefreshTokenRepository refreshTokenRepository , AccessTokenRepository accessTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //유저 정보 (유저 이름, 유저 권한)
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access  = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Redis에 저장
        RefreshToken refreshRedis = new RefreshToken(refresh, username);
        AccessToken  accessRedis  = new AccessToken(access, username);
        refreshTokenRepository.save(refreshRedis);
        accessTokenRepository.save(accessRedis);

        //refreshToken 저장
        //addRefreshEntity(username , refresh, 86400000L);

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

//    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
//
//        Date date = new Date(System.currentTimeMillis() + expiredMs);
//
//        RefreshEntity refreshEntity = new RefreshEntity();
//        refreshEntity.setUsername(username);
//        refreshEntity.setRefresh(refresh);
//        refreshEntity.setExpiration(date.toString());
//
//        refreshRepository.save(refreshEntity);
//}

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
