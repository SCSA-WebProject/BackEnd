package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.Diary;
import com.ssafy.mvc.model.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 1. 전체 일기 목록 조회
    @GetMapping("/list")
    public String list(Model model) {
        List<Diary> list = diaryService.getDiaryList();
        model.addAttribute("list", list);
        return "diary/list"; // -> /WEB-INF/views/diary/list.jsp
    }

    // 2. 일기 상세 조회
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        Diary diary = diaryService.readDiary(id);
        model.addAttribute("diary", diary);
        return "diary/detail";
    }

    // 3. 작성 폼 페이지
    @GetMapping("/write")
    public String writeForm() {
        return "diary/write";
    }

    // 4. 일기 작성 처리
    @PostMapping("/write")
    public String write(@ModelAttribute Diary diary) {
        diaryService.writeDiary(diary);
        return "redirect:/diary/list";
    }

    // 5. 수정 폼
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Diary diary = diaryService.readDiary(id);
        model.addAttribute("diary", diary);
        return "diary/edit";
    }

    // 6. 수정 처리
    @PostMapping("/edit")
    public String edit(@ModelAttribute Diary diary) {
        diaryService.modifyDiary(diary);
        return "redirect:/diary/detail/" + diary.getId();
    }

    // 7. 삭제 처리
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        diaryService.removeDiary(id);
        return "redirect:/diary/list";
    }
}
