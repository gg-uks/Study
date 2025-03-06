package com.example.controller;

import com.example.entity.ImageDTO;
import com.example.repository.BookDAOMyBatis;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UploadController implements  Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 예외처리
        try{
            DiskFileItemFactory factory=new DiskFileItemFactory();
            ServletFileUpload upload=new ServletFileUpload(factory);
            List<FileItem> items=upload.parseRequest(req); // FormData(files(1),files(2)..., book_num, type)
            int book_num=0;
            int type=0;

//      upload.parseRequest(req) → HttpServletRequest에서 전달된 데이터를 FileItem 객체의 리스트로 변환해 준다.
//      이 items 리스트에는 파일 데이터뿐만 아니라 일반 폼 데이터(book_num, type 같은 값) 도 포함되어 있음.
//      따라서 items는 파일과 일반 데이터가 섞여 있는 리스트라고 보면 돼.
            //이부분은 폼필드와 파일필드 를 구분하기 위해 만든것이다
            for(FileItem item  : items){//items 리스트에 들어있는 FileItem 객체를 하나씩 item 변수에 담아서 반복 실행.
                System.out.println(item.toString());  // 디버깅용
                if(item.isFormField()){
                    if(item.getFieldName().equals("book_num")){
                        book_num=Integer.parseInt(item.getString());
                    }else if(item.getFieldName().equals("type")){
                        type=Integer.parseInt(item.getString());
                    }
                }
            }
            // 파일 업로드 하기 : D:\bootcamp2\section6\src\main\webapp
            String uploadPath=req.getServletContext().getRealPath("/uploads");
            //C:|Program Files|Apache Software Foundation|Tomcat 9.0|webapps|MyWebApp|uploads
            String uploadRealPath=uploadPath+ File.separator+book_num;
            File uploadDir=new File(uploadRealPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir(); // 디렉토리가 생성
            }
            BookDAOMyBatis dao=new BookDAOMyBatis();
            for(FileItem item : items){
                if(!item.isFormField()){ // 파일인 경우
                    String fileName= FilenameUtils.getName(item.getName());
//                    item.getName()은 업로드된 파일의 전체 경로를 반환해.
//                     C:\Users\ser\Desktop\image.jpg
//                    그런데, 우리가 필요한 건 파일 이름만이니까, FilenameUtils.getName()을 사용해서 파일의 경로를 제외한 파일명만 추출해.
                    String filePath=uploadDir+File.separator+fileName;
//                    File.separator는 운영 체제에 맞는 파일 경로 구분자야. 예를 들어, 윈도우에서는 \, 리눅스에서는 /로 파일 경로를 구분해.
                    File uploadFile=new File(filePath);
//                    filePath에서 지정한 경로로 File 객체를 생성해.
//                    이 File 객체는 실제로 파일을 다룰 수 있는 객체인데, 아직 파일이 생성되지는 않음
                    item.write(uploadFile); // 쓰기(업로드)

                    ImageDTO imageDTO=new ImageDTO();
                    imageDTO.setBook_num(book_num);
                    System.out.println("📌 Received book_num: " + book_num); //디버깅용
                    imageDTO.setType(type);
                    imageDTO.setPath(fileName);
                    dao.saveImage(imageDTO);  // 이미지 정보 저장
                }
            }
            // 업로드와 데이터베이스에 정보 저장 이후....(Servlet)
            resp.setContentType("application/json;charset=utf-8"); // JSON : {  key:value, key:value.....  }
            PrintWriter out=resp.getWriter();
            String jsonResponse = "{\"status\":\"success\", \"message\":\"File uploaded successfully.\"}";
            out.println(jsonResponse);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null; // fetch()----------->then()
    }
}