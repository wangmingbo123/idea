package org.wang.executor.statement;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.wang.entity.User;
import org.wang.executor.result.SingleResultHandler;
import org.wang.mapping.Mapper;
import org.wang.mapping.StatementPara;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wy on 2017/4/29.
 */
public class PreparedStatementHandler extends BaseStatementHandler{
    Mapper mapper;
    // 参数对象
    public  Object para;
    public PreparedStatementHandler(Mapper mapper) {
        this.mapper = mapper;
    }

    public void parameterize(Statement statement){
           PreparedStatement preparedStatement= (PreparedStatement) statement;
           // 设置参数
           // 设置参数类型
//           Class classType=mapper.getParaType();
           ObjectFactory objectFactory=new DefaultObjectFactory();
           Class  cls=User.class;
//           Object object=objectFactory.create(mapper.getParaType());
           Object object=mapper.getQinPara();
           ObjectWrapperFactory objectWrapperFactory=new DefaultObjectWrapperFactory();
           MetaObject metaObject=MetaObject.forObject(object,objectFactory,objectWrapperFactory);


           // 根据参数名来取值
           MetaObject paraValue=MetaObject.forObject(para,objectFactory,objectWrapperFactory);


        for(int i=0;i<mapper.getParas().size();i++){
            StatementPara statementPara=mapper.paras.get(i);
            String name=statementPara.getName();
            Class type=metaObject.getGetterType(name);
            statementPara.setType(type);
            try {
                if(statementPara.getType().equals(Integer.class)){
                    Integer par= (Integer) paraValue.getValue(statementPara.getName());
                    preparedStatement.setInt(i+1,par);
                }else {
                    String par1= (String) paraValue.getValue(statementPara.getName());

                preparedStatement.setString(i+1,par1);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void update(Statement statement) {

    }

    public Object query(Statement statement) {
        PreparedStatement preparedStatement= (PreparedStatement) statement;
        try {
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 暂时在这里实例化
        resultHandler=new SingleResultHandler(mapper);

        return  resultHandler.handlerResult(statement);
    }

    public void insert(Statement statement) {
           PreparedStatement preparedStatement= (PreparedStatement) statement;
        try {
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public Statement instaniteState() {
        return null;
    }

    protected Statement instantiateStatement(Connection connection)  {
              String sql=mapper.getSql();
        Statement statement=null;
        try {
            statement=connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
