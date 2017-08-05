package com.ufc.shiro.service;

import org.pcloud.spring.common.ApplicationContextHelper;
import org.pcloud.spring.common.Springed;
import org.pcloud.spring.common.StartedServer;

import com.ufc.shiro.realm.CustomRealm;
import com.ufc.shiro.util.SecurityCommon;

@Springed(StartedServer.class)
public class SecurityFilter implements StartedServer {
	@Override
	public void start() throws Exception {
		((CustomRealm) ApplicationContextHelper.getBean("customRealm"))
				.setPermissionService((PermissionService) ApplicationContextHelper
						.getBean(SecurityCommon.PERMISSION_SERVICE_NAME));
	}

}
