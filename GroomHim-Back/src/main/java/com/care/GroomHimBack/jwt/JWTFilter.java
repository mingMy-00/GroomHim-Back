package com.care.GroomHimBack.jwt;

import com.care.GroomHimBack.dto.CustomUserDetails;
import com.care.GroomHimBack.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization header를 찾음
        String authorization = request.getHeader("Authorization");

        //token이 null인지, 인증방식이 잘못됐는지 header 검증
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드를 종료 (필수)
            return;
        }
        System.out.println("authorization now");

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸시간 검증
        if(jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request,response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        /*현재 (token  !=  null) || (isExpired != true)*/

        //token에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role     = jwtUtil.getRole(token);

        //userEntity에 생성하여 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //Spring Security 인증 token 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //session 생성
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //method종료로 그 다음 chain으로 넘겨줌.
        filterChain.doFilter(request, response);




    }

}
