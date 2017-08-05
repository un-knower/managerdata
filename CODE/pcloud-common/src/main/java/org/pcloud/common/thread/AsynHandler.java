package org.pcloud.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynHandler {

	/***
	 *  异步启动一个线程执行某个对象的某个方法
	 * @param obj
	 * 			被执行的类的对象
	 * @param method
	 * 			执行的方法名称
	 * @param args
	 * 			方法的参数
	 * @throws Exception
	 */
	public static synchronized void submit(Object obj, String method, Object[] args) throws Exception {
		  ExecutorService service = Executors.newFixedThreadPool(1);  
          try {  
              service.submit(new HandlerProducer(obj, method, args));  
          } catch (Exception e) { 
        	  	throw new Exception("异步执行java类："+obj.toString()+" 的方法<"+method+">出错!");
          }finally{  
              service.shutdown();  
          }  
	}
}
