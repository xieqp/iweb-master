/**
 * @(#) LoginController.java 2016年8月17日 下午8:29:28
 * Copyright 2016 ORANGE Software Co., Ltd. All Rights Reserved
 */
package com.orange.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <code>LoginController</code>类
 * @author xieqp
 * @version 1.0
 * @since jdk7
 */

@Controller
public class LoginController {
	
	@RequestMapping("/greeting")  
    public ModelAndView greeting(@RequestParam(value="name", defaultValue="World") String name) {  
         Map<String, Object> map = new HashMap<String, Object>();  
         map.put("userName", name);  
         return new ModelAndView("/hello", map);  
    }
	

	
}
