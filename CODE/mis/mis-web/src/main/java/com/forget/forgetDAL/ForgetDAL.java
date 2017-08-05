package com.forget.forgetDAL;

/*
 * 牛顿冷却
 */
public class ForgetDAL {

	/*
	 * return 遗忘剩余量 offsetCount 时间间隔（）
	 */
	public static double rememberValue(double offsetCount) {

		double rememberValue = -Math.log(0.254) / (7 * 60 * 60 * 24 * 6);

		return Math.exp(-rememberValue * (offsetCount));
	}
}
