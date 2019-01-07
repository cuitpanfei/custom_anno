package cn.com.pfinfo.customanno.common.bean;

import cn.com.pfinfo.customanno.anno.MethodMapping;
import cn.com.pfinfo.customanno.enums.HTTPMethodType;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

/**
 * 方法信息封装对象,用于缓存自定义注解{@link MethodMapping}作用的方法对象的相关信息。
 *
 * @author cuitpanfei
 */
public class MethodInfoBean implements Serializable {

    private Object bean;

    private Method method;

    private String uri;

    private HTTPMethodType httpMethodType;

    private ParameterInfoBean[] parameterInfoBeans;

    private boolean hasParam;

    public MethodInfoBean(Object bean, Method method, MethodMapping dispatcherAnnotatin){
        this(bean,method, dispatcherAnnotatin.uri(), dispatcherAnnotatin.method());
    }
    public MethodInfoBean(Object bean, Method method, String uri,HTTPMethodType methodType){
        this.bean=bean;
        this.method=method;
        this.uri=uri;
        this.httpMethodType=methodType;
        Parameter[] parameters = method.getParameters();
        this.parameterInfoBeans =  Arrays.stream(parameters)
                .filter(Objects::nonNull)
                .map(param -> new ParameterInfoBean(param.getName(),param,param.getType()))
                .toArray(ParameterInfoBean[]::new);
        this.hasParam=parameterInfoBeans.length>0;
    }

    public Object invoke(HttpServletRequest request) {
        try{
            if(hasParam){
                Object[] args = getArgsByRequest(request);
                Assert.notNull(args,method.getName()+"参数不能为空");
                return method.invoke(bean, args);
            }else{
                return method.invoke(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "服务器异常";
        }
    }

    /**
     * 获取参数
     * @param request
     * @return
     */
    private Object[] getArgsByRequest(HttpServletRequest request) {
        Object[] args = new Object[parameterInfoBeans.length];
        for (int i = 0,length = parameterInfoBeans.length; i < length; i++) {
            args[i]=request.getParameter(parameterInfoBeans[i].getName());
        }
        return args;
    }


    public String getUri() {
        return uri;
    }

    public HTTPMethodType getHttpMethodType() {
        return httpMethodType;
    }
}
