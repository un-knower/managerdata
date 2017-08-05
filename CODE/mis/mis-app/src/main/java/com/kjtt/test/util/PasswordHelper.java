package com.kjtt.test.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

	private static final String algorithmName = "md5";
	private static final int hashIterations = 2;

	public static void passwordEncryption(Map<String, String> params) {
		String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		String newPassword = new SimpleHash(algorithmName,
				params.get("password"), ByteSource.Util.bytes(params
						.get("username") + salt), hashIterations).toHex();
		params.put("password", newPassword);
		params.put("salt", salt);
	}
	
	public static String getPassword(String userName,String password,String salt) {
		String newPassword = new SimpleHash(algorithmName,
				password, ByteSource.Util.bytes(userName + salt), hashIterations).toHex();
		return  newPassword;
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();

		PasswordHelper.passwordEncryption(map);
//		System.out.println(map.get("password"));
//		System.out.println(PasswordHelper.getPassword("test", "21", map.get("salt").toString()));
		System.out.println(PasswordHelper.getPassword("mmm", "123", "4a9d029e39399dbb42008fb5f62c7d38"));
//		System.out.println(map.get("salt").toString());
	}
	
	public static Map<String,String> crateNewUser(Map<String, String> params) {
		String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		String newPassword = new SimpleHash(algorithmName,
				params.get("password"), ByteSource.Util.bytes(params
						.get("username") + salt), hashIterations).toHex();
		params.put("password", newPassword);
		params.put("salt", salt);
		return  params;
	}
}
