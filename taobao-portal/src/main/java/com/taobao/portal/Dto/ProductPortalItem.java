package com.taobao.portal.Dto;

import com.taobao.pojo.TbItem;

public class ProductPortalItem extends TbItem{
	public String[] getImages() {
		String image = getImage();
		if (image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}

}
