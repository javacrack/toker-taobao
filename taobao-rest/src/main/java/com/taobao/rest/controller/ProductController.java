package com.taobao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.rest.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaoBaoResult getItemBaseInfo(@PathVariable Long itemId) {
		TaoBaoResult result = service.getProductBaseInfo(itemId);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaoBaoResult getItemDesc(@PathVariable Long itemId) {
		TaoBaoResult result = service.getProductDesc(itemId);
		return result;
	}
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaoBaoResult getProductParam(@PathVariable Long itemId) {
		TaoBaoResult result = service.getProductParam(itemId);
		return result;
	}


}
