package org.wang.session;

import org.wang.mapping.Configuration;
import org.wang.session.defaults.DefaultSqlSessionFactory;

/**
 * Created by wy on 2017/4/28.
 */
public class SqlSessionFactoryBuilder {
       public SqlSessionFactory build(Configuration configuration){
                         // 建造者模式
                         // build Factory  pattern
                         return  new DefaultSqlSessionFactory(configuration);

       }

}
