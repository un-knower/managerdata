package org.pcloud.common.log;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class LogUtil {
  
    private  Logger _log = LoggerFactory.getLogger(LogUtil.class);  
  
    public  void debug(String info) {  
        _log.debug(info);  
    }  
  
    public  void info(String info) {  
        _log.info(info);  
    }  
  
    public  void warn(String info) {  
        _log.warn(info);  
    }  
  
    public  void error(String info) {  
        _log.error(info);  
    } 
}
