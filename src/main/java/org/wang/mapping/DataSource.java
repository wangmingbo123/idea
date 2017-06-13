package org.wang.mapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by wy on 2017/4/27.
 */
public class DataSource {
       String type;
       String driver;
       String url;
       String username;
       String password;
       Properties properties=new Properties();
       static {
              try {
                     Class.forName("com.mysql.jdbc.Driver");
              } catch (ClassNotFoundException e) {
                     e.printStackTrace();
              }
       }

       public Properties getProperties() {
              return properties;
       }

       public void setProperties(Properties properties) {
              this.properties = properties;
              driver=properties.getProperty("com.mysql.jdbc.Driver");
              url=properties.getProperty("url");
              username=properties.getProperty("username");
              password=properties.getProperty("password");
       }
       // 获取数据库连接
       public Connection getConnection(){
           try {
               return DriverManager.getConnection(url,username,password);
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return null;
       }


}
