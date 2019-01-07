package cn.com.pfinfo.customanno.common;


import cn.com.pfinfo.customanno.anno.MethodMapping;
import cn.com.pfinfo.customanno.common.bean.MethodInfoBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法信息缓存对象,用于缓存自定义注解{@link MethodMapping}作用的方法对象。
 * @author cuitpanfei
 */
public class MethodInfoCatch {
    private static final Map<String,MethodInfoBean> CATCHEDINFO = new ConcurrentHashMap<>();

    public static boolean isExsists(MethodInfoBean methodInfoBean){
        return CATCHEDINFO.containsKey(getHashKey(methodInfoBean));
    }

    public static void add(MethodInfoBean methodInfoBean){
        CATCHEDINFO.put(getHashKey(methodInfoBean),methodInfoBean);
    }

    public static MethodInfoBean get(String uri,String methodName){
        return CATCHEDINFO.get(getHashKey(uri,methodName));
    }

    private static String getHashKey(MethodInfoBean methodInfoBean){
        return getHashKey(methodInfoBean.getUri(),methodInfoBean.getHttpMethodType().name());
    }
    private static String getHashKey(String uri,String methodName){
        return uri+":"+methodName;
    }

}
