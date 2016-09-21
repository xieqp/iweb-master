/**
 * @(#) JwtUtils.java 2016年9月13日 下午8:54:11
 * Copyright 2016 ORANGE Software Co., Ltd. All Rights Reserved
 */
package com.orange.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

/**
 * <code>JwtUtils</code>类
 * @author xieqp
 * @version 1.0
 * @since jdk7
 */
public class JwtUtils {

	private JwtUtils(){
		super();
	}
	
	public static JwtUtils getInstance(){
		return SingletonHolder.instance;
	}
	
	private static final class SingletonHolder{
		public static final JwtUtils instance = new JwtUtils();
	}
	
	/**
	 * 使用默认的密钥和加密算法生成token
	 * @param payLoad 载荷
	 * @return 对应的jwt
	 * @throws Exception
	 */
	public String generateJWT(Payload payLoad) throws Exception{
		//使用默认的密钥和HS256作为加密算法
		return generateJWT(payLoad, JwtConstants.SECRET_KEY, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param payLoad 载荷
	 * @param secret 密钥
	 * @return 对应的jwt
	 * @throws Exception
	 */
	public String generateJWT(Payload payLoad, String secret) throws Exception{
		//默认使用HS256作为加密算法
		return generateJWT(payLoad, secret, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param payLoad 载荷
	 * @param secrt 密钥
	 * @param al 加密算法
	 * @return 加密的jwt
	 * @throws Exception
	 */
	public String generateJWT(Payload payLoad, String secrt, JWSAlgorithm al) throws Exception{
		if(null == payLoad){
			throw new Exception("payload is null, please check..");
		}
		JWSSigner signer = new MACSigner(secrt.getBytes());
		JWSHeader header = new JWSHeader(al);
		JWSObject jwsObject = new JWSObject(header, payLoad);
		try {
	      jwsObject.sign(signer);
	    }catch (JOSEException e) {
	      throw new Exception(e.getMessage());
	    }
		return jwsObject.serialize();
	}
	
	/**
	 * 使用默认的密钥和算法进行token签名校验
	 * @param token 检测的token
	 * @return token有效返回true，否则返回false
	 * @throws Exception
	 */
	public boolean verifyJWT(String token) throws Exception{
		//使用默认的密钥和HS256作为加密算法
		return verifyJWT(token, JwtConstants.SECRET_KEY, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param token 检测的token
	 * @param secret 密钥
	 * @return token有效返回true，否则返回false
	 * @throws Exception
	 */
	public boolean verifyJWT(String token, String secret) throws Exception{
		//默认使用HS256作为加密算法
		return verifyJWT(token, secret, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param token 检测的token
	 * @param secret 密钥
	 * @param al 加密算法
	 * @return token有效返回true，否则返回false
	 * @throws Exception
	 */
	public boolean verifyJWT(String token, String secret, JWSAlgorithm al) throws Exception{
		if(null == token){
			throw new Exception("token is null, please check..");
		}
		boolean verifiedSignature = false;
		try {
			JWSObject jwsObject = JWSObject.parse(token);
			JWSVerifier verifier = new MACVerifier(secret.getBytes());
			verifiedSignature = jwsObject.verify(verifier);
	    }catch (Exception e) {
	    	System.err.println("Couldn't verify signature: " + e.getMessage());
	    	//throw new Exception(e.getMessage());
	    }
		return verifiedSignature;
	}
}
