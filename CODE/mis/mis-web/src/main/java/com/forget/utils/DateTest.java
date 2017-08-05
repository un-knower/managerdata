package com.forget.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 程序是否逾期
 * 
 * @author yangchao
 *
 */
public class DateTest {

	/**
	 * 判断程序是否逾期，逾期后不可用，目前可用时间到2100-05-02
	 * 
	 * @return
	 */
	public static boolean compare_date() {

		String date1 = "2100-05-02";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date2 = df.format(new Date());
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() >= dt2.getTime()) {
				return true;
			} else if (dt1.getTime() < dt2.getTime()) {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return false;
	}

}
