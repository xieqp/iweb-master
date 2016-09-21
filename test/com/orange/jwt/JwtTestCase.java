/**
 * @(#) JwtTestCase.java 2016年9月14日 上午10:30:26
 * Copyright 2016 ORANGE Software Co., Ltd. All Rights Reserved
 */
package com.orange.jwt;

import org.junit.Test;

import com.nimbusds.jose.Payload;

import net.minidev.json.JSONObject;

/**
 * <code>JwtTestCase</code>类
 * @author xieqp
 * @version 1.0
 * @since jdk7
 */
public class JwtTestCase {

	@Test
	public void generateJwt4String(){
		// TODO Auto-generated method stub
		try {
			String message = "Hello World";
			Payload payload = new Payload(message);
			
			String token = JwtUtils.getInstance().generateJWT(payload);
			System.out.println(token);
			
			String token_helloworld = "eyJhbGciOiJIUzI1NiIsImN0eSI6InRleHRcL3BsYWluIn0.SGVsbG8gV29ybGQ.35kwt4hU26nt901Flu2DJXkuSnR5mGT3Mr2bPpfkJl0";
			System.out.println(JwtUtils.getInstance().verifyJWT(token_helloworld));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void generateJwt4Json(){
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject();
			json.put("uesrname", "admin");
			json.put("psw", "123456");
			
			Payload payload = new Payload(json);
			String token = JwtUtils.getInstance().generateJWT(payload);
			System.out.println(token);
			
			String token_json = "eyJhbGciOiJIUzI1NiIsImN0eSI6InRleHRcL3BsYWluIn0.eyJwc3ciOiIxMjM0NTYiLCJ1ZXNybmFtZSI6ImFkbWluIn0.votmcR27IyrWqCI6YI1zpnQbEzvEUfFgzV6WVBhjNaQ";
			System.out.println(JwtUtils.getInstance().verifyJWT(token_json));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
