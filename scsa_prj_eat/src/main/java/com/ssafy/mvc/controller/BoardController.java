package com.ssafy.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardSearch;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.model.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
	private final BoardService boardService;
	private ResourceLoader resourceLoader;
	
    @Autowired
    public BoardController(BoardService boardService, ResourceLoader resourceLoader) {
        this.boardService = boardService;
        this.resourceLoader = resourceLoader;
    }

	@GetMapping("/list")
	public String list(BoardSearch boardSearch, Model model, HttpSession session) {
		Map<String, Object> result = boardService.getBoardList(boardSearch);
		List<Board> boards = (List<Board>) result.get("list");
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId != null) {
			for (Board board : boards) {
				board.setLiked(boardService.checkLike(board.getId(), userId));
				board.setLikeCount(boardService.getLikeCount(board.getId()));
			}
		}
		
		model.addAttribute("boards", boards);
		model.addAttribute("pr", result.get("pr"));
		return "/board/list";
	}

	@GetMapping("/writeform")
	public String writeform(HttpSession session) {
		String userId = (String) session.getAttribute("loginUserId");
		if (userId == null) {
			return "redirect:/login";
		}
		return "/board/writeform";
	}

	@PostMapping("/write")
	public String write(@RequestParam("attach") MultipartFile attach, @ModelAttribute Board board, HttpSession session) throws IllegalStateException, IOException {
		String userId = (String) session.getAttribute("loginUserId");
		if (userId == null) {
			return "redirect:/login";
		}
		board.setUserId(userId);
		
		String oriName = attach.getOriginalFilename();
		if (oriName != null && oriName.length() > 0) {
			String subDir = new SimpleDateFormat("/yyyy/MM/dd/HH").format(new Date());
			String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/img";
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			
			File subDirFile = new File(uploadPath + subDir);
			if (!subDirFile.exists()) {
				subDirFile.mkdirs();
			}
			
			String systemName = UUID.randomUUID().toString() + "_" + oriName;
			File dest = new File(subDirFile, systemName);
			attach.transferTo(dest);
			
			System.out.println("이미지 저장 경로: " + dest.getAbsolutePath());
			System.out.println("이미지 URL 경로: " + subDir + "/" + systemName);

			BoardFile boardFile = new BoardFile();
			boardFile.setFilePath(subDir);
			boardFile.setOriName(oriName);
			boardFile.setSystemName(systemName);
			board.setBoardFile(boardFile);
		}
		boardService.writeBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam("id") int id, Model model, HttpSession session) {
		Board board = boardService.readBoard(id);
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId != null) {
			board.setLiked(boardService.checkLike(id, userId));
		}
		board.setLikeCount(boardService.getLikeCount(id));
		
		model.addAttribute("board", board);
		return "/board/detail";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id, HttpSession session) {
		String userId = (String) session.getAttribute("loginUserId");
		if (userId == null) {
			return "redirect:/login";
		}
		
		Board board = boardService.readBoard(id);
		if (board == null || !board.getUserId().equals(userId)) {
			return "redirect:/board/list";
		}
		
		boardService.removeBoard(id);
		return "redirect:/board/list";
	}

	@GetMapping("/updateform")
	public String updateform(@RequestParam("id") int id, Model model, HttpSession session) {
		String userId = (String) session.getAttribute("loginUserId");
		if (userId == null) {
			return "redirect:/login";
		}
		
		Board board = boardService.readBoard(id);
		if (board == null || !board.getUserId().equals(userId)) {
			return "redirect:/board/list";
		}
		
		model.addAttribute("board", board);
		return "/board/updateform";
	}

	@PostMapping("/update")
	public String update(@RequestParam(value = "attach", required = false) MultipartFile attach, @ModelAttribute Board board, HttpSession session) throws IllegalStateException, IOException {
		String userId = (String) session.getAttribute("loginUserId");
		if (userId == null) {
			return "redirect:/login";
		}
		
		Board existingBoard = boardService.readBoard(board.getId());
		if (existingBoard == null || !existingBoard.getUserId().equals(userId)) {
			return "redirect:/board/list";
		}
		
		board.setUserId(userId);
		
		if (attach != null && !attach.isEmpty()) {
			String oriName = attach.getOriginalFilename();
			String subDir = new SimpleDateFormat("/yyyy/MM/dd/HH").format(new Date());
			String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/img";
			
			File subDirFile = new File(uploadPath + subDir);
			if (!subDirFile.exists()) {
				subDirFile.mkdirs();
			}
			
			String systemName = UUID.randomUUID().toString() + "_" + oriName;
			File dest = new File(subDirFile, systemName);
			attach.transferTo(dest);

			BoardFile boardFile = new BoardFile();
			boardFile.setFilePath(subDir);
			boardFile.setOriName(oriName);
			boardFile.setSystemName(systemName);
			boardFile.setId(board.getId());
			board.setBoardFile(boardFile);
		}
		boardService.modifyBoard(board);
		return "redirect:/board/detail?id=" + board.getId();
	}

	@GetMapping("/search")
	public String search(@ModelAttribute SearchCondition condition, Model model) {
		model.addAttribute("boards", boardService.searchBoard(condition));
		return "/board/list";
	}

	@PostMapping("/like")
	public String toggleLike(@RequestParam("boardId") int boardId, HttpSession session) {
		String userId = (String) session.getAttribute("loginUserId");
		if (userId == null) {
			return "redirect:/login";
		}
		
		boardService.toggleLike(boardId, userId);
		return "redirect:/board/list";
	}

	@PostMapping("/likeAjax")
	@ResponseBody
	public Map<String, Object> toggleLikeAjax(@RequestParam("boardId") int boardId, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}
		
		boardService.toggleLike(boardId, userId);
		boolean liked = boardService.checkLike(boardId, userId);
		int likeCount = boardService.getLikeCount(boardId);
		
		result.put("success", true);
		result.put("liked", liked);
		result.put("likeCount", likeCount);
		return result;
	}
}
