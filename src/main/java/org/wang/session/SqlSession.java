package org.wang.session;

/**
 * Created by wy on 2017/4/28.
 */
public interface SqlSession extends Cloneable{
        // 插入
        public void insert(String statement, Object parament);
        // 查找一条记录
        public <T> T selectOne();
        public <T> T select(String statement, Object para);
        // 查询多条记录
        public <T> T selectList(String statement, Object para);

}
