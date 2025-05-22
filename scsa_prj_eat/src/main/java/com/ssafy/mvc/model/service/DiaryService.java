package com.ssafy.mvc.model.service;

import java.util.List;
import com.ssafy.mvc.model.dto.Diary;

public interface DiaryService {

    // 전체 일기 조회
    List<Diary> getDiaryList();

    // 단일 일기 조회
    Diary readDiary(int id);

    // 일기 작성
    void writeDiary(Diary diary);

    // 일기 수정
    void modifyDiary(Diary diary);

    // 일기 삭제
    void removeDiary(int id);
}
