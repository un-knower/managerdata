package com.ufc.shiro.service;

import java.util.List;
import java.util.Map;

public interface PermissionService {
	public List<String> queryAllPermissionInfo(String username) throws Exception ;
	public Map<String,Object> queryUserInfo(String username) throws Exception ;
}
