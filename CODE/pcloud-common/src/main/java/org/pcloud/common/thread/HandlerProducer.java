package org.pcloud.common.thread;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.MethodUtils;

public class HandlerProducer implements Runnable {  
	  
    private 	Object obj;  
    private String method;
    private Object[] args;
    
  
    public HandlerProducer(Object obj, String method, Object[] args) {  
        this.obj = obj;  
        this.method = method;  
        this.args = args;  
    }  
  
    @Override  
    public void run() {  
	    	try {
	    		if(args == null){
				MethodUtils.invokeMethod(obj, method);
					
	    		}else{
	    			MethodUtils.invokeMethod(obj, method, args); 
	    		}
	    	} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				 System.out.println("反射执行类对象的指的方法出错!");
		} 
    }  
  
}  