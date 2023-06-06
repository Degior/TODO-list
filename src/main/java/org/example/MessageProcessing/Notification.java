package org.example.MessageProcessing;

import java.time.LocalDateTime;

public class Notification {
    private String message;
    private Long chatId;
    private LocalDateTime time;

    public Notification(String message, Long chatId, LocalDateTime time) {
        this.message = message;
        this.chatId = chatId;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public Long getChatId() {
        return chatId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
