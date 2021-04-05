package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.AdoptionApplication;
import com.example.Animalhelpcenter.data.DatabaseHibernateManager;
import com.example.Animalhelpcenter.data.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    DatabaseManager dm = new DatabaseManager();  //JDBC
    DatabaseHibernateManager dhm = new DatabaseHibernateManager(); //hibernate


    @GetMapping("/cats")
    public String getCats(Model model) {

        var cats = dhm.getCats();
        model.addAttribute("cats", cats);             //key, object
        return "cats";
    }

    @GetMapping("/adopt/{id}")
    public String getAdoptPage(@PathVariable int id, Model model) {

        var cats = dhm.getCats();
        model.addAttribute("cats", cats);
        model.addAttribute("id", id);

        return "adopt";
    }

    @PostMapping("/adopt")
    public ModelAndView saveApplication(@ModelAttribute("addAdoptionApplication") AdoptionApplication dto) {


        var app = new AdoptionApplication(0, dto.getName(), dto.getSurname(), dto.getPhoneNumber(),
                dto.getEmail(), dto.getCatId(), dto.getOtherPets(), dto.getChildren());
        dhm.save(app);

        return new ModelAndView("redirect:/app/sent"); // redirect
    }


    @GetMapping("/app/sent")
    public String getApplicationSentPage() {
        return "app_sent";
    }

    @GetMapping("/test")
    public String getTest(Model model) {
        return "test";
    }

    @GetMapping("/faq")
    public String getFaq(Model model) {
        return "adoption_faq";
    }

    @GetMapping("")
    public String getIndex(Model model) {
        return "index";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        return "contacts";
    }

    @GetMapping("/help")
    public String getHelp(Model model) {
        return "help";
    }

}

