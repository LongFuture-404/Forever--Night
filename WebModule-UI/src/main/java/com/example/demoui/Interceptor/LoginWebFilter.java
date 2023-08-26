//package com.example.demoui.Interceptor;
//
//import com.example.demoui.redis.RedisService;
//
//import jakarta.annotation.Resource;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import java.io.IOException;
//
//import static com.example.demoserve.dao.TokenUtil.*;
//
//@Configuration
//@WebFilter("/*")
//@CrossOrigin
//public class LoginWebFilter implements Filter {
//    @Resource(name = "redisService")
//    RedisService redisService;
//    /**
//     * 在filter中赋值后在此获取
//     */
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req=(HttpServletRequest) servletRequest;
//        HttpServletResponse response=(HttpServletResponse) servletResponse;
//        System.out.println("request: "+req.getRequestURI());
//        String [] sr=new String[]{"/Guest",".css",".js",".png",".jpg"};
//        boolean flag=false;
//        for(String url_suffix:sr){
//            if (req.getRequestURI().contains(url_suffix)){
//                flag=true;
//                break;
//            }
//        }
//        if(flag){
//            filterChain.doFilter(servletRequest,servletResponse);//放行
//        }else{
//            String token;
//            String Refresh;
//            System.out.println(req.getHeader("Authorization"));
//            if(req.getHeader("Authorization")!=null) {
//                token=req.getHeader("Authorization");
//                //token未过期则刷新token
//                if(req.getHeader("Refresh")!=null){
//                    Refresh=req.getHeader("Refresh");
//                    if(Refresh.equals("true")){
//                        System.out.println("刷新token");
//                        response.setHeader("token",refresh(token));
//                        filterChain.doFilter(servletRequest, servletResponse);
//                    }
//                }
//                //token过期则返回登录页面
//                if (!verify(token)) {
//                    System.err.println("token错误,返回登录页面");
//                    response.setHeader("allow", "false");
////                    response.sendRedirect("http://localhost:8080/");
//                }
//            }
//            //token不存在则返回登录页面
//            else {
//                System.err.println("返回登录页面");
//                response.setHeader("allow", "false");
////                response.sendRedirect("http://localhost:8080/");
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
