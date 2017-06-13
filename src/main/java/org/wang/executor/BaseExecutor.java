package org.wang.executor;

import org.apache.ibatis.transaction.Transaction;
import org.wang.mapping.Configuration;
import org.wang.mapping.Mapper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wy on 2017/4/28.
 */
public abstract class  BaseExecutor implements Executor {
       Transaction transaction;
       Configuration configuration;

    public void insert(Mapper mapper, Object parameter) {
           doInsert(mapper,parameter);
    }


    public int update(Mapper mapper, Object parameter) {
        // 进行错误处理
        return doUpdate(mapper,parameter);
    }

    public <E> List<E> query(Mapper mapper, Object parameter) {
        return null;
    }

    public Object select(Mapper mapper, Object para) {

        return doSelect(mapper,para);
    }


    public void commit(boolean req) {

    }

    public void rollback(boolean req) {

    }

    public void close(boolean forceClosed) {

    }

    public Transaction getTransaction() {
           return transaction;
    }

    // 子类来实现
    public abstract int doUpdate(Mapper mapper, Object parameter);

    // 子类来实现
    public abstract void doInsert(Mapper mapper,Object parameter);

    // 子类来实现
    public  abstract Object doSelect(Mapper mapper,Object parameter);



}
