package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.FormatterException;
import org.example.MessageProcessing.NoteFormatter;
import org.example.NoteStrusture.NoteStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class NoteFormatterTest {

    private NoteStorage noteStorage = new NoteStorage();
    private Long chatId = 0l;
    private LocalDate localDate;

    @BeforeEach
    public void setUp(){
        localDate = LocalDate.of(LocalDate.now().getYear(), 10, 10);
        noteStorage.addNote(chatId, localDate);
    }
    /**
     * Тест проверяет возможность добавления текста в заметку и его верное представление для пользователя
     */
    @Test
    public void getNoteTest() throws FormatterException {
        noteStorage.addTaskToNote(chatId, "новая задача");
        Assertions.assertEquals("1. новая задача\n", NoteFormatter.getNoteText(noteStorage.getNote(chatId, localDate)));
    }

    @Test
    public void getAllNotesTest() throws FormatterException{
        LocalDate localDate2 = LocalDate.of(LocalDate.now().getYear(), 11, 11);
        noteStorage.addNote(chatId, localDate2);
        Assertions.assertEquals("2023-11-11\n" +
                        "2023-10-10\n",
                NoteFormatter.getAllNotes(noteStorage.getAllNotes(chatId)));
    }
}
