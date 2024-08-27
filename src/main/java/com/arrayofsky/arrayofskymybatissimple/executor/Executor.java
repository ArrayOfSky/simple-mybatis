package com.arrayofsky.arrayofskymybatissimple.executor;

import com.arrayofsky.arrayofskymybatissimple.mapping.BoundSql;
import com.arrayofsky.arrayofskymybatissimple.mapping.MappedStatement;
import com.arrayofsky.arrayofskymybatissimple.session.ResultHandler;
import com.arrayofsky.arrayofskymybatissimple.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @description 执行器
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);

}