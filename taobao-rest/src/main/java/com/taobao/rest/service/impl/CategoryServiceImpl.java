package com.taobao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.mapper.TbItemCatMapper;
import com.taobao.pojo.TbItemCat;
import com.taobao.pojo.TbItemCatExample;
import com.taobao.pojo.TbItemCatExample.Criteria;
import com.taobao.rest.dto.CategoriesDto;
import com.taobao.rest.dto.CategoryDto;
import com.taobao.rest.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private TbItemCatMapper catMapper;

	@Override
	public CategoriesDto getAllCategories() {
		CategoriesDto catgories=new CategoriesDto();
		catgories.setData(getCategoryList(0));
		return catgories;
	}
	
	private List<?> getCategoryList(long parentId){
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria=example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		 List<TbItemCat> cats=catMapper.selectByExample(example);
		 List datas=new ArrayList();
		 for(TbItemCat tbItemCat : cats) {
			 
			//判断是否为父节点
			 
			if (tbItemCat.getIsParent()) {
				CategoryDto cat = new CategoryDto();
				cat.setUrl("/product/" + tbItemCat.getId() + ".html");
				cat.setName(tbItemCat.getName());
				if(parentId==0) {
					cat.setName("<a href='/product/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				 }
				
				//递归调用
				cat.setItem(getCategoryList(tbItemCat.getId()));
				//添加到列表
				datas.add(cat);
			} else {
				String catItem = "/product/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
				datas.add(catItem);
			}

		 }
		return datas;
	}
}
