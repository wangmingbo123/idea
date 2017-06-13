package org.wang.executor.statement;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.wang.mapping.Configuration;
import org.wang.mapping.Mapper;
import org.wang.mapping.StatementPara;

import javax.jws.Oneway;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by wy on 2017/4/28.
 */
public class SimpleStatementHandler extends BaseStatementHandler{
    Mapper mapper;
    //


    public SimpleStatementHandler(Mapper mapper) {
        this.mapper = mapper;
    }

    public Statement instaniteState() {
        return null;
    }

    protected Statement instantiateStatement(Connection connection) {
        return null;
    }

    //  抽象类没有实现的接口，子类必须实现
    public void parameterize(Statement statement) {
           // 参数化
          String sql=mapper.getSql();
          PreparedStatement preparedStatement= (PreparedStatement) statement;
          ObjectFactory objectFactory=new DefaultObjectFactory();
          Object object=objectFactory.create(mapper.getParaType());
          ObjectWrapperFactory objectWrapperFactory=new DefaultObjectWrapperFactory();
          MetaObject metaObject=MetaObject.forObject(object,objectFactory,objectWrapperFactory);
          Class  classType=metaObject.getGetterType("userAge");
          for(int i=0;i<mapper.paras.size();i++){
              //  参数
              StatementPara statementPara=mapper.paras.get(i);
              String name=statementPara.getName();
              Class type=metaObject.getGetterType(name);
              statementPara.setType(type);
          }



    }

    public void update(Statement statement) {

    }

    public Object query(Statement statement) {
           return null;
    }

    public void insert(Statement statement) {
           String sql=mapper.getSql();

    }
}
