package com.mlh.http;

import org.apache.http.Header;

public abstract class DefaultWebInfoSpider implements IWebInfoSpider{
	
	protected int spiderType;
	
	protected int statusCode;

	protected Header[] header;

	protected Header[] mimeType;

	public int getSpiderType() {
		return spiderType;
	}

	protected void setSpiderType(int spiderType) {
		this.spiderType = spiderType;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public Header[] getHeader() {
		return header;
	}

	public Header[] getMimeType() {
		return mimeType;
	}
	
	
}
