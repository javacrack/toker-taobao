package com.taobao.rest.service;

import java.util.List;

import com.taobao.pojo.TbContent;

public interface ContentService {
	 public List<TbContent> getContentList(long contentCid);
}
