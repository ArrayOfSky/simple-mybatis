package com.arrayofsky.arrayofskymybatissimple.mapper;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 每个MapperProxy对应一个dao
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -6424540398559729838L;

    //这里存放dao的每一个接口的对应关系，应该对应到sql，这里先简单实现
    private Map<String, String> sqlSession;

    //被代理类
    private final Class<T> mapperInterface;

    public MapperProxy(Map<String, String> sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            //这样可以避开Object的tostring equals等方法，避免额外的代理
            return method.invoke(this, args);
        } else {
            //假设数据库语句操作，都是通过接口名称+方法名称作为key
            return "你的被代理了！" + sqlSession.get(mapperInterface.getName() + "." + method.getName());
        }
    }


}
