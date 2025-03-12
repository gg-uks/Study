package com.example.controller;

import com.example.repository.BookDAOMyBatis;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class registerBooksByFileController implements Controller{
    @Override
    public String requestHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //엑셀파일을 받아서 처리하는 부분..
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory(); // 파일 저장 처리 환경 설정 (메모리에 저장할지 디스크에 저장할지 결정)
            ServletFileUpload upload = new ServletFileUpload(factory);// 업로드 처리기 생성 (HTTP 요청에서 업로드된 파일 데이터를 파싱하고 처리)
            List<FileItem> items = upload.parseRequest(req); // 업로드된 파일 데이터를 파싱하여 FileItem 리스트로 변환 (멀티파트 요청에서 파일 추출) : parseRequest()를 사용하면 multipart/form-data 형식의 HTTP 요청을 파싱해서 FileItem 객체로 변환해 줘
            FileItem item = items.get(0);
            System.out.println(item.getName());

            Workbook wb = WorkbookFactory.create(item.getInputStream()); //엑셀파일을 읽어서 메모리에 만든다(엑셀은 워크북이 필요하다)
            System.out.println(wb.getSheetAt(0).getSheetName());

            Sheet sheet = wb.getSheetAt(0);
            System.out.println(sheet.getPhysicalNumberOfRows());//가로행의 마지막순서? => 총개수  //4가 나옴 (테스트용)
            System.out.println(sheet.getLastRowNum());//가로행의 마지막순서?  인덱스로 나옴 //3가 나옴 (테스트용)
            System.out.println(sheet.getFirstRowNum());//가로행의 첫번째 순서?  인덱스로 나옴 //0가 나옴 (테스트용)
            //추가
            BookDAOMyBatis dao = new BookDAOMyBatis();
            dao.excelInsert(wb); //저장성공


        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/list.do";
    }
}
