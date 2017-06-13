package org.wang.executor.statement;

import org.wang.executor.result.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wy on 2017/4/28.
 */
public abstract class BaseStatementHandler implements StatementHandler{
    public ResultHandler resultHandler;
    public abstract Statement instaniteState();

    public Statement prepare(Connection connection) {
        // 根据connection获得Statement
      /*  try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return null;
        }*/
        Statement statement=null;
        // instantiateStatement 为抽象方法
        // 交给子类实现
        statement=instantiateStatement(connection);
        return  statement;

    }

    // 其他的公用操作留给子类来实现

    protected abstract Statement instantiateStatement(Connection connection);

}
