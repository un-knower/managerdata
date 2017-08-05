package com.forget.keywords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.forget.bondModel.BondColl;
import com.forget.bondModel.BondModel;
import com.forget.dataSort.SortByValidValue;
import com.forget.forgetDAL.ForgetDAL;
import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemColls;
import com.forget.itemModel.ItemModel;
import com.forget.utils.DateTest;
import com.forget.utils.SaveObject2Serialize;

/*
 * 词库生成
 */
public class KeywordColl {
	final static Logger logger = LoggerFactory.getLogger(KeywordColl.class);

	public static Double rememberValue = 1.0/(1.0-ForgetDAL.rememberValue(7));
	
	/**
	 * 反序列化词库
	 * 
	 * @param serFilePath
	 * @return
	 */
	public static JSONObject toInitKeywordColl(String serFilePath){
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if(compare_date){// 程序未逾期
			if(serFilePath != null && !serFilePath.trim().equals("")){
				System.setProperty("appPath", serFilePath);
				ItemColls itemColls = ItemColls.getItemColls();// 反序列化词库
				ItemColl itemColl = itemColls.getItemColl();
				if(itemColl != null){
					json.put("state", "success");
					json.put("msg", "");
				}else{
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject saveKeywordColl(String serFilePath,String keywordCollFilePath){
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if(compare_date){// 程序未逾期
			if(serFilePath != null && !serFilePath.trim().equals("")){
				ItemColls itemColls = ItemColls.getItemColls();
				ItemColl itemColl = itemColls.getItemColl();
				SaveObject2Serialize.serialize(serFilePath, itemColl);
				if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
					Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
					Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
					WriteDate(sortMapByValue,keywordCollFilePath);
				}
				json.put("state", "success");
				json.put("msg", "ok");
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	/**
	 * 读取文本，生成序列化词库和直观词库到指定路径
	 * 
	 * @param srcFilePath 待处理文本文件路径（必填,格式UTF-8）;
	 * @param keywordCollFilePath 直观词库文件路径（非必填）;
	 * @param serFilePath 序列化词库路径（必填）;
	 * @return
	 */
	public static JSONObject toKeywordColl(String srcFilePath,
			String keywordCollFilePath, String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if (compare_date) {// 程序未逾期
			if (srcFilePath != null && !srcFilePath.trim().equals("")
					&& serFilePath != null && !serFilePath.trim().equals("")) {
				String str = srcFilePath;
				try {
					BondColl bondColl = new BondColl();// 数据集合
					List<String> list = readTxtFile(str, bondColl);// 读取文件内容，相邻字处理
					ItemColl itemColl = new ItemColl();// 单字集合
					for (String string : list) {
						txtContext2BondColl(string, bondColl, itemColl, true);// 句2词库
					}
					itemColl = clearItemColl(itemColl);// 去掉低于阈值的词（ItemModel）
					SaveObject2Serialize.serialize(serFilePath, itemColl);// itemColl序列化到serFilePath
					if (keywordCollFilePath != null
							&& !keywordCollFilePath.trim().equals("")) {// keywordCollFilePath不为空
						Map<String, ItemModel> keyItemColl = itemColl
								.getKeyItemColl();// 获取数据集合
						Map<String, ItemModel> sortMapByValue = SortByValidValue
								.sortMapByValue(keyItemColl);// 数据排序
						WriteDate(sortMapByValue, keywordCollFilePath);// 将直观词库写入到keywordCollFilePath
					}
					json.put("state", "success");
					json.put("msg", "ok");
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			} else {// srcFilePath或serFilePath为空
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		} else {// 程序已逾期
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		return json;
	}
	
	public static JSONObject toUpdKeywordColl(String srcFilePath,String keywordCollFilePath,String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if(compare_date){// 程序未逾期
			if(srcFilePath != null && !srcFilePath.trim().equals("")){
				String str = srcFilePath;
				try {
					BondColl bondColl = new BondColl();
					List<String> list = readTxtFile(str,bondColl);
					ItemColls itemColls = ItemColls.getItemColls();
					ItemColl itemColl = itemColls.getItemColl();
					for (String string : list) {
						txtContext2BondColl(string,bondColl,itemColl,true);
					}
					itemColl = clearItemColl(itemColl);
					if(serFilePath != null && !serFilePath.trim().equals("")){
						SaveObject2Serialize.serialize(serFilePath, itemColl);
					}
					if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
						Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
						Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
						WriteDate(sortMapByValue,keywordCollFilePath);
					}
					itemColls.setItemColl(itemColl);
					json.put("state", "success");
					json.put("msg", "ok");
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject toUpdKeywordCollByStr(String content,String keywordCollFilePath,String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if(compare_date){// 程序未逾期
			if(content != null && !content.trim().equals("")){
				try {
					BondColl bondColl = new BondColl();
					txtContext2Bond(content,bondColl);
					ItemColls itemColls = ItemColls.getItemColls();
					ItemColl itemColl = itemColls.getItemColl();
					txtContext2BondColl(content,bondColl,itemColl,true);
					itemColl = clearItemColl(itemColl);
					if(serFilePath != null && !serFilePath.trim().equals("")){
						SaveObject2Serialize.serialize(serFilePath, itemColl);
					}
					if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
						Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
						Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
						WriteDate(sortMapByValue,keywordCollFilePath);
					}
					itemColls.setItemColl(itemColl);
					json.put("state", "success");
					json.put("msg", "ok");
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject toDelKeywordColl(String serFilePath,String keywordCollFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if(compare_date){// 程序未逾期
			ItemColls itemColls = ItemColls.getItemColls();
			ItemColl itemColl = itemColls.getItemColl();
			if(serFilePath != null && !serFilePath.trim().equals("")){
				SaveObject2Serialize.serialize(serFilePath, itemColl);
			}
			if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
				Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
				Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
				WriteDate(sortMapByValue,keywordCollFilePath);
			}
			itemColl = new ItemColl();
			itemColls.setItemColl(itemColl);
			json.put("state", "success");
			json.put("msg", "ok");
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		return json;
	}
	
	public static JSONObject getKeywordColl(String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();// 程序是否逾期
		if(compare_date){// 程序未逾期
			if(serFilePath != null && !serFilePath.trim().equals("")){
				try {
					System.setProperty("appPath", serFilePath);
					ItemColls itemColls = ItemColls.getItemColls();
					ItemColl itemColl = itemColls.getItemColl();
					Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
					Map<String,Double> colls = new HashMap<String,Double>();
					if(keyItemColl != null && !keyItemColl.isEmpty()){
						ItemModel value = null;
						for(Map.Entry<String, ItemModel> entry : keyItemColl.entrySet()){
							value = entry.getValue();
							if(value != null){
								colls.put(entry.getKey(),value.getValidCount());
							}
						}
					}
					json.put("state", "success");
					json.put("msg", "ok");
					json.put("keywordColl", colls);
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
					json.put("keywordColl", null);
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
				json.put("keywordColl", null);
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
			json.put("keywordColl", null);
		}
		
		return json;
	}
	
	/**
	 * 尾-单字-集合
	 * 处理
	 * 
	 * @param itemTail 尾-单字
	 * @param itemColl 尾-单字-集合
	 * @return
	 */
	public static ItemColl updateItemColl(String itemTail, ItemColl itemColl) {
		Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();// 尾
		// 第一次出现：尾-单字
		if (!keyItemColl.containsKey(itemTail)) {
			ItemModel itemModel = new ItemModel();// 创建：尾-单字-数据模型
			itemModel.setKeyItem(itemTail);// 单字
			itemModel.setValidCount(Double.valueOf(1));// 单字成熟度
			itemModel.setLastUpdateTime(itemColl.getTotalOffsetCount());// 更新时的偏移量（记录本次入库时处理过的数据总量）
			keyItemColl.put(itemTail, itemModel);
		} else {// 已出现：尾-单字
			ItemModel itemModel = keyItemColl.get(itemTail);// 获取：尾-单字-数据模型
			itemModel.setValidCount(itemModel.getValidCount()
					* ForgetDAL.rememberValue(itemColl.getTotalOffsetCount()
							- itemModel.getLastUpdateTime()) + 1);// 单字成熟度
			itemModel.setLastUpdateTime(itemColl.getTotalOffsetCount());// 更新时的偏移量（记录本次入库时处理过的数据总量）
		}

		// 处理过的数据总量达到了阈值
		if (itemColl.getTotalOffsetCount() >= Double.MAX_VALUE - 10) {
			itemColl = clearItemColl(itemColl);// 清除低于阀值的词
		} else {// 处理过的数据总量未达到阈值
			itemColl.setTotalOffsetCount(itemColl.getTotalOffsetCount() + 1);// 更新处理过的数据总量（相当于一个全局的计时器）
		}
		return itemColl;
	}
	
	/**
	 * 更新数据集合
	 * 
	 * @param itemHead 头-单字
	 * @param itemTail 尾-单字
	 * @param bondColl 数据集合
	 */
	public static void updateBondColl(String itemHead, String itemTail, BondColl bondColl) {
		Map<String, BondModel> keyBondColl = bondColl.getKeybondColl();// key:头-单字；value:键-对象
		// 首字第一次出现
		if (!keyBondColl.containsKey(itemHead)) {
			BondModel bondModel = new BondModel();// 创建：键-对象
			ItemModel headItemModel = bondModel.getHeadItemModel();// 获取：头-单字-数据模型
			headItemModel.setKeyItem(itemHead);// 单字
			headItemModel.setValidCount(Double.valueOf(0));// 单字成熟度
			headItemModel.setLastUpdateTime(bondColl.getTotalOffsetCount());// 更新时的偏移量（记录本次入库时处理过的数据总量）
			keyBondColl.put(itemHead, bondModel);
		}
		// 首字已出现
		BondModel bondModel = keyBondColl.get(itemHead);// 键-对象
		ItemModel headItemModel = bondModel.getHeadItemModel();// 头-单字-数据模型
		headItemModel.setValidCount(1
				+ headItemModel.getValidCount()
				* ForgetDAL.rememberValue(bondColl.getTotalOffsetCount()
						- headItemModel.getLastUpdateTime()));// 单字成熟度
		headItemModel.setLastUpdateTime(bondColl.getTotalOffsetCount());// 更新时的偏移量（记录本次入库时处理过的数据总量）

		ItemColl tailItemColl = bondModel.getTailItemColl();// 尾-单字-集合

		tailItemColl.setTotalOffsetCount(bondColl.getTotalOffsetCount());// 处理过的数据总量（相当于一个全局的计时器）

		updateItemColl(itemTail, tailItemColl);// 尾-单字-集合 处理

		bondColl.setTotalOffsetCount(bondColl.getTotalOffsetCount() + 1);// 更新处理过的数据总量（相当于一个全局的计时器）

		// 处理过的数据总量达到了阈值
		if (bondColl.getTotalOffsetCount() >= Double.MAX_VALUE - 10) {
			bondColl = clearBondColl(bondColl);// 清除低于阀值的词
			bondColl.setTotalOffsetCount(0);// 初始化处理过的数据总量（相当于一个全局的计时器）
		}
	}
	
	/**
	 * 判断是否是以 小写字母、大写字母、数字、'.'开头
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		String regex = "^[a-zA-Z0-9.]+$";// 其他需要，直接修改正则表达式就好
		return str.matches(regex);
	}
	
	/**
	 * 句2键
	 * 相邻字统计
	 * 
	 * @param txt 文本内容		
	 * @param bondColl 数据集合
	 */
	public static void txtContext2Bond(String txt, BondColl bondColl) {
		if (txt != null && txt.length() > 0) {
			String itemHead = txt.substring(0, 1);
			String itemTail = "";
			String letter = "";
			int txtLen = txt.length();// 词库所有字的次数总和
			for (int i = 1; i < txtLen; i++) {
				itemTail = txt.substring(i, i + 1);// 取下一个字
				if (isLetter(itemTail)) {// 是否字母、数字、.
					letter += itemTail;
					if (i == txt.length() - 1) {// 如果到了txt的最后
						itemTail = letter;
						updateBondColl(itemHead, itemTail, bondColl);// 更新数据集合
						itemHead = itemTail;
					}
					continue;
				} else {
					if (letter != null && !letter.trim().equals("")) {// 单字与字母、数字、'.'的数据集合
						updateBondColl(itemHead, letter, bondColl);// 更新数据集合
						letter = "";
					}
					updateBondColl(itemHead, itemTail, bondColl);// 更新数据集合
					itemHead = itemTail;
				}
			}
		}
	}
	
	/**
	 * 句2词库
	 * 
	 * @param txt
	 * @param bondColl
	 * @param itemColl
	 * @param isUpdateBondColl
	 */
	public static void txtContext2BondColl(String txt, BondColl bondColl,
			ItemColl itemColl, boolean isUpdateBondColl) {
		if (isUpdateBondColl) {// 是否更新数据集合
			txtContext2Bond(txt, bondColl);
		}
		StringBuffer buffer = new StringBuffer();
		if (txt.length() != 0) {
			String keywordHead = txt.substring(0, 1);
			buffer.append(keywordHead);
			String keywordTail = "";
			String letter = "";
			int txtLen = txt.length();
			for (int i = 1; i < txtLen; i++) {
				keywordTail = txt.substring(i, i + 1);
				if (isLetter(keywordTail)) {
					letter += keywordTail;
					if (i == txt.length() - 1) {
						keywordTail = letter;
						// 判断头尾是否相关 true:相关;false:不相关
						boolean b = IsBondValid(keywordHead, keywordTail,
								bondColl);
						if (!b) {// 不相关
							String keyword = buffer.toString();
							if (keyword != null && !keyword.trim().equals("")) {
								updateItemColl(keyword, itemColl);// 单字集合处理
							}
							buffer.setLength(0);// 清空StringBuffer
							buffer.append(keywordTail);
						} else {
							buffer.append(keywordTail);
						}
						keywordHead = keywordTail;
					}
					continue;
				} else {
					if (letter != null && !letter.trim().equals("")) {
						// 判断头尾是否相关 true:相关;false:不相关
						boolean b = IsBondValid(keywordHead, letter, bondColl);
						if (!b) {
							String keyword = buffer.toString();
							if (keyword != null && !keyword.trim().equals("")) {
								updateItemColl(keyword, itemColl);
							}
							buffer.setLength(0);// 清空StringBuffer
							buffer.append(letter);
						} else {
							buffer.append(letter);
						}
						letter = "";
					}
					// 判断头尾是否相关 true:相关;false:不相关
					boolean b = IsBondValid(keywordHead, keywordTail, bondColl);
					if (!b) {
						String keyword = buffer.toString();
						if (keyword != null && !keyword.trim().equals("")) {
							updateItemColl(keyword, itemColl);
						}
						buffer.setLength(0);// 清空StringBuffer
						buffer.append(keywordTail);
					} else {
						buffer.append(keywordTail);
					}
					keywordHead = keywordTail;
				}
			}
		}
	}
	
	/**
	 * 切片函数，判断头尾是否相关
	 * 
	 * @param itemHead 头
	 * @param itemTail 尾
	 * @param bondColl 数据集合
	 * @return
	 */
	public static boolean IsBondValid(String itemHead, String itemTail,
			BondColl bondColl) {
		Map<String, BondModel> keyBondColl = bondColl.getKeybondColl();
		double headItemCount = 0;
		double tailItemCount = 0;
		double keyBondCount = 0;
		double totalCount = rememberValue;
		if (!keyBondColl.containsKey(itemHead)
				|| !keyBondColl.containsKey(itemTail)) {
			return false;
		} else {
			BondModel bondModel = keyBondColl.get(itemHead);// 头-键-对象
			ItemModel headItemModel = bondModel.getHeadItemModel();// 头-单字-数据模型
			headItemCount = headItemModel.getValidCount();// 头-单字-成熟度
			ItemColl tailItemColl = bondModel.getTailItemColl();// 尾-单字-集合
			Map<String, ItemModel> keyItemColl = tailItemColl.getKeyItemColl();// 尾-数字模型-集合
			ItemModel itemModel = keyItemColl.get(itemTail);// 尾-单字-数据模型
			keyBondCount = itemModel.getValidCount();// 尾-单字-成熟度
			BondModel model = keyBondColl.get(itemTail);// 尾-键-对象
			ItemModel headItemModel2 = model.getHeadItemModel();// 尾-单字-数据模型
			tailItemCount = headItemModel2.getValidCount();// 尾-单字-成熟度

			if (headItemCount <= 0 || tailItemCount <= 0) {
				return false;
			}

			return keyBondCount / headItemCount > tailItemCount / totalCount;
		}
	}
	
	/**
	 * 读取文件内容，相邻字处理
	 * 
	 * @param filePath 文件具体全路径、文件目录
	 * @param bondColl
	 * @return
	 */
	public static List<String> readTxtFile(String filePath, BondColl bondColl) {
		try {
			List<String> list = new ArrayList<String>();
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {// 判断是否存在，是否为文件
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuffer lineTxt1 = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					lineTxt1.append(line.replaceAll("[\\p{P}&&[^.]]", ""));// 去除特殊符号
//					lineTxt1.append(line.replaceAll("	", "").replaceAll("　", ""));// 杨超添加
					if (lineTxt1.length() >= 4000000) {// 每累计达400w长度的数据，开始处理
						txtContext2Bond(lineTxt1.toString(), bondColl);// 相邻字处理
						list.add(lineTxt1.toString());
						lineTxt1.setLength(0);// 清空lineTxt1
					}
				}
				if (lineTxt1.length() > 0) {// 处理剩余的数据
					txtContext2Bond(lineTxt1.toString(), bondColl);
					list.add(lineTxt1.toString());
				}
				read.close();
				return list;
			} else if (file.exists() && file.isDirectory()) {// 判断是否存在，是否为目录
				File[] listFiles = file.listFiles();// 获取目录下的所有文件
				for (int i = 0; i < listFiles.length; i++) {
					if (listFiles[i].isFile()) {
						InputStreamReader read = new InputStreamReader(
								new FileInputStream(listFiles[i]), encoding);// 考虑到编码格式
						BufferedReader bufferedReader = new BufferedReader(read);
						StringBuffer lineTxt1 = new StringBuffer();
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							lineTxt1.append(line.replaceAll("[\\p{P}&&[^.]]",""));// 去除特殊符号
//							lineTxt1.append(line.replaceAll("	", "").replaceAll("　", ""));// 杨超添加
							if (lineTxt1.length() >= 4000000) {// 每累计达400w长度的数据，开始处理
								txtContext2Bond(lineTxt1.toString(), bondColl);// 相邻字处理
								list.add(lineTxt1.toString());// 添加内容到list
								lineTxt1.setLength(0);// 清空lineTxt1
							}
						}
						if (lineTxt1.length() > 0) {// 处理剩余的数据
							txtContext2Bond(lineTxt1.toString(), bondColl);// 相邻字处理
							list.add(lineTxt1.toString());// 添加内容到list
						}
						read.close();
					}
				}
				return list;
			} else {
				logger.info("找不到指定的文件");
				return null;
			}
		} catch (Exception e) {
			logger.error("读取文件内容出错 ERROR! : ",e);
			return null;
		}
	}
	 
	/**
	 * 写入TXT文本
	 * 
	 * @param map
	 * @param filePath
	 */
	public static void WriteDate(Map<String, ItemModel> map, String filePath) {
		File file = new File(filePath);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			ItemModel key = null;
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (Map.Entry<String, ItemModel> entry : map.entrySet()) {
				key = (ItemModel) entry.getValue();
				if (key.getKeyItem().length() > 1 && key.getValidCount() >= 1.0) {
					key = (ItemModel) entry.getValue();
					output.write("【" + key.getKeyItem() + "】"
							+ key.getValidCount() + "\r\n");
				}
			}
			output.close();
			logger.info("ok.");
		} catch (IOException e) {
			logger.error("ERROR! : ", e);
		}
	}
	 
	/**
	 * 去掉低于阈值的词（ItemModel）
	 * 我们取6天记忆剩余量约为25.4%这个值，按每秒阅读7个字，将其代入牛顿冷却公式可以求得遗忘系数
	 * 
	 * @param itemColl
	 * @return
	 */
	public static ItemColl clearItemColl(ItemColl itemColl) {
		Map<String, ItemModel> itemModelMap = new HashMap<String, ItemModel>();
		Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
		double totalOffsetCount = itemColl.getTotalOffsetCount();
		ItemModel value = null;
		double valid = 0;
		for (Map.Entry<String, ItemModel> entry : keyItemColl.entrySet()) {
			value = (ItemModel) entry.getValue();
			valid = value.getValidCount()
					* ForgetDAL.rememberValue(totalOffsetCount
							- value.getLastUpdateTime());
			value.setValidCount(valid);
			value.setLastUpdateTime(Double.valueOf(0));
			if (valid > 0.254) {
				itemModelMap.put(entry.getKey(), value);
			}
		}
		itemColl.setKeyItemColl(itemModelMap);
		itemColl.setTotalOffsetCount(0);
		return itemColl;
	}
	 
	/**
	 * 去掉低于阈值的键（BondModel）
	 * 我们取6天记忆剩余量约为25.4%这个值，按每秒阅读7个字，将其代入牛顿冷却公式可以求得遗忘系数
	 * 
	 * @param bondColl
	 * @return
	 */
	public static BondColl clearBondColl(BondColl bondColl) {
		Map<String, BondModel> bondModelMap = new HashMap<String, BondModel>();
		Map<String, BondModel> keybondColl = bondColl.getKeybondColl();
		double totalOffsetCount = bondColl.getTotalOffsetCount();
		BondModel value = null;
		double valid = 0;
		for (Map.Entry<String, BondModel> entry : keybondColl.entrySet()) {
			value = (BondModel) entry.getValue();
			value.getHeadItemModel().setLastUpdateTime(Double.valueOf(0));
			valid = value.getHeadItemModel().getValidCount()
					* ForgetDAL.rememberValue(totalOffsetCount
							- value.getHeadItemModel().getLastUpdateTime());
			value.getHeadItemModel().setValidCount(valid);
			if (valid > 0.254) {
				bondModelMap.put(entry.getKey(), entry.getValue());
			}
		}
		bondColl.setTotalOffsetCount(0);
		return bondColl;
	}
	 
	 /*
	  * 正则验证  
	  * 去除包含标点符号的结果
	  */
	 public static Map<String,ItemModel> keywordValid(Map<String,ItemModel> map){
		 
		 //正则验证  字符串中是否包含标点符号
		 Pattern pattern = Pattern.compile("\\p{P}");
		 
		 Iterator<Map.Entry<String,ItemModel>> it = map.entrySet().iterator();  
	        while(it.hasNext()){  
	            Map.Entry<String,ItemModel> entry=it.next();  
	            String key=entry.getKey();  
	            Matcher matcher = pattern.matcher(key);
	            if(key.length() <= 1 || matcher.find()){
	            	it.remove();
	            }
	        }  
	        
		 return map;
	 }
}
