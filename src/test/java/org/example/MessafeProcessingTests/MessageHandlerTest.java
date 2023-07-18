package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.MessageHandler;
import org.example.MessageProcessing.NotificationRepository;
import org.example.MessageProcessing.Parser;
import org.example.MessageProcessing.ParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlerTest {
    private MessageHandler messageHandler;
    TestBot messageSender;

    @BeforeEach
    void setUp() {
        NotificationRepository notificationRepository = new NotificationRepository();
        messageSender = new TestBot();
        messageHandler = new MessageHandler(messageSender, notificationRepository);
    }

    /**
     * Проверка корректности обработки при вводе неизвестной команды
     */

    @Test
    void processInputWithUnknownCommandShouldReturnDefaultMessage() {
        messageHandler.processInput(1L, "/unknownCommand");
        assertEquals("Неизвестная команда :( Для просмотра команд введите команду /help", messageSender.getMessages().get(0));
    }

    /**
     * Проверка работы команды /createNote
     */
    @Test
    public void testNoteCreation() throws ParserException {
        String noteMessage = "12.12";
        LocalDate date = Parser.parseData(noteMessage);


        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "task1");
        messageHandler.processInput(1L, "Пропустить");
        messageHandler.processInput(1L, "task2");

        assertEquals("Теперь можешь внести в план на день несколько задач через Enter. \n", messageSender.getMessages().get(1));

        messageHandler.processInput(1l, "/getNotesList");
        assertEquals("2023-12-12\n", messageSender.getMessages().get(messageSender.getMessages().size()-1));

        messageHandler.processInput(1l, "/openNote");
        messageHandler.processInput(1l, noteMessage);

        assertEquals("1. task1\n" +
                "2. task2\n", messageSender.getMessages().get(messageSender.getMessages().size()-1));
    }


/**
     * Проверка работы команды /openNote если заметки не существует
     */

    @Test
    public void testOpenNoteInvalid() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/openNote");
        messageHandler.processInput(1L, noteMessage);

        assertEquals("Такой заметки не существует. Можете попробовать еще раз", messageSender.getMessages().get(1));
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
        messageHandler.processInput(1L, "Пропустить");

        messageHandler.processInput(1L, "/openNote");
        messageHandler.processInput(1L, noteMessage);

        assertEquals("1. task1\n", messageSender.getMessages().get(3));
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
        messageHandler.processInput(1L, noteMessage);

        assertEquals("Заметка удалена", messageSender.getMessages().get(3));

        messageHandler.processInput(1l, "/getNotesList");
        assertEquals("У вас нет заметок.", messageSender.getMessages().get(4));
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
        messageHandler.processInput(1L, "Пропустить");

        messageHandler.processInput(1L, "/editNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "Добавить task2");

        messageHandler.processInput(1l, "/openNote");
        messageHandler.processInput(1l, noteMessage);
        assertEquals("1. task1\n" +
                "2. task2\n", messageSender.getMessages().get(messageSender.getMessages().size() - 1));
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
        messageHandler.processInput(1L, "Пропустить");

        messageHandler.processInput(1L, "/editNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "Удалить 1");

        assertEquals("Задача удалена", messageSender.getMessages().get(messageSender.getMessages().size()-1));

        messageHandler.processInput(1L, "/openNote");
        messageHandler.processInput(1L, noteMessage);
        assertEquals("", messageSender.getMessages().get(messageSender.getMessages().size()-1));

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
        messageHandler.processInput(1L, "Пропустить");

        messageHandler.processInput(1L, "/editNote");
        messageHandler.processInput(1L, noteMessage);
        messageHandler.processInput(1L, "Отметить выполненным 1");

        messageHandler.processInput(1L, "/openNote");
        messageHandler.processInput(1L, noteMessage);

        assertEquals("V task1\n\n", messageSender.getMessages().get(messageSender.getMessages().size()-1));
    }

}

