package com.taobao.service;

import java.util.List;

import com.taobao.common.pojo.EUTreeNode;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbItemCat;

public interface CategoryService {
	 List<EUTreeNode> getCategoriesByParentId(long parentId);

}
