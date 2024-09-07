package com.arrayofsky.arrayofskymybatissimple.scripting.xmltags;

import com.arrayofsky.arrayofskymybatissimple.builder.BaseBuilder;
import com.arrayofsky.arrayofskymybatissimple.mapping.SqlSource;
import com.arrayofsky.arrayofskymybatissimple.scripting.defaults.RawSqlSource;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @description XML脚本构建器
 */
public class XMLScriptBuilder extends BaseBuilder {

    private Element element;
    private boolean isDynamic;
    private Class<?> parameterType;

    public XMLScriptBuilder(Configuration configuration, Element element, Class<?> parameterType) {
        super(configuration);
        this.element = element;
        this.parameterType = parameterType;
    }

    public SqlSource parseScriptNode() {
        List<SqlNode> contents = parseDynamicTags(element);
        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
        return new RawSqlSource(configuration, rootSqlNode, parameterType);
    }

    List<SqlNode> parseDynamicTags(Element element) {
        List<SqlNode> contents = new ArrayList<>();
        // element.getText 拿到 SQL
        String data = element.getText();
        contents.add(new StaticTextSqlNode(data));
        return contents;
    }

}
