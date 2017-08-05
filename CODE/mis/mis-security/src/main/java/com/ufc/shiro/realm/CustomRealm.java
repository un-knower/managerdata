package com.ufc.shiro.realm;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.ufc.shiro.service.PermissionService;

public class CustomRealm extends AuthorizingRealm {
	
	private PermissionService permissionService;

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/*
	 * 为当前登录的Subject授予权限 本例中该方法的调用时机为需授权资源被访问时
	 * 并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// 获取当前登录的用户名
		String username = (String) super.getAvailablePrincipal(principals);
		// 根据用户名从数据库中获取用户信息
		List<String> permList;
		try {
			permList = getPermissionService().queryAllPermissionInfo(username);
		} catch (Exception e) {
			throw new AuthorizationException("获取用户<"+username+">的资源权限出错！系统提示："+e.getMessage());
		}

		if (null != permList) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermissions(permList);
			return info;
		} else {
			throw new AuthorizationException();

		}
	}

	/*
	 * 本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authToken) throws AuthenticationException {
	 
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		String username = (String) token.getPrincipal();
		Map<String, Object> userMap;
		try {
			userMap = permissionService.queryUserInfo(username);
		} catch (Exception e) {
			throw new AuthenticationException("查询用户"+username+"个人信息出错！系统提示："+e.getMessage());
		}
		
		if (userMap == null) {
			throw new UnknownAccountException();// 没找到帐号
		}
		
		if (userMap.get("state").toString().equals("0")) {
			throw new LockedAccountException(); // 帐号锁定
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				token.getUsername(), // 用户名
				userMap.get("password").toString(), // 密码 要通过查询获得
				ByteSource.Util.bytes(token.getUsername() + userMap.get("salt").toString()),// salt=username+salt
				getName() // realm name
		);
		return authenticationInfo;
	}
}
