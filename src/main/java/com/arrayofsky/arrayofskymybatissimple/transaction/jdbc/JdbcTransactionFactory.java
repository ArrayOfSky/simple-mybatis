package com.arrayofsky.arrayofskymybatissimple.transaction.jdbc;


import com.arrayofsky.arrayofskymybatissimple.session.TransactionIsolationLevel;
import com.arrayofsky.arrayofskymybatissimple.transaction.Transaction;
import com.arrayofsky.arrayofskymybatissimple.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @description JdbcTransaction 工厂
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }

}
