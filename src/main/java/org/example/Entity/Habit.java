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
    private int dayDuration;

    public Habit(Long chatId, String name, String description, int dayDuration) {
        this.chatId = chatId;
        this.name = name;
        this.description = description;
        this.dayDuration = dayDuration;
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

    public int getDayDuration() {
        return dayDuration;
    }

    public void setDayDuration(int dayDuration) {
        this.dayDuration = dayDuration;
    }

    /**
     * Метод уменьшающий продолжительность привычки на 1
     */
    public void decreaseDuration() {
        this.dayDuration--;
    }

    /**
     * Метод возвращающий информацию о привычке
     */
    public String printValues() {
        return "Нужно сделать\nНазвание = " + name + "\nОписание = " + description;
    }
}
