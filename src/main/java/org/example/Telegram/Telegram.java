package org.example.Telegram;

import org.example.MessageSender;

public class Telegram implements MessageSender {
    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId
     * @param message
     */
    @Override
    public void sendMessage(Long chatId, String message) {
        System.out.println("Telegram: " + chatId + " " + message);
    }
}
