package com.example.advancedproject.Services;

import com.example.advancedproject.Entities.DailyNotesEntity;
import com.example.advancedproject.Entities.DiaryEntity;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Exceptions.DailyNoteNotFoundException;
import com.example.advancedproject.Exceptions.DiaryNotFoundException;
import com.example.advancedproject.Repos.DailyNotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service  @Transactional
@Slf4j
@RequiredArgsConstructor
public class DailyNoteService {
    @Autowired
    private DailyNotesRepository dailyNotesRepo;

    public DailyNotesEntity register(DailyNotesEntity dailyNotes){
        return dailyNotesRepo.save(dailyNotes);
    }

    public List<DailyNotesEntity> getDailyNotes(){
        log.info("Fetching All daily notes");
        return dailyNotesRepo.findAll();
    }

    public DailyNotesEntity getOne(Long daily_note_id){
        boolean exists = dailyNotesRepo.existsById(daily_note_id);
        DailyNotesEntity dailyNotes = dailyNotesRepo.findById(daily_note_id).get();
        if(!exists){
            System.out.println("Diary not found");
        }
        return dailyNotes;
    }

    public String deleteDailyNote(Long daily_note_id){
        dailyNotesRepo.deleteById(daily_note_id);
        return "Daily Note " + daily_note_id + " has been successfully deleted";
    }

    public void editDailyNote(DailyNotesEntity dailyNote){
        DailyNotesEntity oldDailyNote = dailyNotesRepo.findById(dailyNote.getDaily_note_id()).get();
        boolean exists = dailyNotesRepo.existsById(oldDailyNote.getDaily_note_id());
        if(!exists){
            System.out.println("Daily Note not found");
        }
        oldDailyNote.setText(dailyNote.getText());
    }
}
