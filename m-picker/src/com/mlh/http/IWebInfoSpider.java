package com.mlh.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.mlh.exception.RequestErrException;

public interface IWebInfoSpider {

	int SPIDER_TYPE_STRING=0;
	
	int SPIDER_TYPE_BYTEARR=1;
	
	int SPIDER_TYPE_OBJECT=2;
	
	String requestString(String url) throws RequestErrException ,ClientProtocolException, IOException;
	
	byte[] requestByteArr(String url) throws RequestErrException,ClientProtocolException, IOException;
	
	Object requestObj(String url) throws RequestErrException,ClientProtocolException, IOException;
	
	int getSpiderType();
}
