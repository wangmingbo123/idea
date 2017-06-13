package org.wang.session.defaults;

import org.wang.executor.Executor;
import org.wang.mapping.Configuration;
import org.wang.mapping.Mapper;
import org.wang.session.SqlSession;

import java.util.List;

/**
 * Created by wy on 2017/4/28.
 */
public class DefaultSqlSession implements SqlSession{
    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    // 配置
    Configuration configuration;

    // 执行者
    Executor executor;

    public void insert(String statement, Object parament) {
        Mapper mapper=configuration.get(statement);
        // 执行者来执行
        executor.insert(mapper,parament);


    }

    public <T> T selectOne() {
        return null;
    }

    public <T> T selectList(String statement, Object para) {
        return null;
    }

    public Object select(String statement, Object para) {
                 Mapper mapper=configuration.get(statement);
        return executor.select(mapper,para);
    }
}
