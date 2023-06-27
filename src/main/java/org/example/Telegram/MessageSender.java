package org.example.Telegram;

import org.example.MessageProcessing.Buttons;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface MessageSender {

    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId  id чата
     * @param message содержание сообщения
     */
    void sendMessage(Long chatId, String message);

    /**
     * Метод отправляющий сообщение c кнопками пользователю
     *
     * @param chatId  id чата
     * @param message содержание сообщения
     * @param replyKeyboard кнопки
     */
    void sendButtonMessage(Long chatId, String message, ReplyKeyboard replyKeyboard);
}