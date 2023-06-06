package org.example.Telegram;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface MessageSender {
    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId
     * @param message
     */
    void sendMessage(Long chatId, String message) throws TelegramApiException;
}