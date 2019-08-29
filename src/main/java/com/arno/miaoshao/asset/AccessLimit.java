package com.arno.miaoshao.asset;

import java.lang.annotation.*;

/**
 * @Author Arno
 * @ data 2019/8/29 14:52.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AccessLimit {

    int seconds();
    int maxCount();
    boolean require() default false;
}
