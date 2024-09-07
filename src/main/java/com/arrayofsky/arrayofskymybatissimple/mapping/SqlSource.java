package com.arrayofsky.arrayofskymybatissimple.mapping;

/**
 * @description SQL源码
 */
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);

}
