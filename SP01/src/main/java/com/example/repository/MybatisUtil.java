package com.example.repository;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    //DB접속 프로그램 실행시 한번만 처리
    static {
        try {
            String resource = "mybatis-config/config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(inputStream);//Connection(Sqlsession) : 10개,5개 20초 ,20초동안 기다림 10개의 커넥션 유효커넥션5개 체크아웃20초 연결대기 20초
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static SqlSession openSession(){ //MybatisUtil.openSession()
        return sqlSessionFactory.openSession();
    }
}
