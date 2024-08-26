package com.arrayofsky.arrayofskymybatissimple.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description 事务接口
 */
public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

}
