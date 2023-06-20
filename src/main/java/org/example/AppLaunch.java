package org.example;

import org.example.MessageProcessing.MessageHandler;
import org.example.NoteStrusture.NoteStorage;
import org.example.Telegram.ConsoleBot;

/**
 * Класс для запуска приложения реализующего бота для ведения списка задач и привычек
 */
public class AppLaunch {
    public static void main(String[] args) {
        AppLaunch.run();
    }

    private static void run() {
        NoteStorage noteStorage = new NoteStorage();
        MessageHandler messageHandler = new MessageHandler(noteStorage);
        ConsoleBot consoleBot = new ConsoleBot(messageHandler);
        consoleBot.onUpdateReceived();
    }
}
