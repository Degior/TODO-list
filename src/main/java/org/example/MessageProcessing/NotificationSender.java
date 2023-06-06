package org.example.MessageProcessing;

import org.example.Telegram.MessageSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

public class NotificationSender implements Runnable {
    MessageSender messageSender;
    NotificationRepository notificationRepository;

    public NotificationSender(MessageSender messageSender, NotificationRepository notificationRepository) {
        this.messageSender = messageSender;
        this.notificationRepository = notificationRepository;
    }

    private void trySendNotification() throws TelegramApiException {
        for (Notification notification : notificationRepository.getNotifications()) {
            if (notification.getTime().isBefore(LocalDateTime.now())) {
                messageSender.sendMessage(notification.getChatId(), notification.getMessage());
                notificationRepository.removeNotification(notification);
            }
        }
    }


    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            try {
                trySendNotification();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            try {
                int sleepTime = 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
