package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.TempHome;
import com.example.Animalhelpcenter.repositories.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class TemporaryHomesController {

    DatabaseManager mng = new DatabaseManager();
    LoginController login = new LoginController();

    @GetMapping("/admin/homes")
    public String getTemporaryHomes(Model model, HttpSession session) {

        var homes = mng.getTempHomes();
        model.addAttribute("homes", homes);

        return login.checkUser(session, "/admin/temp_homes");
    }

    @GetMapping("/admin/homes/manage")
    public String manageTemporaryHome(@RequestParam int selectedHome, @RequestParam String clickedButton, Model model, HttpSession session) {

        if (clickedButton.equals("edit")) {
            editTemporaryHome(selectedHome, model, session);
            return login.checkUser(session, "/admin/edit_home");
        } else {
            deleteTemporaryHome(selectedHome);
            return getTemporaryHomes(model, session);
        }
    }

    public String editTemporaryHome(@PathVariable int id, Model model, HttpSession session) {

        TempHome home = (TempHome) mng.getObject(TempHome.class, id);
        model.addAttribute("home", home);
        model.addAttribute("id", id);

        return login.checkUser(session, "/admin/edit_home");
    }

    @PostMapping("/admin/homes/edit")
    public ModelAndView updateTemporaryHome(@ModelAttribute("editHome") TempHome tempHome) {

        mng.updateObject(tempHome);
        return new ModelAndView("redirect:/admin/homes");
    }

    public void deleteTemporaryHome(int selectedHome) {
        TempHome homeToDelete = (TempHome) mng.getObject(TempHome.class, selectedHome);
        mng.deleteObject(homeToDelete);
    }

    @GetMapping("/admin/homes/add")
    public String addTemporaryHome(HttpSession session) {

        return login.checkUser(session, "/admin/add_temp_home");
    }

    @PostMapping("/admin/homes/add")
    public ModelAndView saveTemporaryHome(@ModelAttribute("addTempHome") TempHome tempHome) {

        mng.saveObject(tempHome);

        return new ModelAndView("redirect:/admin/homes"); // redirect
    }
}
