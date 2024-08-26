package com.arrayofsky.arrayofskymybatissimple.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @description 数据源工厂
 */
public interface DataSourceFactory {

    void setProperties(Properties props);

    DataSource getDataSource();

}
