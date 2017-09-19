package com.taobao.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.service.ProductSpecificationService;

@Controller
@RequestMapping("/product/")
public class ProductSpecificationController {
	@Autowired
	private ProductSpecificationService productSpecificationService;
	
	@RequestMapping("/specification/{itemId}")
	public String getItemParamItemById(@PathVariable Long itemId, Model model) {
		String itemParamItem = productSpecificationService.geParamItemByItemId(itemId);
		model.addAttribute("itemParam", itemParamItem);
		return "item-param-show";
	}

}
