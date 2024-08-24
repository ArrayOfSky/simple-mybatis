package com.arrayofsky.arrayofskymybatissimple.session.defaults;

import com.arrayofsky.arrayofskymybatissimple.mapper.MapperRegistry;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSessionFactory;

/**
 *  默认的 DefaultSqlSessionFactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }

}
