package com.care.GroomHimBack.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //회원 번호

    //아이디 (중복 검사 ->프론트)
    //비밀번호
    //핸드폰 번호 (인증번호)
    //닉네임
    //이메일 (본인인증 하나 ??)
    //성별
    //생년월일 (본인입력 YYYY/MM/DD)
    private String username;
    private String password;

    private String role;
    
    //아직 값 다 안정함
}
