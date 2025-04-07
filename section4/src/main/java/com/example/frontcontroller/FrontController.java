package com.example.frontcontroller;

import com.example.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.do") //list.do , delete.do , update.do ~~
public class FrontController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Http://localhost:8081/MF01/list.do
        String reqPath = req.getRequestURI(); //요청 url을 받을때 사용 /MF01/list.do
        String cpath = req.getContextPath(); //  /MF01을 가지고옴
        String command = reqPath.substring(cpath.length()); //list.do 만 가지고옴 cpath.length()의 길이만큼 건너띄어서
        System.out.println("📢 요청된 command: " + command); // 디버깅용 로그  /list.do




        //HandlerMapping
        HandlerMapping mapping = new HandlerMapping();
        Controller controller = mapping.getController(command); //요청 url hashMap에 저장되어있는 것을 가져옴
        if(controller==null){
            return;
        }
        String nextView = controller.requestHandler(req,resp);
        //nextView에 따라서 포워드인지 리다이렉트인지 2가지로 갈린다
        navigate(req,resp,nextView);


    }
    private void  navigate(HttpServletRequest req,HttpServletResponse resp, String nextView)throws IOException,ServletException{
        if(nextView!=null){
            if(!nextView.contains("/")){
                //forward경로
                RequestDispatcher rd = req.getRequestDispatcher(ViewResolver.makeView(nextView));
                rd.forward(req,resp);
            }else{
                //redirect
                resp.sendRedirect(nextView.split(":/")[1]); //리다이렉트는 컨텍스트 패스를 브라우저에서 알아서 처리해줌

            }
        }
    }
}
