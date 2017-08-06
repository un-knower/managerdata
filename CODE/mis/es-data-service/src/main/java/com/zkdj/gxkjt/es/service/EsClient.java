/*package com.zkdj.gxkjt.es.service;

import java.net.InetAddress;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.ufc.user.utils.BulkRequest;
import com.ufc.user.utils.BulkResponse;
import com.ufc.user.utils.ByteSizeValue;

public class EsClient {
	// 用于提供单例的TransportClient BulkProcessor
	static public TransportClient tclient = null;
	static BulkProcessor staticBulkProcessor = null;

	// 【获取TransportClient 的方法】
	public static TransportClient getClient() {
		try {
			if (tclient == null) {
				String EsHosts = "10.10.2.1:9300,10.10.2.2:9300";
				Settings settings = Settings.settingsBuilder()
						.put("cluster.name", "wshare_es")// 设置集群名称
						.put("tclient.transport.sniff", true).build();// 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中

				tclient = TransportClient.builder().settings(settings).build();
				String[] nodes = EsHosts.split(",");
				for (String node : nodes) {
					if (node.length() > 0) {// 跳过为空的node（当开头、结尾有逗号或多个连续逗号时会出现空node）
						String[] hostPort = node.split(":");
						tclient.addTransportAddress(new InetSocketTransportAddress(
								InetAddress.getByName(hostPort[0]), Integer
										.parseInt(hostPort[1])));

					}
				}
			}// if
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tclient;
	}

	public static BulkProcessor getBulkProcessor() {
		// 自动批量提交方式
		if (staticBulkProcessor == null) {
			try {
				staticBulkProcessor = BulkProcessor
						.builder(getClient(), new BulkProcessor.Listener() {
							@Override
							public void beforeBulk(long executionId,
									BulkRequest request) {
								// 提交前调用
								// System.out.println(new Date().toString() +
								// " before");
							}

							@Override
							public void afterBulk(long executionId,
									BulkRequest request, BulkResponse response) {
								// 提交结束后调用（无论成功或失败）
								// System.out.println(new Date().toString() +
								// " response.hasFailures=" +
								// response.hasFailures());
								log.info("提交"
										+ response.getItems().length
										+ "个文档，用时"
										+ response.getTookInMillis()
										+ "MS"
										+ (response.hasFailures() ? " 有文档提交失败！"
												: ""));
								// response.hasFailures();//是否有提交失败
							}

							@Override
							public void afterBulk(long executionId,
									BulkRequest request, Throwable failure) {
								// 提交结束且失败时调用
								log.error(" 有文档提交失败！after failure=" + failure);
							}
						})

						.setBulkActions(1000)// 文档数量达到1000时提交
						.setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))// 总文档体积达到5MB时提交
																			// //
						.setFlushInterval(TimeValue.timeValueSeconds(5))// 每5S提交一次（无论文档数量、体积是否达到阈值）
						.setConcurrentRequests(1)// 加1后为可并行的提交请求数，即设为0代表只可1个请求并行，设为1为2个并行
						.build();
				// staticBulkProcessor.awaitClose(10,
				// TimeUnit.MINUTES);//关闭，如有未提交完成的文档则等待完成，最多等待10分钟
			} catch (Exception e) {// 关闭时抛出异常
				e.printStackTrace();
			}
		}// if

		return staticBulkProcessor;
	}
}
*/