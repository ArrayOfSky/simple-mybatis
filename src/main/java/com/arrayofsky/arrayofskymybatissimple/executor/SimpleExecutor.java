package com.arrayofsky.arrayofskymybatissimple.executor;

import com.arrayofsky.arrayofskymybatissimple.executor.statement.StatementHandler;
import com.arrayofsky.arrayofskymybatissimple.mapping.BoundSql;
import com.arrayofsky.arrayofskymybatissimple.mapping.MappedStatement;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import com.arrayofsky.arrayofskymybatissimple.session.ResultHandler;
import com.arrayofsky.arrayofskymybatissimple.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @description 简单执行器
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        try {
            // 获取语句处理器，其中内聚了结果集处理器
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, resultHandler, boundSql);
           // 获取连接
            Connection connection = transaction.getConnection();
            // 获取预处理Statement
            Statement stmt = handler.prepare(connection);
            //注入参数
            handler.parameterize(stmt);
            return handler.query(stmt, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
