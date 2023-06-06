package org.example;

import org.example.MessageProcessing.MessageHandler;
import org.example.Telegram.Telegram;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

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
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegram);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
