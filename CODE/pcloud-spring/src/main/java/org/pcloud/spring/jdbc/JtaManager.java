package org.pcloud.spring.jdbc;

import java.util.List;
import java.util.Map;
@SuppressWarnings("unchecked")
public interface JtaManager {
	/**
	 * 批量的保存或更新
	 * @param tempName
	 * 				数据库JDBCTemplate名称
	 * @param sqlSegmentMaps
	 * 			<
	 * 				String: sql-> update/insert   语句有占位符 ?
	 * 				Object: params-> 其实是一个String[] 用于替换 sql 中的占位符
	 * 			>
	 * @throws Exception
	 * 			抛出异常
	 *      
	 */
	public  void saveOrupdate(final String tempName, final Map<String, Object[]>... sqlSegmentMaps) throws Exception;
	
	/**
	 * 批量的保存或更新,在不同的数据库或实例中
	 * @param moreDSSqlSegmentMap
	 *  <
	 *  		数据库JDBCTemplate名称 :
	 * 			<
	 * 				String: sql-> update/insert   语句有占位符 ?
	 * 				Object: params-> 其实是一个String[] 用于替换 sql 中的占位符
	 * 			>
	 *  >
	 * @throws Exception
	 * 				抛出异常
	 */
	public void saveOrupdate(final Map<String,  Map<String, Object[]>> moreDSSqlSegmentMap)throws Exception;
	
	/**
	 * 查询指定数据库中的业务表信息
	 * @param tempName
	 * 				数据库名称
	 * @param sqlSegmentMap
	 * 			<
	 * 				String: sql-> select   语句有占位符 ?
	 * 				Object: params-> 其实是一个String[] 用于替换 sql 中的占位符
	 * 			>
	 * @return
	 *      [{},...]
	 * @throws Exception
	 *            抛出异常
	 */
	public  List<Map<String, Object>> query(String tempName, Map<String, Object[]> sqlSegmentMap)throws Exception;
	
	/**
	 * 查询指定数据库中的业务表信息
	 * @param tempName
	 * 				数据库名称
	 * @param sql
	 * 			select 语句
	 * @param params
	 * 			其实是一个String[] 用于替换 sql 中的占位符
	 * @return
	 *      [{},...]
	 * @throws Exception
	 *            抛出异常
	 */
	public List<Map<	String,Object>> queryForList(String tempName,String sql,Object... params) throws Exception;
	
	
	/**
	 * 查询指定数据库中的业务表的记录数
	 * @param tempName
	 * 				数据库名称
	 * @param sqlSegmentMap
	 * 			<
	 * 				String: sql-> select   语句有占位符 ?
	 * 				Object: params-> 其实是一个String[] 用于替换 sql 中的占位符
	 * 			>
	 * @return
	 *      数值 如 1、2、3...
	 * @throws Exception
	 *            抛出异常
	 */
	public int queryForInt(String tempName, Map<String, Object[]> sqlSegmentMap) throws Exception;
	
}
