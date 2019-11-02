package com.base.base.DB;

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
public class FirebaseToken {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int userNum; // 유저의 고유 번호이자 수


    @Column(columnDefinition = "TEXT", unique = true)
    private String firebaseToken = " ";

    // base
    @Builder
    public FirebaseToken(String firebaseToken){
        this.firebaseToken = firebaseToken;
    }

    public FirebaseToken(){

    }
}