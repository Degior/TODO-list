package org.example.Telegram;

import org.example.Logic;

import java.util.Scanner;

/**
 * Класс отвечающей за работу Telegram. Пока не реализован.
 */
public class Telegram implements MessageSender {
    private final Logic logic;

    /**
     * Конструктор класса Telegram
     *
     * @param logic
     */
    public Telegram(Logic logic) {
        this.logic = logic;
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
            String response = logic.processInput(chatId, input);
            System.out.println(response);
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