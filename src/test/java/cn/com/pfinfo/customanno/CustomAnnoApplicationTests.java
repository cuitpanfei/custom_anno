package cn.com.pfinfo.customanno;

import cn.com.pfinfo.customanno.common.bean.ParameterInfoBean;
import cn.com.pfinfo.customanno.enums.HTTPMethodType;
import org.junit.Test;

import java.lang.reflect.Parameter;
import java.util.Arrays;

public class CustomAnnoApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(HTTPMethodType.GET.name());

        Parameter[] parameters = new Parameter[0];
        Object[] arrs = Arrays.stream(parameters)
                .map(param -> new ParameterInfoBean(param.getName(),param,param.getType()))
                .toArray();
        System.out.println(arrs ==null);
        System.out.println(arrs instanceof ParameterInfoBean[]);
        System.out.println(arrs.length);
    }

}
