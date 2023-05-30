package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    public LoginController() {
    }
    @GetMapping
    public String loginPage(Model model, HttpSession httpSession){
        model.addAttribute("signupSuccess", httpSession.getAttribute("signupSuccess"));
        return "login";
    }
}
