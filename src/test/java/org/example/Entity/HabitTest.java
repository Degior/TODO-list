package org.example.Entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HabitTest {
    private Habit habit;

    @Before
    public void setUp() {
        habit = new Habit(1L, "Exercise", "Do exercise daily", 7);
    }

    @Test
    public void testPrintValues() {
        String expectedOutput = "Нужно сделать\nНазвание = Exercise\nОписание = Do exercise daily";
        String actualOutput = habit.printValues();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testDecreaseDuration() {
        int initialDuration = habit.getDayDuration();
        int expectedDuration = initialDuration - 1;

        habit.decreaseDuration();

        assertEquals(expectedDuration, habit.getDayDuration());
    }
}
