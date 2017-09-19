package com.taobao.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.ExceptionUtil;
import com.taobao.order.dto.OrderDto;
import com.taobao.order.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/create")
	@ResponseBody
	public TaoBaoResult createOrder(@RequestBody OrderDto orderDto) {
		try {
			TaoBaoResult result = orderService.createOrder(orderDto, orderDto.getOrderItems(), orderDto.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaoBaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/echo")
	@ResponseBody
	public String echo() {
		return "Order System is running ";
	}
}
