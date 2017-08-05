package com.forget.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test3 {
	public static void main(String args[]) {
		try {
			captureHtml();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void captureHtml() throws Exception {  
	    String strURL = "http://www.sse.com.cn/disclosure/listedinfo/announcement/c/600787_20020528_1.pdf";  
	    URL url = new URL(strURL);  
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();  
	    InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");  
	    BufferedReader bufReader = new BufferedReader(input);  
	    String line = "";  
	    StringBuilder contentBuf = new StringBuilder();  
	    while ((line = bufReader.readLine()) != null) {  
	        contentBuf.append(line);  
	    }  
	    String buf = contentBuf.toString();  
	    int beginIx = buf.indexOf("查询结果[");  
	    int endIx = buf.indexOf("上面四项依次显示的是");  
	    //String result = buf.substring(beginIx, endIx);  
	    System.out.println("captureHtml()的结果：\n" + buf);  
	}  

}
