package com.arrayofsky.arrayofskymybatissimple.builder;


import com.arrayofsky.arrayofskymybatissimple.session.Configuration;

/**
 * @description 构建器的基类，建造者模式
 */
public abstract class BaseBuilder {

    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
