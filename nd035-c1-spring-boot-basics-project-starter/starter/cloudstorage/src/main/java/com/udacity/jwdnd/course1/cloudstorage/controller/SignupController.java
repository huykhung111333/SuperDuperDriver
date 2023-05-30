package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService  userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String singup(){
        return "signup";
    }
    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model, HttpSession httpSession){
        String signupError = "";
        if(userService.getUser(user.getUsername()) != null){
            signupError = "Username is already exist.";
        } else{
            if(userService.createUser(user) <= 0){
                signupError = "Something went wrong, please try again.";
            }
        }
        if(signupError.isBlank()){
            httpSession.setAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }
        return "redirect:/login";
    }


}
