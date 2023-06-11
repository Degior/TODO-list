package org.example.NoteStructureTests;

import org.example.MessageProcessing.FormatterException;
import org.example.MessageProcessing.NoteFormatter;
import org.example.NoteStrusture.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoteTest {

    Note note;

    @BeforeEach
    public void setUo(){
        note = new Note();
    }

    @Test
    public void getTextTask(){
        note.addTask("новое задание");
        try {
            Assertions.assertEquals(NoteFormatter.getNoteText(note), "-новое задание\n");
        }catch (FormatterException e){
            e.printStackTrace();
        }

    }

    @Test
    public void getTextTask2(){
        note.addTask("новое задание");
        note.addTask("еще одно задание");
        try {
            Assertions.assertEquals(NoteFormatter.getNoteText(note), "-новое задание\n-еще одно задание\n");
        }catch (FormatterException e){
            e.printStackTrace();
        }
    }

}
