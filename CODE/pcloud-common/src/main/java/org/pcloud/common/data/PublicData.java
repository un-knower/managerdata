package org.pcloud.common.data;

import java.util.UUID;

import org.pcloud.common.date.DateHelper;

public class PublicData {
		public  String getVal(String parmName) throws Exception{
			if(parmName.equals("system.nowTime")){
				return DateHelper.currentDatetime();
			}else if(parmName.equals("system.uuid")){
				return UUID.randomUUID().toString();
			}else{
				throw new Exception("数据库参数公共服务类没有提供 参数<"+parmName+">的服务");
			}
		}
}
