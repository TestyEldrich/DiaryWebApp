package com.example.advancedproject.Controllers;

import com.example.advancedproject.Entities.DailyNotesEntity;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PageController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("log-in");
    }
    @GetMapping("/main")
    public ModelAndView mainPage(){
        return new ModelAndView("main");
    }
    @GetMapping("/admin")
    public ModelAndView adminPage(){
        return new ModelAndView("admin");
    }
    @GetMapping("/admin2")
    public ModelAndView admin2Page(){
        return new ModelAndView("admin2");
    }
    @GetMapping("/user/logout")
    public ModelAndView logout(HttpServletRequest request){
        return new ModelAndView("log-out");
    }
}
