package com.taobao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.mapper.TbItemCatMapper;
import com.taobao.pojo.TbItemCat;
import com.taobao.pojo.TbItemCatExample;
import com.taobao.pojo.TbItemCatExample.Criteria;
import com.taobao.pojo.TbItemParam;
import com.taobao.pojo.TbItemParamExample;
import com.taobao.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	TbItemCatMapper mapper;
	@Override
	public List<EUTreeNode> getCategoriesByParentId(long parentId) {
		TbItemCatExample example= new TbItemCatExample();
		Criteria criteria =example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> categories=mapper.selectByExample(example);
		List<EUTreeNode> nodes=new ArrayList<EUTreeNode>();
		for(TbItemCat category:categories) {
			EUTreeNode node=new EUTreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}



}
