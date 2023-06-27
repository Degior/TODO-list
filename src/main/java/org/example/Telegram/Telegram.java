package org.example.Telegram;

import org.example.MessageProcessing.Buttons;
import org.example.MessageProcessing.MessageHandler;
import org.example.MessageProcessing.NotificationRepository;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс отвечающей за работу Telegram. Пока не реализован.
 */
public class Telegram extends TelegramLongPollingBot implements MessageSender {

    private NotificationRepository notificationRepository;
    private MessageHandler messageHandler;


    //private final Reader reader = new Reader();

    /**
     * Конструктор класса Telegram
     */
    public Telegram(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        this.messageHandler = new MessageHandler( this, notificationRepository);
    }

    /**
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        //return reader.readFile("src/main/resources/name.txt");
        return "microboticbot";
    }

    /**
     * @return токен бота
     */
    @Override
    public String getBotToken() {
        //return reader.readFile("src/main/resources/token.txt");
        return "6201091772:AAH2DD1nlTXpPJhw7bEfkAXrXeHN4J16cWE";
    }


    /**
     * Метод, который принимает и отправляет сообщения
     *
     * @param update сообщение
     */
    public void onUpdateReceived(Update update) {
        /*try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage();
                Long chatId = message.getChatId();
                String response = messageHandler.processInput(chatId, message.getText());
                SendMessage outMess = new SendMessage();

                outMess.setChatId(chatId.toString());
                outMess.setText(response);

                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            messageHandler.processInput(chatId, message.getText());
        }
        else if (update.hasCallbackQuery()){
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            messageHandler.processInput(chatId, update.getCallbackQuery().getData());
        }
    }

    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId  id чата
     * @param message сообщение
     */
    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendButtonMessage(Long chatId, String message, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboard);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}