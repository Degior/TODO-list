package org.example.NoteStructureTests;

import org.example.MessageProcessing.FormatterException;
import org.example.MessageProcessing.NoteFormatter;
import org.example.NoteStrusture.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование класса Note
 */
public class NoteTest {

    Note note;

    @BeforeEach
    public void setUp(){
        note = new Note();
        note.addTask("новое задание");
    }

    /**
     * Тестирование добавления задачи в заметку
     */
    @Test
    public void getTextTask(){
        try {
            Assertions.assertEquals(NoteFormatter.getNoteText(note), "1. новое задание\n");
        }catch (FormatterException e){
            e.printStackTrace();
        }

    }

    /**
     * Тестирование удаления задачи
     */
    @Test
    public void deleteTask(){
        Assertions.assertTrue(note.deleteTask(1));
    }

    /**
     * Тестирование невозможности удаления несуществующей задачи
     */
    @Test
    public void deleteTaskWithFail(){
        note.deleteTask(1);
        Assertions.assertFalse(note.deleteTask(1));
    }

}
