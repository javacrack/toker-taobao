package com.taobao.service;

import com.taobao.common.pojo.EUDataGridResult;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbItem;

public interface ProductService {
	TbItem getTbItemById(long id);
	EUDataGridResult getItems(int currentPage,int rows);
	TaoBaoResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception;
}
