package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {
    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int insertFile(MultipartFile multipartFile, int userId) throws Exception {
        File file = new File(multipartFile.getOriginalFilename(), multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), userId, multipartFile.getBytes());
        return fileMapper.createFile(file);
    }

    public int deleteFile(int fileId) {
        return fileMapper.deleteFileById(fileId);
    }

    public List<File> getListFileByUserId(int userId) {
        return fileMapper.getFileListByUserId(userId);
    }

    public boolean checkExistFileName(String fileName, int userId) {
        return fileMapper.checkExistFileName(fileName, userId) == null;
    }

    public File getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }
}
