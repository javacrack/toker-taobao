package com.taobao.search.service;

import com.taobao.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int page, int rows) throws Exception;

}
