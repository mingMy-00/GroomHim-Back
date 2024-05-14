package com.care.GroomHimBack.service;


import com.care.GroomHimBack.dto.JoinDTO;
import com.care.GroomHimBack.entity.UserEntity;
import com.care.GroomHimBack.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //초기화 시켜주기
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository        = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public void joinProcess(JoinDTO joinDto) {
        //1. 값을 빼준다.
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        //2. 이미 존재하는지 확인하기
        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
