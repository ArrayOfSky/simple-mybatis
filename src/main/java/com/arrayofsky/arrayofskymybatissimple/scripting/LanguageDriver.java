package com.arrayofsky.arrayofskymybatissimple.scripting;

import com.arrayofsky.arrayofskymybatissimple.mapping.SqlSource;
import com.arrayofsky.arrayofskymybatissimple.session.Configuration;
import org.dom4j.Element;

/**
 * @description 脚本语言驱动
 */
public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);

}
