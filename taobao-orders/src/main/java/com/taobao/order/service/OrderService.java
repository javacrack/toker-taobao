package com.taobao.order.service;

import java.util.List;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbOrderShipping;

public interface OrderService {
	TaoBaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) ;
}
