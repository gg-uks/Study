package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadController implements  Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // ?book_num,path
        String book_num=req.getParameter("book_num"); //쿼리스트링값으로 넘어온 값을 받아야함
        String filePath=req.getParameter("path");

        String uploadDirectory = req.getServletContext().getRealPath("/uploads");//업로드스 폴더를 가리킴
        String imagePath=uploadDirectory+ File.separator+book_num+File.separator+filePath;//book_num/filePath 파일경로를 가리킨다
        File file = new File(imagePath);
        if(file.exists()){ //만약 존재한다면
            resp.setContentType("image/jpeg"); //또는 파일 타입에 맞는 Mine 타입 지정 jpeg뿐만아니라 png도 내려감
            resp.setContentLength((int)file.length());//파일의 길이 정보를 알려줘야 함

            //파일을 읽어서 클라이언트에게 전송
            try(FileInputStream ins=new FileInputStream(file); //파일을 읽어옴
                OutputStream outputStream=resp.getOutputStream()){ //읽어온 파일을 내보냄
                byte[] buffer=new byte[4096]; //4096바이트 읽어와서
                int bytesRead=-1;
                while ( (bytesRead=ins.read(buffer))!=-1){ //인풋 스트림을 통하여 읽어옴 4096만큼
                    outputStream.write(buffer,0, bytesRead); //아웃풋 스트림을 이용해 버퍼에 있는 내용을 0byte부터 읽어드리는 바이트수만큼 write한다
                }
                //outputStream.close();
            }
        }else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        }
        return null;
    }
}