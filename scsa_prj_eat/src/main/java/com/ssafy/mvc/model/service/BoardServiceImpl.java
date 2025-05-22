package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardSearch;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.util.PageResult;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;

	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public Map<String, Object> getBoardList(BoardSearch boardSearch) {
		List<Board> list = boardDao.selectAll(boardSearch);
		for (Board board : list) {
			board.setBoardFile(boardDao.selectBoardFileByNo(board.getId()));
		}
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("pr", new PageResult(boardSearch.getPage(), boardDao.selectBoardCount(boardSearch),
				boardSearch.getListSize()));
		return result;
	}

	@Override
	public Board readBoard(int id) {
		System.out.println(id + "번 맛집을 읽어옵니다.");
		Board board = boardDao.selectOne(id);
		board.setBoardFile(boardDao.selectBoardFileByNo(id));
		return board;
	}

	@Override
	@Transactional
	public void writeBoard(Board board) {
		boardDao.insertBoard(board);
		if (board.getBoardFile() != null) {
			board.getBoardFile().setId(board.getId());
			boardDao.insertBoardFile(board.getBoardFile());
		}
	}

	@Override
	@Transactional
	public void modifyBoard(Board board) {
	    boardDao.updateBoard(board);

	    if (board.getBoardFile() != null) {
	        // 기존 파일 삭제 로직이 필요하다면 먼저 삭제
	        // boardDao.deleteBoardFileByBoardId(board.getId()); // 선택사항

	        board.getBoardFile().setId(board.getId());
	        boardDao.insertBoardFile(board.getBoardFile()); // 새 파일 INSERT
	    }
	}
	@Override
	@Transactional
	public void removeBoard(int id) {
		boardDao.deleteBoard(id);
	}

	@Override
	public List<Board> searchBoard(SearchCondition condition) {
		return boardDao.selectByWhere(condition);
	}
}
