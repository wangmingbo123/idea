package org.wang.mapping;

/**
 * Created by wy on 2017/4/27.
 */
public class StatementPara {
       String name;
       public  Class<?> type;

    public StatementPara(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
