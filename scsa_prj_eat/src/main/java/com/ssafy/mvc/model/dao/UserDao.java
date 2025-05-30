package com.ssafy.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.Board;

public interface UserDao {
	public List<User> selectAll();

	public void insertUser(User user);

	public User selectOne(Map<String, String> info);
	
	
	public int checkUserId(String id);
	
	public User selectById(String id);
	
	public List<Board> selectLikedBoards(String userId);
}