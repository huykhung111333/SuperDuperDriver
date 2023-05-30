package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

    public List<Note> getListNoteByUserId(Integer userId) {
        return noteMapper.getListNoteByUserId(userId);
    }

    public Note getNoteById(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }
}
