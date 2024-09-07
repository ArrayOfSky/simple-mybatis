package com.arrayofsky.arrayofskymybatissimple.mapping;

import com.arrayofsky.arrayofskymybatissimple.reflection.MetaObject;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 绑定的SQL,是从SqlSource而来，将动态内容都处理完成得到的SQL语句字符串，其中包括?,还有绑定的参数
 */
public class BoundSql {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private Map<String, Object> additionalParameters;
    private MetaObject metaParameters;


    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }


}
