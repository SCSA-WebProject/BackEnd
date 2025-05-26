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
	
	// 좋아요 토글
	void toggleLike(int boardId, String userId);
	
	// 좋아요 상태 확인
	boolean checkLike(int boardId, String userId);
	
	// 좋아요 수 조회
	int getLikeCount(int boardId);
	
	// 최근 등록된 맛집 목록
	List<Board> getRecentBoards(int limit);
	
	// 좋아요가 많은 맛집 목록
	List<Board> getPopularBoards(int limit);
}
