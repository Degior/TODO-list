package org.example.NoteStructureTests;
import org.example.NoteStrusture.NoteStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
/**
 * Тестирование класса NoteStorage
 */
public class NoteStorageTest {
    private NoteStorage noteStorage;
    private LocalDate localDate;
    private Long chatId = 0l;

    @BeforeEach
    public void setUp(){
        noteStorage = new NoteStorage();
        localDate = LocalDate.of(LocalDate.now().getYear(), 10, 10);
        noteStorage.addNote(chatId, localDate);
    }

    /**
     * Тест проверяет возможность добавления заметки
     */
    @Test
    public void appendNote(){
        LocalDate localDate2 = LocalDate.of(LocalDate.now().getYear(), 11, 11);
        Assertions.assertTrue(noteStorage.addNote(chatId, localDate2));
    }

    /**
     * Тест проверяет невозможность добавления заметки по такой же дате
     */
    @Test
    public void appendNoteWithFail(){
        Assertions.assertFalse(noteStorage.addNote(chatId, localDate));
    }


    /**
     * Тест проверяет возможность удаления заметки
     */
    @Test
    public void isPossibleToDeleteNoteTest(){
        Assertions.assertTrue(noteStorage.isPossibleToDeleteNote(chatId, localDate));
    }

    /**
     * Тест проверяет логику удаления заметки
     */
    @Test
    public void deleteNoteTest(){
        noteStorage.deleteNote(chatId, localDate);
        Assertions.assertFalse(noteStorage.isPossibleToDeleteNote(chatId, localDate));
    }

    /**
     * Тест проверяет отсутствие возможности удаление несуществующей заметки (так как не зарегистрирован пользователь)
     */
   @Test
    public void deleteNoteWithFail(){
       Long chatId2 = 2l;
       Assertions.assertEquals(false, noteStorage.isPossibleToDeleteNote(chatId2, localDate));
    }

    /**
     * Тест проверяет отсутствие возможности удаление несуществующей заметки (так как нет такой заметки)
     */
    @Test
    public void deleteNoteWithFail2(){
        LocalDate localDate2 = LocalDate.of(LocalDate.now().getYear(), 11, 11);
        Assertions.assertFalse(noteStorage.isPossibleToDeleteNote(chatId, localDate2));
    }

    /**
     * Тест проверяет работу метода сброса текущей заметки
     */
    @Test
    public void resetNoteTest(){
        noteStorage.resetNote(chatId);
        String message = "";
        try {
            noteStorage.addTaskToNote(chatId, "text");
        }catch (NullPointerException e){
            message = e.getMessage();
        }
        Assertions.assertEquals("Cannot invoke \"org.example.NoteStrusture.Note.addTask(String)\" because the return value of \"java.util.Map.get(Object)\" is null", message);
    }

}
