package com.example.Animalhelpcenter.mvc;

import com.example.Animalhelpcenter.data.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String getIndexPage(Model model){

        var dm = new DatabaseManager();
        var cats = dm.getCats();
        model.addAttribute("cats",cats);             //key, object
        return "index";
    }

    @GetMapping("/adopt")
    public String getAdoptPage(Model model){
        return "adopt";
    }
}
