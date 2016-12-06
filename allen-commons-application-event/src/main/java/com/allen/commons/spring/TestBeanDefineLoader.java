package com.allen.commons.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.allen.common.baselib.collection.Lists;
import com.allen.common.baselib.lang.Strings;
import com.allen.commons.event.annotation.ClassEvent;

public class TestBeanDefineLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(TestBeanDefineLoader.class);
	
	private ApplicationContext applicationContext;
	
	private String packagePath = "";
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//避免该方法加载2次
		if(event.getApplicationContext().getParent() !=null)
			return;
		
		logger.info("start execute  TestBeanDefineLoader onApplicationEvent method ……");
		applicationContext = event.getApplicationContext();
		try {
			List<Class> classes = getClasses(packagePath);
			for (Class c : classes) {
				Annotation[] a = c.getAnnotations();
				for (Annotation annotation : a) {
					if(annotation instanceof ClassEvent){
						Object obj = applicationContext.getBean(c);
						System.out.println(obj.toString());
					}
				}
				Field[] fields = c.getDeclaredFields();
				for (Field field : fields) {
					Annotation[] annotations = field.getAnnotations();
					for (Annotation an : annotations) {
						if(an instanceof ClassEvent){
							Object obj = applicationContext.getBean(c);
							System.out.println(obj.toString());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("end execute TestBeanDefineLoader onApplicationEvent method ……");
	}
	
	
	/**
	 * 获取类集合
	 * @param packName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> getClasses(String packName) throws Exception{
		if(Strings.isBlank(packName))
			return null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packName.replace(".", "/");
		logger.info("reflect path:" + path);
		Enumeration enumeration = classLoader.getResources(path);
		List<Class> classList = Lists.newList();
		List<File> fileList = Lists.newList();
		URL urlResource;
		while(enumeration.hasMoreElements()){
			urlResource = (URL) enumeration.nextElement();
			String type = urlResource.getProtocol();
			logger.info("urlResource type:" + type);
			if("file".equals(type)){
				fileList.add(new File(urlResource.getFile()));
				//classList.addAll(findClasses(directory, packName));
			}else if("jar".equals(type)){
				//String jarPath = urlResource.getPath();
			}
		}
		
		for (File file : fileList) {
			classList.addAll(findClasses(file, packName));
		}
		return classList;
	}
	
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory,String packageName){
		List<Class> classList = Lists.newList();
		if(!directory.isDirectory())
			return classList;
		
		File[] files = directory.listFiles();
		for (File file : files) {
			if(file.isDirectory()){
				if(file.getName().contains(".")){
					
				}
				logger.info(packageName + "." + file.getName());
				classList.addAll(findClasses(file, packageName + "." + file.getName()));
			}
			else if(file.getName().endsWith(".class")){
				String className = packageName + "." + file.getName().substring(0,file.getName().length()-6);
				try {
					Class c = Class.forName(className);
					classList.add(c);
				} catch (ClassNotFoundException e) {
					logger.error("class.forName:" + className + "ClassNotFoundException" , e);
				}
			}
		}
		return classList;
	}
	

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}


	public static void main(String[] args) throws Exception{
		String packageName="F:/allen/commons-baselibs";
		File file = new File(packageName);
		System.out.println(file.isDirectory());
		System.out.println(file.getName());
		
		//String packName = "com.allen.commons.spring";
		//getClasses(packName);
	}

}
