package com.kjtt.test.controller;

import org.pcloud.spring.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value ="test")
public class Test extends BaseController {

	
	@RequestMapping(value="ms")
	public void test(){
		System.out.println("ssss");
		System.out.println("ssss");
		System.out.println("ssss");
		System.out.println("ssss");
		System.out.println("ssss");
		
	}
}
