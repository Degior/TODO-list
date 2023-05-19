package org.example.Repository;

import org.example.Entity.Habit;
import org.example.Report;

import java.util.HashMap;
import java.util.Map;

public class HabitsTrackerRepository {
    private Map<Long, HashMap<Habit, Boolean>> chatIdToHabitsMap = new HashMap<>();


    /**
     * Метод для добавления привычки в список привычек пользователя.
     *
     * @param chatId идентификатор чата
     * @param habit  привычка
     * @return true, если привычка добавлена, false, если привычка уже существует
     */
    public boolean addHabit(Long chatId, Habit habit) {
        if (chatIdToHabitsMap.containsKey(chatId)) {
            HashMap<Habit, Boolean> habitBooleanHashMap = chatIdToHabitsMap.get(chatId);
            if (habitBooleanHashMap.containsKey(habit)) {
                return false;
            }
            habitBooleanHashMap.put(habit, false);
            return true;
        }
        HashMap<Habit, Boolean> habitBooleanHashMap = new HashMap<>();
        habitBooleanHashMap.put(habit, false);
        chatIdToHabitsMap.put(chatId, habitBooleanHashMap);
        return true;
    }

    /**
     * Метод для удаления привычки из списка привычек пользователя.
     *
     * @param chatId
     * @param habitName
     * @return true, если привычка удалена, false, если привычки не существует
     */
    public boolean removeHabit(Long chatId, String habitName) {
        if (chatIdToHabitsMap.containsKey(chatId)) {
            HashMap<Habit, Boolean> habitBooleanHashMap = chatIdToHabitsMap.get(chatId);
            for (Habit habit : habitBooleanHashMap.keySet()) {
                if (habit.getName().equals(habitName)) {
                    habitBooleanHashMap.remove(habit);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для получения списка привычек пользователя.
     *
     * @param chatId идентификатор чата
     * @return список привычек пользователя
     */
    public String getHabits(Long chatId) {
        if (chatIdToHabitsMap.containsKey(chatId)) {
            HashMap<Habit, Boolean> habitBooleanHashMap = chatIdToHabitsMap.get(chatId);
            StringBuilder stringBuilder = new StringBuilder();
            for (Habit habit : habitBooleanHashMap.keySet()) {
                stringBuilder.append(habit.getName()).append("\n");
            }
            return stringBuilder.toString();
        }
        return Report.NO_HABITS;
    }

    /**
     * Метод для провекри налиячия у пользователя такой привычки
     *
     * @param chatId идентификатор чата
     * @return список привычек пользователя
     */
    public boolean checkHabit(Long chatId, String habitName) {
        if (chatIdToHabitsMap.containsKey(chatId)) {
            HashMap<Habit, Boolean> habitBooleanHashMap = chatIdToHabitsMap.get(chatId);
            for (Habit habit : habitBooleanHashMap.keySet()) {
                if (habit.getName().equals(habitName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для редактирования привычки
     *
     * @param chatId                       идентификатор чата
     * @param nameHabitThatUserWantsToEdit название привычки, которую пользователь хочет отредактировать
     * @param name                         название привычки
     * @param description                  описание привычки
     * @param duration                     продолжительность привычки
     * @return список привычек пользователя
     */
    public void editHabit(Long chatId, String nameHabitThatUserWantsToEdit, String name, String description, int duration) {
        if (chatIdToHabitsMap.containsKey(chatId)) {
            HashMap<Habit, Boolean> habitBooleanHashMap = chatIdToHabitsMap.get(chatId);
            for (Habit habit : habitBooleanHashMap.keySet()) {
                if (habit.getName().equals(nameHabitThatUserWantsToEdit)) {
                    if (name != null) {
                        habit.setName(name);
                    }
                    if (description != null) {
                        habit.setDescription(description);
                    }
                    if (duration != -1) {
                        habit.setDayDuration(duration);
                    }
                }
            }
        }
    }

    /**
     * Метод для отметки привычки
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение пользователя
     * @return true, если привычка отмечена, false, если привычки не существует
     */
    public boolean markHabit(Long chatId, String textMsg) {
        if (chatIdToHabitsMap.containsKey(chatId)) {
            HashMap<Habit, Boolean> habitBooleanHashMap = chatIdToHabitsMap.get(chatId);
            for (Habit habit : habitBooleanHashMap.keySet()) {
                if (habit.getName().equals(textMsg)) {
                    habitBooleanHashMap.put(habit, true);
                    return true;
                }
            }
        }
        return false;
    }
}
