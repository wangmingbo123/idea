package org.wang.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/4/27.
 */
public class Mapper {
             String id=null;
             Class<?> paraType;
             Object qinPara;
             Class<?> returnType;
             Object returnPara;
             String sql=null;

    public List<StatementPara> getParas() {
        return paras;
    }

    public void setParas(List<StatementPara> paras) {
        this.paras = paras;
    }

    public List<StatementPara> paras=new ArrayList<StatementPara>();
             public void  add(StatementPara para){
                    paras.add(para);
             }
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getParaType() {
        return paraType;
    }

    public void setParaType(Class<?> paraType) {
        this.paraType = paraType;
    }

    public Object getQinPara() {
        return qinPara;
    }

    public void setQinPara(Object qinPara) {
        this.qinPara = qinPara;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Object getReturnPara() {
        return returnPara;
    }

    public void setReturnPara(Object returnPara) {
        this.returnPara = returnPara;
    }



}
