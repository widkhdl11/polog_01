package com.polog.polog.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MainController {

    @GetMapping("/api/post/new")
    public String main(){
        return "redirect:/";
    }

    //@PostMapping("/api/post/write")
//    public String main(){
//        System.out.println("실행");
//        log.info("main");
//        return "main";
//    }
}
