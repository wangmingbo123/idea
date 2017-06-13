package org.wang.executor;

import org.wang.executor.statement.PreparedStatementHandler;
import org.wang.executor.statement.StatementHandler;
import org.wang.mapping.Configuration;
import org.wang.mapping.Mapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wy on 2017/4/28.
 */
public class SimpleExecutor extends  BaseExecutor{
    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    Configuration configuration;
    public int doUpdate(Mapper mapper, Object parameter) {
        StatementHandler handler=configuration.newStatementHandler(mapper.getId());

//        return handle
        return 1;
    }

    public void doInsert(Mapper mapper, Object parameter) {
           // 获得语句处理
           StatementHandler handler=configuration.newStatementHandler(mapper.getId());
           Statement statement=prepareStatement(handler,parameter);
           handler.insert(statement);
    }

    public Object doSelect(Mapper mapper, Object parameter) {
        StatementHandler handler=configuration.newStatementHandler(mapper.getId());
        Statement statement=prepareStatement(handler,parameter);
        return handler.query(statement);
    }


    public Statement prepareStatement(StatementHandler handler,Object parament){
        // 获得connection对象
       Connection connection=getConnection();
       Statement statement=null;
       // 准备
       statement=handler.prepare(connection);
       // 准备参数
        ((PreparedStatementHandler)handler).para=parament;

       // 参数化
       handler.parameterize(statement);
       return statement;

    }
    public Connection getConnection(){
        Connection connection=configuration.getEnvironment().getDataSource().getConnection();
        /*try {
            return transaction.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return connection;
    }

}
