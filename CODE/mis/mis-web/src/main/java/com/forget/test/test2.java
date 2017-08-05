package com.forget.test;

public class test2 {
	public static void main(String args[]) {
//		String regex = "^[a-zA-Z0-9.]+$";//其他需要，直接修改正则表达式就好
//		System.out.println( "中7.a".matches(regex));
//		StringBuilder sb = new StringBuilder();
//		sb.append("aaaaaa");
//		System.out.println("1: "+sb.toString());
//		sb.setLength(0);
//		System.out.println("2: "+sb.toString());
		
		String str = "	负责　　拟定	负责";
		System.out.println(str.replaceAll("	", "").replaceAll("　", ""));
	}

}
