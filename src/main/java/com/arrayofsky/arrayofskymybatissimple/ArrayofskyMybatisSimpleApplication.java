package com.arrayofsky.arrayofskymybatissimple;

import com.arrayofsky.arrayofskymybatissimple.mapper.MapperProxyFactory;
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

        MapperProxyFactory<UserDao> factory = new MapperProxyFactory<>(UserDao.class);


        //初始化sqlsession，具体接口方法和sql语句的映射关系
        Map<String, String> sqlSession = new HashMap<>();
        sqlSession.put("com.arrayofsky.arrayofskymybatissimple.test.UserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("com.arrayofsky.arrayofskymybatissimple.test.UserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
        UserDao userDao = factory.newInstance(sqlSession);

        //执行测试
        String res = userDao.queryUserName("10001");
        logger.info("测试结果：{}", res);

        //测试对于Object方法的放行
        userDao.hashCode();

    }

}
