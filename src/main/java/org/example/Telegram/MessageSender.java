package org.example.Telegram;

public interface MessageSender {

    /**
     * Метод, который принимает и отправляет сообщения
     */
    void onUpdateReceived();

    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId  id чата
     * @param message содержание сообщения
     */
    void sendMessage(Long chatId, String message);
}