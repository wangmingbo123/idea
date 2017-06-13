package org.wang.executor.result;

import java.sql.Statement;

/**
 * Created by wy on 2017/5/1.
 */
public interface ResultHandler {
       Object handlerResult(Statement statement);
}
