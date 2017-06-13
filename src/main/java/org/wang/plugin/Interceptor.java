package org.wang.plugin;

import java.util.Properties;

/**
 * Created by wy on 2017/5/1.
 */
public interface Interceptor {
       // 拦截
       Object intercept(Invocation invocation);
       Object plugin(Object target);

       // 设置属性
       void setProperties(Properties properties);


}
