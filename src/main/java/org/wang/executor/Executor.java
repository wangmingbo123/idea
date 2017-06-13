package org.wang.executor;

import org.apache.ibatis.transaction.Transaction;
import org.wang.mapping.Mapper;

import java.util.List;

/**
 * Created by wy on 2017/4/28.
 */
public interface Executor {

       public void insert(Mapper mapper, Object parameter);
       int update(Mapper mapper, Object parameter);
       <E> List<E> query(Mapper mapper, Object parameter);
       Object select(Mapper mapper, Object para);

       // 事务
       // 提交
       void  commit(boolean req);
       //   回滚
       void rollback(boolean req);

       void  close(boolean forceClosed);
       Transaction getTransaction();



}
