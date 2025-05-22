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

}
