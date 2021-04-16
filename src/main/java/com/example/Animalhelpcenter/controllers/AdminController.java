package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.*;
import com.example.Animalhelpcenter.dto.SelectAppModel;
import com.example.Animalhelpcenter.repositories.DatabaseHibernateManager;
import com.example.Animalhelpcenter.repositories.DatabaseManager;
import com.example.Animalhelpcenter.session.SessionData;
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

        return checkUser(session, "/admin/choose_cat");
    }

    @GetMapping("/admin/edit/{id}")             // edit specific cat
    public String editCat(@PathVariable int id, Model model, HttpSession session) {

        var cat = dhm.getCatById(id);
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);

        return checkUser(session, "/admin/edit_cat");
    }

    @PostMapping("/admin/edit")
    public ModelAndView saveApplication(@ModelAttribute("editCat") Cat dto) {

        dhm.updateCat(dto);
        return new ModelAndView("redirect:/admin/edit");
    }

    @GetMapping("/admin/add")
    public String addCat(Model model, HttpSession session) {

        var cats = dhm.getCats();
        model.addAttribute("cats", cats);

        return checkUser(session, "/admin/add_cat");
    }

    @PostMapping("/admin/add")
    public ModelAndView saveCat(@ModelAttribute("addCat") Cat dto) {

        if (dto.getPicturePath().equals("")) {
            dto.setPicturePath("/images/default.png");
        }
        System.out.println(dto.getPicturePath());

        var cat = new Cat(0, dto.getName(), dto.getAge(), dto.getColor(),
                dto.getSex(), dto.getNeutered(), dto.getDescription(), dto.getPicturePath(),
                dto.getCatStatus(), dto.getCatArrival(), dto.getTempHomeId(), dto.getVolunteerId());
        dhm.save(cat);

        return new ModelAndView("redirect:/admin/edit"); // redirect
    }

    @GetMapping("/admin/delete/{id}")
    public ModelAndView deleteCat(@PathVariable int id) {

        dhm.deleteCat(id);
        return new ModelAndView("redirect:/admin/edit");
    }

    /*    @GetMapping("/admin/confirm/{id}")     // confirmation of delete
    public String confirmCarDelete(@PathVariable int id, Model model) {

        var items = dhm.getCats();

        var cat = dhm.getCatById(id);

        model.addAttribute("title", "Cats");
        model.addAttribute("cats", items);
        model.addAttribute("confirmDelete", cat);

        return "deletetest";
    }*/

    @GetMapping("/admin/applications")
    public String getApplications(@ModelAttribute("switchRegion") SelectAppModel selectAppModel, Model model, HttpSession session) {

        var apls = dhm.getApplicationsWithCat();

        var aplId = selectAppModel.getSelectedApp();
        model.addAttribute("apls", apls);
        model.addAttribute("selectedApp", aplId);

        return checkUser(session, "/admin/view_apps");
    }

    @GetMapping("/admin/applications/manage")
    public ModelAndView manageApp(@RequestParam int selectedApp, @RequestParam String clickedButton) {

        if (clickedButton.equals("accept")) {
            acceptApp(selectedApp);
        } else if (clickedButton.equals("reject")) {
            rejectApp(selectedApp);
        } else {
            deleteApp(selectedApp);
        }
        return new ModelAndView("redirect:/admin/applications");
    }

    public void deleteApp(int selectedApp) {

        var appl = dhm.getApplicationById(selectedApp);
        dhm.deleteApp(selectedApp);
    }

    public void acceptApp(int selectedApp) {

        var appl = dhm.getApplicationById(selectedApp);
        var cat = appl.getCat();
        cat.setCatStatus("adopted"); // changes cat status from "available" to "adopted" --> so it's not visible on user page anymore

        dhm.updateCat(cat);
        appl.setAppStatus("accepted");
        dhm.update(appl);
    }

    public void rejectApp(int selectedApp) {

        var appl = dhm.getApplicationById(selectedApp);

        appl.setAppStatus("rejected");
        dhm.update(appl);
    }

    @GetMapping("/admin/volunteers")
    public String getVolunteers(Model model, HttpSession session) {

        var volunteers = dhm.getVolunteers();
        model.addAttribute("volunteers", volunteers);

        return checkUser(session, "/admin/volunteers");
    }

    @GetMapping("/admin/volunteers/manage")
    public String manageVolunteer(@RequestParam int selectedVolunteer, @RequestParam String clickedButton, Model model, HttpSession session) {

        if (clickedButton.equals("edit")) {
            editVolunteer(selectedVolunteer, model, session);
            return checkUser(session, "/admin/edit_volunteer");
        } else {
            deleteVolunteer(selectedVolunteer);
            return getVolunteers(model, session);
        }
    }


    public String editVolunteer(@PathVariable int id, Model model, HttpSession session) {

        var volunteer = dhm.getVolunteerById(id);
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("id", id);

        return checkUser(session, "/admin/edit_volunteer");
    }

    @PostMapping("/admin/volunteers/edit")
    public ModelAndView saveVolunteer(@ModelAttribute("editVolunteer") Volunteer volunteer) {

        dhm.update(volunteer);
        return new ModelAndView("redirect:/admin/volunteers");
    }

    public void deleteVolunteer(int selectedVolunteer) {
        var volunteerToDelete = dhm.getVolunteerById(selectedVolunteer);
        dhm.delete(volunteerToDelete);
    }






    @GetMapping("/admin/homes")
    public String getTemporaryHomes(Model model, HttpSession session) {

        var homes = dhm.getTempHomes();
        model.addAttribute("homes", homes);

        return checkUser(session, "/admin/temp_homes");
    }

    @GetMapping("/admin/homes/manage")
    public String manageTemporaryHome(@RequestParam int selectedHome, @RequestParam String clickedButton, Model model, HttpSession session) {

        if (clickedButton.equals("edit")) {
            editTemporaryHome(selectedHome, model, session);
            return checkUser(session, "/admin/edit_home");
        } else {
            deleteTemporaryHome(selectedHome);
            return getTemporaryHomes(model, session);
        }
    }

    public String editTemporaryHome(@PathVariable int id, Model model, HttpSession session) {

        var home = dhm.getTempHomeById(id);
        model.addAttribute("home", home);
        model.addAttribute("id", id);

        return checkUser(session, "/admin/edit_home");
    }

    @PostMapping("/admin/homes/edit")
    public ModelAndView saveTemporaryHome(@ModelAttribute("editHome") TempHome tempHome) {

        dhm.update(tempHome);
        return new ModelAndView("redirect:/admin/homes");
    }

    public void deleteTemporaryHome(int selectedHome) {
        var homeToDelete = dhm.getTempHomeById(selectedHome);
        dhm.delete(homeToDelete);
    }

    private String checkUser(HttpSession session, String templateName) {
        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "info/access_denied";
        }
        return templateName;
    }

}
