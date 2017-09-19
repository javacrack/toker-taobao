package com.taobao.service;

import java.util.List;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.pojo.TaoBaoResult;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);

	TaoBaoResult insertContentCategory(long parentId, String name);
	
	TaoBaoResult deleteContentCategory(long parentId, long id);

	TaoBaoResult updateCategoryName(Long id, String name);
}
