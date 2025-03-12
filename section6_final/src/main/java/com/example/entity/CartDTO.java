package com.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {//JSON으로 날라오는 데이터와 이름이 일치해야함
    private int cart_number;
    private String customer_id;
    private int book_num;
    private int quantity;
    private LocalDateTime cart_date;
}
