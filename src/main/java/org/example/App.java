package org.example;

import org.example.Repository.HabitsTrackerRepository;
import org.example.Services.HabitsTrackerService;
import org.example.Telegram.Telegram;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        HabitsTrackerRepository habitsTrackerRepository = new HabitsTrackerRepository();
        MessageSender messageSender = new Telegram();
        HabitsTrackerService habitsTrackerService = new HabitsTrackerService(messageSender, habitsTrackerRepository);
        try {
            Thread threadTracker = new Thread(habitsTrackerService);
            threadTracker.start();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
