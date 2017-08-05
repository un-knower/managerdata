package com.forget.itemModel;

import com.forget.utils.ReadObject2Deserialize;

/**
 * 反序列化词库
 * 
 * @author yangchao
 *
 */
public class ItemColls implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ItemColls itemColls;
	static {
		itemColls = new ItemColls();
		String appPath = System.getProperty("appPath");
		ItemColl itemColl = (ItemColl) ReadObject2Deserialize
				.deserialize(appPath);
		itemColls.setItemColl(itemColl);
	}
	private ItemColl itemColl;

	public ItemColl getItemColl() {
		return itemColl;
	}

	public void setItemColl(ItemColl itemColl) {
		this.itemColl = itemColl;
	}

	private ItemColls() {

	}

	public static ItemColls getItemColls() {
		return itemColls;

	}

}
