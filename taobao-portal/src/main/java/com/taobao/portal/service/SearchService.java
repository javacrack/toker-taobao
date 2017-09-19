package com.taobao.portal.service;

import com.taobao.common.pojo.SearchResult;

public interface SearchService {
	SearchResult searchItemList(String queryString, Integer page) throws Exception;
}
