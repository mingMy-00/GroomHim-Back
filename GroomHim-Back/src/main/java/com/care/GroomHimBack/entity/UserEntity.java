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
@Table(name = "MBR")
public class UserEntity

        // 회원 번호
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "mbr_no", nullable = false, columnDefinition = "VARCHAR(30) COMMENT '회원 번호'")
        private int mbrNo;

        // 아이디
        @Column(name = "username", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '아이디'")
        private String username;

        // 비밀번호
        @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100) COMMENT '비밀번호'")
        private String password;

        // 핸드폰 번호
        @Column(name = "phone_number", nullable = false, columnDefinition = "VARCHAR(15) COMMENT '핸드폰 번호'")
        private String phoneNumber;

        // 닉네임
        @Column(name = "nickname", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '닉네임'")
        private String nickname;

        // 이메일
        @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(100) COMMENT '이메일'")
        private String email;

        // 성별
        @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(3) COMMENT '성별'")
        private String gender;

        // 생년월일 (본인입력 YYYY/MM/DD)
        @Column(name = "birthdate", nullable = false, columnDefinition = "DATE COMMENT '생년월일 (본인입력 YYYY/MM/DD)'")
        @Temporal(TemporalType.DATE)
        private Date birthdate;

        // 이름
        @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(15) COMMENT '이름'")
        private String name;

        // 회원 역할
        @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '회원 역할'")
        private String role;

        // Getters and Setters
        public int getMbrNo() {
            return mbrNo;
        }

        public void setMbrNo(int mbrNo) {
            this.mbrNo = mbrNo;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Date getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(Date birthdate) {
            this.birthdate = birthdate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
}
