package org.example.Telegram;

import org.example.MessageProcessing.MessageHandler;

import java.util.Scanner;

/**
 * Класс симулирующий работу Telegram
 */
public class ConsoleBot implements MessageSender {
    private final MessageHandler messageHandler;

    /**
     * Конструктор класса
     */
    public ConsoleBot(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Метод, который принимает и отправляет сообщения
     */
    public void onUpdateReceived() {
        Scanner scanner = new Scanner(System.in);
        long chatId = 0;
        while (true) {
            System.out.print("User: ");
            String input = scanner.nextLine();
            String response = messageHandler.processInput(chatId, input);
            sendMessage(chatId, response);
        }
    }


    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId id пользователя
     * @param message сообщение, отправляемое пользователю
     */
    @Override
    public void sendMessage(Long chatId, String message) {
        System.out.println("Telegram: " + message);
    }
}