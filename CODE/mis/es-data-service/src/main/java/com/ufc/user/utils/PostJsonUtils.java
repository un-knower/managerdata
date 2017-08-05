package com.ufc.user.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class PostJsonUtils {
	
	public static String getPostJson(HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
			 String tempLine = br.readLine();
	         String crlf=System.getProperty("line.separator");
	         while (tempLine != null){
	        	 sb.append(tempLine);
	        	 sb.append(crlf);
	             tempLine = br.readLine();
	         }
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
		return sb.toString();
	}
	
	
}
