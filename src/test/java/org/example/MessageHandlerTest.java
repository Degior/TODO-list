package org.example;

import org.example.MessageProcessing.MessageHandler;
import org.example.Repository.HabitsTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageHandlerTest {
    private HabitsTrackerRepository habitsTrackerRepository;
    private MessageHandler messageHandler;

    @BeforeEach
    void setUp() {
        habitsTrackerRepository = Mockito.mock(HabitsTrackerRepository.class);
        messageHandler = new MessageHandler(habitsTrackerRepository);
    }

    @Test
    void processInput_withStartCommand_shouldReturnStartMessage() {
        String result = messageHandler.processInput(1L, "/start");
        assertEquals(Report.START_MESSAGE, result);
    }

    @Test
    void processInput_withHelpCommand_shouldReturnHelpMessage() {
        String result = messageHandler.processInput(1L, "/help");
        assertEquals(Report.HELP_MESSAGE, result);
    }

    @Test
    void processInput_withShowHabitCommand_shouldReturnShowHabitMessage() {
        String result = messageHandler.processInput(1L, "/showHabit");
        assertEquals(Report.HABIT_SHOW, result);
    }

    @Test
    void processInput_withUnknownCommand_shouldReturnDefaultMessage() {
        String result = messageHandler.processInput(1L, "/unknownCommand");
        assertEquals(Report.DEFAULT_MESSAGE, result);
    }
}
