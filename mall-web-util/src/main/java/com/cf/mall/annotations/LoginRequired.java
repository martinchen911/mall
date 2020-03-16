package com.cf.mall.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法认证注解
 * @author chen
 *
 * 使用范围：方法
 * 生效范围：运行时
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

    boolean loginSuccess() default true;

}
