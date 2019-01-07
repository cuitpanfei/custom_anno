package cn.com.pfinfo.customanno.anno;


import cn.com.pfinfo.customanno.enums.HTTPMethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cuitpanfei
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMapping {
    String uri();

    HTTPMethodType method() default  HTTPMethodType.GET;


    /**
     * 建议书写一个默认的负责人邮箱
     * @return 使用此注解的方法的负责人邮箱
     */
    String email() default  "";

}
