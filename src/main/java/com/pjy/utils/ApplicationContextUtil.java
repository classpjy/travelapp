package com.pjy.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//用来获取springboot工厂
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    //此方法会在应用程序创建好工厂后传递给该方法
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //提供在工厂中创建号的bean
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
