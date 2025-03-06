package com.example.frontcontroller;

import com.example.controller.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class HandlerMapping {
    // Key-Value
    private HashMap<String, Controller> mappings;

    public HandlerMapping(){
         mappings=new HashMap<>();
         try{
             loadMappingFromExternalSource();
         }catch (Exception e){
             e.printStackTrace();
         }
    }

    //프로퍼티 파일을 읽어서 하는 방식
    /*private void loadMappingFromExternalSource() throws Exception{
        InputStream inputStream=getClass().getClassLoader().getResourceAsStream("mappingConfig.properties"); //핸들러 매핑 파일
        if(inputStream==null){
             throw new IOException("mappingConfig.properties 파일이 없습니다.");
        }
        Properties properties=new Properties(); //프로퍼티 파일을 읽는 클래스 (프로퍼티스 파일을 읽을때)
        properties.load(inputStream); //로드 읽어온다 프로퍼티 파일을 읽어옴   (프로퍼티스 파일을 읽을때)

        for(String key : properties.stringPropertyNames()){ //프로퍼티 파일에 있는 핸들러된 이름을 읽어 key로뺀다
              String className=properties.getProperty(key); //key로 뺀 값들을 className에 저장  -> /list.do=com.example.controller.BookListController
              Class<?> clazz=Class.forName(className); //문자열로된 이름을 가지고 객체를 생성해준다 : 리플렉션 기법
              Controller controller= (Controller) clazz.getDeclaredConstructor().newInstance();  // new com.example.controller.BookListController()  부모것으로 받아야 함
              mappings.put(key, controller);
        }
        inputStream.close();
    }*/

    //xml파일을 읽어서 매핑하는 방식
    private void loadMappingFromExternalSource() throws Exception {
        // XML 파일을 로드합니다.
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mappingConfig.xml");
        if (inputStream == null) {
            throw new IOException("mappingConfig.xml 파일이 없습니다.");
        }

        // XML 파일을 파싱할 DocumentBuilder 객체 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);

        // XML 문서 정규화 (선택 사항이지만 좋습니다)
        document.getDocumentElement().normalize();

        // <command> 태그들 가져오기
        NodeList nodeList = document.getElementsByTagName("command");

        // 각 <command> 태그를 순차적으로 처리
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);         //Node는 XML 문서에서 사용할 수 있는 가장 일반적인 인터페이스입니다. 모든 XML 요소(태그)는 Node 타입을 상속합니다.
            if (node.getNodeType() == Node.ELEMENT_NODE) {  //*Node.ELEMENT_NODE**는 요소 노드를 나타내며, 여기서는 <command> 태그가 요소 노드로 처리될 수 있음을 의미합니다.
                                                            //즉, <command> 태그만 처리하기 위해 ELEMENT_NODE인지 확인하는 조건입니다.
                Element element = (Element) node;    //node를 Element로 형변환합니다. 왜냐하면 Node는 매우 일반적인 클래스이고, 실제로 XML의 요소는 Element로 다뤄야 하기 때문입니다. Element는 XML에서 태그 요소를 다룰 수 있는 구체적인 인터페이스입니다. Element 인터페이스를 사용하면 태그의 속성을 읽고 수정할 수 있습니다.
                                                        //이렇게 node를 Element로 변환하면 XML 요소의 속성을 쉽게 추출할 수 있습니다.

                // key와 value 속성 값을 추출
                String url = element.getAttribute("key");
                String className = element.getAttribute("value");

                // Reflection을 사용하여 컨트롤러 클래스 인스턴스 생성
                Class<?> clazz = Class.forName(className); //Class.forName(className)는 리플렉션을 이용하여 문자열로 된 클래스 이름을 실제 Class 객체로 변환합니다.
                //className은 "com.example.controller.BookListController"와 같은 문자열이므로, 이 문자열을 이용해 동적으로 해당 클래스의 정보를 가져옵니다.

                Controller controller = (Controller) clazz.getDeclaredConstructor().newInstance();

                // URL과 Controller 객체를 매핑에 추가
                mappings.put(url, controller);
            }
        }

        // 입력 스트림을 닫습니다.
        inputStream.close();
    }




    public Controller getController(String key){ // /list.do  -> commend값을 매개변수로 받아 호출
        return mappings.get(key);  // new com.example.controller.BookListController()
    }
}
