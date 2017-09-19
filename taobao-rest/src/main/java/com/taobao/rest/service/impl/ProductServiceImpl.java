package com.taobao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.JsonUtils;
import com.taobao.mapper.TbItemDescMapper;
import com.taobao.mapper.TbItemMapper;
import com.taobao.mapper.TbItemParamItemMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemParamItemExample.Criteria;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.pojo.TbItemParamItemExample;
import com.taobao.rest.dao.JedisClient;
import com.taobao.rest.dao.impl.JedisClientSingle;
import com.taobao.rest.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private TbItemMapper productMapper;
	@Autowired
	private TbItemDescMapper productDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	@Autowired
	@Qualifier("jedisClientSingle")
	private JedisClient jedisClient;


	@Override
	public TaoBaoResult getProductBaseInfo(long itemId) {
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaoBaoResult.ok(item);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItem 	item=productMapper.selectByPrimaryKey(itemId);	
		
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaoBaoResult.ok(item);
	}


	@Override
	public TaoBaoResult getProductDesc(long itemId) {
		TbItemDesc itemDesc ;
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				itemDesc= JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TaoBaoResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//创建查询条件
		itemDesc = productDescMapper.selectByPrimaryKey(itemId);
		try {
			//把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			//设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return TaoBaoResult.ok(itemDesc);
	}


	@Override
	public TaoBaoResult getProductParam(long itemId) {
		TbItemParamItem paramItem;
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				paramItem= JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaoBaoResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size()>0) {
			paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaoBaoResult.ok(paramItem);
		}
		return TaoBaoResult.build(400, "无此商品规格");

	}

}
