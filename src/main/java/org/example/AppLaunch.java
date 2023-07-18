package org.example;

import org.example.MessageProcessing.MessageHandler;
import org.example.MessageProcessing.NotificationRepository;
import org.example.MessageProcessing.NotificationSender;
import org.example.Telegram.Telegram;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Класс для запуска приложения реализующего бота для ведения списка задач и привычек
 */
public class AppLaunch {
    public static void main(String[] args) {
        AppLaunch.run();
    }

    private static void run() {
        NotificationRepository notificationRepository = new NotificationRepository();
        Telegram telegram = new Telegram(notificationRepository);
        NotificationSender notificationSender = new NotificationSender(telegram, notificationRepository);
        try {
            Thread threadNotification = new Thread(notificationSender);
            threadNotification.start();

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegram);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}