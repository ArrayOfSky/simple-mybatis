package com.arrayofsky.arrayofskymybatissimple.session.defaults;

import com.arrayofsky.arrayofskymybatissimple.executor.Executor;
import com.arrayofsky.arrayofskymybatissimple.mapper.MapperRegistry;
import com.arrayofsky.arrayofskymybatissimple.mapping.Environment;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSession;
import com.arrayofsky.arrayofskymybatissimple.session.SqlSessionFactory;
import com.arrayofsky.arrayofskymybatissimple.session.TransactionIsolationLevel;
import com.arrayofsky.arrayofskymybatissimple.transaction.Transaction;
import com.arrayofsky.arrayofskymybatissimple.transaction.TransactionFactory;

import java.sql.SQLException;

/**
 *  默认的 DefaultSqlSessionFactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            // 创建执行器
            final Executor executor = configuration.newExecutor(tx);
            // 创建DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }

}
