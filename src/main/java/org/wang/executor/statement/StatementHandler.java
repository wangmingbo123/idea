package org.wang.executor.statement;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by wy on 2017/4/27.
 */
public interface StatementHandler {
      // 准备
      Statement prepare(Connection connection);


      // 参数化
      void parameterize(Statement statement);

      // 更新
      void update(Statement statement);

      //  查询
      Object query(Statement statement);

      // insert 插入
      void insert(Statement statement);



}
