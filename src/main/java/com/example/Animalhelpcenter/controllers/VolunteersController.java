package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.Volunteer;
import com.example.Animalhelpcenter.repositories.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class VolunteersController {

    DatabaseManager mng = new DatabaseManager();
    LoginController login = new LoginController();

    @GetMapping("/admin/volunteers")
    public String getVolunteers(Model model, HttpSession session) {

        var volunteers = mng.getVolunteers();
        model.addAttribute("volunteers", volunteers);

        return login.checkUser(session, "/admin/volunteers");
    }

    @GetMapping("/admin/volunteers/manage")
    public String manageVolunteer(@RequestParam int selectedVolunteer, @RequestParam String clickedButton, Model model, HttpSession session) {

        if (clickedButton.equals("edit")) {
            editVolunteer(selectedVolunteer, model, session);
            return login.checkUser(session, "/admin/edit_volunteer");
        } else {
            deleteVolunteer(selectedVolunteer);
            return getVolunteers(model, session);
        }
    }

    @GetMapping("/admin/volunteers/add")
    public String addVolunteer(HttpSession session) {

        return login.checkUser(session, "/admin/add_volunteer");
    }

    @PostMapping("/admin/volunteers/add")
    public ModelAndView saveVolunteer(@ModelAttribute("addVolunteer") Volunteer volunteer) {

        mng.saveObject(volunteer);

        return new ModelAndView("redirect:/admin/volunteers");
    }


    public String editVolunteer(@PathVariable int id, Model model, HttpSession session) {

        Volunteer volunteer = (Volunteer) mng.getObject(Volunteer.class, id);
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("id", id);

        return login.checkUser(session, "/admin/edit_volunteer");
    }

    @PostMapping("/admin/volunteers/edit")
    public ModelAndView updateVolunteer(@ModelAttribute("editVolunteer") Volunteer volunteer) {

        mng.updateObject(volunteer);
        return new ModelAndView("redirect:/admin/volunteers");
    }

    public void deleteVolunteer(int selectedVolunteer) {
        Volunteer volunteerToDelete = (Volunteer) mng.getObject(Volunteer.class, selectedVolunteer);
        mng.deleteObject(volunteerToDelete);
    }
}
