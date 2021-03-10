package com.matrix.util;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


public class TestCode {
	/**************************** RSA加密解密测试 ********************************/
	public static void main(String[] args) {
		try {
			RSAUtil rsa = new RSAUtil();
			String str = "whos your daddy";
			RSAPublicKey pubKey = rsa.getRSAPublicKey();
			RSAPrivateKey priKey = rsa.getRSAPrivateKey();
			byte[] enRsaBytes = rsa.encrypt(pubKey, str.getBytes());
			
			String prkey = Base64.getEncoder().encodeToString(priKey.getEncoded());
			System.out.println("私  钥 = " + prkey); 
			System.out.println(prkey.length()); 
			
			String pukey = Base64.getEncoder().encodeToString(pubKey.getEncoded());
			System.out.println("公  钥 = " + pukey); 
			System.out.println(pukey.length()); 
			
			
			System.out.println("加密后 = " + Base64.getEncoder().encodeToString(enRsaBytes)); 
			System.out.println("解密后 = " + new String(rsa.decrypt(priKey, enRsaBytes)));
			
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
