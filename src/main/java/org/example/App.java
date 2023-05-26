package org.example;

import org.example.Repository.HabitsTrackerRepository;
import org.example.Telegram.Telegram;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        App.run();
    }

    private static void run() {
        HabitsTrackerRepository habitsTrackerRepository = new HabitsTrackerRepository();
        Logic logic = new Logic(habitsTrackerRepository);
        Telegram telegram = new Telegram(logic);
        telegram.onUpdateReceived();
    }
}
