package org.pcloud.spring.common;
  
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationContextHelper implements ApplicationContextAware , ApplicationListener<ContextRefreshedEvent>{
    private static ApplicationContext appCtx;    
    private static ContextRefreshedEvent event;   
   	private static ConfigurableApplicationContext configApplicationContext = null;  
    /**  
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。  
     * @param applicationContext ApplicationContext 对象.  
     */    
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {    
        appCtx = applicationContext;    
        configApplicationContext = (ConfigurableApplicationContext)applicationContext; 
    }  
      
    /** 
     * 获取ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext(){  
        return appCtx;  
    }  
      
    /**  
     * 这是一个便利的方法，帮助我们快速得到一个BEAN  
     * @param beanName bean的名字  
     * @return 返回一个bean对象  
     */    
    public static Object getBean( String beanName ) {    
        try{
			return appCtx.getBean( beanName ); 
		}catch(Exception e){
			try {
				return  event.getApplicationContext().getBean(beanName);
			} catch (BeansException e1) {
				return configApplicationContext.getBean( beanName ); 
			}
		}
    } 

	public void onApplicationEvent(ContextRefreshedEvent event) {
		setEvent(event);
 
        Map<String, Object> serviceBeanMap = appCtx.getBeansWithAnnotation(Springed.class);
	     if (serviceBeanMap != null && serviceBeanMap.size() > 0) {
	         for (Object serviceBean : serviceBeanMap.values()) {
	        		try {
						MethodUtils.invokeMethod(serviceBean, "start");
					} catch (Exception e) {
						e.printStackTrace();
					}
	         }
	     }
	}

	public static void setEvent(ContextRefreshedEvent event) {
		ApplicationContextHelper.event = event;
	}
	
	 /** 
     * 向spring的beanFactory动态地装载bean 
     * 	"classpath:spring"+File.separator+"spring-dynamic-datasource.xml"
     * @param configLocationString 要装载的bean所在的xml配置文件位置。 
     */  
    public static void loadBean(String configLocationString){
    		ConfigurableListableBeanFactory beanFactory = configApplicationContext.getBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)beanFactory);  
        beanDefinitionReader.setResourceLoader(getApplicationContext());  
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));  
        try {  
            String[] configLocations = new String[]{configLocationString};  
            for(int i=0;i<configLocations.length;i++)  
                beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocations[i]));  
        } catch (BeansException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
	
}   