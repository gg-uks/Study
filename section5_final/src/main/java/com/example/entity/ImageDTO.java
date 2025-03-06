package com.example.entity;

import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private int book_num;
    private int type;
    private String path; // 파일이름
}
