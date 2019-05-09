package com.matrix.util;

import java.util.UUID;

public class UuidUtil {
	public static String uid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String upperCaseUuid(){
		return UUID.randomUUID().toString().toUpperCase();
	}
}
