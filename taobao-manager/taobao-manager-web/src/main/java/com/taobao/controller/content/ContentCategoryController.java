package com.taobao.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
    @ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaoBaoResult createContentCategory(Long parentId, String name) {
		TaoBaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaoBaoResult deleteContentCategory(Long parentId, Long id) {
		TaoBaoResult result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
	}
	@RequestMapping("/update")
	@ResponseBody
	public TaoBaoResult updateContentCategory(Long id, String name) {
		TaoBaoResult result = contentCategoryService.updateCategoryName(id, name);
		return result;
	}
}
