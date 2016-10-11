package com.mlh.exception;

public class RequestErrException extends Exception{
	
	private static final long serialVersionUID = 6087189707884520159L;

	public RequestErrException(){
		super();
	}
	
	public RequestErrException(String message){
		super(message);
	}
	
	public RequestErrException(Throwable t){
		super(t);
	}
	
	public RequestErrException(String message,Throwable t){
		super(message,t);
	}
	
}
