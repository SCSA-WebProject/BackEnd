package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.Board;

public interface UserService {
	//전체 사용자 목록 불러오기
	public List<User> getUserList();
	//사용자 등록하기
	public void signup(User user);
	//로그인 하기
	public User login(String id, String password);
	
	
	int checkUserId(String id);
	
	User getUserById(String id);
	
	List<Board> getLikedBoards(String userId);
}
