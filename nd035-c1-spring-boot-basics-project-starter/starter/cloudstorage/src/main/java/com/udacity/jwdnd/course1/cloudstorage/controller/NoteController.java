package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/note")
public class NoteController {
    AuthenticationService authenticationService;

    NoteService noteService;

    public NoteController(AuthenticationService authenticationService, NoteService noteService) {
        this.authenticationService = authenticationService;
        this.noteService = noteService;
    }

    @PostMapping("/add")
    public String createOrUpdateNote(@ModelAttribute("note") Note note, HttpSession httpSession) {
        Integer userId = authenticationService.getUserId();
        note.setUserId(userId);
        Integer noteId = note.getNoteId();
        int isSuccess = 0;
        String exceptionMessage = null;
        try {
            if (noteId == null || noteId.toString().equals("")) {
                isSuccess = noteService.createNote(note);
            } else {
                isSuccess = noteService.updateNote(note);
            }
        } catch (Exception e){
            exceptionMessage = e.getMessage();
        }
        httpSession.setAttribute("isSuccess", isSuccess);
        httpSession.setAttribute("errorMessage", null);
        httpSession.setAttribute("exceptionMessage", exceptionMessage);
        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable String noteId, HttpSession httpSession) {
        int isSuccess = noteService.deleteNote(Integer.parseInt(noteId));
        httpSession.setAttribute("isSuccess", isSuccess);
        return "redirect:/result";
    }
}
