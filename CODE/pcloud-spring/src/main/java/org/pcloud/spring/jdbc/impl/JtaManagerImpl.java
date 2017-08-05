package org.pcloud.spring.jdbc.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.pcloud.spring.common.ApplicationContextHelper;
import org.pcloud.spring.jdbc.JtaManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


@Component
@SuppressWarnings("unchecked")
public class JtaManagerImpl implements JtaManager {

	@Resource(name = "transactionTemplate")
	private TransactionTemplate transactionTemplate;

	public void saveOrupdate(final String tempName,
			final Map<String, Object[]>... sqlSegmentMaps) throws Exception {
		 
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(tempName);
					String[] sqls = new String[sqlSegmentMaps.length];
					Object[] params = new Object[sqlSegmentMaps.length];
					for (int i = 0; i < sqlSegmentMaps.length; i++) {
						Map<String, Object[]> sqlSegmentMap = sqlSegmentMaps[i];
						for (Map.Entry<String, Object[]> entry : sqlSegmentMap
								.entrySet()) {
							sqls[i] = entry.getKey();
							params[i] = entry.getValue();
						}
					}
					jdbcTemplate.batchUpdate(sqls, params);
				} catch (Exception ex) {
					// 通过调用 TransactionStatus 对象的 setRollbackOnly() 方法来回滚事务。
					status.setRollbackOnly();
					throw new RuntimeException(ex.getMessage());
				}
			}
		});
	}
	
//	Map<String, List<Object[]>> sqls = new HashMap<String, List<Object[]>>();
//	public void saveOrupdate(final String tempName, final Map<String, List<Object[]>> sqlSegmentMaps) throws Exception {
//		
//		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//			@Override
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//				try {
//					JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(tempName);
//					
//					for (Map.Entry<String, List<Object[]>> map : sqlSegmentMaps.entrySet()) {
//						map.getKey();
//						map.getValue();
//					}
//					
//					
//					String[] sqls = new String[sqlSegmentMaps.length];
//					Object[] params = new Object[sqlSegmentMaps.length];
//					for (int i = 0; i < sqlSegmentMaps.length; i++) {
//						Map<String, Object[]> sqlSegmentMap = sqlSegmentMaps[i];
//						for (Map.Entry<String, Object[]> entry : sqlSegmentMap
//								.entrySet()) {
//							sqls[i] = entry.getKey();
//							params[i] = entry.getValue();
//						}
//					}
//					jdbcTemplate.batchUpdate(sqls, params);
//				} catch (Exception ex) {
//					// 通过调用 TransactionStatus 对象的 setRollbackOnly() 方法来回滚事务。
//					status.setRollbackOnly();
//					throw new RuntimeException(ex.getMessage());
//				}
//			}
//		});
//	}

	public void saveOrupdate(
			final Map<String, Map<String, Object[]>> moreDSSqlSegmentMap)
			throws Exception {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					for (Map.Entry<String, Map<String, Object[]>> oneDSSqlSegmentMap : moreDSSqlSegmentMap
							.entrySet()) {
						JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(oneDSSqlSegmentMap.getKey());
							 
						Map<String, Object[]> sqlSegmentMaps = oneDSSqlSegmentMap
								.getValue();
						String[] sqls = new String[sqlSegmentMaps.size()];
						int count = 0;
						Object[] params = new Object[sqlSegmentMaps.size()];

						for (Map.Entry<String, Object[]> sqlSegmentMap : sqlSegmentMaps
								.entrySet()) {
							sqls[count] = sqlSegmentMap.getKey();
							params[count] = sqlSegmentMap.getValue();
							count++;
						}
						jdbcTemplate.batchUpdate(sqls, params);
					}
				} catch (Exception ex) {
					// 通过调用 TransactionStatus 对象的 setRollbackOnly() 方法来回滚事务。
					status.setRollbackOnly();
					throw new RuntimeException(ex.getMessage());
				}
			}
		});
	}

	public List<Map<String, Object>> query(String tempName,
			Map<String, Object[]> sqlSegmentMap) throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(tempName);
		String sql = "";
		Object[] params = null;
		for (Map.Entry<String, Object[]> entry : sqlSegmentMap.entrySet()) {
			sql = entry.getKey();
			params = entry.getValue();
		}
		return jdbcTemplate.queryForList(sql, params);
	}

	public List<Map<String, Object>> queryForList(String tempName, String sql,
			Object... params) throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(tempName);
		if (params == null) {
			return jdbcTemplate.queryForList(sql);
		}
		return jdbcTemplate.queryForList(sql, params);
	}

	public int queryForInt(String tempName, Map<String, Object[]> sqlSegmentMap)
			throws Exception {
		String sql = "";
		Object[] params = null;
		for (Map.Entry<String, Object[]> entry : sqlSegmentMap.entrySet()) {
			sql = entry.getKey();
			params = entry.getValue();
		}
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHelper.getBean(tempName);
		return jdbcTemplate.queryForObject(sql, Integer.class, params);
	}

}
