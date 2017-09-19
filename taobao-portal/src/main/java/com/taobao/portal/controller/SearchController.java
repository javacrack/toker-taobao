package com.taobao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taobao.common.pojo.SearchResult;
import com.taobao.portal.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String searchItemList(@RequestParam(value="q")String queryString, Integer page, Model model) throws Exception {
		//字符串转码
		queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
		
		SearchResult searchResult = searchService.searchItemList(queryString, page);
		model.addAttribute("itemList", searchResult.getSolrProductList());
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("page", searchResult.getCurPage());	
		return "search";
	}
}
