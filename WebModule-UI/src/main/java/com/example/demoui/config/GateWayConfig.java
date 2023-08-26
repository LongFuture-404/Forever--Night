package com.example.demoui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GateWayConfig {
    /*
        springmvc：
            在Controller或者它的接口上使用//@CrossOrigin注解
        gateway：
            配置CorsWebFilter设置允许跨域
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");//哪些请求方式允许跨域访问
        config.addAllowedHeader("*");//允许携带哪些请求头跨域访问
        config.addAllowedOrigin("*");//允许哪些origin跨域访问
        config.setAllowCredentials(true);//是否允许携带cookie跨域访问
        configSource.registerCorsConfiguration("/**", config);
//        configSource.registerCorsConfiguration("/**/sms/**",config2);
        return new CorsWebFilter(configSource);
    }
}
