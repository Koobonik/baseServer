package com.base.base.DB;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Service
@Data
@Entity
public class LogHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int logNum; // 유저의 고유 번호이자 수

    @Column(columnDefinition = "DATE")
    private Date date;
    
    @Column(columnDefinition = "TEXT")
    private String access;
}
