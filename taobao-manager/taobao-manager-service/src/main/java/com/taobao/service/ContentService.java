package com.taobao.service;

import java.util.List;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;

public interface ContentService {

	EUDataGridResult getContentList(long categoryId, int currentPage, int pageSize);

	TaoBaoResult insertContent(TbContent content);

	TaoBaoResult deleteContents(String ids);
}
