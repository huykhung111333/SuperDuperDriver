package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RequestMapping("/file")
@Controller
public class FileController {
    FileService fileService;
    AuthenticationService authenticationService;

    public FileController(FileService fileService, AuthenticationService authenticationService) {
        this.fileService = fileService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public String insertFile(@RequestParam("file") MultipartFile file, HttpSession httpSession){

        int userId = authenticationService.getUserId();
        String errorMessage, exceptionMessage;
        int isSuccess = fileService.checkExistFileName(file.getOriginalFilename(), userId) ? 1 : 0;
        if (isSuccess != 1){
            errorMessage = "File name is already exists";
            assignHttpSession(httpSession, isSuccess, errorMessage, null);
            return "redirect:/result";
        }

        try {
            isSuccess = fileService.insertFile(file, userId);
            assignHttpSession(httpSession, isSuccess, null, null);
        } catch (Exception exception){
            exceptionMessage = exception.getLocalizedMessage();
            assignHttpSession(httpSession, isSuccess, null, exceptionMessage);
            return "redirect:/result";
        }
        return "redirect:/result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable String fileId, HttpSession httpSession){
        int isSuccess = 0;
        try {
            isSuccess = fileService.deleteFile(Integer.parseInt(fileId));
            if (isSuccess > 0) {
                assignHttpSession(httpSession, isSuccess, null, null );
            } else {
                assignHttpSession(httpSession, isSuccess, "No file has been deleted!", null );
            }
            return "redirect:/result";
        } catch (Exception ex) {
            assignHttpSession(httpSession, isSuccess, null, ex.getMessage() );
            return "redirect:/result";
        }
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable int fileId) {
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    private static HttpSession assignHttpSession(HttpSession httpSession, int isSuccess, String errorMessage, String exceptionMessage){
        httpSession.setAttribute("isSuccess", isSuccess);
        httpSession.setAttribute("errorMessage", errorMessage);
        httpSession.setAttribute("exceptionMessage", exceptionMessage);
        return httpSession;
    }
}
