package com.taobao.portal.service;

import com.taobao.portal.Dto.ProductPortalItem;

public interface ProductService {
	public ProductPortalItem getItemById(Long itemId) ;
	public String getItemDescById(Long itemId);
	public String getItemParam(Long itemId);
}
