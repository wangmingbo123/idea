package org.wang.plugin;

import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.wang.entity.User;
import org.wang.mapper.UserDao;

import java.util.Properties;

/**
 * Created by wy on 2017/5/1.
 */
@Intercepts({@Signature(type = UserDao.class,method = "update",args = {User.class})
             ,
             @Signature(type =UserDao.class,method = "insert",args ={User.class})})
public class WangPlugin implements Interceptor{
    public Object intercept(Invocation invocation) {
//          UserDao userDao= (UserDao) invocation.getTarget();
        System.out.println("class is intercept"+invocation.getTarget().getClass());
        System.out.println("class method is intercept "+invocation.getMethod().getName());
        System.out.println("WangPlugin intercept");
        return invocation.procedd();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    public void setProperties(Properties properties) {

    }
}
