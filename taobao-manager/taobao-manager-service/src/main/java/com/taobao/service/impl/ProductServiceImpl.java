package com.taobao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.IDUtils;
import com.taobao.mapper.TbItemDescMapper;
import com.taobao.mapper.TbItemMapper;
import com.taobao.mapper.TbItemParamItemMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemExample;
import com.taobao.pojo.TbItemExample.Criteria;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getTbItemById(long id) {
		//第一种方法
//		itemMapper.selectByPrimaryKey(id);
		//第二种按条件查询
		TbItemExample example=new TbItemExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(id);
		
		List<TbItem> items=itemMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(items)) {
			return items.get(0);
		}
		return null;
	}

	@Override
	public EUDataGridResult getItems(int currentPage, int pageSize) {
		TbItemExample example=new TbItemExample();
		PageHelper.startPage(currentPage, pageSize);
		List<TbItem> items=itemMapper.selectByExample(example);
		EUDataGridResult results=new EUDataGridResult();
		PageInfo<TbItem> infos=new PageInfo<TbItem>(items);
		results.setTotal(infos.getTotal());
		results.setRows(items);
		return results;
	}

	@Override
	public TaoBaoResult createItem(TbItem tbItem, String desc,String itemParams) throws Exception {
		long itemId=IDUtils.genItemId();
		tbItem.setId(itemId);
		Date date=new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItem.setStatus((byte) 1);
		itemMapper.insert(tbItem);
		TaoBaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception(); //事物交给spring控制了，有异常spring会回滚
		}
		
		//添加商品规格
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		
		itemParamItemMapper.insert(itemParamItem);


		return TaoBaoResult.ok();
	}
	/**
	 *添加商品描述 
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaoBaoResult insertItemDesc(Long itemId, String desc) {
		try {
			TbItemDesc itemDesc = new TbItemDesc();
			itemDesc.setItemId(itemId);
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(new Date());
			itemDescMapper.insert(itemDesc);
		} catch (Exception e) {
			return null;
		}
		return TaoBaoResult.ok();
	}


	
}
