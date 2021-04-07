package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.*;
import com.example.Animalhelpcenter.session.SessionData;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
//@RequestMapping(name ="/admin")
public class AdminController {
    DatabaseManager dm = new DatabaseManager();  //JDBC
    DatabaseHibernateManager dhm = new DatabaseHibernateManager(); //hibernate


    @GetMapping("/admin/edit")                  // choose cat to edit page
    public String manageCats(Model model, HttpSession session) {

        var cats = dhm.getCats();
        model.addAttribute("cats", cats);

        return checkUser(session , "choose_cat");
    }

    @GetMapping("/admin/edit/{id}")             // edit specific cat
    public String editCat(@PathVariable int id, Model model, HttpSession session) {

        var cat = dhm.getById(id);
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);

        return checkUser(session , "edit_cat");
    }

    @PostMapping("/admin/edit")
    public ModelAndView saveApplication(@ModelAttribute("editCat") Cat dto) {

        System.out.println(dto.getId());
        Cat catToUpdate = dhm.getById(dto.getId());

        catToUpdate.setId(dto.getId());
        catToUpdate.setName(dto.getName());
        catToUpdate.setAge(dto.getAge());
        catToUpdate.setColor(dto.getColor());
        catToUpdate.setSex(dto.getSex());
        catToUpdate.setNeutered(dto.getNeutered());
        catToUpdate.setDescription(dto.getDescription());
        catToUpdate.setPicturePath(dto.getPicturePath());
        catToUpdate.setCatStatus(dto.getCatStatus());
        catToUpdate.setCatArrival(dto.getCatArrival());
        catToUpdate.setTempHomeId(dto.getTempHomeId());
/*
        Cat newCat = new Cat(0, dto.getName(), dto.getAge(), dto.getColor(),
                dto.getSex(), dto.getNeutered(), dto.getDescription(), dto.getPicturePath(),
                dto.getCatStatus(), dto.getCatArrival(), dto.getTempHomeId());*/

        dhm.updateCat(catToUpdate);

        return new ModelAndView("redirect:/app/sent"); // redirect
    }

    @GetMapping("/admin/add")                  // choose cat to edit page
    public String addCat(Model model, HttpSession session) {

        var cats = dhm.getCats();
        model.addAttribute("cats", cats);

        return checkUser(session , "add_cat");
    }

    @PostMapping("/admin/add")
    public ModelAndView saveCat(@ModelAttribute("addCat") Cat dto) {

        System.out.println(dto.getPicturePath());

        var cat = new Cat(0, dto.getName(), dto.getAge(), dto.getColor(),
                dto.getSex(), dto.getNeutered(), dto.getDescription(), dto.getPicturePath(),
                dto.getCatStatus(), dto.getCatArrival(), dto.getTempHomeId());
        dhm.save(cat);

        return new ModelAndView("redirect:/admin/edit"); // redirect
    }

    @GetMapping("/admin/view-applications")
    public String getApplications(Model model, HttpSession session) {

        var apls = dhm.getApplications();
        model.addAttribute("apls", apls);

        return checkUser(session , "view_apps");
    }

    private String checkUser(HttpSession session, String templateName) {
        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "access_denied";
        }
        return templateName;
    }


    @Test
    public void getApplications() {
        var apps = dhm.getApplications();
        for (var app : apps
        ) {
            System.out.println(app.getName() + " " + app.getCatId() + " " + app.getCatName());
        }
    }

    @Test
    public void getCatById() {
        int id = 5;
        var cat = dhm.getById(id);
        System.out.println(cat.getName());
    }
}
