package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.*;
import com.example.Animalhelpcenter.dto.SelectedApplicationModel;
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

    DatabaseManager mng = new DatabaseManager();


    @GetMapping("/admin/cats/edit")                  // choose cat to edit
    public String manageCats(Model model, HttpSession session) {

        var cats = mng.getCats();
        model.addAttribute("cats", cats);

        return checkUser(session, "/admin/choose_cat");
    }

    @GetMapping("/admin/cats/edit/{id}")             // edit specific cat
    public String editCat(@PathVariable int id, Model model, HttpSession session) {

        Cat cat = (Cat) mng.getObject(Cat.class, id);
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);

        return checkUser(session, "/admin/edit_cat");
    }

    @PostMapping("/admin/cats/edit")
    public ModelAndView updateCat(@ModelAttribute("editCat") Cat dto) {

        mng.updateCat(dto);
        return new ModelAndView("redirect:/admin/cats/edit");
    }

    @GetMapping("/admin/cats/add")
    public String addCat(Model model, HttpSession session) {

        var cats = mng.getCats();
        model.addAttribute("cats", cats);

        return checkUser(session, "/admin/add_cat");
    }

    @PostMapping("/admin/cats/add")
    public ModelAndView saveCat(@ModelAttribute("addCat") Cat dto) {

        if (dto.getPicturePath().equals("")) {
            dto.setPicturePath("/images/default.png");
        }
        System.out.println(dto.getPicturePath());

        var cat = new Cat(0, dto.getName(), dto.getAge(), dto.getColor(),
                dto.getSex(), dto.getNeutered(), dto.getDescription(), dto.getPicturePath(),
                dto.getCatStatus(), dto.getCatArrival(), dto.getTempHomeId(), dto.getVolunteerId());
        mng.saveObject(cat);

        return new ModelAndView("redirect:/admin/cats/edit"); // redirect
    }

    @GetMapping("/admin/cats/delete/{id}")
    public ModelAndView deleteCat(@PathVariable int id) {

        Cat catToDelete = (Cat) mng.getObject(Cat.class, id);
        mng.deleteObject(catToDelete);
        return new ModelAndView("redirect:/admin/cats/edit");
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
    public String getApplications(@ModelAttribute("selectApp") SelectedApplicationModel selectedApplicationModel, Model model, HttpSession session) {

        var apls = mng.getApplications();

        var aplId = selectedApplicationModel.getSelectedApp();
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

        AdoptionApplication appl = (AdoptionApplication) mng.getObject(AdoptionApplication.class, selectedApp);
        mng.deleteObject(appl);
    }

    public void acceptApp(int selectedApp) {

        AdoptionApplication appl = (AdoptionApplication) mng.getObject(AdoptionApplication.class, selectedApp);
//        var appl = mng.getApplicationById(selectedApp);
        var cat = appl.getCat();
        cat.setCatStatus("adopted"); // changes cat status from "available" to "adopted" --> so it's not visible on user page anymore

        mng.updateCat(cat);
        appl.setAppStatus("accepted");
        mng.updateObject(appl);
    }

    public void rejectApp(int selectedApp) {

        AdoptionApplication appl = (AdoptionApplication) mng.getObject(AdoptionApplication.class, selectedApp);

        appl.setAppStatus("rejected");
        mng.updateObject(appl);
    }

    @GetMapping("/admin/volunteers")
    public String getVolunteers(Model model, HttpSession session) {

        var volunteers = mng.getVolunteers();
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

        Volunteer volunteer = (Volunteer) mng.getObject(Volunteer.class, id);
//        var volunteer = mng.getVolunteerById(id);
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("id", id);

        return checkUser(session, "/admin/edit_volunteer");
    }

    @PostMapping("/admin/volunteers/edit")
    public ModelAndView saveVolunteer(@ModelAttribute("editVolunteer") Volunteer volunteer) {

        mng.updateObject(volunteer);
        return new ModelAndView("redirect:/admin/volunteers");
    }

    public void deleteVolunteer(int selectedVolunteer) {
        Volunteer volunteerToDelete = (Volunteer) mng.getObject(Volunteer.class, selectedVolunteer);
        mng.deleteObject(volunteerToDelete);
    }

    @GetMapping("/admin/homes")
    public String getTemporaryHomes(Model model, HttpSession session) {

        var homes = mng.getTempHomes();
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

        TempHome home = (TempHome) mng.getObject(TempHome.class, id);
//        var home = mng.getTempHomeById(id);
        model.addAttribute("home", home);
        model.addAttribute("id", id);

        return checkUser(session, "/admin/edit_home");
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

        return checkUser(session, "/admin/add_temp_home");
    }

    @PostMapping("/admin/homes/add")
    public ModelAndView saveTemporaryHome(@ModelAttribute("addTempHome") TempHome dto) {

        mng.saveObject(dto);

        return new ModelAndView("redirect:/admin/homes"); // redirect
    }

    private String checkUser(HttpSession session, String templateName) {
        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "info/access_denied";
        }
        return templateName;
    }

}
