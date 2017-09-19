package com.taobao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.taobao.common.pojo.CookieUtils;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.JsonUtils;
import com.taobao.pojo.TbItem;
import com.taobao.portal.Dto.CartItemDto;
import com.taobao.portal.service.CartService;
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${PRODUCT_INFO_URL}")
	private String PRODUCT_INFO_URL;

	@Override
	public TaoBaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		//取商品信息
		CartItemDto cartItem = null;
		//取购物车商品列表
		List<CartItemDto> itemList = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		if(!CollectionUtils.isEmpty(itemList)) {
			for (CartItemDto cItem : itemList) {
				//如果存在此商品
				if (cItem.getId() == itemId) {
					//增加商品数量
					cItem.setNum(cItem.getNum() + num);
					cartItem = cItem;
					break;
				}
			}
		}
		
		if (cartItem == null) {
			cartItem = new CartItemDto();
			//根据商品id查询商品基本信息。
			String json = HttpClientUtil.doGet(REST_BASE_URL + PRODUCT_INFO_URL + itemId); 
			//把json转换成java对象
			TaoBaoResult taotaoResult = TaoBaoResult.formatToPojo(json, TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem item = (TbItem) taotaoResult.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage() == null ? "":item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
			}
			//添加到购物车列表
			itemList.add(cartItem);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		
		return TaoBaoResult.ok();

	}

	/**
	 * 从cookie中取商品列表
	 * <p>Title: getCartItemList</p>
	 * <p>Description: </p>
	 * @return
	 */
	private List<CartItemDto> getCartItemList(HttpServletRequest request) {
		//从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cartJson == null) {
			return new ArrayList<>();
		}
		//把json转换成商品列表
		try {
			List<CartItemDto> list = JsonUtils.jsonToList(cartJson, CartItemDto.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<CartItemDto> getCartItemList(HttpServletRequest request, HttpServletResponse response) {		
		return getCartItemList(request);
	}

	@Override
	public TaoBaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<CartItemDto> itemList = getCartItemList(request);
		//从列表中找到此商品
		for (CartItemDto cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				itemList.remove(cartItem);
				break;
			}
				 
		}
		//把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		
		return TaoBaoResult.ok();

	}

}
