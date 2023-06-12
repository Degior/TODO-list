package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.MessageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlerTest {
    private MessageHandler messageHandler;

    @BeforeEach
    void setUp() {
        messageHandler = new MessageHandler();
    }

    @Test
    void processInputWithStartCommandShouldReturnStartMessage() {
        String result = messageHandler.processInput(1L, "/start");
        Assertions.assertEquals("""
            Привет! Я бот, который поможет тебе сформировать полезные привычки и ввести список твоих дел. 
            Я буду напоминать тебе о твоих привычках и следить за твоими успехами. 
            Для начала работы со мной введи "Помощь"
            """, result);
    }

    @Test
    public void processInput_NonStartCommand_ReturnsStartMessage() {
        String result = messageHandler.processInput(1L, "Начать");
        assertEquals("""
            Привет! Я бот, который поможет тебе сформировать полезные привычки и ввести список твоих дел. 
            Я буду напоминать тебе о твоих привычках и следить за твоими успехами. 
            Для начала работы со мной введи "Помощь"
            """, result);
    }

    @Test
    void processInputWithHelpCommandShouldReturnHelpMessage() {
        String result = messageHandler.processInput(1L, "/help");
        assertEquals("""
            Я умею показывать твои дела и привычки и добавлять новые
            /help (Помощь)
            вывести это сообщение
            /getNotesList (Список заметок)
            показать список заметок
            /createNote (Добавить заметку)
            добавить заметку на день
            /deleteNote (Удалить заметку)
            удалить заметку
            /openNote (Открыть заметку)
            открыть заметку
            /cancel (Отмена)
            отменить текущее действие
            """, result);
    }

    @Test
    void processInputWithUnknownCommandShouldReturnDefaultMessage() {
        String result = messageHandler.processInput(1L, "/unknownCommand");
        assertEquals("Неизвестная команда :( Для просмотра команд введите команду /help", result);
    }

    @Test
    public void testAppendNoteValidNoteReturnsNoteModificationMessage() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/createNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("Теперь можешь внести в план на день несколько задач через Enter. \n", result);
    }

    @Test
    public void testAppendNoteInvalidNoteReturnsNoteModificationMessage() {
        String noteMessage = "12.12.2020";

        messageHandler.processInput(1L, "/createNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("Теперь можешь внести в план на день несколько задач через Enter. \n", result);
    }

    @Test
    public void testOpenNoteInvalidNoteReturnsNoteMessage() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/openNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("Такой заметки не существует. Можете попробовать еще раз", result);
    }

    @Test
    public void testNoteDeletionValidNoteReturnsNoteDeletionMessage() {
        String noteMessage = "12.12";

        messageHandler.processInput(1L, "/createNote");
        messageHandler.processInput(1L, noteMessage);

        messageHandler.processInput(1L, "/deleteNote");
        String result = messageHandler.processInput(1L, noteMessage);

        assertEquals("Заметка удалена", result);
    }

    @Test
    public void OpenNoteTest(){
        String noteMessage = "12.12";

        messageHandler.processInput(1l, "/createNote");
        messageHandler.processInput(1l, noteMessage);
        messageHandler.processInput(1l, "купить цветов");
        messageHandler.processInput(1l, "украсить дом");

        messageHandler.processInput(1l, "/openNote");


        assertEquals("1. купить цветов\n2. украсить дом\n",  messageHandler.processInput(1l, noteMessage));
    }
}
