package com.taobao.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.HttpClientUtil;
import com.taobao.mapper.TbContentMapper;
import com.taobao.pojo.TbContent;
import com.taobao.pojo.TbContentExample;
import com.taobao.pojo.TbContentExample.Criteria;
import com.taobao.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_CONTENT_REDIS_MANAGEMENT}")
	private String REST_CONTENT_REDIS_MANAGEMENT;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Override
	public EUDataGridResult getContentList(long categoryId,int currentPage, int pageSize) {
		TbContentExample example = new TbContentExample();
		PageHelper.startPage(currentPage, pageSize);
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents=contentMapper.selectByExample(example);
		EUDataGridResult results=new EUDataGridResult();
		PageInfo<TbContent> infos=new PageInfo<TbContent>(contents);
		results.setRows(contents);
		results.setTotal(infos.getTotal());
		return results;
	}
	@Override
	public TaoBaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_REDIS_MANAGEMENT+content.getCategoryId());
		}catch(Exception e){
			e.printStackTrace();//通知管理员 而不能阻止正常的业务逻辑
		}
		return TaoBaoResult.ok();
	}
	@Override
	public TaoBaoResult deleteContents(String ids) {
//		if(ids.contains(",")) {
			List<String> result=Arrays.asList(ids.split(","));
			for(String id:result) {
				contentMapper.deleteByPrimaryKey(Long.parseLong(id));
			}
//		}
		
		return TaoBaoResult.ok();
	}

}
