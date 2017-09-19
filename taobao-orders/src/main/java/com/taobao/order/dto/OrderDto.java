package com.taobao.order.dto;

import java.util.List;

import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbOrderShipping;

public class OrderDto extends TbOrder{

	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
