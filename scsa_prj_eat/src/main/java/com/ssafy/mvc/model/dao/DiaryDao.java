package com.ssafy.mvc.model.dao;

import java.util.List;
import com.ssafy.mvc.model.dto.Diary;

public interface DiaryDao {

    // 전체 일기 목록
    List<Diary> selectAll();

    // 유저 일기 목록
    List<Diary> selectByUser(String userId);
    
    // 단일 일기 조회
    Diary selectOne(int id);

    // 일기 등록
    int insertDiary(Diary diary);

    // 일기 수정
    void updateDiary(Diary diary);

    // 일기 삭제
    void deleteDiary(int id);
}
