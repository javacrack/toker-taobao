package com.taobao.portal.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.SearchResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.HttpClientUtil;
@Service
public class SearchServiceImpl implements SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult searchItemList(String queryString, Integer page) throws Exception {
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page==null?"1":page.toString());
		//调用taotao-search提供的搜索服务
		String resultString = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		//转换成taotaoResult对象
		TaoBaoResult taotaoResult = TaoBaoResult.formatToPojo(resultString, SearchResult.class);
		SearchResult searchResult = null;
		//查询成功
		if (taotaoResult.getStatus() == 200) {
			//取查询结果
			searchResult = (SearchResult) taotaoResult.getData();
		}
		
		return searchResult;

	}

}
