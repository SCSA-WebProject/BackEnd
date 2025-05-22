package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mvc.model.dao.DiaryDao;
import com.ssafy.mvc.model.dto.Diary;

@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDao diaryDao;

    public DiaryServiceImpl(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }

    @Override
    public List<Diary> getDiaryList() {
        return diaryDao.selectAll();
    }

    @Override
    public Diary readDiary(int id) {
        System.out.println(id + "번 일기를 읽어옵니다.");
        return diaryDao.selectOne(id);
    }

    @Override
    @Transactional
    public void writeDiary(Diary diary) {
        diaryDao.insertDiary(diary);
    }

    @Override
    @Transactional
    public void modifyDiary(Diary diary) {
        diaryDao.updateDiary(diary);
    }

    @Override
    @Transactional
    public void removeDiary(int id) {
        diaryDao.deleteDiary(id);
    }
}
