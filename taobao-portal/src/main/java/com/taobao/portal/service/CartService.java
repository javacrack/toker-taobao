package com.taobao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.portal.Dto.CartItemDto;

public interface CartService {
	public TaoBaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	public List<CartItemDto> getCartItemList(HttpServletRequest request, HttpServletResponse response);
	
	public TaoBaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) ;
}
