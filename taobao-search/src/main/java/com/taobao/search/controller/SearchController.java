package com.taobao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.SearchResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.ExceptionUtil;
import com.taobao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/query", method=RequestMethod.GET)
	@ResponseBody
	public TaoBaoResult search(@RequestParam("q")String queryString, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="60")Integer rows) {
		//查询条件不能为空
		if (StringUtils.isBlank(queryString)) {
			return TaoBaoResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			//Tomcat默认的编码为ISO8859-1，需要转换成utf-8的编码。
			queryString = new String(queryString.getBytes("ISO8859-1"),"utf-8");
			searchResult = searchService.search(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return TaoBaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaoBaoResult.ok(searchResult);
		
	}
	
}
