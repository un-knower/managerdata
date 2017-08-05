package com.forget.itemModel;

/**
 * 单字 数据模型
 * 
 * @author yangchao
 *
 */
public class ItemModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyItem; // 单字
	private Double validCount; // 单字成熟度
	private Double lastUpdateTime; // 更新时的偏移量（相当于记录本次入库的时间）

	public String getKeyItem() {
		return keyItem;
	}

	public void setKeyItem(String keyItem) {
		this.keyItem = keyItem;
	}

	public Double getValidCount() {
		return validCount;
	}

	public void setValidCount(Double validCount) {
		this.validCount = validCount;
	}

	public Double getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Double lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
