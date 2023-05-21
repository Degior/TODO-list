package org.example;

import org.example.Repository.HabitsTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogicTest {
    private HabitsTrackerRepository habitsTrackerRepository;
    private Logic logic;

    @BeforeEach
    void setUp() {
        habitsTrackerRepository = Mockito.mock(HabitsTrackerRepository.class);
        logic = new Logic(habitsTrackerRepository);
    }

    @Test
    void processInput_withStartCommand_shouldReturnStartMessage() {
        String result = logic.processInput(1L, "/start");
        assertEquals(Report.START_MESSAGE, result);
    }

    @Test
    void processInput_withHelpCommand_shouldReturnHelpMessage() {
        String result = logic.processInput(1L, "/help");
        assertEquals(Report.HELP_MESSAGE, result);
    }

    @Test
    void processInput_withAddHabitCommand_shouldSetLastMessageAndReturnAddHabitMessage() {
        String result = logic.processInput(1L, "/addHabit");
        assertEquals(Report.HABIT_ADD, result);
        assertEquals(Report.HABIT_ADD, logic.getLastMessage());
    }

    @Test
    void processInput_withRemoveHabitCommand_shouldSetLastMessageAndReturnRemoveHabitMessage() {
        String result = logic.processInput(1L, "/removeHabit");
        assertEquals(Report.HABIT_REMOVE, result);
        assertEquals(Report.HABIT_REMOVE, logic.getLastMessage());
    }

    @Test
    void processInput_withShowHabitCommand_shouldReturnShowHabitMessage() {
        String result = logic.processInput(1L, "/showHabit");
        assertEquals(Report.HABIT_SHOW, result);
    }

    @Test
    void processInput_withEditHabitCommand_shouldSetLastMessageAndReturnEditHabitMessage() {
        String result = logic.processInput(1L, "/editHabit");
        assertEquals(Report.HABIT_EDIT, result);
        assertEquals(Report.HABIT_EDIT, logic.getLastMessage());
    }

    @Test
    void processInput_withMarkHabitCommand_shouldSetLastMessageAndReturnMarkHabitMessage() {
        String result = logic.processInput(1L, "/markHabit");
        assertEquals(Report.HABIT_MARK, result);
        assertEquals(Report.HABIT_MARK, logic.getLastMessage());
    }


    @Test
    void processInput_withUnknownCommand_shouldReturnDefaultMessage() {
        String result = logic.processInput(1L, "/unknownCommand");
        assertEquals(Report.DEFAULT_MESSAGE, result);
    }
}
