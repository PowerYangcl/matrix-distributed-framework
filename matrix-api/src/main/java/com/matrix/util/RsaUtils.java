package com.matrix.util;


import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alibaba.fastjson.JSONObject;

import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.io.*;
import java.math.BigInteger;


/**
 * @description: 非对称加密算法工具类。 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年1月11日 下午8:14:12 
 * @version 1.0.0
 */
public class RsaUtils {
	private RsaUtils() { 
	} 
	private static class LazyHolder {
		private static final RsaUtils INSTANCE = new RsaUtils();
	}
	public static final RsaUtils getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	private static final String ALGORITHM = "RSA";    // DiffieHellman|DSA|RSA|EC
	private static final Integer KEY_SIZE = 1024; // 块加密的大小，可以更改，但是不要太大，否则效率会低
	
	/**
	 * @description: 生成一对秘钥 
	 *
	 * @author Yangcl
	 * @date 2018年1月11日 下午8:26:54 
	 * @version 1.0.0
	 */
	public JSONObject initKeyPair() {
		JSONObject keys = new JSONObject(); 
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM ,new BouncyCastleProvider());
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.genKeyPair(); 
			String pukey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
			String prkey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
			keys.put("pukey", pukey);
			keys.put("prkey", prkey);
			keys.put("status", "success");
			return keys;
		} catch (Exception e) {
			e.printStackTrace(); 
			keys.put("status", "error");
		}
		return keys;
	}
}





























