package com.ufc.shiro.util;

import java.io.ObjectStreamException;
import java.io.Serializable;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.shiro.authc.ExcessiveAttemptsException;

public class AttackValidate  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Ehcache passwordRetryCache;
	
	   //内部类  
    private static class MySingletonHandler{  
        private static AttackValidate instance = new AttackValidate();  
    }   
      
    private AttackValidate(){
    		CacheManager cacheManager = new CacheManager(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }  
    
    public static AttackValidate getInstance() {   
        return MySingletonHandler.instance;  
    }  
    
    //该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉  
    protected Object readResolve() throws ObjectStreamException {  
        return MySingletonHandler.instance;   
    }
    
    public boolean validate(String username) throws Exception {
		
		Element element = passwordRetryCache.get(username);
		if (element == null) {
			element = new Element(username, 0);
			passwordRetryCache.put(element);
		}
		
		int value = Integer.parseInt(element.getValue().toString());

		if (value >= 5) {
			throw new ExcessiveAttemptsException();
		}
	
		return true;
	}

	public void addErrorCount(String username) {
		Element element = null;
		int value = Integer.parseInt( passwordRetryCache.get(username).getValue().toString());
		if(value == 0){
			element = new Element(username, 1);
		}else{
			element = new Element(username, ++value);
		}
		passwordRetryCache.put(element);
	}
	
	public void remove(String username) {
		passwordRetryCache.remove(username);
	}
}
