package com.taobao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.common.pojo.CookieUtils;
import com.taobao.pojo.TbUser;
import com.taobao.portal.Dto.CartItemDto;
import com.taobao.portal.Dto.OrderDto;
import com.taobao.portal.service.CartService;
import com.taobao.portal.service.PortalOrderService;

@Controller
@RequestMapping("/order")
public class PortalOrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private PortalOrderService portalOrderService;

	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		//取购物车商品列表
		List<CartItemDto> list = cartService.getCartItemList(request, response);
		//传递给页面
		model.addAttribute("cartList", list);
		return "order-cart";
	}

	@RequestMapping("/create")
	public String createOrder(HttpServletRequest request,OrderDto order, Model model) {		
		TbUser user=(TbUser) request.getAttribute("user");
		order.setUserId(user.getId());
		order.setBuyerNick(user.getUsername());
		String orderId = portalOrderService.createOrder(order);		
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", order.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}

}
