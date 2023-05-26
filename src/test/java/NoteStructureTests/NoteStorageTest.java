package NoteStructureTests;

import NoteStrusture.NoteException;
import NoteStrusture.NoteStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class NoteStorageTest {

    NoteStorage noteStorage;
    LocalDate localDate;

    @BeforeEach
    public void setUp(){
        noteStorage = new NoteStorage();
        localDate = LocalDate.of(LocalDate.now().getYear(), 10, 10);
        try {
            noteStorage.appendNote(localDate);
        }catch (NoteException e){
            e.printStackTrace();
        }
    }

    @Test
    public void appendNoteWithException(){
        NoteException thrown = Assertions.assertThrows(NoteException.class, () -> {
            noteStorage.appendNote(localDate);
        });

        Assertions.assertEquals("Такая заметка уже есть", thrown.getMessage());
    }

    @Test
    public void fillNoteTest(){
        noteStorage.fillNote("новая задача");
        try {
            Assertions.assertEquals("-новая задача\n", noteStorage.getNoteText(localDate));
        }catch (NoteException e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteNoteTest(){
        Assertions.assertEquals(true, noteStorage.deleteNote());

    }
}
