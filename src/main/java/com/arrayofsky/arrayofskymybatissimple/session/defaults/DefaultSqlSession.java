package com.arrayofsky.arrayofskymybatissimple.session.defaults;

import com.arrayofsky.arrayofskymybatissimple.executor.Executor;
import com.arrayofsky.arrayofskymybatissimple.mapping.BoundSql;
import com.arrayofsky.arrayofskymybatissimple.mapping.Environment;
import com.arrayofsky.arrayofskymybatissimple.mapping.MappedStatement;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  默认SqlSession实现类
 *  这里暂时还是没有走数据库 还是简单的加上代理语句
 */
public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }
    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了！" + statement);
    }


    @Override
    public <T> T selectOne(String statement, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        List<T> list = executor.query(ms, parameter, Executor.NO_RESULT_HANDLER, ms.getBoundSql());
        return list.get(0);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
