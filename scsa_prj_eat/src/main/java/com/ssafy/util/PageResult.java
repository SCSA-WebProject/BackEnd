package com.ssafy.util;

public class PageResult {

	private int page;
	private int beginPage;
	private int endPage;
	private int lastPage;
	private boolean prev;
	private boolean next;

	private static final int LIST_SIZE = 10;
	private static final int TAB_SIZE = 10;

	public PageResult(int page, int totalCount) {
		this(page, totalCount, LIST_SIZE, TAB_SIZE);
	}

	public PageResult(int page, int totalCount, int listSize) {
		this(page, totalCount, listSize, TAB_SIZE);
	}

	public PageResult(int page, int totalCount, int listSize, int tabSize) {
		this.page = page;
		this.lastPage = (totalCount % listSize == 0) ? totalCount / listSize : totalCount / listSize + 1;
		int tab = (page - 1) / tabSize + 1;
		this.beginPage = (tab - 1) * tabSize + 1;
		this.endPage = (tab * tabSize < lastPage) ? tab * tabSize : lastPage;

		this.prev = beginPage != 1;
		this.next = endPage != lastPage;
	}

	public int getPage() {
		return page;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}
	

}
