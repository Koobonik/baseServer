package com.cafecostes.cafe.DB;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;

//@AllArgsConstructor
@Getter
@Setter
@Service
@Data
@Entity // This tells Hibernate to make a table out of this class
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int userNum; // 유저의 고유 번호이자 수

    // null 값은 회원가입 안되게

    @Column(columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String userLoginId; // 유저 로그인 시 아이디
    @Column(columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String userEmail; // 유저 이메일
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String userLoginPassword; // 유저 로그인 시 패스워드
    @Column(columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String userName; // 유저 이름
    @Column(columnDefinition = "VARCHAR(255)")
    private String userPhoneNumber; // 유저 폰번
    @Column(columnDefinition = "TEXT")
    private String gender; // 유저 성별
    @Column(columnDefinition = "TEXT")
    private int age; // 유저 나이

    @Column(columnDefinition = "VARCHAR(255)")
    private String birthday;

    private int coupon; // 쿠폰 생성
    @Column(columnDefinition = "TEXT", nullable = false)
    private String datetime;

    @Column(columnDefinition = "TEXT")
    private String firebaseToken = " ";

    @Column(columnDefinition = "INT")
    private int userPermission = 0;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    @Builder
    public Users(String userLoginId, String userLoginPassword, String userName, String userEmail, String userPhoneNumber, int coupon, String gender, int age, String datetime){
        //this.userNum = userNum;
        this.userLoginId = userLoginId;
        this.userLoginPassword = userLoginPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.coupon = coupon;
        this.gender = gender;
        this.age = age;
        this.datetime = datetime;
    }

    // flutter
    @Builder
    public Users(String userLoginId, String userLoginPassword, String userName, int coupon, String datetime, String userEmail, String birthday){
        //this.userNum = userNum;
        this.userLoginId = userLoginId;
        this.userLoginPassword = userLoginPassword;
        this.userName = userName;
        this.coupon = coupon;
        this.datetime = datetime;
        this.userEmail = userEmail;
        this.birthday = birthday;
    }
    public Users(){}

//    public Long getUserNum() {
//        return userNum;
//    }
//
//    public String getUserLoginId() {
//        return userLoginId;
//    }
//
//    public String getUserLoginPassword() {
//        return userLoginPassword;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public String getUserEmail() {
//        return userEmail;
//    }
//
//    public String getUserPhoneNumber() {
//        return userPhoneNumber;
//    }
//
//    public String getCreatedDate(){
//        return  getCreatedDate();
//    }
//
//    public void setUserLoginId(String userLoginId) {
//        this.userLoginId = userLoginId;
//    }
//
//    public void setUserLoginPassword(String userLoginPassword) {
//        this.userLoginPassword = userLoginPassword;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void setUserEmail(String userEmail) {
//        this.userEmail = userEmail;
//    }
//
//    public void setUserPhoneNumber(String userPhoneNumber) {
//        this.userPhoneNumber = userPhoneNumber;
//    }


}