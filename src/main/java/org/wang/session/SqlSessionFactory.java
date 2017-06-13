package org.wang.session;

/**
 * Created by wy on 2017/4/28.
 */
public interface SqlSessionFactory {
       // 打开回话
       public SqlSession openSession();

}
