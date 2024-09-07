package com.arrayofsky.arrayofskymybatissimple.scripting.xmltags;

import com.arrayofsky.arrayofskymybatissimple.mapping.SqlSource;
import com.arrayofsky.arrayofskymybatissimple.scripting.LanguageDriver;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import org.dom4j.Element;

/**
 * @description XML语言驱动器
 */
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

}