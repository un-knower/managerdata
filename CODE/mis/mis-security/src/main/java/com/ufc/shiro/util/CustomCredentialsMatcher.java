package com.ufc.shiro.util;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
/** 
 * 自定义 密码验证类 
 */  
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {  
	
     @Override  
        public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {  
            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
            SimpleAuthenticationInfo sinfo = (SimpleAuthenticationInfo) info;  
  
            Object tokenCredentials = encrypt(String.valueOf(token.getPassword()),sinfo.getCredentialsSalt());  
            Object accountCredentials = getCredentials(info);  
            
			 //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
			if (equals(tokenCredentials, accountCredentials)) {
				return true;
			}else{
				ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest(); 
				HttpServletRequest httpRequest = WebUtils.toHttp(request); 
				httpRequest.setAttribute("password_error", "密码错误");
				return false;
			}
        }  
  
        //将传进来密码加密方法  
        private String encrypt(String password,ByteSource salt) {  
        	  return new SimpleHash("md5", password, salt,2).toHex();
        }  
}  