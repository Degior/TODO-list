package org.example.Repository;

import org.example.Entity.Habit;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HabitsTrackerRepositoryTest {
    private HabitsTrackerRepository habitsTrackerRepository;

    @Before
    public void setUp() {
        habitsTrackerRepository = new HabitsTrackerRepository();
    }

    @Test
    public void testAddHabit() {
        LocalTime time = LocalTime.of(10, 0);
        Habit habit = new Habit(1L, "Exercise", "1", 2);

        habitsTrackerRepository.addHabit(time, habit);

        assertEquals(habit, habitsTrackerRepository.getHabitByTime(time));
    }

    @Test
    public void testRemoveHabitIfDurationEnd() {
        LocalTime time = LocalTime.of(10, 0);
        Habit habit = new Habit(1L, "Exercise", "1", 2);

        habitsTrackerRepository.addHabit(time, habit);
        habitsTrackerRepository.removeHabitIfDurationEnd(habit);
        habitsTrackerRepository.removeHabitIfDurationEnd(habit);

        assertNull(habitsTrackerRepository.getHabitByTime(time));
    }

    @Test
    public void testGetEarliestHabitTime() {
        LocalTime time1 = LocalTime.of(10, 0);
        LocalTime time2 = LocalTime.of(8, 0);
        Habit habit1 = new Habit(1L, "Exercise", "1", 2);
        Habit habit2 = new Habit(2L, "Read", "1", 3);

        habitsTrackerRepository.addHabit(time1, habit1);
        habitsTrackerRepository.addHabit(time2, habit2);

        assertEquals(time2, habitsTrackerRepository.getEarliestHabitTime());
    }

    @Test
    public void testGetHabitByTime() {
        LocalTime time = LocalTime.of(10, 0);
        Habit habit = new Habit(1L, "Exercise", "1", 2);

        habitsTrackerRepository.addHabit(time, habit);

        assertEquals(habit, habitsTrackerRepository.getHabitByTime(time));
    }
}
