package com.allen.commons.event.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
		System.out.println(method.getName());
		
		//执行被拦截的方法,切记,如果不返回次对象,则被拦截的方法不会被执行
		Object object = invocation.proceed();
		return object;
	}

	

}
