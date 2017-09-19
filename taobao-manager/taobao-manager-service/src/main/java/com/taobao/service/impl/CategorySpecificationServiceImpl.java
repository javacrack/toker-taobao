package com.taobao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.mapper.TbItemParamMapper;
import com.taobao.pojo.TbItemParam;
import com.taobao.pojo.TbItemParamExample;
import com.taobao.pojo.TbItemParamExample.Criteria;
import com.taobao.service.CategorySpecificationService;
@Service
public class CategorySpecificationServiceImpl implements CategorySpecificationService{
	@Autowired
	TbItemParamMapper itemParamMapper;
	/**
	 * 查询当前category是否存在规格参数
	 */
	@Override
	public TaoBaoResult queryCatalogById(long cid) throws Exception {
		// TODO Auto-generated method stub
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//如果查询中有结果返回查询结果
		if (!CollectionUtils.isEmpty(list)) {
			return TaoBaoResult.ok(list.get(0));
		}
		//查询成功但是没有查到数据
		return TaoBaoResult.ok();

	}
	
	/**
	 * 保存category对应的规格参数
	 */
	@Override
	public TaoBaoResult saveItemParam(long cid, String itemParam) throws Exception {
		//创建TbItemParam对象
		TbItemParam param = new TbItemParam();
		param.setItemCatId(cid);
		param.setParamData(itemParam);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		//向数据库添加数据
		itemParamMapper.insert(param);
		
		return TaoBaoResult.ok();
		
	}


}
