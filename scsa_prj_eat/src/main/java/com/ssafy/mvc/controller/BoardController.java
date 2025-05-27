package com.ssafy.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardFile;
import com.ssafy.mvc.model.dto.BoardSearch;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.model.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BoardController {
	private final BoardService boardService;
	private ResourceLoader resourceLoader;
	
    @Autowired
    public BoardController(BoardService boardService, ResourceLoader resourceLoader) {
        this.boardService = boardService;
        this.resourceLoader = resourceLoader;
    }

	@GetMapping("/list")
	@ResponseBody
	public Map<String, Object> list(BoardSearch boardSearch, HttpSession session) {
		Map<String, Object> result = boardService.getBoardList(boardSearch);
		List<Board> boards = (List<Board>) result.get("list");
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId != null) {
			for (Board board : boards) {
				board.setLiked(boardService.checkLike(board.getId(), userId));
				board.setLikeCount(boardService.getLikeCount(board.getId()));
			}
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("boards", boards);
		response.put("pagination", result.get("pr"));
		return response;
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
	@ResponseBody
	public Map<String, Object> write(@RequestParam(value = "attach", required = false) MultipartFile attach, 
								   @ModelAttribute Board board, 
								   HttpSession session) throws IllegalStateException, IOException {
		Map<String, Object> response = new HashMap<>();
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId == null) {
			response.put("success", false);
			response.put("error", "로그인이 필요합니다.");
			return response;
		}
		
		board.setUserId(userId);
		
		if (attach != null && !attach.isEmpty()) {
			String oriName = attach.getOriginalFilename();
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
			
			BoardFile boardFile = new BoardFile();
			boardFile.setFilePath(subDir);
			boardFile.setOriName(oriName);
			boardFile.setSystemName(systemName);
			board.setBoardFile(boardFile);
		}
		
		try {
			boardService.writeBoard(board);
			response.put("success", true);
			response.put("message", "게시글이 등록되었습니다.");
			response.put("boardId", board.getId());
		} catch (Exception e) {
			response.put("success", false);
			response.put("error", "게시글 등록에 실패했습니다.");
		}
		
		return response;
	}

	@GetMapping("/detail")
	@ResponseBody
	public Map<String, Object> detail(@RequestParam("id") int id, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		Board board = boardService.readBoard(id);
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId != null) {
			board.setLiked(boardService.checkLike(id, userId));
		}
		board.setLikeCount(boardService.getLikeCount(id));
		
		response.put("success", true);
		response.put("board", board);
		return response;
	}

	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") int id, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId == null) {
			response.put("success", false);
			response.put("error", "로그인이 필요합니다.");
			return response;
		}
		
		Board board = boardService.readBoard(id);
		if (board == null || !board.getUserId().equals(userId)) {
			response.put("success", false);
			response.put("error", "삭제 권한이 없습니다.");
			return response;
		}
		
		try {
			boardService.removeBoard(id);
			response.put("success", true);
			response.put("message", "게시글이 삭제되었습니다.");
		} catch (Exception e) {
			response.put("success", false);
			response.put("error", "게시글 삭제에 실패했습니다.");
		}
		
		return response;
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
	@ResponseBody
	public Map<String, Object> update(@RequestParam(value = "attach", required = false) MultipartFile attach, 
									@ModelAttribute Board board, 
									HttpSession session) throws IllegalStateException, IOException {
		Map<String, Object> response = new HashMap<>();
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId == null) {
			response.put("success", false);
			response.put("error", "로그인이 필요합니다.");
			return response;
		}
		
		Board existingBoard = boardService.readBoard(board.getId());
		if (existingBoard == null || !existingBoard.getUserId().equals(userId)) {
			response.put("success", false);
			response.put("error", "수정 권한이 없습니다.");
			return response;
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
		
		try {
			boardService.modifyBoard(board);
			response.put("success", true);
			response.put("message", "게시글이 수정되었습니다.");
		} catch (Exception e) {
			response.put("success", false);
			response.put("error", "게시글 수정에 실패했습니다.");
		}
		
		return response;
	}

	@GetMapping("/search")
	@ResponseBody
	public Map<String, Object> search(@ModelAttribute SearchCondition condition) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Board> boards = boardService.searchBoard(condition);
			response.put("success", true);
			response.put("boards", boards);
		} catch (Exception e) {
			response.put("success", false);
			response.put("error", "검색에 실패했습니다.");
		}
		return response;
	}

	@PostMapping("/like")
	@ResponseBody
	public Map<String, Object> toggleLike(@RequestParam("boardId") int boardId, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		String userId = (String) session.getAttribute("loginUserId");
		
		if (userId == null) {
			response.put("success", false);
			response.put("error", "로그인이 필요합니다.");
			return response;
		}
		
		try {
			boardService.toggleLike(boardId, userId);
			boolean liked = boardService.checkLike(boardId, userId);
			int likeCount = boardService.getLikeCount(boardId);
			
			response.put("success", true);
			response.put("liked", liked);
			response.put("likeCount", likeCount);
		} catch (Exception e) {
			response.put("success", false);
			response.put("error", "좋아요 처리에 실패했습니다.");
		}
		
		return response;
	}
}



