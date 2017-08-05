package com.ufc.user.utils;

import java.util.List;

/** 
* @author DengJie 
* @version 创建时间：2017年7月28日
* 类说明:
*/
public class FullSearchUtils {
	public static void getStop(StringBuffer sql, String field, String value){
		boolean flag = ParseRuleUtils.isSingleWord(value);
		if(!flag){
			Rule rule = ParseRuleUtils.doParse(value);
			List<String> stopList = rule.getStop_list();
			if(!EmptyUtils.isEmpty(stopList)){
				for(int i = 0; i < stopList.size(); i++){
					sql.append(" "+field+" <> '"+stopList.get(i)+"' and");
				}
			}
		}
	}
	public static void getSearch(StringBuffer sql, String field, String value){
		boolean flag = ParseRuleUtils.isSingleWord(value);
		String newSql = "";
		if(flag){
			//单词
			if("all".equals(field)){
				sql.append(" ( ");
				sql.append(" content = '"+value+"'");
				sql.append(" or title = '"+value+"'");
				sql.append(" ) ");
			}else{
				sql.append(" "+field+" = '"+value+"' or");
			}
		}else{
			//规则词
			Rule rule = ParseRuleUtils.doParse(value);
			List<String> mustList = rule.getMust_list();
			List<String> maybeList = rule.getMaybe_list();
			List<String> stopList = rule.getStop_list();
			
			//吕大鹏  * 北京
			//吕大鹏  * 北京 * 海淀区
			if(EmptyUtils.isEmpty(maybeList)){
				sql.append(" ( ");
				for(int i = 0; i < mustList.size(); i++){
					sql.append(" "+field+" = '"+mustList.get(i)+"' ");
					if(i != mustList.size() - 1){
						sql.append(" and");
					}
				}
				sql.append(" ) ");
			}else{
			//吕大鹏  * （北京 + 张家口）
			//吕大鹏  * 软件工程师  *（北京   + 张家口）
				if(!EmptyUtils.isEmpty(mustList)){
					sql.append(" ( ");
					for(int i = 0; i < mustList.size(); i++){
						sql.append(" "+field+" = '"+mustList.get(i)+"' and");
						
					}
				}
				if(!EmptyUtils.isEmpty(maybeList)){
					sql.append(" (");
					for(int i = 0; i < maybeList.size(); i++){
						sql.append(" "+field+" = '"+maybeList.get(i)+"' ");
						if(i != maybeList.size() - 1){
							sql.append(" or");
						}
					}
					newSql = sql.toString();
					if(newSql.trim().endsWith("or")){
						newSql = newSql.trim().substring(0, newSql.trim().length() - 2);
						sql.setLength(0);
						sql.append(newSql);
					}
					
					sql.append(" )");
				}
				if(!EmptyUtils.isEmpty(mustList)){
					sql.append(" ) ");
				}
			}

			sql.append(" or");
		}
	}
}
