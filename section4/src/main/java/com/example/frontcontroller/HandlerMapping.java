package com.example.frontcontroller;

import com.example.controller.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class HandlerMapping {
    // key-Value
    private HashMap<String, Controller> mappings;

    public HandlerMapping(){
        mappings = new HashMap<>();
        try{
            loadMappingFromExternalSource();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadMappingFromExternalSource() throws Exception{
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mappingConfig.properties");//클래스패스(classpath)에서 mappingConfig.properties 파일을 읽어옴
        if(inputStream==null){
            throw new IOException("mappingConfig.properties 파일이 없습니다");
        }
        Properties properties = new Properties();
        properties.load(inputStream);

        for(String key:properties.stringPropertyNames()){  //properties.stringPropertyNames()로 **모든 키(URL 패턴)**를 가져옴.
            String className = properties.getProperty(key); //Class.forName(className)으로 문자열로 된 클래스 이름을 클래스로 변환하고,
            Class<?> clazz = Class.forName(className);
            Controller controller= (Controller) clazz.getDeclaredConstructor().newInstance(); //clazz.getDeclaredConstructor().newInstance()로 해당 클래스의 객체를 동적으로 생성함.
            mappings.put(key,controller);//key(요청 URL) - value(Controller 객체) 형태로 저장
        }
        inputStream.close();
    }
    public Controller getController(String key){
        return mappings.get(key);
    }
}
