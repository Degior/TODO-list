package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.MessageHandler;
import org.example.MessageProcessing.Parser;
import org.example.MessageProcessing.ParserException;
import org.example.NoteStrusture.NoteStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageHandlerTest {
    private MessageHandler messageHandler;

    private NoteStorage noteStorage;

    @BeforeEach
    void setUp() {
        noteStorage = new NoteStorage();
        messageHandler = new MessageHandler(noteStorage);
    }

    /**
     * Проверка корректности обработки при вводе неизвестной команды
     */
    @Test
    void processInputWithUnknownCommandShouldReturnDefaultMessage() {
        String result = messageHandler.processInput(1L, "/unknownCommand");
        assertEquals("Неизвестная команда :( Для просмотра команд введите команду /help", result);
    }

    /**
     * Проверка работы команды /createNote
     */
    @Test
    public void testNoteCreation() throws ParserException {
        String noteMessage = "12.12";
        LocalDate date = Parser.parseData(noteMessage);


        messageHandler.processInput(1L, "/createNote");
        String result1 = messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "task1");
        messageHandler.processInput(1L, "task2");

        assertEquals("Теперь можешь внести в план на день несколько задач через Enter. \n", result1);
        assertEquals(1, Objects.requireNonNull(noteStorage.getAllNotes(1L)).size());
        assertEquals("task1", Objects.requireNonNull(noteStorage.getNote(1L, date)).getTasks().get(0).getDescription());
        assertEquals("task2", Objects.requireNonNull(noteStorage.getNote(1L, date)).getTasks().get(1).getDescription());
    }

    /**
     * Проверка работы команды /openNote если заметки не существует
     */
    @Test
    public void testOpenNoteInvalid() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/openNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("Такой заметки не существует. Можете попробовать еще раз", result);
    }

    /**
     * Проверка работы команды /openNote если заметка существует
     */
    @Test
    public void testOpenNoteValid() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "task1");

        messageHandler.processInput(1L, "/openNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("1. task1\n", result);
    }

    /**
     * Проверка работы команды /deleteNote
     */
    @Test
    public void testNoteDelete() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);

        messageHandler.processInput(1L, "/deleteNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("Заметка удалена", result);
        assertEquals(0, noteStorage.getAllNotes(1L).size());
    }

    /**
     * Проверка работы команды /editNote для добавления задачи
     */
    @Test
    public void testEditNoteAdding() throws ParserException {
        String noteMessage = "12.12";
        LocalDate date = Parser.parseData(noteMessage);

        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "task1");

        messageHandler.processInput(1L, "/editNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "Добавить task2");

        assertEquals(1, Objects.requireNonNull(noteStorage.getAllNotes(1L)).size());
        assertEquals("task1", Objects.requireNonNull(noteStorage.getNote(1L, date)).getTasks().get(0).getDescription());
        assertEquals("task2", Objects.requireNonNull(noteStorage.getNote(1L, date)).getTasks().get(1).getDescription());
    }

    /**
     * Проверка работы команды /editNote для удаления задачи
     */
    @Test
    public void testEditNoteDeleting() throws ParserException {
        String noteMessage = "12.12";
        LocalDate date = Parser.parseData(noteMessage);

        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "task1");

        messageHandler.processInput(1L, "/editNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "Удалить 1");

        assertEquals(1, Objects.requireNonNull(noteStorage.getAllNotes(1L)).size());
        assertEquals(0, Objects.requireNonNull(noteStorage.getNote(1L, date)).getTasks().size());
    }

    /**
     * Проверка работы команды /editNote для отметки о выполнении задачи
     */
    @Test
    public void testEditNoteMarking() throws ParserException {
        String noteMessage = "12.12";
        LocalDate date = Parser.parseData(noteMessage);

        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "task1");

        messageHandler.processInput(1L, "/editNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "Отметить выполненным 1");

        assertEquals(1, Objects.requireNonNull(noteStorage.getAllNotes(1L)).size());
        assertTrue(Objects.requireNonNull(noteStorage.getNote(1L, date)).getTasks().get(0).isDone());
    }

}
