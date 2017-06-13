package org.wang.main;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by wy on 2017/5/1.
 */
@Intercepts(@Signature(type = StatementHandler.class,method ="prepare",args = {Connection.class}))
public class HelloPlugin implements Interceptor{
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler= (StatementHandler) invocation.getTarget();
        System.out.println("intercept");
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        System.out.println("plugin ");
        return Plugin.wrap(target,this);
    }

    public void setProperties(Properties properties) {
        System.out.println("setProperties");
        System.out.println("setProperties "+properties.get("name"));

    }
}
