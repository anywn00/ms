package com.arno.miaoshao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author arno
 * @date 2019-08-13 22:18
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    UserArgumentResolver userArgumentResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
