package org.example;

import org.example.MessageProcessing.MessageHandler;
import org.example.Telegram.Telegram;

/**
 * Класс для запуска приложения реализующего бота для ведения списка задач и привычек
 */
public class App {
    public static void main(String[] args) {
        App.run();
    }

    private static void run() {
        MessageHandler messageHandler = new MessageHandler();
        Telegram telegram = new Telegram(messageHandler);
        telegram.onUpdateReceived();
    }
}
