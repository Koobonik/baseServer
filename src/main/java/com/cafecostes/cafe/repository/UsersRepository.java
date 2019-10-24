package com.cafecostes.cafe.repository;

import com.cafecostes.cafe.DB.Users;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UsersRepository extends JpaRepository<Users, Integer> {
//    @Query("SELECT p " +
//            "FROM Users p " +
//            "ORDER BY p.usernum DESC")
//    Stream<Users> findAllDesc();
    Users findByUserNameAndUserEmail(String name, String email);
    Users findByUserLoginIdAndUserEmail(String id, String email);
    Users findByUserLoginId(String id);
    Users findByUserName(String name);
    Users findByUserEmail(String email);
    Users findByUserNum(int user_num);
    Users findByUserLoginIdAndUserLoginPassword(String id, String pwd);


//    @Query("SELECT Users.userLoginId " +
//            "FROM Users p " +
//            "WHERE p.userEmail = name AND p.userName = name")
//    Users findOne(@Param("name") String name, @Param("name") String email);
}