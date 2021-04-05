package com.example.Animalhelpcenter.controllers;

import com.example.Animalhelpcenter.data.Cat;
import com.example.Animalhelpcenter.data.DatabaseHibernateManager;
import com.example.Animalhelpcenter.data.DatabaseManager;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    DatabaseManager dm = new DatabaseManager();  //JDBC
    DatabaseHibernateManager dhm = new DatabaseHibernateManager(); //hibernate


    @GetMapping("/admin/edit")                  // choose cat to edit page
    public String manageCats(Model model){
        //if user null access should be denied
        var cats = dhm.getCats();
        model.addAttribute("cats", cats);
        return "choose_cat";
    }

    @GetMapping("/admin/edit/{id}")             // edit specific cat
    public String editCat(@PathVariable int id, Model model){

        var cat = dhm.getById(id);
        model.addAttribute("cat", cat);
        return "edit_cat";
    }

    @Test
    public void getCatById(){
        int id = 5;
        var cat = dhm.getById(id);
        System.out.println(cat.getName());
    }

   /* @PostMapping("/admin/add")    // not ready yet
    public ModelAndView saveApplication(@ModelAttribute("addCat") Cat dto) {


        var cat = new Cat(0, dto.getName(), dto.getAge(), dto.getColor(),
                dto.getSex(), dto.getNeutered(), dto.getDescription(), null,
                dto.getCatStatus(), dto.getCatArrival(), dto.getTempHomeId());
        dhm.save(cat);

        return new ModelAndView("redirect:/app/sent"); // redirect
    }*/
}
