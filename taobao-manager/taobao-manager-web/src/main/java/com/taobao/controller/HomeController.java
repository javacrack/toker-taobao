package com.taobao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger logger=LoggerFactory.getLogger(HomeController.class);
	@RequestMapping("/")
	public String homePage() {
		logger.info("进入首页了!!!");
		return "index";
	}
	/**
	 * <span>商品管理</span>
 		<ul>
     		<li data-options="attributes:{'url':'item-add'}">新增商品</li>
     		<li data-options="attributes:{'url':'item-list'}">查询商品</li>
     		<li data-options="attributes:{'url':'item-param-list'}">规格参数</li>
     	</ul>
	 * @param name
	 * @return 跳转到相应的名称的jsp
	 */
	@RequestMapping("/{pageUrl}")
	public String homePage(@PathVariable(value="pageUrl") String name) {
		return name;
	}
}
