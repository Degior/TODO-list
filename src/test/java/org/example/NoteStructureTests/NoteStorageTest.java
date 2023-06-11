package org.example.NoteStructureTests;

import org.example.MessageProcessing.FormatterException;
import org.example.MessageProcessing.NoteFormatter;
import org.example.NoteStrusture.NoteStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class NoteStorageTest {

    NoteStorage noteStorage;
    LocalDate localDate;

    Long chatId = 0l;

    Long chatId2 = 2l;

    @BeforeEach
    public void setUp(){
        noteStorage = new NoteStorage();
        localDate = LocalDate.of(LocalDate.now().getYear(), 10, 10);

        noteStorage.addNote(chatId, localDate);
    }

    @Test
    public void appendNoteWithException(){

        Assertions.assertEquals(false, noteStorage.addNote(chatId, localDate));
    }

    @Test
    public void fillNoteTest() throws FormatterException {
        noteStorage.addTaskToNote("новая задача");
        Assertions.assertEquals("1. новая задача\n", NoteFormatter.getNoteText(noteStorage.getNote(chatId, localDate)));
    }

    @Test
    public void deleteNoteTest(){
        Assertions.assertEquals(true, noteStorage.deleteNote(chatId, localDate));

    }

    @Test
    public void deleteNoteWithFail(){
        Assertions.assertEquals(false, noteStorage.deleteNote(chatId2, localDate));
    }
}
