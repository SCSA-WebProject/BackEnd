package com.ssafy.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.BoardServiceImpl;
import com.ssafy.mvc.model.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
	private final UserServiceImpl userService;
	private final BoardServiceImpl boardService;

	//	@Autowired
	public UserController(UserServiceImpl userService, BoardServiceImpl boardService) {
		this.userService = userService;
		this.boardService = boardService;
	}
	
	@GetMapping("/")
	public String home() {
	    return "redirect:http://localhost:5173";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "redirect:http://localhost:5173";
	}

	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestBody User user, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		User tmp = userService.login(user.getId(), user.getPassword());
		
		if(tmp == null) {
			response.put("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return response;
		}
		
		// 세션에 사용자 정보 저장
		session.setAttribute("loginUser", tmp.getName());
		session.setAttribute("loginUserId", tmp.getId());
		
		// 응답에 사용자 정보 추가 (비밀번호 제외)
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id", tmp.getId());
		userInfo.put("name", tmp.getName());
		userInfo.put("classNum", tmp.getClassNum());
		userInfo.put("phone", tmp.getPhone());
		userInfo.put("companyCode", tmp.getCompanyCode());
		userInfo.put("companyName", tmp.getCompanyName());
		
		response.put("user", userInfo);
		return response;
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		session.invalidate();
		response.put("message", "로그아웃되었습니다.");
		return response;
	}
	
	@GetMapping("/users")
	public String userList(Model model) {
		model.addAttribute("userList", userService.getUserList());
		return "/user/adminPage";
	}
	

	// 회원가입 -> 실습시간에 만들어보기
	@GetMapping("/signup")
	public String signupform() {
		return "redirect:http://localhost:5173/signup";
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public Map<String, Object> signup(@RequestBody User user) {
		Map<String, Object> response = new HashMap<>();
		try {
			userService.signup(user);
			response.put("message", "회원가입이 완료되었습니다.");
		} catch (Exception e) {
			response.put("error", "회원가입 중 오류가 발생했습니다.");
		}
		return response;
	}


	@GetMapping("/checkId")
	@ResponseBody
	public String checkUserId(@RequestParam("id") String id) {
	    int count = userService.checkUserId(id);
	    if (count > 0) {
	        return "duplicate";
	    } else {
	        return "available";
	    }
	}
	
	@GetMapping("/mypage/boards")
	@ResponseBody
	public Map<String, Object> getMyBoards(@RequestParam("userId") String userId) {
		Map<String, Object> response = new HashMap<>();
		
		if (userId == null || userId.trim().isEmpty()) {
			response.put("error", "사용자 ID가 필요합니다.");
			return response;
		}
		
		try {
			// 사용자가 작성한 맛집 목록 조회
			List<Board> myBoards = boardService.getBoardsByUserId(userId);
			response.put("boards", myBoards);
		} catch (Exception e) {
			response.put("error", "맛집 목록 조회 중 오류가 발생했습니다.");
		}
		
		return response;
	}

}



