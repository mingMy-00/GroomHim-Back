package com.care.GroomHimBack.controller;

import com.care.GroomHimBack.dto.JoinDTO;
import com.care.GroomHimBack.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody //api 서버이기 때문에 응답 responseBody로
public class JoinController {

  private final JoinService joinService;

  public JoinController(JoinService joinService) {
    this.joinService = joinService;
  }

  @PostMapping("/user/join")
  public String joinProcess(JoinDTO joinDTO) {
    joinService.joinProcess(joinDTO);
    return "ok";
  }
}
