package com.mlh.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.mlh.exception.RequestErrException;

/**
 * 爬出整个网页信息
 * 
 * @author menglihao
 * @since 20140504
 * @version 1.0
 */
public class StringWebInfoSpider extends DefaultWebInfoSpider {

	private CloseableHttpClient client = HttpClients.createDefault();

	private String charset;

	private String responseContent;
	
	public StringWebInfoSpider(){
		super.spiderType=IWebInfoSpider.SPIDER_TYPE_STRING;
	}

	public String requestString(String url) throws RequestErrException,
			ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = client.execute(get);
		initResponseHeader(response);
		if (199 <= statusCode && statusCode >= 400) {
			throw new RequestErrException("请求失败：" + statusCode);
		}
		Header[] headers = response.getHeaders("Content-Type");
		String charset = "GBK";
		for (int i = 0; i < headers.length; i++) {
			if (headers[i].getValue().contains("charset")) {
				charset = headers[i].getValue().split(";")[1].split("=")[1];
			}
		}
		this.charset = charset;
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,
				charset));
		String buff = "";
		StringBuilder builder = new StringBuilder();
		while ((buff = reader.readLine()) != null) {
			builder.append(buff).append("\r\n");
		}
		if (null != response) {
			response.close();
		}
		if (null != reader) {
			reader.close();
		}
		this.responseContent = builder.toString();
		return builder.toString();
	}

	private void initResponseHeader(CloseableHttpResponse response) {
		this.statusCode = response.getStatusLine().getStatusCode();
		this.header = response.getAllHeaders();
		this.mimeType = response.getHeaders("Content-Type");
	}

	public String getCharset() {
		return charset;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public static void main(String[] args) throws Exception {
		StringWebInfoSpider spider = new StringWebInfoSpider();
		System.out.println(spider.requestString("http://www.baidu.com"));
	}

	@Override
	public byte[] requestByteArr(String url) {
		return null;
	}

	@Override
	public Object requestObj(String url) {
		return null;
	}

}
