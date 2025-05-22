package com.ssafy.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardSearch;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.model.service.BoardService;

@Controller
public class BoardController {
	private final BoardService boardService;
	private ResourceLoader resourceLoader;
	
    @Autowired
    public BoardController(BoardService boardService, ResourceLoader resourceLoader) {
        this.boardService = boardService;
        this.resourceLoader = resourceLoader;
    }

	@GetMapping("list")
	public String list(BoardSearch boardSearch, Model model) {
		Map<String, Object> result = boardService.getBoardList(boardSearch);
		model.addAttribute("boards", result.get("list"));
		model.addAttribute("pr", result.get("pr"));
		return "/board/list";
	}

	@GetMapping("/writeform")
	public String writeform() {
		return "/board/writeform";
	}

	@PostMapping("write")
	public String write(@RequestParam("attach") MultipartFile attach, @ModelAttribute Board board) throws IllegalStateException, IOException {
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
		return "redirect:list";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam("id") int id, Model model) {
		Board board = boardService.readBoard(id);
		model.addAttribute("board", board);
		return "/board/detail";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		boardService.removeBoard(id);
		return "redirect:list";
	}

	@GetMapping("updateform")
	public String updateform(@RequestParam("id") int id, Model model) {
		Board board = boardService.readBoard(id);
		model.addAttribute("board", board);
		return "/board/updateform";
	}

	@PostMapping("update")
	public String update(@RequestParam(value = "attach", required = false) MultipartFile attach, @ModelAttribute Board board) throws IllegalStateException, IOException {
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
		return "redirect:detail?id=" + board.getId();
	}

	@GetMapping("/search")
	public String search(@ModelAttribute SearchCondition condition, Model model) {
		model.addAttribute("boards", boardService.searchBoard(condition));
		return "/board/list";
	}
}
