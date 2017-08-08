package com.zkdj.gxkjt.es.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.es.common.service.TransportClientRepository;
import com.ufc.user.utils.Pager;

@Service
public class EsCommonService {
	private static Logger logger = LoggerFactory.getLogger(EsCommonService.class);
	@Autowired
	private TransportClientRepository eSTransportClient;
	
	// 模糊查询
	public Pager<JSONObject> searchFullText(String filed, String queryValue, int pageNum, int pageSize, String type, String... indexs) {
		return eSTransportClient.searchFullText(filed, queryValue, pageNum, pageSize, type, indexs);
	}
	// 根据ID查询
	public String queryById(String index, String type, String id) {
		return eSTransportClient.getIdx(index, type, id);
	}
	// 根据ID删除
	public String deleteById(String index, String type, String id) {
		return eSTransportClient.deleteById(index, type, id);
	}
	// 根据ID更新
	public String updateById(String index, String type, String id, Object doc) {
		return eSTransportClient.updateDoc(index, type, id, doc);
	}
	
	// 模糊查询-无分页
	public Pager<JSONObject> searchAllSimilarity(String filed, Object queryValue, int pageNum, int pageSize, String type, String... indexs) {
		return eSTransportClient.searchAllSimilarity(filed, queryValue, pageNum, pageSize, type, indexs);
	}
    
}
