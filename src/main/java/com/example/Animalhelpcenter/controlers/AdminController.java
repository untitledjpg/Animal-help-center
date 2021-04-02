package com.example.Animalhelpcenter.controlers;

import com.example.Animalhelpcenter.data.Admin;
import com.example.Animalhelpcenter.data.AdminRepository;
import com.example.Animalhelpcenter.mvc.AdminLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    private AdminRepository repo;

    public AdminController() {
        repo = new AdminRepository();
    }

    @GetMapping("")
    public String getIndex(Model model) {
        model.addAttribute("error","");
        model.addAttribute("hasError", false);
        return "login";
    }
    //HttpSession session
    @PostMapping("")
    public String login(AdminLoginDto userData, Model model, HttpServletRequest request) {

        var user = repo.login(userData.getLogin(), userData.getPwd());

        if(user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }

        request.getSession().setAttribute("User", user);

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {

        var userAdmin = (Admin)session.getAttribute("User");

        model.addAttribute("user", userAdmin);

        return "profile";
    }
}

