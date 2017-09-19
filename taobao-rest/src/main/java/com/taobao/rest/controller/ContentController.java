package com.taobao.rest.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;
import com.taobao.rest.service.ContentService;
import com.taobao.common.utils.ExceptionUtil;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaoBaoResult getContentList(@PathVariable Long contentCategoryId) {
		try {
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return TaoBaoResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			//这个错误发给其他接口的程序员看的
			return TaoBaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
