package com.arrayofsky.arrayofskymybatissimple.mapper;

import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 先用简单工厂模式实现
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @SuppressWarnings("unchecked")
    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

}
