package com.arrayofsky.arrayofskymybatissimple.builder;


import com.arrayofsky.arrayofskymybatissimple.mapping.BoundSql;
import com.arrayofsky.arrayofskymybatissimple.mapping.ParameterMapping;
import com.arrayofsky.arrayofskymybatissimple.mapping.SqlSource;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;

import java.util.List;

/**
 * @description 静态SQL源码
 */
public class StaticSqlSource implements SqlSource {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }

}
