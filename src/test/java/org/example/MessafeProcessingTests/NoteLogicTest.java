package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.NotesLogic;
import org.example.NoteStrusture.NoteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class NoteLogicTest {

    NotesLogic notesLogic = new NotesLogic();
    LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), 10, 10);

    LocalDate localDate2 = LocalDate.of(LocalDate.now().getYear(), 11, 10);


    @BeforeEach
    public void setUp() throws NoteException {
        notesLogic.addNote(localDate);
        notesLogic.addTextToNote("text for first note");
        notesLogic.addNote(localDate2);
        notesLogic.addTextToNote("text for second note");
    }

    @Test
    public void getAllNotestest(){
        Assertions.assertEquals("2023-10-10\n2023-11-10\n", notesLogic.getAllNotes());
    }

}
