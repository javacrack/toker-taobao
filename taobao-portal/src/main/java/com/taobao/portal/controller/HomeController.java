package com.taobao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.portal.service.ContentService;

@Controller
public class HomeController {
	@Autowired
	private ContentService ContentService;
	@RequestMapping("/index")
	public String HomePage(Model model) {
		String adJson = ContentService.getContentList();
		model.addAttribute("ad1", adJson);

		return "index";
	}
}
