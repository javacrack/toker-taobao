package com.taobao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.ExceptionUtil;
import com.taobao.rest.dao.JedisClient;
import com.taobao.rest.service.CacheService;
@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
	@Qualifier("jedisClientSingle")
	private JedisClient jedisClient;
	@Value("${HOME_CONTENT_REDIS_KEY}")
	private String HOME_CONTENT_REDIS_KEY;

	@Override
	public TaoBaoResult clearContentCache(Long cid)  {		
		try {
			jedisClient.hdel(HOME_CONTENT_REDIS_KEY, String.valueOf(cid));
		} catch (Exception e) {
			TaoBaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaoBaoResult.ok();
	}

}
