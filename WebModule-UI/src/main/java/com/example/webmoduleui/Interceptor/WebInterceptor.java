package com.example.webmoduleui.Interceptor;

import com.example.webmoduleui.annotation.RequireRoles;
import com.example.webmoduleui.service.UserRoleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static com.example.datamoduleserve.dao.TokenUtil.*;

@Component("WebInterceptor")
public class WebInterceptor implements HandlerInterceptor {

    @Resource(name = "UserRoleService")
    UserRoleService userRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        /**
         * 通过Feign接口异步传递token参数
         */
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        // 放行OPTIONS请求
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        String token=request.getHeader("Authorization");
        String Refresh=request.getHeader("Refresh");
//        System.out.println(request.getHeader("Authorization"));

        //token不存在则返回登录页面
        if (ObjectUtils.isEmpty(token)||(!verify(token))) {
            System.err.println("token错误,返回登录页面");
            response.setHeader("allow", "false");
            throw new Exception();
        }

        String userId=getUserId(token);
        //token未过期则刷新token
        if ((!ObjectUtils.isEmpty(Refresh)) && Refresh.equals("true")) {
//            System.out.println("刷新token");
            response.setHeader("token",refresh(token));
        }

        //获取方法句柄，通过该句柄获取方法上注解，并获取注解中的value
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireRoles rolesAnno = handlerMethod.getMethodAnnotation(RequireRoles.class);

        if (!ObjectUtils.isEmpty(rolesAnno)) {
            String[] roles = rolesAnno.value();
            List<String> roleCodeList = userRoleService.getRoleByUserId(userId);
            boolean roleFlag = false;
            for (String role : roles) {
                if (roleCodeList.contains(role)) {
                    roleFlag = true;
                    break;
                }
            }
            if (!roleFlag) {
                response.setHeader("role_allow", "false");
                throw new Exception();
            }
        }

        return true;
    }
}

///**
// *@author
// *@description 上下文装饰器
// * 同模块下异步跨线程传递ThreadLocal对象
// */
//public class ContextTaskDecorator implements TaskDecorator {
//    @Override
//    public Runnable decorate(Runnable runnable) {
//        //获取父线程的LoginUser
//        LoginUser loginUser = UserContextHolder.currentLoginUser();
//        return () -> {
//            try {
//                // 将主线程的请求信息，设置到子线程中
//                UserContextHolder.set(loginUser);
//                // 执行子线程，这一步不要忘了
//                runnable.run();
//            } finally {
//                // 线程结束，清空这些信息，否则可能造成内存泄漏
//                UserContextHolder.shutdown();
//            }
//        };
//    }
//}
////用于解决父子线程间的数据共享
//executor.setTaskDecorator(new ContextTaskDecorator());