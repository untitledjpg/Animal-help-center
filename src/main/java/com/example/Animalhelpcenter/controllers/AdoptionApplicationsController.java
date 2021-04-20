package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.AdoptionApplication;
import com.example.Animalhelpcenter.dto.SelectedApplicationModel;
import com.example.Animalhelpcenter.repositories.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AdoptionApplicationsController {

    DatabaseManager mng = new DatabaseManager();
    LoginController login = new LoginController();

    @GetMapping("/admin/applications")
    public String getApplications(@ModelAttribute("selectApp") SelectedApplicationModel selectedApplicationModel, Model model, HttpSession session) {

        var apls = mng.getApplications();

        var aplId = selectedApplicationModel.getSelectedApp();
        model.addAttribute("apls", apls);
        model.addAttribute("selectedApp", aplId);

        return login.checkUser(session, "/admin/view_apps");
    }

    @GetMapping("/admin/applications/manage")
    public ModelAndView manageApplication(@RequestParam int selectedApp, @RequestParam String clickedButton) {

        if (clickedButton.equals("accept")) {
            acceptApplication(selectedApp);
        } else if (clickedButton.equals("reject")) {
            rejectApplication(selectedApp);
        } else {
            deleteApplication(selectedApp);
        }
        return new ModelAndView("redirect:/admin/applications");
    }

    public void acceptApplication(int selectedApp) {

        AdoptionApplication appl = (AdoptionApplication) mng.getObject(AdoptionApplication.class, selectedApp);
        var cat = appl.getCat();
        cat.setCatStatus("adopted"); // changes cat status from "available" to "adopted" --> so it's not visible on user page anymore

        mng.updateObject(cat);
        appl.setAppStatus("accepted");
        mng.updateObject(appl);
    }

    public void rejectApplication(int selectedApp) {

        AdoptionApplication appl = (AdoptionApplication) mng.getObject(AdoptionApplication.class, selectedApp);

        appl.setAppStatus("rejected");
        mng.updateObject(appl);
    }

    public void deleteApplication(int selectedApp) {

        AdoptionApplication appl = (AdoptionApplication) mng.getObject(AdoptionApplication.class, selectedApp);
        mng.deleteObject(appl);
    }
}
