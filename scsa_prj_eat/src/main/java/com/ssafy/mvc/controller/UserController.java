package com.ssafy.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	private final UserServiceImpl userService;

//	@Autowired
	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String home() {
	    return "redirect:/login";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "/user/loginform";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute User user, HttpSession session, Model model) {
		User tmp = userService.login(user.getId(), user.getPassword());
		// tmp : 정상로그인 -> User 정보
		// 		비정상로그인 -> null
		if(tmp==null) {
			model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "/user/loginform"; // 로그인 화면으로 보내기
		}
		
		// 로그인 제대로 됐을 때 실행되는 코드

		session.setAttribute("loginUser", tmp.getName());
		session.setAttribute("loginUserId", tmp.getId());

		return "redirect:list";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    // 세션 무효화
	    session.invalidate();
	    // 로그인 페이지로 리다이렉트
	    return "redirect:login";
	}
	
	@GetMapping("/users")
	public String userList(Model model) {
		model.addAttribute("userList", userService.getUserList());
		return "/user/adminPage";
	}
	

	// 회원가입 -> 실습시간에 만들어보기
	@GetMapping("/signup")
	public String signupform() {
		return "/user/signupform";
	}
	
	@PostMapping("signup")
	public String signup(@ModelAttribute User user) {
		userService.signup(user);
		return "redirect:login";
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
}
