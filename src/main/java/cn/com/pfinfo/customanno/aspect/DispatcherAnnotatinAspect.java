package cn.com.pfinfo.customanno.aspect;


import cn.com.pfinfo.customanno.anno.MethodMapping;
import cn.com.pfinfo.customanno.email.MailService;
import cn.com.pfinfo.customanno.exception.ErrorMsgException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static cn.com.pfinfo.customanno.email.EmailConstant.DEFAULT_EMAIL;

/**
 * MethodMapping 切面,用于切面处理使用了{@link MethodMapping @MethodMapping}注解的方法
 */
@Aspect
@Component
public class DispatcherAnnotatinAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(DispatcherAnnotatinAspect.class);

    @Autowired
    private MailService mailService;

    @Pointcut("@annotation(cn.com.pfinfo.customanno.anno.MethodMapping)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object handlerDispatcherAnnotatin(ProceedingJoinPoint pjp) throws Throwable {
        try {
            StopWatch stopwatch = new StopWatch();
            stopwatch.start();
            LOGGER.info("执行Controller开始: {},参数：{}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));
            Object obj = pjp.proceed(pjp.getArgs());
            stopwatch.stop();
            LOGGER.info("耗时：{}(毫秒).", stopwatch.getTotalTimeSeconds());
            return obj;
        } catch (ErrorMsgException e) {
            LOGGER.info("cn.com.pfinfo.customanno.aspect.DispatcherAnnotatinAspect.toEmail start Time: {}ms", TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis()));
            toEmail(e, pjp);
            LOGGER.info("cn.com.pfinfo.customanno.aspect.DispatcherAnnotatinAspect.toEmail end Time: {}ms", TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis()));
            throw e;
        }
    }

    @Async
    void toEmail(ErrorMsgException e, ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        MethodMapping annotatin = targetMethod.getAnnotation(MethodMapping.class);
        String email=e.email();
        if ("".equals(email)) {
            email = annotatin.email();
        }
        if ("".equals(email)) {
            email = DEFAULT_EMAIL;
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        String finalEmail = email;
        executorService.execute(() -> mailService.sendSimpleMail(finalEmail, e.getMessage(), e.print()));
    }

}
