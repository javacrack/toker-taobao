package com.taobao.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {
	//转换成json数据时使用u作为key	
	@JsonProperty("u")
	private String url;
	
	@JsonProperty("n")
	private String name;
	
	@JsonProperty("i")
	private List<?> item;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<?> getItem() {
		return item;
	}

	public void setItem(List<?> item) {
		this.item = item;
	}

	
}	
