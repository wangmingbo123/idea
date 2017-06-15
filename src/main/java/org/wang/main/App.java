package org.wang.main;

import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    @Test
    public void testReflection(){
        User user=new User();
        user.setUserName("wang");
        user.setUserPass("123456");
        user.setUserAge(12);
        ObjectFactory objectFactory=new DefaultObjectFactory();
        User u=objectFactory.create(User.class);
        ObjectWrapperFactory objectWrapperFactory=new DefaultObjectWrapperFactory();
        MetaObject metaObject=MetaObject.forObject(user,objectFactory,objectWrapperFactory);
        Class  classType=metaObject.getGetterType("userAge");
        System.out.println(classType);
    }
    @Test
    public void plain() throws SQLException {
        String url="jdbc:mysql://localhost:3306/lixueyuan";
        Connection connection= DriverManager.getConnection(url,"root","123456");
        //   Statement stmt=connection.createStatement();
        String sql="select * from user where userId=1";
        String insertSql="INSERT INTO user(userName,userPass,userAge) VALUES(?,?,?)";
        PreparedStatement preparedStatement=null;
        preparedStatement=connection.prepareStatement(insertSql);
        preparedStatement.setString(1,"h");
        preparedStatement.setString(2,"h");
        preparedStatement.setInt(3,12);
        preparedStatement.execute();

    }



}
