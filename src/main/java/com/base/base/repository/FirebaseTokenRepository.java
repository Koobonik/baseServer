package com.base.base.repository;

import com.base.base.DB.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Integer> {
//    @Query("SELECT p " +
//            "FROM Users p " +
//            "ORDER BY p.usernum DESC")
//    Stream<Users> findAllDesc();

    FirebaseToken findByFirebaseToken(String token);

//    @Query("SELECT Users.userLoginId " +
//            "FROM Users p " +
//            "WHERE p.userEmail = name AND p.userName = name")
//    Users findOne(@Param("name") String name, @Param("name") String email);
}