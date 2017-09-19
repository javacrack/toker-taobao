package com.taobao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.rest.service.CacheService;

@Controller
@RequestMapping("/redis")
public class RedisController {
	@Autowired
	private CacheService redisService;
	
	@RequestMapping("/content/{contentCid}")
	public TaoBaoResult contentCacheSync(@PathVariable Long contentCid) {
		TaoBaoResult result = redisService.clearContentCache(contentCid);
		return result;
	}

}
