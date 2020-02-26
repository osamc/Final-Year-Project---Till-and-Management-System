package com.sam.tillsystem.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sam.tillsystem.models.page.PageInfo;
import com.sam.tillsystem.service.PageImpl;

@Controller
@EnableAutoConfiguration
@RequestMapping("/test")
public class test {

	@Autowired
	PageImpl impl;
	
	@RequestMapping("/helloworld")
	@ResponseBody
	public String hello() {
		impl.createPage("test", 1, 1);
		return "Hello";
	}
	
	@RequestMapping("/getId/{id}")
	@ResponseBody
	public PageInfo getTest(@PathVariable("id") int id) {
		PageInfo info = impl.getPage(id);
		return info;
	}
	
	
	
}
