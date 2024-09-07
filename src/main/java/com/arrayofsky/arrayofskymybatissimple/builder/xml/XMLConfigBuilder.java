package com.arrayofsky.arrayofskymybatissimple.builder.xml;

import com.arrayofsky.arrayofskymybatissimple.builder.BaseBuilder;
import com.arrayofsky.arrayofskymybatissimple.datasource.DataSourceFactory;
import com.arrayofsky.arrayofskymybatissimple.io.Resources;
import com.arrayofsky.arrayofskymybatissimple.mapping.BoundSql;
import com.arrayofsky.arrayofskymybatissimple.mapping.Environment;
import com.arrayofsky.arrayofskymybatissimple.mapping.MappedStatement;
import com.arrayofsky.arrayofskymybatissimple.mapping.SqlCommandType;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import com.arrayofsky.arrayofskymybatissimple.transaction.TransactionFactory;
import org.xml.sax.InputSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description XML配置构建器，建造者模式，继承BaseBuilder
 */
public class XMLConfigBuilder extends BaseBuilder {

    private Element root;

    public XMLConfigBuilder(Reader reader) {
        // 1. 调用父类初始化Configuration
        super(new Configuration());

        // 2. 使用dom4j库处理xml
        // 2.1 创建一个SAXReader对象
        SAXReader saxReader = new SAXReader();
        try {
            // 2.2 使用SAXReader对象读取xml文件内容，并将其转换为Document对象
            Document document = saxReader.read(new InputSource(reader));
            // 2.3 获取Document对象的根元素
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析配置；类型别名、插件、对象工厂、对象包装工厂、设置、环境、类型转换、映射器
     *
     * @return Configuration
     */
    public Configuration parse() {
        try {
            // 环境
            environmentsElement(root.element("environments"));
            // 解析映射器
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }



    /**
     * <environments default="development">
     * <environment id="development">
     * <transactionManager type="JDBC">
     * <property name="..." value="..."/>
     * </transactionManager>
     * <dataSource type="POOLED">
     * <property name="driver" value="${driver}"/>
     * <property name="url" value="${url}"/>
     * <property name="username" value="${username}"/>
     * <property name="password" value="${password}"/>
     * </dataSource>
     * </environment>
     * </environments>
     */
    private void environmentsElement(Element context) throws Exception {
        String environment = context.attributeValue("default");

        List<Element> environmentList = context.elements("environment");
        for (Element e : environmentList) {
            String id = e.attributeValue("id");
            if (environment.equals(id)) {
                // 事务管理器，从配置文件加载是使用的什么事务管理器，然后从typeAliasRegistry中获取对应的类，然后通过类来实例化对象
                TransactionFactory txFactory = (TransactionFactory) typeAliasRegistry.resolveAlias(e.element("transactionManager").attributeValue("type")).newInstance();

                // 数据源 这里的处理方法和事务管理器差不多
                Element dataSourceElement = e.element("dataSource");
                DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceElement.attributeValue("type")).newInstance();

                // 获取数据源的属性 交给数据源工厂处理
                List<Element> propertyList = dataSourceElement.elements("property");
                Properties props = new Properties();
                for (Element property : propertyList) {
                    props.setProperty(property.attributeValue("name"), property.attributeValue("value"));
                }
                dataSourceFactory.setProperties(props);


                DataSource dataSource = dataSourceFactory.getDataSource();

                // 构建环境
                Environment.Builder environmentBuilder = new Environment.Builder(id)
                        .transactionFactory(txFactory)
                        .dataSource(dataSource);

                configuration.setEnvironment(environmentBuilder.build());
            }
        }
    }




    private void mapperElement(Element mappers) throws Exception {

        List<Element> mapperList = mappers.elements("mapper");
        for (Element e : mapperList) {
            // 读取mappers配置下的每一个mapper
            String resource = e.attributeValue("resource");
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // 在for循环里每个mapper都重新new一个XMLMapperBuilder，来解析
            XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource);
            mapperParser.parse();
        }
    }

}