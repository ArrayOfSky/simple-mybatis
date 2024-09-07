package com.arrayofsky.arrayofskymybatissimple.session;

import com.arrayofsky.arrayofskymybatissimple.datasource.druid.DruidDataSourceFactory;
import com.arrayofsky.arrayofskymybatissimple.datasource.pooled.PooledDataSourceFactory;
import com.arrayofsky.arrayofskymybatissimple.datasource.unpooled.UnpooledDataSourceFactory;
import com.arrayofsky.arrayofskymybatissimple.executor.Executor;
import com.arrayofsky.arrayofskymybatissimple.executor.SimpleExecutor;
import com.arrayofsky.arrayofskymybatissimple.executor.resultset.DefaultResultSetHandler;
import com.arrayofsky.arrayofskymybatissimple.executor.resultset.ResultSetHandler;
import com.arrayofsky.arrayofskymybatissimple.executor.statement.PreparedStatementHandler;
import com.arrayofsky.arrayofskymybatissimple.executor.statement.StatementHandler;
import com.arrayofsky.arrayofskymybatissimple.mapper.MapperRegistry;
import com.arrayofsky.arrayofskymybatissimple.mapping.BoundSql;
import com.arrayofsky.arrayofskymybatissimple.mapping.Environment;
import com.arrayofsky.arrayofskymybatissimple.mapping.MappedStatement;
import com.arrayofsky.arrayofskymybatissimple.reflection.MetaObject;
import com.arrayofsky.arrayofskymybatissimple.reflection.factory.DefaultObjectFactory;
import com.arrayofsky.arrayofskymybatissimple.reflection.factory.ObjectFactory;
import com.arrayofsky.arrayofskymybatissimple.reflection.wrapper.DefaultObjectWrapperFactory;
import com.arrayofsky.arrayofskymybatissimple.reflection.wrapper.ObjectWrapperFactory;
import com.arrayofsky.arrayofskymybatissimple.scripting.LanguageDriverRegistry;
import com.arrayofsky.arrayofskymybatissimple.scripting.xmltags.XMLLanguageDriver;
import com.arrayofsky.arrayofskymybatissimple.transaction.Transaction;
import com.arrayofsky.arrayofskymybatissimple.transaction.jdbc.JdbcTransactionFactory;
import com.arrayofsky.arrayofskymybatissimple.type.TypeAliasRegistry;
import com.arrayofsky.arrayofskymybatissimple.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description 配置项
 */
public class Configuration {

    //环境
    protected Environment environment;

    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();


    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();


    // 已加载的资源
    protected final Set<String> loadedResources = new HashSet<>();

    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();
    // 类型处理器注册机
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    // 对象工厂和对象包装器工厂
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected String databaseId;


    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);

        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    /**
     * 创建结果集处理器
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    /**
     * 生产执行器
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }

    public String getDatabaseId() {
        return databaseId;
    }

    // 创建元对象
    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
    }


    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public LanguageDriverRegistry getLanguageRegistry() {
        return languageRegistry;
    }
}
