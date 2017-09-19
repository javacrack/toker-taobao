package com.taobao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.portal.Dto.ProductPortalItem;
import com.taobao.portal.service.ProductService;

@Controller
@RequestMapping("/item")
public class ProductController {
	@Autowired
	private ProductService service;

	@RequestMapping("/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model) {
		ProductPortalItem item = (ProductPortalItem) service.getItemById(itemId);
		model.addAttribute("item", item);
		
		return "item";
	}
	@RequestMapping(value="/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String string = service.getItemDescById(itemId);
		return string;
	}
	
	@RequestMapping(value="param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String string = service.getItemParam(itemId);
		return string;
	}


}
