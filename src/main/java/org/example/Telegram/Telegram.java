package org.example.Telegram;

import org.example.MessageProcessing.MessageHandler;

import java.util.Scanner;

/**
 * Класс отвечающей за работу Telegram. Пока не реализован.
 */
public class Telegram implements MessageSender {
    private final MessageHandler messageHandler;

    /**
     * Конструктор класса Telegram
     */
    public Telegram(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Метод, который принимает и отправляет сообщения
     */
    public void onUpdateReceived() {
        Scanner scanner = new Scanner(System.in);
        long chatId = 0;
        while (true) {
            System.out.print("Введите сообщение: ");
            String input = scanner.nextLine();
            String response = messageHandler.processInput(chatId, input);
            sendMessage(chatId, response);
        }
    }


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