package com.mlh.util;

import java.util.UUID;

public class Identity {

	private Identity(){}
	
	public static String uuid(){
		return UUID.randomUUID().toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Identity.uuid());;
	}
}
