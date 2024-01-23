package com.pamarg.springweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyFilesController {

    @GetMapping("/myFiles")
    public String myFiles(@RequestParam(name="path", required=false) String path, Model model) {
        model.addAttribute("path", path);
        return "myFiles";
    }

}