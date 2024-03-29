package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.Admin;
import com.example.Animalhelpcenter.repositories.AdminRepository;
import com.example.Animalhelpcenter.dto.AdminLoginDto;
import com.example.Animalhelpcenter.session.SessionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private AdminRepository repo;

    public LoginController() {
        repo = new AdminRepository();
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("hasError", false);

        return "/admin/login";
    }

    @PostMapping("/login")
    public String login(AdminLoginDto userData, Model model, HttpServletRequest request) {

        var user = repo.login(userData.getLogin(), userData.getPwd());

        if (user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "/admin/login";
        }

        request.getSession().setAttribute("User", user);
        model.addAttribute("user", user);

        return "/admin/admin";
    }

    @GetMapping("/logout")
    public ModelAndView logOut(HttpSession session){
        session.setAttribute(SessionData.User, null);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, HttpSession session) { // can access with servlet request and session

        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "info/access_denied";
        }

        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getId());

        return "/admin/admin";
    }


    public String checkUser(HttpSession session, String templateName) {
        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "info/access_denied";
        }
        return templateName;
    }
}

