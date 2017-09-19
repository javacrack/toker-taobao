package com.taobao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * @author Toker
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {
	@RequestMapping("/showRegister")
	public String showRegister() {
		return "register";
	}
	@RequestMapping("/showLogin")
	public String showLogin(String redirect,Model model) {
		model.addAttribute("redirect",redirect);
		return "login";
	}
}
