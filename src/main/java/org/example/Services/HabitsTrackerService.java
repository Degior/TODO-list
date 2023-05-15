package org.example.Services;

import org.example.Entity.Habit;
import org.example.MessageSender;
import org.example.Repository.HabitsTrackerRepository;

import java.time.LocalTime;

/**
 * Класс реализующий функционал трекера привычек
 */
public class HabitsTrackerService implements Runnable {
    private final MessageSender messageSender;
    private final HabitsTrackerRepository habitsTrackerRepository;

    public HabitsTrackerService(MessageSender messageSender, HabitsTrackerRepository habitsTrackerRepository) {
        this.messageSender = messageSender;
        this.habitsTrackerRepository = habitsTrackerRepository;
    }

    private void trySendNotification() {
        LocalTime earliestHabitTime = habitsTrackerRepository.getEarliestHabitTime();
        if (LocalTime.now().isAfter(earliestHabitTime)) {
            Habit habit = habitsTrackerRepository.getHabitByTime(earliestHabitTime);
            messageSender.sendMessage(habit.getChatId(), habit.printValues());
            habitsTrackerRepository.removeHabitIfDurationEnd(habit);
        }
    }

    @Override
    public void run() {
        while (true) {
            trySendNotification();
            try {
                int sleepTime = 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
