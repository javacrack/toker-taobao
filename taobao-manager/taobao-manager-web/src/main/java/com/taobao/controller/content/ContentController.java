package com.taobao.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;
import com.taobao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list")
    @ResponseBody
	public EUDataGridResult getContentCatList(Long categoryId,@RequestParam(value="page", defaultValue="0")int page,@RequestParam int rows) {
		EUDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaoBaoResult insertContent(TbContent content) {
		TaoBaoResult result = contentService.insertContent(content);
		return result;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public TaoBaoResult deleteContent(String ids) {
		TaoBaoResult result = contentService.deleteContents(ids);
		return result;
	}
}
