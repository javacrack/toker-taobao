package com.taobao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.mapper.TbContentCategoryMapper;
import com.taobao.pojo.TbContentCategory;
import com.taobao.pojo.TbContentCategoryExample;
import com.taobao.pojo.TbContentCategoryExample.Criteria;
import com.taobao.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 内容分类管理树形节点列表
	 */
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");			
			resultList.add(node);
		}
		return resultList;

	}
	@Override
	public TaoBaoResult insertContentCategory(long parentId, String name) {		
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaoBaoResult.ok(contentCategory);
	}
	
	@Override
	public TaoBaoResult deleteContentCategory(long parentId, long id) {
		// TODO Auto-generated method stub
		contentCategoryMapper.deleteByPrimaryKey(id);
		List<EUTreeNode> nodes= getCategoryList(parentId);
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(CollectionUtils.isEmpty(nodes)&&parentCat.getIsParent()) {
			parentCat.setIsParent(false);
		}
		return TaoBaoResult.ok();
		
	}
	@Override
	public TaoBaoResult updateCategoryName(Long id, String name) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		TbContentCategory record = new TbContentCategory();
		record.setName(name);
		contentCategoryMapper.updateByExampleSelective(record, example);
		return TaoBaoResult.ok();
	}
	
}
