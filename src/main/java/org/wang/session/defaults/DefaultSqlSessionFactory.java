package org.wang.session.defaults;

import org.wang.executor.Executor;
import org.wang.mapping.Configuration;
import org.wang.session.SqlSession;
import org.wang.session.SqlSessionFactory;

/**
 * Created by wy on 2017/4/28.
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{
    Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        Executor executor=configuration.newExecutor(configuration);
        SqlSession sqlSession=new DefaultSqlSession(configuration,executor);
        return sqlSession;
    }
}
