package com.taobao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taobao.common.pojo.SearchResult;
import com.taobao.common.pojo.SolrProduct;
import com.taobao.search.dao.SearchDao;

@Repository
public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//返回值对象
		SearchResult result = new SearchResult();
		//根据查询条件查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取查询结果总数量
		result.setRecordCount(solrDocumentList.getNumFound());
		//商品列表
		List<SolrProduct> solrProductList = new ArrayList<>();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//取商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			//创建一商品对象
			SolrProduct solrProduct = new SolrProduct();
			solrProduct.setId(Long.parseLong((String) solrDocument.get("id")));
			//取高亮显示的结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size()>0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			solrProduct.setTitle(title);
			solrProduct.setImage((String) solrDocument.get("item_image"));
			solrProduct.setPrice((long) solrDocument.get("item_price"));
			solrProduct.setSell_point((String) solrDocument.get("item_sell_point"));
			solrProduct.setCategory_name((String) solrDocument.get("item_category_name"));
			//添加的商品列表
			solrProductList.add(solrProduct);
		}
		result.setSolrProductList(solrProductList);
		return result;
	}

}
