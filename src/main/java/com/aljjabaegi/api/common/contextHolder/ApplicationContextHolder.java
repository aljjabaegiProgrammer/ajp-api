package com.aljjabaegi.api.common.contextHolder;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Spring bean 에 등록되지 않은 객체에서 Runtime 시점에 Spring bean 에 등록된 객체 조회 시 활용<br />
 * UserService userService = ApplicationContextHolder.getContext().getBean(UserService.class);<br />
 *
 * @author GEONLEE
 * @since 2024-04-02
 **/
@Configuration
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}