// package com.ssafy.mvc.controller;

// import java.io.File;
// import java.io.IOException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ResourceLoader;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.multipart.MultipartFile;

// import com.ssafy.mvc.model.dto.Board;
// import com.ssafy.mvc.model.dto.BoardFile;
// import com.ssafy.mvc.model.dto.BoardSearch;
// import com.ssafy.mvc.model.dto.SearchCondition;
// import com.ssafy.mvc.model.service.BoardService;

// import jakarta.servlet.http.HttpSession;

// @Controller
// @RequestMapping("/board")
// @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
// public class BoardController {
//  private final BoardService boardService;
//  private ResourceLoader resourceLoader;
  
//     @Autowired
//     public BoardController(BoardService boardService, ResourceLoader resourceLoader) {
//         this.boardService = boardService;
//         this.resourceLoader = resourceLoader;
//     }

//  @GetMapping("/list")
//  @ResponseBody
//  public Map<String, Object> list(BoardSearch boardSearch, HttpSession session) {
//    Map<String, Object> result = boardService.getBoardList(boardSearch);
//    List<Board> boards = (List<Board>) result.get("list");
//    String userId = (String) session.getAttribute("loginUserId");
    
//    if (userId != null) {
//      for (Board board : boards) {
//        board.setLiked(boardService.checkLike(board.getId(), userId));
//        board.setLikeCount(boardService.getLikeCount(board.getId()));
//      }
//    }
    
//    return result;
//  }

//  @GetMapping("/writeform")
//  public String writeform(HttpSession session) {
//    String userId = (String) session.getAttribute("loginUserId");
//    if (userId == null) {
//      return "redirect:/login";
//    }
//    return "/board/writeform";
//  }

//  @PostMapping("/write")
//  @ResponseBody
//  public Map<String, Object> write(@RequestParam(value = "attach", required = false) MultipartFile attach, 
//                   @ModelAttribute Board board, 
//                   HttpSession session) throws IllegalStateException, IOException {
//    Map<String, Object> response = new HashMap<>();
//    String userId = (String) session.getAttribute("loginUserId");
    
//    if (userId == null) {
//      response.put("error", "로그인이 필요합니다.");
//      return response;
//    }
    
//    board.setUserId(userId);
    
//    if (attach != null && !attach.isEmpty()) {
//      String oriName = attach.getOriginalFilename();
//      String subDir = new SimpleDateFormat("/yyyy/MM/dd/HH").format(new Date());
//      String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/img";
//      File uploadDir = new File(uploadPath);
//      if (!uploadDir.exists()) {
//        uploadDir.mkdirs();
//      }
      
//      File subDirFile = new File(uploadPath + subDir);
//      if (!subDirFile.exists()) {
//        subDirFile.mkdirs();
//      }
      
//      String systemName = UUID.randomUUID().toString() + "_" + oriName;
//      File dest = new File(subDirFile, systemName);
//      attach.transferTo(dest);
      
//      BoardFile boardFile = new BoardFile();
//      boardFile.setFilePath(subDir);
//      boardFile.setOriName(oriName);
//      boardFile.setSystemName(systemName);
//      board.setBoardFile(boardFile);
//    }
    
//    try {
//      boardService.writeBoard(board);
//      response.put("success", true);
//      response.put("message", "게시글이 등록되었습니다.");
//      response.put("boardId", board.getId());
//    } catch (Exception e) {
//      response.put("success", false);
//      response.put("error", "게시글 등록에 실패했습니다.");
//    }
    
//    return response;
//  }

//  @GetMapping("/detail")
//  @ResponseBody
//  public Map<String, Object> detail(@RequestParam("id") int id, HttpSession session) {
//    Map<String, Object> response = new HashMap<>();
//    Board board = boardService.readBoard(id);
//    String userId = (String) session.getAttribute("loginUserId");
    
//    if (userId != null) {
//      board.setLiked(boardService.checkLike(id, userId));
//    }
//    board.setLikeCount(boardService.getLikeCount(id));
    
//    response.put("board", board);
//    return response;
//  }

