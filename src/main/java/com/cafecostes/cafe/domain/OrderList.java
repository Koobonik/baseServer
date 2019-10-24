package com.cafecostes.cafe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // 주문 고유 식별 아이디

    @Column(columnDefinition = "TEXT", nullable = false)
    private String orderMesage; // 주문하면서 담길 메시지

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private boolean state = false;

    public OrderList(String orderMesage, boolean state) {
        this.orderMesage = orderMesage;
        this.state = state;
    }

}
