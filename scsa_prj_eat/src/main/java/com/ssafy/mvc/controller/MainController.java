//package com.ssafy.mvc.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.ssafy.mvc.model.service.BoardService;
//
//@Controller
//public class MainController {
//    private final BoardService boardService;
//
//    public MainController(BoardService boardService) {
//        this.boardService = boardService;
//    }
//
//    @GetMapping("/main")
//    public String main(Model model) {
//        // 최근 등록된 맛집 5개
//        model.addAttribute("recentBoards", boardService.getRecentBoards(5));
//        // 좋아요가 많은 맛집 5개
//        model.addAttribute("popularBoards", boardService.getPopularBoards(5));
//        return "main";
//    }
//} 


package com.ssafy.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MainController {
    private final BoardService boardService;

    public MainController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/main")
    @ResponseBody
    public Map<String, Object> main(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String userId = (String) session.getAttribute("loginUserId");
        
        // 최근 등록된 맛집 5개
        List<Board> recentBoards = boardService.getRecentBoards(5);
        // 좋아요가 많은 맛집 5개
        List<Board> popularBoards = boardService.getPopularBoards(5);

        // 로그인한 사용자가 있는 경우 좋아요 상태 설정
        if (userId != null) {
            for (Board board : recentBoards) {
                board.setLiked(boardService.checkLike(board.getId(), userId));
                board.setLikeCount(boardService.getLikeCount(board.getId()));
            }
            for (Board board : popularBoards) {
                board.setLiked(boardService.checkLike(board.getId(), userId));
                board.setLikeCount(boardService.getLikeCount(board.getId()));
            }
        }

        response.put("recentBoards", recentBoards);
        response.put("popularBoards", popularBoards);
        return response;
    }
} 
