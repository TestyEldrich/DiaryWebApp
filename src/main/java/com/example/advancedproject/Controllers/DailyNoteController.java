package com.example.advancedproject.Controllers;

import com.example.advancedproject.Entities.DailyNotesEntity;
import com.example.advancedproject.Entities.DiaryEntity;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Exceptions.DailyNoteNotFoundException;
import com.example.advancedproject.Exceptions.DiaryNotFoundException;
import com.example.advancedproject.Services.DailyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/dailyNote")
public class DailyNoteController {
    @Autowired
    private DailyNoteService dailyNoteService;

    @PostMapping("/register")
    public ResponseEntity Register(@ModelAttribute DailyNotesEntity dailyNotes){
        try{
            dailyNoteService.register(dailyNotes);
            return ResponseEntity.ok("Daily Note created");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @GetMapping("/getDailyNotes")
    public ModelAndView getDailyNotes(Model model){
        List<DailyNotesEntity> dailyNotes = dailyNoteService.getDailyNotes();
        model.addAttribute("dailyNotes", dailyNotes);
        return new ModelAndView("dailyNotesPage");
    }

    @GetMapping("/getDailyNote/{daily_note_id}")
    public ModelAndView GetOneDailyNote(@PathVariable Long daily_note_id, Model model){
        DailyNotesEntity dailyNote = dailyNoteService.getOne(daily_note_id);
        model.addAttribute("dailyNote", dailyNote);
        return new ModelAndView("editDailyNote");

    }

    @PostMapping("/editDailyNote")
    public ResponseEntity edit(@ModelAttribute DailyNotesEntity requestDailyNote) {
        dailyNoteService.editDailyNote(requestDailyNote);
        return ResponseEntity.ok("Successfully edited");
    }

    @PostMapping("/deleteDailyNote/{daily_note_id}")
    public ResponseEntity DeleteDailyNote(@PathVariable Long daily_note_id){
        try{
            return ResponseEntity.ok(dailyNoteService.deleteDailyNote(daily_note_id));
        } catch(Exception E){
            return ResponseEntity.badRequest().body("Error occurred");
        }
    }

    @GetMapping("/newDailyNote")
    public ModelAndView newDiary(){
        return new ModelAndView("newDailyNote");
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
