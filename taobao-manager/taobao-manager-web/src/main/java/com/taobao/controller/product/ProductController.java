package com.taobao.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbItem;
import com.taobao.service.ProductService;

@Controller
@RequestMapping("/item")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemByItemId(@PathVariable long itemId) {
		TbItem item=productService.getTbItemById(itemId);
		return item;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItems(int page,int  rows) {
		EUDataGridResult results=productService.getItems(page, rows);
		return results;
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public TaoBaoResult saveItem(TbItem item,String desc,String itemParams) throws Exception {
		//添加商品信息- 
		TaoBaoResult result=productService.createItem(item,desc,itemParams);
		return result;
	}

	
	
}
