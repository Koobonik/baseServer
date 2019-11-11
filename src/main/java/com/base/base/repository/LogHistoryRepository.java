package com.base.base.repository;

import com.base.base.DB.FirebaseToken;
import com.base.base.DB.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface LogHistoryRepository extends JpaRepository<LogHistory, Integer> {
//    @Query("SELECT p " +
//            "FROM Users p " +
//            "ORDER BY p.usernum DESC")
//    Stream<Users> findAllDesc();

    LogHistory findByLogNum(int num);

//    @Query("SELECT Users.userLoginId " +
//            "FROM Users p " +
//            "WHERE p.userEmail = name AND p.userName = name")
//    Users findOne(@Param("name") String name, @Param("name") String email);
}