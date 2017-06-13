package org.wang.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/5/1.
 */
public class InterceptorChain {
       List<Interceptor> interceptors=new ArrayList<Interceptor>();

       // 拦截
       public Object pluginAll(Object target){
              for(Interceptor interceptor:interceptors){
                  target=interceptor.plugin(target);
              }
              return target;
       }
       public void addInterceptor(Interceptor interceptor){
           interceptors.add(interceptor);
       }


}
