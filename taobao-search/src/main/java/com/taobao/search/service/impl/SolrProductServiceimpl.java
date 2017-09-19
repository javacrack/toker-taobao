package com.taobao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.SolrProduct;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.ExceptionUtil;
import com.taobao.search.mapper.SolrProductMapper;
@Service
public class SolrProductServiceimpl implements com.taobao.search.service.SolrProductService {
	@Autowired
	private SolrProductMapper mapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public TaoBaoResult ImportProductsIntoSolr() {
		try {
			//查询所有商品信息
			List<SolrProduct> products=mapper.getSolrProducts();
			//把商品信息写入索引库
			SolrInputDocument document;
			for(SolrProduct pro:products) {
				document=new SolrInputDocument();
				document.setField("id", pro.getId());
				document.setField("item_title", pro.getTitle());
				document.setField("item_sell_point", pro.getSell_point());
				document.setField("item_price", pro.getPrice());
				document.setField("item_image", pro.getImage());
				document.setField("item_category_name", pro.getCategory_name());
				document.setField("item_desc", pro.getItem_desc());
				solrServer.add(document);

			}
			//提交修改
			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return TaoBaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaoBaoResult.ok();
	}

}
