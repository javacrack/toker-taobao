package com.taobao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.JsonUtils;
import com.taobao.portal.Dto.OrderDto;
import com.taobao.portal.service.PortalOrderService;

@Service
public class PortalOrderServiceImpl implements PortalOrderService{
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	

	@Override
	public String createOrder(OrderDto order) {
		//调用taotao-order的服务提交订单。
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//把json转换成taotaoResult
		TaoBaoResult taotaoResult = TaoBaoResult.format(json);
		if (taotaoResult.getStatus() == 200) {
			Integer temp=(Integer) taotaoResult.getData();
			Long orderId = temp.longValue();
//			Long orderId2=Long.valueOf((Integer) taotaoResult.getData());
			return orderId.toString();
		}
		return "";
	}

}
