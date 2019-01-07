package cn.com.pfinfo.customanno.httpservlet;

import cn.com.pfinfo.customanno.common.MethodInfoCatch;
import cn.com.pfinfo.customanno.common.bean.MethodInfoBean;
import cn.com.pfinfo.customanno.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cuitpanfei
 */
@Component
public class DispatcherServlet extends HttpServlet implements Servlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.获取请求的URL
        String requestURI = req.getRequestURI();
        if (StringUtils.isEmpty(requestURI)) {
            return;
        }else if("/favicon.ico".equals(requestURI)){
            return;
        }
        // 2.从MethodInfoCatch获取MethodInfoBean对象
        MethodInfoBean infoBean = MethodInfoCatch.get(requestURI,req.getMethod());

        // 3.使用反射机制执行方法
        Object obj=infoBean.invoke(req);
        try{
            if(!(obj instanceof String)){
                obj = JSON.toJSONString(obj);
            }
        }catch (Exception e){
        }
        //返回数据
        resp.getWriter().print(obj);
    }
}
