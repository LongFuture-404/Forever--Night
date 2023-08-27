package com.example.webmoduleui.config;

import com.example.webmoduleui.Interceptor.WebInterceptor;
import com.example.webmoduleui.constants.PropertyConstant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Resource(name = "PropertyConstant")
    private PropertyConstant propertyConstant;
    @Resource(name = "WebInterceptor")
    private WebInterceptor webInterceptor;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler参数是文件浏览路径，addResourceLocations参数是文件存放路径
        registry.addResourceHandler(propertyConstant.getViewPath() + "**")
                .addResourceLocations("file:" + propertyConstant.getUploadPath());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/Guest/**");

    }
}
