package com.arrayofsky.arrayofskymybatissimple;

import com.arrayofsky.arrayofskymybatissimple.io.Resources;
import com.arrayofsky.arrayofskymybatissimple.mapper.MapperRegistry;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSessionFactory;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSessionFactoryBuilder;
import com.arrayofsky.arrayofskymybatissimple.session.defaults.DefaultSqlSessionFactory;
import com.arrayofsky.arrayofskymybatissimple.test.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.Reader;

@SpringBootApplication
public class ArrayofskyMybatisSimpleApplication {


    private static Logger logger = LoggerFactory.getLogger(ArrayofskyMybatisSimpleApplication.class);

    public static void main(String[] args) throws IOException {

        // 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 3. 测试验证
        String res = userDao.queryUserInfoById("10001");
        logger.info("测试结果：{}", res);

    }



}
