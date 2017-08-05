package org.pcloud.common.filemaker;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 * DOM格式转换类
 * 
 * @author 徐海洋
 * 
 */
public class LableFilter {

    public static Map<String, String> charMap;

    static {
        charMap = new LinkedHashMap<String, String>();
        charMap.put("&", "&amp;");
        charMap.put("<", "&lt;");
        charMap.put(">", "&gt;");
        charMap.put("'", "&apos;");
        charMap.put("\"", "&quot;");
        charMap.put("\n", "&#x000A;");
    }

    /***
     * 将dom对象中的标签符号格式化
     * 
     * @param obj
     *            dom字符串
     * @return 转换后的字符串
     * @throws Exception
     *             异常对象
     */
    public String xmlCharTran(Object obj) throws Exception {
        if (obj == null || obj.equals("")) {
            return null;
        }
        Iterator<String> it = charMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = charMap.get(key);
            obj = String.valueOf(obj).replaceAll(key, value);
        }
        return obj.toString();
    }

}
