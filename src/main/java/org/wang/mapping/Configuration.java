package org.wang.mapping;

import org.wang.executor.Executor;
import org.wang.executor.SimpleExecutor;
import org.wang.executor.statement.PreparedStatementHandler;
import org.wang.executor.statement.SimpleStatementHandler;
import org.wang.executor.statement.StatementHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wy on 2017/4/27.
 */
public class Configuration {
       public Map<String,Mapper> mappers=new HashMap<String, Mapper>();

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment environment;
       public void put(String statementId,Mapper mapper){
              mappers.put(statementId,mapper);
       }
       public Mapper get(String statementId){
              return mappers.get(statementId);
       }

       //     获得新的StatementHandler
       //
       public StatementHandler newStatementHandler(String statementId){
              Mapper mapper=mappers.get(statementId);
              //StatementHandler handler=new SimpleStatementHandler(mapper);
              StatementHandler handler=new PreparedStatementHandler(mapper);
              return handler;
       }

       // 获取新的 Executor

       public Executor newExecutor(Configuration configuration){
              Executor executor=new SimpleExecutor(configuration);
              // 后面添加plugin
              //  实现拦截器模式
              return executor;

       }







}
