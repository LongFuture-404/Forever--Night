package com.example.demoui.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();

        Properties props = new Properties();
        //设置边框
        props.setProperty(Constants.KAPTCHA_BORDER, "no");
        //设置图片的宽度
        props.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "120");
        //设置图片的高度
        props.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "60");
        //设置图片验证码要使用的字符串
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        //设置验证码要显示的字符个数
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");

        Config config = new Config(props);

        kaptcha.setConfig(config);

        return kaptcha;
    }
}
