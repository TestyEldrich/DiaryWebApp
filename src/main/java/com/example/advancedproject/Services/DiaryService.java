package com.example.advancedproject.Services;

import com.example.advancedproject.Entities.DailyNotesEntity;
import com.example.advancedproject.Entities.DiaryEntity;
import com.example.advancedproject.Exceptions.DiaryNotFoundException;
import com.example.advancedproject.Repos.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepo;

    public DiaryEntity register(DiaryEntity diary){
        return diaryRepo.save(diary);
    }

    public List<DiaryEntity> getDiaries(){
        log.info("Fetching All daily notes");
        return diaryRepo.findAll();
    }

    public DiaryEntity getOne(Long diary_id){
        boolean exists = diaryRepo.existsById(diary_id);
        DiaryEntity diary = diaryRepo.findById(diary_id).get();
        if(!exists){
            System.out.println("Diary not found");
        }
        return diary;
    }

    public String deleteDiary(Long diary_id){
        diaryRepo.deleteById(diary_id);
        return "Diary " + diary_id + " has been successfully deleted";
    }

    public void editDiary(DiaryEntity diary){
        DiaryEntity oldDiary = diaryRepo.findById(diary.getDiary_id()).get();
        boolean exists = diaryRepo.existsById(oldDiary.getDiary_id());
        oldDiary.setText(diary.getText());
        oldDiary.setTitle(diary.getTitle());
        diaryRepo.save(oldDiary);
    }
}
