package com.ufc.user.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文本断句工具类
 * 
 * @author dj
 *
 */
public class SplitUtils {
	/**
	 * 将文本断句
	 * 
	 * @param text
	 *            文本
	 * @return List<String>
	 */
	public static List<String> SentenceSplit(String text) {
		List<String> SentenceItems = new ArrayList<>();
		text = text.trim().replace("\\s*|\t|\r|\n", "").trim();
		String[] array = text.split("[。|?|？|!|！|:|：|;|；]");
		List<String> lines = Arrays.asList(array);
		String temp_line = "";
		int temp_index = 0;
		for (String line : lines) {
			temp_line = line.trim().replaceAll("^[(|)|（|）]", "").replaceAll("^[A-Za-z0-9]", "").replaceAll("^[一-九]", "")
					.replaceAll("^[①-⑩]", "").replaceAll("^[(|)|（|）]", "").replaceAll("^[.|、|。]", "");
			if ((temp_line.length() > 30)) {
				while (temp_line.length() > 30) {
					try {
						if ((temp_index = temp_line.indexOf(",")) != -1
								|| (temp_index = temp_line.indexOf("，")) != -1) {
							String sentence = temp_line.substring(0, temp_index).trim();
							sentence = sentence.trim().replaceAll("^[(|)|（|）]", "").replaceAll("^[A-Za-z0-9]", "")
									.replaceAll("^[一-九]", "").replaceAll("^[①-⑩]", "").replaceAll("^[(|)|（|）]", "")
									.replaceAll("^[.|、|。]", "");
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
					SentenceItems.add(temp_line.trim());
			}
		}
		return SentenceItems;
	}
}
