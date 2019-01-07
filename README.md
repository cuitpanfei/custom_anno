# 自定义前端控制器注解

**使用此jar包的用户需要注意**：
<ol>
<li>使用此jar包,将会强制拦截所有http请求，所以，此jar包不兼容其它框架的前端控制器</li>
<li>此jar包依赖于SpringBoot Email，需要在项目的配置文件中添加email相关配置

```yaml
spring:
  mail:
    host: smtp.163.com
    username: xxxx@163.com
    password: xxxx
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
```

</li>
<li>MethodMapping、ErrorMsgException。这两个类可能是你编程的时候使用最多的类，请多了解一下</li>
<li>MethodMapping、ResolvePostProcessor、DispatcherServlet。这三个类可能是你了解此jar包设计原理的重要类</li>
</ol>