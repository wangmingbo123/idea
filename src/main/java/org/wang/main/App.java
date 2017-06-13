package org.wang.main;

import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.wang.entity.User;
import org.wang.mapper.UserDao;
import org.wang.mapper.UserDaoImpl;
import org.wang.plugin.Interceptor;
import org.wang.plugin.InterceptorChain;
import org.wang.plugin.WangPlugin;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wy on 2017/4/27.
 */
public class App {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        //DriverManager
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        User user=new User();
        user.setUserAge(12);
        user.setUserName("nie");
        user.setUserPass("123456");
        //  session.insert("org.wang.mapper.UserMapper.insertUser",user);
        //  session.commit();
        User user1=session.selectOne("org.wang.mapper.UserMapper.getUserById",1);
        //System.out.println(new Gson().toJson(user1));

        //  idleConnections
        //  空闲连接
        //  activeConnections
        //  活动连接

        //session.selectList("org.wang.mapper.UserMapper.getUsers",1);
        //session.commit();
        session.selectOne("org.wang.mapper.UserMapper.getUserById",1);
        session.close();


    }
    @Test
    public void  testPlugin(){
        InterceptorChain interceptorChain=new InterceptorChain();
        Interceptor one=new WangPlugin();
        interceptorChain.addInterceptor(one);

        // 代理类
        UserDao target=new UserDaoImpl();
        target= (UserDao) interceptorChain.pluginAll(target);
        target.insert(new User());
        target.update(new User());

    }



}
