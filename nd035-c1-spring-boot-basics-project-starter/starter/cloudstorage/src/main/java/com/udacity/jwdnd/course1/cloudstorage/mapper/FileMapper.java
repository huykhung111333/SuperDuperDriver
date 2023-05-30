package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("Insert into FILES(filename, contenttype, filesize, userid, filedata) Values(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(File file);

    @Select("Select * From FILES Where fileName = #{fileName} And userId = #{userId}")
    File checkExistFileName(String fileName, int userId);

    @Delete("Delete FILES Where fileId = #{fileId}")
    int deleteFileById(int fileId);

    @Select("Select * From FILES Where userId = #{userId}")
    List<File> getFileListByUserId(int userId);

    @Select("Select * From FILES Where fileId = #{fileId}")
    File getFileById(int fileId);
}
