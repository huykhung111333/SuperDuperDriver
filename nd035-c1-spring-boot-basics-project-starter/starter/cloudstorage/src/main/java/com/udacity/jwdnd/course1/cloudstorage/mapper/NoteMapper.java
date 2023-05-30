package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("Select * From NOTES Where userId = #{userId}")
    List<Note> getListNoteByUserId(Integer userId);

    @Insert("Insert into NOTES (notetitle, notedescription, userId) Values(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("Update NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} Where noteId = #{noteId}")
    int updateNote(Note note);

    @Delete("Delete From NOTES Where noteId = #{noteId}")
    int deleteNote(Integer noteId);

    @Select("Select * From NOTES Where noteId = #{noteId}")
    Note getNoteById(Integer noteId);


}
