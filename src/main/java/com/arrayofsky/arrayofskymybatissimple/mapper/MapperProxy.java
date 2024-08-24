package com.arrayofsky.arrayofskymybatissimple.mapper;

import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 每个MapperProxy对应一个dao
 * 先默认实现基础的代理模式
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return sqlSession.selectOne(method.getName(), args);
        }
    }


}
