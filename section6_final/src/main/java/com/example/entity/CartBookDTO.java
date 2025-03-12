package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartBookDTO {//슈퍼DTO
    private int cart_number;
    private String customer_id;
    private int book_num;
    private int quantity;
    private LocalDateTime cart_date;
    //책정보
    private Long num;
    private String title;
    private int price;
    private String author;
    private int page;
}
