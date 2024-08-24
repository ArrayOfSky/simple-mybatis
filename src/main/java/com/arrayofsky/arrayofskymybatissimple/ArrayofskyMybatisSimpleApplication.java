package com.arrayofsky.arrayofskymybatissimple;

import com.arrayofsky.arrayofskymybatissimple.mapper.MapperProxyFactory;
import com.arrayofsky.arrayofskymybatissimple.mapper.MapperRegistry;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSessionFactory;
import com.arrayofsky.arrayofskymybatissimple.session.defaults.DefaultSqlSessionFactory;
import com.arrayofsky.arrayofskymybatissimple.test.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ArrayofskyMybatisSimpleApplication {


    private static Logger logger = LoggerFactory.getLogger(ArrayofskyMybatisSimpleApplication.class);

    public static void main(String[] args) {

        // 1. 注册 Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("com.arrayofsky.arrayofskymybatissimple.test");

        // 2. 从 SqlSession 工厂获取 Session
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3. 获取映射器对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 4. 测试验证
        String res = userDao.queryUserName("10001");
        logger.info("测试结果：{}", res);

    }



}
