package com.taobao.rest.service;


import com.taobao.common.pojo.TaoBaoResult;

public interface ProductService {

	public TaoBaoResult getProductBaseInfo(long itemId);
	public TaoBaoResult getProductDesc(long itemId);
	public TaoBaoResult getProductParam(long itemId);
}
