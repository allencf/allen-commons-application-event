package com.allen.commons.event.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allen.commons.event.annotation.ClassEvent;
import com.allen.commons.event.annotation.MethodEvent;


/**
 * 基于spring AOP的事件触发器
 * All rights Reserved, Designed By HQYG
 * Copyright:   Copyright(C) 2016
 * Company:     allen
 * author:      allen
 * Createdate:  2016年12月2日上午11:16:50
 */
public class AopMethodEventDispatcher implements MethodInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(AopMethodEventDispatcher.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		logger.info("execute AopMethodEventDispathcer invoke method -------------------------");
		Method method = invocation.getMethod();
		
	    MethodEvent event = method.getAnnotation(MethodEvent.class);
	    ClassEvent classEvent = method.getAnnotation(ClassEvent.class);
	    if(event != null){
	    	System.out.println("event is MethodEvent");
	    }
	    if(classEvent != null){
	    	System.out.println("event is ClassEvent");
	    }
		System.out.println(method.getName());
		System.out.println(event.toString());
		
		//执行被拦截的方法,切记,如果不返回次对象,则被拦截的方法不会被执行
		Object object = invocation.proceed();
		return object;
	}

	

}
