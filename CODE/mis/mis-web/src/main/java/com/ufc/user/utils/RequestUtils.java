package com.ufc.user.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {

	private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);
	
	/**
	 * 发送Http Request
	 * @param restUrl
	 * @param param
	 * @return String, 结果
	 */
	public String sendPost(String restUrl, String param) {
		HttpURLConnection httpurlconnection = null;
		try {
			URL url = new URL(restUrl);
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setRequestProperty("content-type", "text/html");
			httpurlconnection.setRequestProperty("Accept-Charset", "utf-8");
			httpurlconnection.setRequestProperty("contentType", "utf-8");
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setDoInput(true);
			httpurlconnection.setUseCaches(false);
			httpurlconnection.getOutputStream().write(param.getBytes("utf-8"));
			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();
			logger.info("send Post:【" + restUrl + "】.");
			logger.info("send Param:【" + param + "】.");
			if (200 == httpurlconnection.getResponseCode()) {
				return readContents(httpurlconnection);
			} else {
				//sendPost(restUrl, param);// 非正常情况 重发一次
				httpurlconnection.disconnect();
			}
		} catch (Exception e) {
			logger.error("send Post:【" + restUrl + "---" + param + "】 error.", e);
			//sendPost(restUrl, param);// 出错 重发一次
			httpurlconnection.disconnect();
		} finally {
			if (httpurlconnection != null)
				httpurlconnection.disconnect();
		}
		return "";
	}

	/**
	 * 读取相应的内容
	 * @param httpurlconnection
	 * @return String,内容
	 * @throws IOException
	 */
	public static String readContents(HttpURLConnection httpurlconnection) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(),"utf-8"));
			StringBuffer temp = new StringBuffer();
			String inputLine = in.readLine();
			while (inputLine != null) {
				temp.append(inputLine.trim());
				inputLine = in.readLine();
			}
			return temp.toString();
		} catch (IOException e) {
			logger.error("readContents error", e.getMessage());
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("readContents close BufferedReader error", e.getMessage());
				throw e;
			}
		}
	}
	
	
}
