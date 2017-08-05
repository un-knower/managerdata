package com.ufc.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
public class SentenceItem {
    /// <summary>
    /// 内容
    /// </summary>
	public String content;
    /// <summary>
    /// 句子集合
    /// </summary>
	public List<String> items;
	public String source;
    /// <summary>
    /// 相似度
    /// </summary>
	public int similar=0;
    /// <summary>
    /// 内容高亮
    /// </summary>
	public String colorText;
	
	public SentenceItem(String source,String content) {
		this.content=content;
		this.source=source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getItems() {
		return items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getSimilar() {
		return SimilarRun();
	}
	public void setSimilar(int similar) {
		this.similar = similar;
	}
	
	public String getColorText() {
		return colorText;
	}
	public void setColorText(String colorText) {
		this.colorText = colorText;
	}
	/**
	 * 相似度计算
	 * @return
	 */
	public int SimilarRun() {
		SentenceSplit();
		int kw_count = items.size();
		colorText = content;
		//第一次断句匹配
		for (String subText : items) {
			if (colorText.indexOf(subText) != -1) {
				colorText = colorText.replace(subText, "<span class='red'>" + subText + "</span>");
				return 100;
			}
		}
		// 第二次断句匹配
		Set<String> set = null;
		Set<String> list = new HashSet<>();
		for (String subText : items) {
			String[] array = subText.split("[,|，|、|\\|/|(|)|\"|“|”|\\|‘|’| |\t|\n|\r]");
			int find_count = 0;
			List<String> sublist = Arrays.asList(array);
			set = new HashSet<>();
			set.addAll(sublist);
			kw_count += set.size();
			for (String item : set) {
				if (StringUtils.isNotEmpty(item.trim()) && !list.contains(item) && colorText.indexOf(item) != -1) {
					colorText = colorText.replace(item, "<span class='green'>" + item + "</span>");
					find_count++;
				}
			}
			list.addAll(set);
			return (int) (find_count * 1.0 / kw_count * 100);
		}
		return 0;
	}
	/**
	 * 文本断句
	 * @return
	 */
	public void SentenceSplit() {
		List<String> SentenceItems = new ArrayList<>();
		source = source.trim().replace("\r\n", "\n").replace("\r", "").trim();
		String[] array = source.split("[。|?|？|!|！|:|：|;|；]");
		List<String> lines = Arrays.asList(array);
		String temp_line = "";
		int temp_index = 0;
		for (String line : lines) {
			temp_line = line.trim();
			if ((temp_line.length() > 30)) {
				while (temp_line.length() > 30) {
					try {
						if ((temp_index = temp_line.indexOf(",")) != -1
								|| (temp_index = temp_line.indexOf("，")) != -1) {
							String sentence = temp_line.substring(0, temp_index).trim();
							if (!SentenceItems.contains(sentence))
								SentenceItems.add(sentence);
							temp_line = temp_line.substring(temp_index + 1);
						} else
							break;
					} catch (Exception ex) {
						break;
					}
					if (temp_line.trim() != "")
						if (!SentenceItems.contains(temp_line.trim()))
							SentenceItems.add(temp_line.trim());
				}
			} else {
				if (!SentenceItems.contains(temp_line.trim()))
					SentenceItems.add(line.trim());
			}
		}
		setItems(SentenceItems);
	}
}