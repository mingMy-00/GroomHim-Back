package com.care.GroomHimBack.controller;

import com.care.GroomHimBack.dto.JoinDTO;
import com.care.GroomHimBack.dto.UserDTO;
import com.care.GroomHimBack.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody //api 서버이기 때문에 응답 responseBody로
public class UserController {

    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }

    
    /*
    * 회원가입 => 약관동의 insert
    * */
    @PostMapping("api/enroll")
    public void enrollProcess(/*약관동의 테이블DTO 받기*/) {
        //동의하고 회원가입하기 버튼 클릭
        //약관동의 테이블 DB에 insert하는 method
        //약관동의 테이블에는 회원번호, 약관 4개 
    }

    /*
    * 회원가입 => 아이디 중복확인
    * */
    @PostMapping("api/checkId")
    public void checkId(@RequestBody String id) {
        //매개변수로 id값 받아서 중복확인 DB갔다옴. => 있는지만 확인

        //id값 중복이면 "이미있는 아이디 " return.
        //값 중복 아니면 "사용 가능한 아이디"
    }

    @GetMapping("api/InsertID")
    public void InsertID(@RequestParam String checkId) {
        //id 중복확인 후 다음 클릭한 method
        //checkId DB에 insert
    }

    @PostMapping("api/InsertPassword")
    public void InsertPassword(@RequestBody String password) {
        //비번 입력 후 다음 클릭
        //password db insert
    }

    @PostMapping("api/InsertPhoneNum")
    public void InsertPhoneNum(@RequestBody String phoneNum) {
        //핸폰 번호 입력 후 다음 클릭
        //핸폰 번호 DB Insert
    }

    @GetMapping("api/checkNickName")
    public void checkNickName(@RequestParam String nickName) {
        //닉네임 중복 확인하기


    }

    @PostMapping("api/insertNickEmail")
    public void InsertNickEmail(@RequestParam String nickName, @RequestParam String Email) {
        //닉네임, 이메일 insert
    }

    @PostMapping("api/insertGenderBirth")
    public void insertGenderBirth(@RequestParam String gender, @RequestParam String Birth) {
        //성별, 생년월일
    }

    /*
    * 로그인
    * */
    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "ok";
    }

}
