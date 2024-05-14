package com.care.GroomHimBack.repository;

import com.care.GroomHimBack.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {


}
