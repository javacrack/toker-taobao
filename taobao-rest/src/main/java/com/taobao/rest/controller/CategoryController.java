package com.taobao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.utils.JsonUtils;
import com.taobao.rest.dto.CategoriesDto;
import com.taobao.rest.service.CategoryService;
@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService service;
	/**
	 * 接收页面传递过来的参数。参数就是方法的名称。返回一个json数据，需要把json数据包装成一句js代码。返回一个字符串
	 */
	/**
	 * 方法一：
	 * 使用MappingJacksonValue对象包装返回结果，并设置jsonp的回调方法
	 * spring4.1后才支持的
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public MappingJacksonValue queryAll(String callback) throws Exception {
		//查询分类列表
		CategoriesDto result = service.getAllCategories();
		//包装jsonp
		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		//设置包装的回调方法名
		jacksonValue.setJsonpFunction(callback);
		
		return jacksonValue;
	}
	
	/**
	 * 方法二：
	 * 先把ItemCatResult对象转换成json字符串，然后使用字符串拼接的方法拼装成jsonp格式的数据。
	 * 需要设置相应结果的MediaType。
	 * @param callback
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public String queryAll(String callback) throws Exception {
//		//查询分类列表
//		CategoriesDto result = service.queryAllCategory();
//		//把对象转换成json数据
//		String jsonResult = JsonUtils.objectToJson(result);
//		//拼接字符串
//		String resultStr = callback + "(" + jsonResult + ");";
//		
//		return resultStr;
//	}


}
