package org.example.Entity;

/**
 * Класс привычки
 * Содержит в себе название привычки, описание, её продолжительность
 */
public class Habit {
    private Long Id;
    private Long chatId;
    private String name;
    private String description;
    private int duration;

    public Habit(Long chatId, String name, String description, int duration) {
        this.chatId = chatId;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void decreaseDuration() {
        this.duration--;
    }

    public String printValues() {
        return "\nname = " + name + "\ndescription = " + description;
    }
}
