package com.ssafy.mvc.model.dto;

public class Board {
	private int id;
	private String title;
	private String region;
	private String category;
	private int price;
	private String userId;
	private BoardFile boardFile;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public BoardFile getBoardFile() {
		return boardFile;
	}
	public void setBoardFile(BoardFile boardFile) {
		this.boardFile = boardFile;
	}

	public Board() {
	}

	public Board(String title, String region, String category, int price, String userId) {
		super();
		this.title = title;
		this.region = region;
		this.category = category;
		this.price = price;
		this.userId = userId;
	}

	public Board(int id, String title, String region, String category, int price, String userId) {
		this.id = id;
		this.title = title;
		this.region = region;
		this.category = category;
		this.price = price;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", region=" + region + ", category=" + category + ", price=" + price + "]";
	}
}
