package com.allen.commons.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 方法事件配置
 * Company:     HQYG.
 * author:      allen
 * Createdate:  2016年12月2日上午11:50:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodEvent {

}
