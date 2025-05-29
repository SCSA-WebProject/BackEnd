package com.ssafy.mvc.model.dto;

public class SearchCondition {
	private String orderBy;
	private String orderByDir;

	public SearchCondition() {
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByDir() {
		return orderByDir;
	}

	public void setOrderByDir(String orderByDir) {
		this.orderByDir = orderByDir;
	}

}
