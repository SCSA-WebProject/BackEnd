package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.Board;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> getUserList() {
		return userDao.selectAll();
	}

	@Override
	public void signup(User user) {
		userDao.insertUser(user);
		System.out.println("회원가입 완료");
	}

	@Override
	public User login(String id, String password) {
		Map<String, String> info = new HashMap<>();
		info.put("id", id);
		info.put("password", password);
		User tmp = userDao.selectOne(info);
		return tmp;
	}
	
	@Override
	public int checkUserId(String id) {
	    return userDao.checkUserId(id);
	}

	@Override
	public User getUserById(String id) {
		return userDao.selectById(id);
	}

	@Override
	public List<Board> getLikedBoards(String userId) {
		return userDao.selectLikedBoards(userId);
	}

}
