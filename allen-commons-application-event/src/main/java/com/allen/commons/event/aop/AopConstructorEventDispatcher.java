package com.allen.commons.event.aop;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.ConstructorInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopConstructorEventDispatcher implements ConstructorInterceptor{
	
	private final static Logger logger = LoggerFactory.getLogger(AopConstructorEventDispatcher.class);

	@Override
	public Object construct(ConstructorInvocation invocation) throws Throwable {
	 	logger.info("execute AopConstructorEventDispatcher construct method --------------------------");
		Object[] obj = invocation.getArguments();
	 	for (Object object : obj) {
			System.out.println(object.toString());
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
