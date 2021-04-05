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
public class AdminController {

    private AdminRepository repo;

    public AdminController(){
        repo = new AdminRepository();
    }

    @GetMapping("") //1 come to page
    public String getIndex(Model model){
        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "login";
    }
    //HttpSession session

    @PostMapping("") // 2 return, after posting data - error
    public String login(AdminLoginDto userData, Model model, HttpServletRequest request) {    //same name as name of form

        var user = repo.login(userData.getLogin(), userData.getPwd());
        if (user == null){
            model.addAttribute("error","Unable to login");
            model.addAttribute("hasError", true);
            return "login"; // redirect back to login
        }
        request.getSession().setAttribute(SessionData.User, user);

        model.addAttribute("user", user);

        return "admin";
        //return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) { // can access with servlet request and session

        var user = (Admin)session.getAttribute(SessionData.User); // SessionData.User (new class)
        if (user == null){
            return "access_denied";
        }
        //      session.setAttribute();  // to set something in session

        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getId());

        return "profile";
    }
}