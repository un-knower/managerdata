package com.ufc.user.utils;

import java.util.ArrayList;

public class ParseRuleUtils {
    /**
     * 单词校验
     * @param paramString
     * @return boolean
     */
	public static boolean isSingleWord(String paramString) {
		return (paramString.indexOf("*") == -1)
				&& (paramString.indexOf("+") == -1)
				&& (paramString.indexOf("-") == -1);
	}

	public static String getSingleWord(String paramString) {
		if ((paramString.indexOf("*") == -1)
				&& (paramString.indexOf("+") == -1)
				&& (paramString.indexOf("-") == -1))
			return paramString;
		return null;
	}

	public static Rule doParse(String paramString) {
		Rule rule = new Rule();
		paramString = paramString.replaceAll("（", "(");
		paramString = paramString.replaceAll("）", ")");
		ArrayList<String> localArrayList1;
		if (isSingleWord(paramString)) {
			localArrayList1 = new ArrayList<String>();
			localArrayList1.add(paramString.trim().replaceAll(" ", ""));
			rule.setMust_list(localArrayList1);
		}
		String[] localObject1;
		int j;
		int k;
		if ((paramString.indexOf("*") > -1) && (paramString.indexOf("+") == -1)
				&& (paramString.indexOf("-") == -1)) {
			localArrayList1 = new ArrayList<String>();
			paramString = paramString.replace("(", "");
			paramString = paramString.replace(")", "");
			localObject1 = paramString.split("\\*");
			j = localObject1.length;
			for (k = 0; k < j; k++)
				localArrayList1.add(localObject1[k].trim().replaceAll(" ", ""));
			rule.setMust_list(localArrayList1);
		}
		if ((paramString.indexOf("*") == -1) && (paramString.indexOf("+") > -1)
				&& (paramString.indexOf("-") == -1)) {
			localArrayList1 = new ArrayList<String>();
			paramString = paramString.replace("(", "");
			paramString = paramString.replace(")", "");
			localObject1 = paramString.split("\\+");
			j = localObject1.length;
			for (k = 0; k < j; k++)
				localArrayList1.add(localObject1[k].trim());
			rule.setMaybe_list(localArrayList1);
		}
		String[] localObject2;
		String str2;
		int i6;
		if ((paramString.indexOf("*") == -1)
				&& (paramString.indexOf("-") > -1)
				&& ((paramString.indexOf("+") == -1) || (paramString
						.indexOf("-") < paramString.indexOf("+")))) {
			localArrayList1 = new ArrayList<String>();
			String str1 = paramString.substring(0, paramString.indexOf("-"));
			str1 = str1.replace("(", "");
			str1 = str1.replace(")", "");
			localObject2 = str1.split("\\*");

			k = localObject2.length;
			for (int n = 0; n < k; n++){
				localArrayList1.add(localObject2[n].trim().replaceAll(" ", ""));
			}
			rule.setMust_list(localArrayList1);
			ArrayList<String> localArrayList2 = new ArrayList<String>();
			str2 = paramString.substring(paramString.indexOf("-") + 1);
			str2 = str2.replace("(", "");
			str2 = str2.replace(")", "");
			localObject2 = str2.split("\\+");
			k = localObject2.length;
			for (i6 = 0; i6 < k; i6++)
				localArrayList2.add(localObject2[i6].trim());
			rule.setStop_list(localArrayList2);
		}
		if ((paramString.indexOf("*") > -1)
				&& (paramString.indexOf("-") > -1)
				&& ((paramString.indexOf("+") == -1) || (paramString
						.indexOf("+") > paramString.indexOf("-")))) {
			localArrayList1 = new ArrayList<String>();
			String str1 = paramString.substring(0, paramString.indexOf("-"));
			str1 = str1.replace("(", "");
			str1 = str1.replace(")", "");
			localObject2 = str1.split("\\*");
			k = localObject2.length;
			for (int i1 = 0; i1 < k; i1++){
				localArrayList1.add(localObject2[i1].trim().replaceAll(" ", ""));
			}
			rule.setMust_list(localArrayList1);
			ArrayList<String> localArrayList3 = new ArrayList<String>();
			str2 = paramString.substring(paramString.indexOf("-") + 1);
			str2 = str2.replace("(", "");
			str2 = str2.replace(")", "");
			localObject2 = str2.split("\\+");
			k = localObject2.length;
			for (i6 = 0; i6 < k; i6++)
				localArrayList3.add(localObject2[i6].trim());
			rule.setStop_list(localArrayList3);
		}
		int i4;
		int i9;
		//* 和  + 同时存在
		if ((paramString.indexOf("*") > -1) && (paramString.indexOf("+") > -1)
				&& (paramString.indexOf("-") == -1)) {
			//(军人+首长+战士)*收钱
			if(paramString.lastIndexOf("*") > paramString.lastIndexOf("+")){
				localArrayList1 = new ArrayList<String>();
				String str1 = paramString
						.substring(0, paramString.lastIndexOf("*"));
				str1 = str1.replace("(", "");
				str1 = str1.replace(")", "");
				localObject2 = str1.split("\\+");
				k = localObject2.length;
				for (int i2 = 0; i2 < k; i2++){
					localArrayList1.add(localObject2[i2].trim().replaceAll(" ", ""));
				}
				rule.setMaybe_list(localArrayList1);
				ArrayList<String> localArrayList4 = new ArrayList<String>();
				i4 = paramString.lastIndexOf("*");
				i6 = paramString.length();
				String str4 = paramString.substring(i4 + 1, i6);
				str4 = str4.replace("(", "");
				str4 = str4.replace(")", "");
				localObject2 = str4.split("\\*");
				k = localObject2.length;
				for (i9 = 0; i9 < k; i9++){
					localArrayList4.add(localObject2[i9].trim());
				}
				rule.setMust_list(localArrayList4);
			}else{
				localArrayList1 = new ArrayList<String>();
				String str1 = paramString
						.substring(0, paramString.lastIndexOf("*"));
				str1 = str1.replace("(", "");
				str1 = str1.replace(")", "");
				localObject2 = str1.split("\\*");
				k = localObject2.length;
				for (int i2 = 0; i2 < k; i2++){
					localArrayList1.add(localObject2[i2].trim().replaceAll(" ", ""));
				}
				rule.setMust_list(localArrayList1);
				ArrayList<String> localArrayList4 = new ArrayList<String>();
				i4 = paramString.lastIndexOf("*");
				i6 = paramString.length();
				String str4 = paramString.substring(i4 + 1, i6);
				str4 = str4.replace("(", "");
				str4 = str4.replace(")", "");
				localObject2 = str4.split("\\+");
				k = localObject2.length;
				for (i9 = 0; i9 < k; i9++){
					localArrayList4.add(localObject2[i9].trim());
				}
				rule.setMaybe_list(localArrayList4);
			}
		}
		int i3;
		if ((paramString.indexOf("*") == -1) && (paramString.indexOf("+") > -1)
				&& (paramString.indexOf("-") > -1)) {

			localArrayList1 = new ArrayList<String>();
			int i = paramString.indexOf("-");
			String str1 = paramString.substring(0, i);
			str1 = str1.replace("(", "");
			str1 = str1.replace(")", "");
			String[] arrayOfString = str1.split("\\+");
			i3 = arrayOfString.length;
			for (i4 = 0; i4 < i3; i4++){
				localArrayList1.add(arrayOfString[i4].trim());
			}
			rule.setMaybe_list(localArrayList1);
			ArrayList<String> localArrayList6 = new ArrayList<String>();
			String str3 = paramString.substring(paramString.indexOf("-") + 1);
			str3 = str3.replace("(", "");
			str3 = str3.replace(")", "");
			arrayOfString = str3.split("\\+");
			i3 = arrayOfString.length;
			for (int i8 = 0; i8 < i3; i8++){
				localArrayList6.add(arrayOfString[i8].trim());
			}
			rule.setStop_list(localArrayList6);
		}
		if ((paramString.indexOf("*") > -1) && (paramString.indexOf("-") > -1)
				&& (paramString.indexOf("+") > -1)
				&& (paramString.indexOf("+") < paramString.indexOf("-"))) {
			localArrayList1 = new ArrayList<String>();
			String str1 = paramString
					.substring(0, paramString.lastIndexOf("*"));
			str1 = str1.replace("(", "");
			str1 = str1.replace(")", "");
			localObject2 = str1.split("\\*");
			int m = localObject2.length;
			for (i3 = 0; i3 < m; i3++){
				localArrayList1.add(localObject2[i3].trim().replaceAll(" ", ""));
			}
			rule.setMust_list(localArrayList1);
			ArrayList<String> localArrayList5 = new ArrayList<String>();
			int i5 = paramString.lastIndexOf("*");
			int i7 = paramString.indexOf("-");
			String str5 = paramString.substring(i5 + 1, i7 - 1);
			str5 = str5.replace("(", "");
			str5 = str5.replace(")", "");
			localObject2 = str5.split("\\+");
			m = localObject2.length;
			for (i9 = 0; i9 < m; i9++){
				localArrayList5.add(localObject2[i9].trim());
			}
			rule.setMaybe_list(localArrayList5);
			ArrayList<String> localArrayList7 = new ArrayList<String>();
			String str6 = paramString.substring(paramString.indexOf("-") + 1);
			str6 = str6.replace("(", "");
			str6 = str6.replace(")", "");
			localObject2 = str6.split("\\+");
			m = localObject2.length;
			for (int i10 = 0; i10 < m; i10++){
				localArrayList7.add(localObject2[i10].trim());
			}
			rule.setStop_list(localArrayList7);
		}
		return rule;
	}

	public static String chkRule(String paramString) {
		String str;
		try {
			str = paramString;
			str = str.replaceAll("\\++", "+");
			str = str.replaceAll("\\-+", "-");
			str = str.replaceAll("\\*+", "*");
		} catch (Exception localException) {
			str = null;
		}
		return str;
	}
}