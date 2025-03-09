package com.example.entity;

import lombok.Data;

@Data
public class CustomerDTO {
    private String customer_id; //아이디
    private String password;    //비밀번호
    private String customer_name; //이름
    private int age;    //나이
    private String rating; //고객등급
    private String occupation;//직업
    private int reserves;  //누적금액

}
