package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.*;
import com.example.Animalhelpcenter.mvc.SelectAppModel;
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

        return checkUser(session , "choose_cat");
    }

    @GetMapping("/admin/edit/{id}")             // edit specific cat
    public String editCat(@PathVariable int id, Model model, HttpSession session) {

        var cat = dhm.getCatById(id);
        model.addAttribute("cat", cat);
        model.addAttribute("id", id);

        return checkUser(session , "edit_cat");
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

        return checkUser(session , "add_cat");
    }

    @PostMapping("/admin/add")
    public ModelAndView saveCat(@ModelAttribute("addCat") Cat dto) {

        if (dto.getPicturePath().equals("")){
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

    @GetMapping("/admin/view/applications")
    public String getApplications(@ModelAttribute ("switchRegion") SelectAppModel selectAppModel, Model model, HttpSession session) {

        var apls = dhm.getApplications();

        var aplId = selectAppModel.getSelectedApp();
        model.addAttribute("apls", apls);
        model.addAttribute("selectedApp", aplId);

        return checkUser(session , "view_apps");
    }

    @GetMapping("/admin/delete/app")
    public ModelAndView deleteApp(@RequestParam int selectedApp) {

        dhm.deleteApp(selectedApp);
        return new ModelAndView("redirect:/admin/view-applications");
    }

    @GetMapping("/admin/view/volunteers")
    public String getVolunteers(Model model, HttpSession session) {

        var volunteers = dhm.getVolunteers();
        model.addAttribute("volunteers", volunteers);

        return checkUser(session , "volunteers");
    }

    @GetMapping("/admin/delete/volunteer")
    public ModelAndView deleteVolunteer(@RequestParam int selectedVolunteer) {

        var volunteerToDelete = dhm.getVolunteerById(selectedVolunteer);

        dhm.delete(volunteerToDelete);
        return new ModelAndView("redirect:/admin/view/volunteers");
    }

    @GetMapping("/admin/view/homes")
    public String getTemporaryHomes(Model model, HttpSession session) {

        var homes = dhm.getTempHomes();
        model.addAttribute("homes", homes);

        return checkUser(session , "temp_homes");
    }

    @GetMapping("/admin/delete/home")
    public ModelAndView deleteTemporaryHome(@RequestParam int selectedHome) {

        var homeToDelete = dhm.getTempHomeById(selectedHome);

        dhm.delete(homeToDelete);
        return new ModelAndView("redirect:/admin/view/homes");
    }

    private String checkUser(HttpSession session, String templateName) {
        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "access_denied";
        }
        return templateName;
    }
}
