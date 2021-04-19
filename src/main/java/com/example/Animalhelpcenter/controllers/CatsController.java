package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.Cat;
import com.example.Animalhelpcenter.repositories.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class CatsController {

    DatabaseManager mng = new DatabaseManager();
    LoginController login = new LoginController();

    @GetMapping("/admin/cats/edit")                  // choose cat to edit
    public String manageCats(Model model, HttpSession session) {

        var cats = mng.getCats();
        model.addAttribute("cats", cats);

        return login.checkUser(session, "/admin/choose_cat");
    }

    @GetMapping("/admin/cats/edit/{id}")             // edit specific cat
    public String editCat(@PathVariable int id, Model model, HttpSession session) {

        Cat cat = (Cat) mng.getObject(Cat.class, id);
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);

        return login.checkUser(session, "/admin/edit_cat");
    }

    @PostMapping("/admin/cats/edit")
    public ModelAndView updateCat(@ModelAttribute("editCat") Cat cat) {

        mng.updateObject(cat);
        return new ModelAndView("redirect:/admin/cats/edit");
    }

    @GetMapping("/admin/cats/add")
    public String addCat(Model model, HttpSession session) {

        var cats = mng.getCats();
        model.addAttribute("cats", cats);

        return login.checkUser(session, "/admin/add_cat");
    }

    @PostMapping("/admin/cats/add")
    public ModelAndView saveCat(@ModelAttribute("addCat") Cat cat) {

        if (cat.getPicturePath().equals("")) {
            cat.setPicturePath("/images/default.png");
        }
        mng.saveObject(cat);

        return new ModelAndView("redirect:/admin/cats/edit"); // redirect
    }

    @GetMapping("/admin/cats/delete/{id}")
    public ModelAndView deleteCat(@PathVariable int id) {

        Cat catToDelete = (Cat) mng.getObject(Cat.class, id);
        mng.deleteObject(catToDelete);
        return new ModelAndView("redirect:/admin/cats/edit");
    }
}
