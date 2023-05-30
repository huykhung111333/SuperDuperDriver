package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    NoteService noteService;

    AuthenticationService authenticationService;
    FileService fileService;

    CredentialService credentialService;

    public HomeController(NoteService noteService, AuthenticationService authenticationService, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String homeView(Model model){
        Integer userId = authenticationService.getUserId();
        List<Note> listNote = noteService.getListNoteByUserId(userId);
        List<File> fileList = fileService.getListFileByUserId(userId);
        List<Credential> credentialList = credentialService.getCredentialsListByUserId(userId);
        Note note = new Note(); File file = new File(); Credential credential = new Credential();
        model.addAttribute("note", note);
        model.addAttribute("listNote", listNote);
        model.addAttribute("file", file);
        model.addAttribute("fileList", fileList);
        model.addAttribute("credential", credential);
        model.addAttribute("credentialList", credentialList);
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/result")
    public String resultView(HttpSession httpSession, Model model){
        model.addAttribute("isSuccess", httpSession.getAttribute("isSuccess"));
        model.addAttribute("errorMessage", httpSession.getAttribute("errorMessage"));
        model.addAttribute("exceptionMessage", httpSession.getAttribute("exceptionMessage"));
        return "result";
    }
}