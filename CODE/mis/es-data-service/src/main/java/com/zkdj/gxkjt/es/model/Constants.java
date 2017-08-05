package com.zkdj.gxkjt.es.model;


/**
 * 公共常量
 * @author
 */

public class Constants {
	
	/**
	 * Controller Service构建消息返回
	 * 1. 索引类型消息、状态码
	 * 2. JSON转Object消息、状态码
	 * 3. 操作消息、状态码
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:44:41
	 */
	public static class Msg{
		//Msg
		public static final String SUCCESS = "操作成功";
		public static final String UNKNOWN_ERROR = "未知错误";
		public static final String SERVICE_TEMPORARILY_UNAVAILABLE = "后端服务暂时不可用";
		public static final String UNSUPPORTED_OPENAPI_METHOD = "Open api接口不被支持";	
		public static final String OPEN_API_REQUEST_LIMIT_REACHED = "应用对open api接口的调用请求数达到上限";
		public static final String UNAUTHORIZED_CLIENT_IP_ADDRESS = "open api调用端的IP未被授权";
		public static final String INVALID_PARAMETER = "参数无效或缺失";
		public static final String INVALID_TOKEN = "token无效";	
		public static final String INVALID_CALL_ID_PARAMETER = "Call_id参数无效或已超时";	
		public static final String INCORRECT_SIGNATURE = "签名无效";	
		public static final String TOO_MANY_PARAMETERS = "参数过多";	
		public static final String UNSUPPORTED_SIGNATURE_METHOD = "参数签名算法未被平台所支持";	
		public static final String NO_PERMISSION_TO_ACCESS_DATA = "没有权限访问数据";	//没有权限访问数据
		
		public static final String EVENT_NOT_EXISTS = "事件不存在";	
		
		//MsgCode
		public static final String SUCCESS_CODE = "0";
		public static final String UNKNOWN_ERROR_CODE = "1";
		public static final String SERVICE_TEMPORARILY_UNAVAILABLE_CODE = "2";
		public static final String UNSUPPORTED_OPENAPI_METHOD_CODE = "3";	
		public static final String OPEN_API_REQUEST_LIMIT_REACHED_CODE = "4";
		public static final String UNAUTHORIZED_CLIENT_IP_ADDRESS_CODE = "5";
		public static final String INVALID_PARAMETER_CODE = "100";
		public static final String INVALID_TOKEN_CODE = "101";	
		public static final String INVALID_CALL_ID_PARAMETER_CODE = "103";	
		public static final String INCORRECT_SIGNATURE_CODE = "104";	
		public static final String TOO_MANY_PARAMETERS_CODE = "105";	
		public static final String UNSUPPORTED_SIGNATURE_METHOD_CODE = "106";	
		public static final String NO_PERMISSION_TO_ACCESS_DATA_CODE = "200";	//没有权限访问数据
		
	}
	
	/**
	 * 生成Token需要的key
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:44:25
	 */
	public static class Md5{
		public static final String GEN_TOKEN_KEY = "zkdj_yuQing"; //生成TOKEN需要的key
		public static final long TOKEN_LIVE_TIME = 100;  //TOKEN 有效期
	}
	
	/**
	 * 默认分页
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:44:00
	 */
	public static class MyPage{
		public static final Integer page = 0;
		public static final Integer size = 10;
	}
	
	/**
	 * 索引名称、索引类型
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:43:21
	 */
	public static class MyCarrie{
		
		public static final String CARRIE = "carrie";
		
		public static final String SYNTHESIZE = "综合";
		public static final String SYNTHESIZE_ID = "2000";
		public static final String NEWS = "新闻";
		public static final String NEWS_ID = "2001";
		public static final String BLOGS = "博客";
		public static final String BLOGS_ID = "2002";
		public static final String BBS = "论坛";
		public static final String BBS_ID = "2003";
		public static final String WEIB = "微博";
		public static final String WEIB_ID = "2004";
		public static final String WEIX = "微信";
		public static final String WEIX_ID = "2005";
		public static final String QQ = "QQ群";
		public static final String QQ_ID = "2006";
		public static final String OTHER = "其他";
		public static final String OTHER_ID = "2999";
	}
	
	
	/**
	 * 默认排序字段和排序类型
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:43:49
	 */
	public static class MySort{
		public static final String sortField = "id";
		public static final String sortType = "DESC";
	}
	
	/**
	 * 分词器
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:43:35
	 */
	public static class AnalysisType{
		public static final String IK = "ik";
	}
	
	/**
	 * 统计类型查询 统计名称 常量 
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:48:14
	 */
	public static class MyFacetName{
		public static final String HISTOGRAM_FACET_NAME = "HistogramFacetName";
		public static final String STAT_FACET_NAME = "statFacetName";
		public static final String RANGE_FACET_NAME = "rangeFacetName";
		public static final String FILTER_FACET_NAME = "filterFacetName";
		public static final String QUERY_FACET_NAME = "queryFacetName";
	}
	
	/**
	 * 行业配置
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:48:14
	 */
	public static class Industry{
		//通用行业配置
		public static final String PUBLIC_INDUSTRY = "public.industry";
	}	
	
	/**
	 * 平台对外提供的获取数据的接口
	 * river获取数据的接口,都需要指定一个APPID,来验证权限
	 * @author LvDapeng
	 * @date 2015年8月22日 上午12:48:14
	 */
	public static class AppID{
		//通用行业配置
		public static final String RIVER_APPID = "river.appid";
	}	
	
	/**
	 * 平台满足 新增用户 立马能看到数据 
	 * 新增了相关词的中文词全文检索
	 * 以下参数是设置相关中文检索的词数最大限制
	 * @author LvDapeng
	 * @date 2015年12月30日 上午1:14:22
	 */
	public static class FulltextCount{
		public static final String RELATED_FULLTEXT_SEARCH_COUNT = "related.fulltext.search.count";
	}
}
