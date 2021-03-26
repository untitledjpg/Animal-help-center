package com.example.Animalhelpcenter.mvc;

import com.example.Animalhelpcenter.data.AdoptionApplication;
import com.example.Animalhelpcenter.data.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class MainController {

    DatabaseManager dm = new DatabaseManager();

    @GetMapping("")
    public String getIndexPage(Model model){

        var cats = dm.getCats();
        model.addAttribute("cats",cats);             //key, object
        return "index";
    }

    @GetMapping("/adopt")
    public String addAdoptApplicationToDB(Model model){

        var cats = dm.getCats();
        model.addAttribute("cats", cats);

        return "adopt";
    }

    @PostMapping("/adopt")
    public ModelAndView addCity(@ModelAttribute("addAdoptionApplication") AdoptionApplication dto){

        var app = new AdoptionApplication(0, dto.getName(), dto.getSurname(), dto.getPhoneNumber(),
                dto.getEmail(), dto.getCatId(), dto.getOtherPets(), dto.getChildren());
        dm.addAdoptApplication(app );

        return new ModelAndView("redirect:/app/sent"); // redirect
    }


    @GetMapping("/app/sent")
    public String getApplicationSent(Model model){
        return "app_sent";
    }
}

