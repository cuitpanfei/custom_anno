package cn.com.pfinfo.customanno.common.postprocessor;

import cn.com.pfinfo.customanno.anno.MethodMapping;
import cn.com.pfinfo.customanno.common.MethodInfoCatch;
import cn.com.pfinfo.customanno.common.bean.MethodInfoBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class ResolvePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String beanName) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(o.getClass());
        if (methods != null) {
            Arrays.stream(methods).forEach(m -> {
                MethodMapping dispatcherAnnotatin = m.getAnnotation(MethodMapping.class);
                if (dispatcherAnnotatin != null) {
                    MethodInfoBean infoBean = new MethodInfoBean(o, m, dispatcherAnnotatin);
                    if (MethodInfoCatch.isExsists(infoBean)) {
                        throw new BeanCreationNotAllowedException(beanName, "uri and method is duplicate! invocableMethod = " + infoBean);
                    } else {
                        MethodInfoCatch.add(infoBean);
                    }
                }
            });
        }
        return o;
    }
}
