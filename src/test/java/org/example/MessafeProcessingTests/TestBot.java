package org.example.MessafeProcessingTests;

import org.example.MessageProcessing.NotificationRepository;
import org.example.Telegram.MessageSender;
import org.example.Telegram.Telegram;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.ArrayList;
import java.util.List;

public class TestBot implements MessageSender {

    private List<String> messages = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    @Override
    public void sendButtonMessage(Long chatId, String message, ReplyKeyboard replyKeyboard) {

    }

    public List<String> getMessages() {
        return messages;
    }
}
