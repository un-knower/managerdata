package com.forget.bondModel;

import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemModel;

/**
 * 键-对象（头-尾-对象）
 * 1、头-单字-数据模型
 * 2、尾-单字-集合
 * 
 * @author yangchao
 *
 */
public class BondModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemModel headItemModel = new ItemModel();// 头-单字-数据模型
	private ItemColl tailItemColl = new ItemColl();// 尾-单字-集合

	public ItemModel getHeadItemModel() {
		return headItemModel;
	}

	public void setHeadItemModel(ItemModel headItemModel) {
		this.headItemModel = headItemModel;
	}

	public ItemColl getTailItemColl() {
		return tailItemColl;
	}

	public void setTailItemColl(ItemColl tailItemColl) {
		this.tailItemColl = tailItemColl;
	}

}