//  @PostMapping("/delete")
//  @ResponseBody
//  public Map<String, Object> delete(@RequestParam("id") int id, HttpSession session) {
//    Map<String, Object> response = new HashMap<>();
//    String userId = (String) session.getAttribute("loginUserId");
    
//    if (userId == null) {
//      response.put("error", "로그인이 필요합니다.");
//      return response;
//    }
    
//    Board board = boardService.readBoard(id);
//    if (board == null || !board.getUserId().equals(userId)) {
//      response.put("error", "삭제 권한이 없습니다.");
//      return response;
//    }
    
//    try {
//      boardService.removeBoard(id);
//      response.put("success", true);
//      response.put("message", "게시글이 삭제되었습니다.");
//    } catch (Exception e) {
//      response.put("success", false);
//      response.put("error", "게시글 삭제에 실패했습니다.");
//    }
    
//    return response;
//  }

//  @GetMapping("/updateform")
//  public String updateform(@RequestParam("id") int id, Model model, HttpSession session) {
//    String userId = (String) session.getAttribute("loginUserId");
//    if (userId == null) {
//      return "redirect:/login";
//    }
    
//    Board board = boardService.readBoard(id);
//    if (board == null || !board.getUserId().equals(userId)) {
//      return "redirect:/board/list";
//    }
    
//    model.addAttribute("board", board);
//    return "/board/updateform";
//  }

//  @PostMapping("/update")
//  @ResponseBody
//  public Map<String, Object> update(@RequestParam(value = "attach", required = false) MultipartFile attach, 
//                  @ModelAttribute Board board, 
//                  HttpSession session) throws IllegalStateException, IOException {
//    Map<String, Object> response = new HashMap<>();
//    String userId = (String) session.getAttribute("loginUserId");
    
//    if (userId == null) {
//      response.put("error", "로그인이 필요합니다.");
//      return response;
//    }
    
//    Board existingBoard = boardService.readBoard(board.getId());
//    if (existingBoard == null || !existingBoard.getUserId().equals(userId)) {
//      response.put("error", "수정 권한이 없습니다.");
//      return response;
//    }
    
//    board.setUserId(userId);
    
//    if (attach != null && !attach.isEmpty()) {
//      String oriName = attach.getOriginalFilename();
//      String subDir = new SimpleDateFormat("/yyyy/MM/dd/HH").format(new Date());
//      String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/img";
      
//      File subDirFile = new File(uploadPath + subDir);
//      if (!subDirFile.exists()) {
//        subDirFile.mkdirs();
//      }
      
//      String systemName = UUID.randomUUID().toString() + "_" + oriName;
//      File dest = new File(subDirFile, systemName);
//      attach.transferTo(dest);

//      BoardFile boardFile = new BoardFile();
//      boardFile.setFilePath(subDir);
//      boardFile.setOriName(oriName);
//      boardFile.setSystemName(systemName);
//      boardFile.setId(board.getId());
//      board.setBoardFile(boardFile);
//    }
    
//    try {
//      boardService.modifyBoard(board);
//      response.put("success", true);
//      response.put("message", "게시글이 수정되었습니다.");
//    } catch (Exception e) {
//      response.put("success", false);
//      response.put("error", "게시글 수정에 실패했습니다.");
//    }
    
//    return response;
//  }

//  @GetMapping("/search")
//  @ResponseBody
//  public Map<String, Object> search(@ModelAttribute SearchCondition condition) {
//    Map<String, Object> response = new HashMap<>();
//    try {
//      List<Board> boards = boardService.searchBoard(condition);
//      response.put("success", true);
//      response.put("boards", boards);
//    } catch (Exception e) {
//      response.put("success", false);
//      response.put("error", "검색에 실패했습니다.");
//    }
//    return response;
//  }

//  @PostMapping("/like")
//  @ResponseBody
//  public Map<String, Object> toggleLike(@RequestParam("boardId") int boardId, HttpSession session) {
//    Map<String, Object> response = new HashMap<>();
//    String userId = (String) session.getAttribute("loginUserId");
    
//    if (userId == null) {
//      response.put("error", "로그인이 필요합니다.");
//      return response;
//    }
    
//    try {
//      boardService.toggleLike(boardId, userId);
//      boolean liked = boardService.checkLike(boardId, userId);
//      int likeCount = boardService.getLikeCount(boardId);
      
//      response.put("success", true);
//      response.put("liked", liked);
//      response.put("likeCount", likeCount);
//    } catch (Exception e) {
//      response.put("success", false);
//      response.put("error", "좋아요 처리에 실패했습니다.");
//    }
    
//    return response;
//  }
// }