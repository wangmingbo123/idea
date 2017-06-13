package org.wang.builder;

import org.wang.mapping.Configuration;
import org.wang.mapping.Mapper;
import org.wang.mapping.StatementPara;
import org.wang.parse.GenericTokenParser;

import java.util.List;

/**
 * Created by wy on 2017/4/27.
 */
public class SqlSourceBuilder {
     Configuration configuration;

    public SqlSourceBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public String parse(String orginalSql,String statementId){
             StringBuilder builder=new StringBuilder(orginalSql);
             String emptySql=builder.toString().trim();
             ParameterMappingTokenHandler handler=new ParameterMappingTokenHandler(configuration);
             GenericTokenParser parser=new GenericTokenParser("#{","}",handler);
             String sql=parser.parse(emptySql);
             Mapper mapper=configuration.get(statementId);

             // 参数名从sql语句获得
             //  而参数名的类型则由  parameterType="org.wang.entity.User"获得
            for(String para:handler.paraNames){
                mapper.add(new StatementPara(para));
            }

            // 在这里设置参数类型
            Object object=mapper.getQinPara();




            return  sql;
      }
}
