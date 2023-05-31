package org.example.NoteStructureTests;

import org.example.NoteStrusture.NoteException;
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
        try {
            noteStorage.appendNote(chatId, localDate);
        }catch (NoteException e){
            e.printStackTrace();
        }
    }

    @Test
    public void appendNoteWithException(){
        NoteException thrown = Assertions.assertThrows(NoteException.class, () -> {
            noteStorage.appendNote(chatId, localDate);
        });

        Assertions.assertEquals("Такая заметка уже есть", thrown.getMessage());
    }

    /*@Test
    public void fillNoteTest(){
        noteStorage.fillNote("новая задача");
        try {2
            Assertions.assertEquals("1. новая задача\n", noteStorage.getNoteText(chatId, localDate));
        }catch (NoteException e){
            e.printStackTrace();
        }
    }*/

    @Test
    public void deleteNoteTest(){
        Assertions.assertEquals(true, noteStorage.deleteNote(chatId, localDate));

    }

    @Test
    public void deleteNoteWithFail(){
        Assertions.assertEquals(false, noteStorage.deleteNote(chatId2, localDate));
    }
}
