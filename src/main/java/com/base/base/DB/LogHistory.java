package com.base.base.DB;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Service
@Data
@Entity
public class LogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int logNum; // 유저의 고유 번호이자 수

    @Column(columnDefinition = "TEXT")
    private String  date;
    
    @Column(columnDefinition = "TEXT")
    private String access;

    @Builder
    public LogHistory(String  date, String access){
        this.date = date;
        this.access = access;
    }
}
