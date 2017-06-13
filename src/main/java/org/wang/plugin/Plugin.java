package org.wang.plugin;

import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wy on 2017/5/1.
 */
public class Plugin implements InvocationHandler{
    // 目标类
    Object target;
    // 被拦截的类的方法

    // 拦截器
    // 该拦截器包含了被拦截的类和该类的一些方法
    Interceptor interceptor;
    // 通过拦截器来获取

    // 拦截的类和其方法的匹配
    static Map<Class<?>,Set<Method>> signMap=new HashMap<Class<?>, Set<Method>>();

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           // 获取所有拦截的方法
           Set<Method> methods=signMap.get(method.getDeclaringClass());
           if(methods!=null&& methods.contains(method)){
               // 该方法被拦截
               return interceptor.intercept(new Invocation(target,method,args));
           }
           // 否者，该方法没有被拦截
           return  method.invoke(target,args);

    }
    // 可以实现一个类拦截多个方法
    static Object wrap(Object target,Interceptor interceptor){
        Intercepts intercepts=interceptor.getClass().getAnnotation(Intercepts.class);
        Signature signature[]=intercepts.value();
        Set<Method> methods=new HashSet<Method>();

        // 获取应该拦截那些方法
        for(Signature sign:signature){
            // 遍历 Signature
            // 被拦截的类名
            Class type=sign.type();
            String method=sign.method();
            Class arg[]=sign.args();
            // 通过 方法名 和 参数名 来获取方法
            Method m=null;
            try {
                m=type.getMethod(method,arg);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            methods.add(m);
            signMap.put(type,methods);
        }

        // 获取target 实现的接口和方法
        // 和前面应该拦截的接口方法
        // 进行比较
        // 来判断是否需要进行代理拦截
        Class cls=target.getClass();

        // 实现的接口数组
        Set<Class> ints=new HashSet<Class>();


        for(Class inter:cls.getInterfaces()){
                  if(signMap.containsKey(inter)){
                    // 该接口被拦截
                     ints.add(inter);
                  }

        }

        //生成代理类
        if(ints.size()>0)
            return Proxy.newProxyInstance(cls.getClassLoader(),ints.toArray(new Class<?>[ints.size()]),new Plugin(target,interceptor));
        else
            return target;
        // 否者，不用生成代理
    }

}
