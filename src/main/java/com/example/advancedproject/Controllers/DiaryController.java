package com.example.advancedproject.Controllers;

import com.example.advancedproject.Entities.DailyNotesEntity;
import com.example.advancedproject.Entities.DiaryEntity;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Exceptions.DiaryNotFoundException;
import com.example.advancedproject.Exceptions.EmailAlreadyInUseException;
import com.example.advancedproject.Services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    @PostMapping("/register")
    public ResponseEntity Register(@ModelAttribute DiaryEntity diary){
        try{
            diaryService.register(diary);
            return ResponseEntity.ok("Diary created");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @GetMapping("/getDiaries")
    public ModelAndView getDiaries(Model model){
        List<DiaryEntity> diaries = diaryService.getDiaries();
        model.addAttribute("diaries", diaries);
        return new ModelAndView("diary");
    }

    @GetMapping("/getDiary/{diary_id}")
    public ModelAndView GetOneDiary(@PathVariable Long diary_id, Model model){
        DiaryEntity diary = diaryService.getOne(diary_id);
        model.addAttribute("diary", diary);
        return new ModelAndView("editDiary");
    }

    @GetMapping("/newDiary")
    public ModelAndView newDiary(){
        return new ModelAndView("newDiary");
    }

    @PostMapping("/editDiary")
    public ResponseEntity edit(@ModelAttribute DiaryEntity requestDiary) {
        diaryService.editDiary(requestDiary);
        return ResponseEntity.ok("Successfully edited");
    }

    @PostMapping("/deleteDiary/{diary_id}")
    public ResponseEntity DeleteDiary(@PathVariable Long diary_id){
        try{
            return ResponseEntity.ok(diaryService.deleteDiary(diary_id));
        } catch(Exception E){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @GetMapping("/checkEntity")
    public ResponseEntity CheckEntity(){
        try{
            return ResponseEntity.ok("Everything works ok");
        } catch(Exception E){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }
}
