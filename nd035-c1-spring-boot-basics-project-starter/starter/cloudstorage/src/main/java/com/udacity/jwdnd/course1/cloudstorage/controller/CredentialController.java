package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    CredentialService credentialService;

    AuthenticationService authenticationService;
    EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, AuthenticationService authenticationService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.authenticationService = authenticationService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute("credential") Credential credential, HttpSession httpSession) {
        credential.setUserId(authenticationService.getUserId());
        int isSuccess = 0;
        try {
            isSuccess = credentialService.insertOrUpdateCredential(credential);
            if (isSuccess > 0) {
                assignHttpSession(httpSession, isSuccess, null, null);
            } else {
                if (credential.getCredentialId() != 0) {
                    assignHttpSession(httpSession, isSuccess, "Can't update credential. Please try again!", null);
                } else {
                    assignHttpSession(httpSession, isSuccess, "Can't add new credential. Please try again!", null);
                }
            }
        } catch (Exception ex) {
            assignHttpSession(httpSession, isSuccess, null, ex.getMessage());
        }
        return "redirect:/result";
    }

    private static HttpSession assignHttpSession(HttpSession httpSession, int isSuccess, String errorMessage, String exceptionMessage) {
        httpSession.setAttribute("isSuccess", isSuccess);
        httpSession.setAttribute("errorMessage", errorMessage);
        httpSession.setAttribute("exceptionMessage", exceptionMessage);
        return httpSession;
    }

    @GetMapping("/passwordRequest")
    public ResponseEntity<String> handleRequest(@RequestParam("param") String credentialId) {
        Credential credential = credentialService.getCredentialById(Integer.parseInt(credentialId));
        String password = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        return ResponseEntity.ok(password);
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") String credentialId, HttpSession httpSession) {
        int isSuccess = 0;
        try {
            isSuccess = credentialService.deleteCredential(Integer.parseInt(credentialId));
            if (isSuccess > 0) {
                assignHttpSession(httpSession, isSuccess, null, null);
            } else {
                assignHttpSession(httpSession, isSuccess, "Can't delete credential. Please try again!", null);
            }
        } catch (Exception ex) {
            assignHttpSession(httpSession, isSuccess, null, ex.getMessage());
        }
        return "redirect:/result";
    }
}