package com.forget.utils;

public class Hanzi2Unicode {

	 public static String toUNICODE(String str)
	    {
		 str = (str == null ? "" : str); 
		 String tmp; 
		 StringBuffer sb = new StringBuffer(1000); 
		 char c; 
		 int i, j; 
		 sb.setLength(0); 
		 for (i = 0; i < str.length(); i++) 
		 { 
		 c = str.charAt(i); 
		 sb.append("\\u"); 
		 j = (c >>>8); //取出高8位 
		 tmp = Integer.toHexString(j); 
		 if (tmp.length() == 1) 
		 sb.append("0"); 
		 sb.append(tmp); 
		 j = (c & 0xFF); //取出低8位 
		 tmp = Integer.toHexString(j); 
		 if (tmp.length() == 1) 
		 sb.append("0"); 
		 sb.append(tmp); 
	    }
		return (new String(sb)); 
    }
	 
	 public static String toHanzi(String dataStr){
		 int start = 0;   
	        int end = 0;   
	       final StringBuffer buffer = new StringBuffer();   
	        while (start > -1) {   
	           end = dataStr.indexOf("\\u", start + 2);   
	            String charStr = "";   
	            if (end == -1) {   
	                charStr = dataStr.substring(start + 2, dataStr.length());   
	           } else {   
	               charStr = dataStr.substring(start + 2, end);   
	            }   
	            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。   
	          buffer.append(new Character(letter).toString());   
	          start = end;   
	        }  
	        return buffer.toString();
	 }
}
