package org.example.Repository;

import org.example.Entity.Habit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HabitsTrackerRepositoryTest {
    private HabitsTrackerRepository habitsTrackerRepository;

    @BeforeEach
    public void setUp() {
        habitsTrackerRepository = new HabitsTrackerRepository();
    }

    @Test
    public void testAddHabit() {
        Long chatId = 1L;
        Habit habit = new Habit("Exercise", "Do exercise daily", 30);
        boolean result = habitsTrackerRepository.addHabit(chatId, habit);
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddHabit_duplicateHabit() {
        Long chatId = 1L;
        Habit habit = new Habit("Exercise", "Do exercise daily", 30);
        habitsTrackerRepository.addHabit(chatId, habit);

        boolean result = habitsTrackerRepository.addHabit(chatId, habit);
        Assertions.assertFalse(result);
    }

    @Test
    public void testRemoveHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";
        Habit habit = new Habit(habitName, "Do exercise daily", 30);
        habitsTrackerRepository.addHabit(chatId, habit);

        boolean result = habitsTrackerRepository.removeHabit(chatId, habitName);
        Assertions.assertTrue(result);
    }

    @Test
    public void testRemoveHabit_nonExistentHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";

        boolean result = habitsTrackerRepository.removeHabit(chatId, habitName);
        Assertions.assertFalse(result);
    }

    @Test
    public void testGetHabits() {
        Long chatId = 1L;
        Habit habit1 = new Habit("Exercise", "Do exercise daily", 30);
        Habit habit2 = new Habit("Read", "Read a book", 60);
        habitsTrackerRepository.addHabit(chatId, habit1);
        habitsTrackerRepository.addHabit(chatId, habit2);

        String result = habitsTrackerRepository.getHabits(chatId);
        String expected = "Exercise\nRead\n";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetHabits_noHabits() {
        Long chatId = 1L;

        String result = habitsTrackerRepository.getHabits(chatId);
        String expected = "У вас нет привычек!";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testCheckHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";
        Habit habit = new Habit(habitName, "Do exercise daily", 30);
        habitsTrackerRepository.addHabit(chatId, habit);

        boolean result = habitsTrackerRepository.checkHabit(chatId, habitName);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckHabit_nonExistentHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";

        boolean result = habitsTrackerRepository.checkHabit(chatId, habitName);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEditHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";
        Habit habit = new Habit(habitName, "Do exercise daily", 30);
        habitsTrackerRepository.addHabit(chatId, habit);

        String newName = "Running";
        String newDescription = "Go for a run daily";
        int newDuration = 45;
        habitsTrackerRepository.editHabit(chatId, habitName, newName, newDescription, newDuration);

        HashMap<Habit, Boolean> habitBooleanHashMap = habitsTrackerRepository.chatIdToHabitsMap.get(chatId);
        Habit editedHabit = null;
        for (Map.Entry<Habit, Boolean> entry : habitBooleanHashMap.entrySet()) {
            if (entry.getKey().getName().equals(newName)) {
                editedHabit = entry.getKey();
                break;
            }
        }

        Assertions.assertNotNull(editedHabit);
        Assertions.assertEquals(newName, editedHabit.getName());
        Assertions.assertEquals(newDescription, editedHabit.getDescription());
        Assertions.assertEquals(newDuration, editedHabit.getDayDuration());
    }

    @Test
    public void testMarkHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";
        Habit habit = new Habit(habitName, "Do exercise daily", 30);
        habitsTrackerRepository.addHabit(chatId, habit);

        boolean result = habitsTrackerRepository.markHabit(chatId, habitName);
        Assertions.assertTrue(result);

        HashMap<Habit, Boolean> habitBooleanHashMap = habitsTrackerRepository.chatIdToHabitsMap.get(chatId);
        Boolean marked = habitBooleanHashMap.get(habit);

        Assertions.assertTrue(marked);
    }

    @Test
    public void testMarkHabit_nonExistentHabit() {
        Long chatId = 1L;
        String habitName = "Exercise";

        boolean result = habitsTrackerRepository.markHabit(chatId, habitName);
        Assertions.assertFalse(result);
    }
}
