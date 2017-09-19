package com.taobao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.mapper.TbItemMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemExample;

public class PageHelperTest {
	@Test
	public void PageHelperTest() {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		TbItemMapper mapper=applicationContext.getBean(TbItemMapper.class);
		TbItemExample example=new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> items=mapper.selectByExample(example);
		//取商品列表
		for(TbItem item:items) {
			System.out.println(item.getTitle());
		}
		//取分页信息
		PageInfo<TbItem> infos=new PageInfo<TbItem>(items);
		System.out.println(infos.getTotal());
	}
}