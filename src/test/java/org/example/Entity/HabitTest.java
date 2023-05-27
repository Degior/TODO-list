package org.example.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HabitTest {
    private Habit habit;

    @BeforeEach
    public void setup() {
        habit = new Habit("Exercise", "Daily workout routine", 30);
    }
    @Test
    public void testGetName() {
        Assertions.assertEquals("Exercise", habit.getName());
    }

    @Test
    public void testSetName() {
        habit.setName("Running");
        Assertions.assertEquals("Running", habit.getName());
    }

    @Test
    public void testGetDescription() {
        Assertions.assertEquals("Daily workout routine", habit.getDescription());
    }

    @Test
    public void testSetDescription() {
        habit.setDescription("Morning yoga session");
        Assertions.assertEquals("Morning yoga session", habit.getDescription());
    }

    @Test
    public void testGetDayDuration() {
        Assertions.assertEquals(30, habit.getDayDuration());
    }

    @Test
    public void testSetDayDuration() {
        habit.setDayDuration(60);
        Assertions.assertEquals(60, habit.getDayDuration());
    }

    @Test
    public void testDecreaseDuration() {
        habit.decreaseDuration();
        Assertions.assertEquals(29, habit.getDayDuration());
    }

    @Test
    public void testPrintValues() {
        String expected = "Название = Exercise\nОписание = Daily workout routine";
        Assertions.assertEquals(expected, habit.printValues());
    }
}
