package cn.com.pfinfo.customanno.common.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * 用于封装方法参数相关信息
 *
 * @author cuitpanfei
 */
public class ParameterInfoBean implements Serializable {
    private String name;
    private Parameter parameter;
    private Integer index;
    private Class<?> parameterType;

    public ParameterInfoBean(String name, Parameter parameter, Class<?> parameterType) {
        this.name = name;
        this.parameter = parameter;
        try {
            Field field = parameter.getClass().getDeclaredField("index");
            field.setAccessible(true);
            index = (Integer) field.get(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.parameterType = parameterType;
    }

    public String getName() {
        return name;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }
}
