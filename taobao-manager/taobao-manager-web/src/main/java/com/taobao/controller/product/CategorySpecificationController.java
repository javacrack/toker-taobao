package com.taobao.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.service.CategorySpecificationService;

@Controller
@RequestMapping("/item/param")
public class CategorySpecificationController {
	@Autowired
	private CategorySpecificationService catSpecificationService;
	@RequestMapping("query/itemcatid/{cid}")
	@ResponseBody
	public TaoBaoResult queryCatalogById(@PathVariable long cid) throws Exception {
		//根据分类id查询列表
		TaoBaoResult result = catSpecificationService.queryCatalogById(cid);
		return result;
	}

	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaoBaoResult saveItemParam(@PathVariable Long cid, String paramData) throws Exception {
		TaoBaoResult result = catSpecificationService.saveItemParam(cid, paramData);
		return result;
	}

}
