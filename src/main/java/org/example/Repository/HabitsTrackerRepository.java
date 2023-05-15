package org.example.Repository;

import org.example.Entity.Habit;

import java.time.LocalTime;
import java.util.TreeMap;

/**
 * Класс хранящий информацию о привычках пользователя и работающий с ними.
 * Содержит в себе список привычек, методы для работы с ним.
 */
public class HabitsTrackerRepository {
    private final TreeMap<LocalTime, Habit> timeToHabitsMap = new TreeMap<>();

    /**
     * Метод добавляющий привычку в список привычек
     *
     * @param localTime - время, в которое пользователь хочет получать уведомления
     * @param habit     - привычка, которую пользователь хочет добавить
     */
    public void addHabit(LocalTime localTime, Habit habit) {
        timeToHabitsMap.put(localTime, habit);
    }

    /**
     * Метод удаляющий привычку из списка привычек
     *
     * @param habit - привычка, которую пользователь хочет удалить
     */
    public void removeHabitIfDurationEnd(Habit habit) {
        habit.decreaseDuration();
        if (habit.getDuration() == 0) {
            timeToHabitsMap.values().remove(habit);
        }
    }

    /**
     * Метод возвращающий время самой ранней привычки
     */
    public LocalTime getEarliestHabitTime() {
        return timeToHabitsMap.firstKey();
    }

    /**
     * Метод возвращающий привычку по времени
     *
     * @param localTime - время, по которому пользователь хочет получить привычку
     */
    public Habit getHabitByTime(LocalTime localTime) {
        return timeToHabitsMap.get(localTime);
    }
}
