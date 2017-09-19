package com.taobao.service;

import com.taobao.common.pojo.TaoBaoResult;

public interface CategorySpecificationService {
	public TaoBaoResult queryCatalogById(long cid) throws Exception;

	TaoBaoResult saveItemParam(long cid, String itemParam) throws Exception;
}
