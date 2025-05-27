package com.ssafy.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ssafy.mvc.model.service.BoardService;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {
    private final BoardService boardService;

    public MainController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/main")
    public String main(Model model) {
        // 최근 등록된 맛집 5개
        model.addAttribute("recentBoards", boardService.getRecentBoards(5));
        // 좋아요가 많은 맛집 5개
        model.addAttribute("popularBoards", boardService.getPopularBoards(5));
        return "main";
    }
} 