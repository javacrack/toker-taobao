package com.taobao.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.service.CategoryService;

@Controller
@RequestMapping("/item/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> categoryList(@RequestParam(value="id", defaultValue="0") Long parentId) {
		/**
		 * 如果这里不建EUTreeNode这个pojo对象也是可以的我们可以把他转换到hashmap里去
		 * for (TbItemCat tbItemCat : list) {
			Map node = new HashMap<>();
			node.put("id", tbItemCat.getId());
			node.put("text", tbItemCat.getName());			
			node.put("state", tbItemCat.getIsParent()?"closed":"open");
			catList.add(node);
		 */
		
		 List<EUTreeNode> nodes=categoryService.getCategoriesByParentId(parentId);
		 return nodes;
	}
}
