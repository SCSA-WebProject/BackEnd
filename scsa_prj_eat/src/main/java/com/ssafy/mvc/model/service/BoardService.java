package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardSearch;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface BoardService {
	// 맛집 전체 조회
	public Map<String, Object> getBoardList(BoardSearch boardSearch);

	// 맛집 상세조회
	public Board readBoard(int id);
	
	// 맛집 등록
	public void writeBoard(Board board);
	
	// 맛집 수정
	public void modifyBoard(Board board);
	
	// 맛집 삭제
	public void removeBoard(int id);
	
	// 맛집 검색
	public List<Board> searchBoard(SearchCondition condition);
}
