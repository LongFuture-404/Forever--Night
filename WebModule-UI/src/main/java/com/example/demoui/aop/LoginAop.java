package com.example.demoui.aop;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

import static com.example.demoserve.dao.TokenUtil.verify;

@Service("loginAop")
@Aspect
@Order(1)
public class LoginAop {
    @Pointcut("execution(* com.example.demoui.controller.*.*(..))")
    public void pointCut(){

    }

    //开启事务
    @Before("pointCut()")
    public void startTrans() throws ServletException, IOException {
        //同一个域名的线程间可以继承数据
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(sra, true);//设置子线程共享
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
//        HttpServletResponse response;
        if (attributes != null) {
//            response = attributes.getResponse();
            request = attributes.getRequest();
            System.out.println();
            if(!(request.getRequestURI().contains("/Guest"))){
                System.out.println("切点前操作执行完成");
//                String token=request.getHeader("Authorization");
//                if(!verify(token)){
//                    if (response != null) {
//                        response.setHeader("allow","false");
//                        response.sendRedirect("http://localhost:8080/");
//                    }
//                }
            }
        }
    }

    //提交
//    @AfterReturning("pointCut()")
//    public void Commit() {
//
//    }

    //回滚操作
//    @AfterThrowing("pointCut()")
//    public void rollback() {
//
//    }
}
