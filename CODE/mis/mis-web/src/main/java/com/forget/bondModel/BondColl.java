package com.forget.bondModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据集合
 * 
 * @author yangchao
 *
 */
public class BondColl implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 首字-对象
	 */
	private Map<String, BondModel> keybondColl = new HashMap<String, BondModel>();

	/**
	 * 处理过的数据总量（相当于一个全局的计时器）
	 */
	private double totalOffsetCount = 0;

	public Map<String, BondModel> getKeybondColl() {
		return keybondColl;
	}

	public void setKeybondColl(Map<String, BondModel> keybondColl) {
		this.keybondColl = keybondColl;
	}

	public double getTotalOffsetCount() {
		return totalOffsetCount;
	}

	public void setTotalOffsetCount(double totalOffsetCount) {
		this.totalOffsetCount = totalOffsetCount;
	}

}
