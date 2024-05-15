package com.care.GroomHimBack.repository;

import com.care.GroomHimBack.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /*회원 가입 전에 이 user가 있는지 찾음*/
    boolean existsByUsername(String username);

    /*jwt에서 검증 로직을 할 때 이 user의 정볼르 가져옴*/
    UserEntity findByUsername(String username);
}