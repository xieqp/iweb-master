/**
 * @(#) JwtUtils.java 2016��9��13�� ����8:54:11
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
 * <code>JwtUtils</code>��
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
	 * ʹ��Ĭ�ϵ���Կ�ͼ����㷨����token
	 * @param payLoad �غ�
	 * @return ��Ӧ��jwt
	 * @throws Exception
	 */
	public String generateJWT(Payload payLoad) throws Exception{
		//ʹ��Ĭ�ϵ���Կ��HS256��Ϊ�����㷨
		return generateJWT(payLoad, JwtConstants.SECRET_KEY, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param payLoad �غ�
	 * @param secret ��Կ
	 * @return ��Ӧ��jwt
	 * @throws Exception
	 */
	public String generateJWT(Payload payLoad, String secret) throws Exception{
		//Ĭ��ʹ��HS256��Ϊ�����㷨
		return generateJWT(payLoad, secret, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param payLoad �غ�
	 * @param secrt ��Կ
	 * @param al �����㷨
	 * @return ���ܵ�jwt
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
	 * ʹ��Ĭ�ϵ���Կ���㷨����tokenǩ��У��
	 * @param token ����token
	 * @return token��Ч����true�����򷵻�false
	 * @throws Exception
	 */
	public boolean verifyJWT(String token) throws Exception{
		//ʹ��Ĭ�ϵ���Կ��HS256��Ϊ�����㷨
		return verifyJWT(token, JwtConstants.SECRET_KEY, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param token ����token
	 * @param secret ��Կ
	 * @return token��Ч����true�����򷵻�false
	 * @throws Exception
	 */
	public boolean verifyJWT(String token, String secret) throws Exception{
		//Ĭ��ʹ��HS256��Ϊ�����㷨
		return verifyJWT(token, secret, JWSAlgorithm.HS256);
	}
	
	/**
	 * 
	 * @param token ����token
	 * @param secret ��Կ
	 * @param al �����㷨
	 * @return token��Ч����true�����򷵻�false
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
