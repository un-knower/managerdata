package com.zkdj.gxkjt.es.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.index.IndexRequest;


/**
 * Created by ZhangDong on 2015/12/25.
 */
public class EsInsert2 {
    static Log log = LogFactory.getLog(EsInsert2.class);
    public static void add(String json) {
                try {  //EsClient.getBulkProcessor()是位于上方EsClient类中的方法
                    //EsClient.getBulkProcessor().add(new IndexRequest("设置的index name", "设置的type name","要插入的文档的ID").source(json));//添加文档，以便自动提交
                } catch (Exception e) {
                    log.error("add文档时出现异常：e=" + e + " json=" + json);
                }
    }
}
//手动 批量更新
//        BulkRequestBuilder bulkRequest = tclient.prepareBulk();
//        for(int i=500;i<1000;i++){
//            //业务对象
//            String json = "";
//            IndexRequestBuilder indexRequest = tclient.prepareIndex("twitter", "tweet")
//                    //指定不重复的ID
//                    .setSource(json).setId(String.valueOf(i));
//            //添加到builder中
//            bulkRequest.add(indexRequest);
//        }
//
//        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
//        if (bulkResponse.hasFailures()) {
//            // process failures by iterating through each bulk response item
//            System.out.println(bulkResponse.buildFailureMessage());
//        }

//单个文档提交
//        String json = "{\"relationship\":{},\"tags\":[\"camera\",\"video\"]}";
//        IndexResponse response = getClient().prepareIndex("dots", "scan", JSON.parseObject(json).getString("rid")).setSource(json).get();
//        return response.toString();
