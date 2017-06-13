package org.wang.executor.result;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.wang.mapping.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;

/**
 * Created by wy on 2017/5/1.
 */
public class SingleResultHandler implements ResultHandler{
    Mapper mapper;

    public SingleResultHandler(Mapper mapper) {
        this.mapper = mapper;
    }

    public Object handlerResult(Statement statement) {
        ResultSet rs=null;
        Object object=mapper.getReturnPara();
        MetaObject metaObject=MetaObject.forObject(object,new DefaultObjectFactory(),new DefaultObjectWrapperFactory());
        String get[]=metaObject.getGetterNames();
        try {
            rs=statement.getResultSet();
            while (rs.next()){
                   for(String g:get){
                       Class clsType=metaObject.getGetterType(g);
                       if(clsType.equals(Integer.class)){
                           Integer value=rs.getInt(g);
                           metaObject.setValue(g,value);
                       }else if(clsType.equals(String.class)){
                           String value=rs.getString(g);
                           metaObject.setValue(g,value);

                       }

                   }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return object;
    }
}
