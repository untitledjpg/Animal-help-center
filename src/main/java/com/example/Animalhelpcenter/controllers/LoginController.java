package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.Admin;
import com.example.Animalhelpcenter.data.AdminRepository;
import com.example.Animalhelpcenter.mvc.AdminLoginDto;
import com.example.Animalhelpcenter.session.SessionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private AdminRepository repo;

    public LoginController() {
        repo = new AdminRepository();
    }

    @GetMapping("")
    public String getLogin(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "login";
    }

    //HttpSession session
    @PostMapping("")
    public String login(AdminLoginDto userData, Model model, HttpServletRequest request) {

        var user = repo.login(userData.getLogin(), userData.getPwd());

        if (user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }

        request.getSession().setAttribute("User", user);

        model.addAttribute("user", user);

        return "admin";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, HttpSession session) { // can access with servlet request and session

        var user = (Admin) session.getAttribute(SessionData.User);
        if (user == null) {
            return "access_denied";
        }

        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getId());

        return "admin";
    }
}

