package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.MessageHandler;
import org.example.MessageProcessing.MessageHandlerState;
import org.example.Report;
import org.example.Repository.HabitsTrackerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlerTest {
    private HabitsTrackerRepository habitsTrackerRepository;
    private MessageHandler messageHandler;

    @BeforeEach
    void setUp() {
        habitsTrackerRepository = new HabitsTrackerRepository();
        messageHandler = new MessageHandler(habitsTrackerRepository);
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
            /addHabit (Добавить привычку)
            добавить привычку
            /removeHabit (Убрать привычку)
            удалить привычку
            /showHabit (Просмотреть привычки)
            показать список привычек
            /editHabit (Редактировать привычку)
            редактировать привычку
            /markHabit (Отметить выполнение)
            отметить выполнение привычки
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
    public void testHabitCreationValidHabitReturnsHabitCreationMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка успешно добавлена!", result);
    }

    @Test
    public void testHabitCreationInvalidHabitReturnsHabitCreationMessage() {
        String habitMessage = "Просыпаться;Описание привычки";

        messageHandler.processInput(1L, "/addHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка не добавлена!", result);
    }

    @Test
    public void testHabitRemovalValidHabitReturnsHabitRemovalMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться";

        messageHandler.processInput(1L, "/removeHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка успешно удалена!", result);
    }

    @Test
    public void testHabitRemovalInvalidHabitReturnsHabitRemovalMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться;Описание привычки";

        messageHandler.processInput(1L, "/removeHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка не удалена!", result);
    }

    @Test
    public void testHabitEditValidHabitReturnsHabitEditMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться";

        messageHandler.processInput(1L, "/editHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Название: Просыпаться;Описание: Описание привычки;Продолжительность: 13";

        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка успешно отредактирована!", result);
    }

    @Test
    public void testHabitEditInvalidHabitReturnsHabitEditMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться1";

        messageHandler.processInput(1L, "/editHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка не отредактирована! Не найдено привычки с таким названием", result);
    }

    @Test
    public void testHabitEditInvalidHabitReturnsHabitEditMessage2() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться";

        messageHandler.processInput(1L, "/editHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Продолжительность: -5";

        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычку нельзя так  отредактировать!", result);
    }

    @Test
    public void testHabitMarkValidHabitReturnsHabitMarkMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться";

        messageHandler.processInput(1L, "/markHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка успешно отмечена!", result);
    }

    @Test
    public void testHabitMarkInvalidHabitReturnsHabitMarkMessage() {
        String habitMessage = "Просыпаться;Описание привычки;12";

        messageHandler.processInput(1L, "/addHabit");
        messageHandler.processInput(1L, habitMessage);

        habitMessage = "Просыпаться1";

        messageHandler.processInput(1L, "/markHabit");
        String result = messageHandler.processInput(1L, habitMessage);

        assertEquals("Привычка не отмечена!", result);
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
