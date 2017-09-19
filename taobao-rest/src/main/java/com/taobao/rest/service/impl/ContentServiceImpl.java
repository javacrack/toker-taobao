package com.taobao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.taobao.common.utils.JsonUtils;
import com.taobao.mapper.TbContentMapper;
import com.taobao.pojo.TbContent;
import com.taobao.pojo.TbContentExample;
import com.taobao.pojo.TbContentExample.Criteria;
import com.taobao.rest.dao.JedisClient;
import com.taobao.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	@Qualifier("jedisClientSingle")
	private JedisClient jedisClient;
	@Value("${HOME_CONTENT_REDIS_KEY}")
	private String HOME_CONTENT_REDIS_KEY;

	@Override
	public List<TbContent> getContentList(long contentCid) {
		try {
			String cacheValue=jedisClient.hget(HOME_CONTENT_REDIS_KEY, String.valueOf(contentCid));
			if(StringUtils.isNotBlank(cacheValue)) {
				List<TbContent> list=JsonUtils.jsonToList(cacheValue, TbContent.class);
				return list;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		//根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			try {
				String cacheValue=JsonUtils.objectToJson(list);
				jedisClient.hset(HOME_CONTENT_REDIS_KEY, String.valueOf(contentCid), cacheValue);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;

	}

}
