package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardSearch;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface BoardDao {

	// 전체 맛집 목록을 조회
	public abstract List<Board> selectAll(BoardSearch boardSearch);

	// ID에 해당하는 맛집 하나 가져오기
	public abstract Board selectOne(int id);

	// 맛집 등록
	public int insertBoard(Board board);

	// 맛집 삭제
	public void deleteBoard(int id);

	// 맛집 수정
	public void updateBoard(Board board);

	// 검색 기능
	public List<Board> selectByWhere(SearchCondition condition);

	public abstract int selectBoardCount(BoardSearch boardSearch);
	
	void insertBoardFile(BoardFile boardFile);
	
	BoardFile selectBoardFileByNo(int id);

	// 좋아요 추가/삭제
	void toggleLike(int boardId, String userId);
	
	// 좋아요 상태 확인
	boolean checkLike(int boardId, String userId);
	
	// 좋아요 수 조회
	int getLikeCount(int boardId);

	// 최근 등록된 맛집 목록
	List<Board> selectRecentBoards(int limit);
	
	// 좋아요가 많은 맛집 목록
	List<Board> selectPopularBoards(int limit);

}
