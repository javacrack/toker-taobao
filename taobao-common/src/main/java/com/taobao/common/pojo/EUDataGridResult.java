package com.taobao.common.pojo;

import java.util.List;

public class EUDataGridResult {
	private long  total;
	/*
	 * 加了T之后class上也要加T
	 */
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}	
