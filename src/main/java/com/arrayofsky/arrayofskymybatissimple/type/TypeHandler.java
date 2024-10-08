package com.arrayofsky.arrayofskymybatissimple.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description 类型处理器
 */
public interface TypeHandler<T> {

    /**
     * 设置参数
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

}
