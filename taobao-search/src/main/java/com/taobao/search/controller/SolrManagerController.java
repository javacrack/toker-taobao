package com.taobao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.search.service.SolrProductService;

@Controller
@RequestMapping("/manager")
public class SolrManagerController {
	@Autowired
	private SolrProductService ps;
	
	@RequestMapping("import")
	@ResponseBody
	public TaoBaoResult impProductIntoSolr() {
		return ps.ImportProductsIntoSolr();
	}
}
