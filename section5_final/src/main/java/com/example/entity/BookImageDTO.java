package com.example.entity;

import lombok.Data;

@Data
public class BookImageDTO {
    // 책의 정보
    private Long num;
    private String title;
    private int price;
    private String author;
    private int page;
    // 이미지 정보
    private Long id;
    private int book_num;
    private int type;
    private String path; // 파일이름
}
