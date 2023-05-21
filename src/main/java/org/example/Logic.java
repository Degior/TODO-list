package org.example;

import org.example.Entity.Habit;
import org.example.Repository.HabitsTrackerRepository;

/**
 * Класс Logic. Отвечает за логику работы программы.
 */
public class Logic {
    private final HabitsTrackerRepository habitsTrackerRepository;

    public Logic(HabitsTrackerRepository habitsTrackerRepository) {
        this.habitsTrackerRepository = habitsTrackerRepository;
    }

    private final String[] commands = {"/start", "/help", "/addHabit", "/removeHabit", "/showHabit", "/editHabit"};

    private String nameHabitThatUserWantsToEdit = null;
    private String lastMessage = null;

    /**
     * Замена сообщений на специальные команды
     *
     * @param textMsg сообщение
     * @return специальная команда
     */

    private String handle(String textMsg) {
        return switch (textMsg) {
            case "Начать" -> "/start";
            case "Помощь" -> "/help";
            case "Просмотреть привычки" -> "/showHabit";
            case "Добавить привычку" -> "/addHabit";
            case "Убрать привычку" -> "/removeHabit";
            case "Редактировать привычку" -> "/editHabit";
            case "Отметить выполнение" -> "/markHabit";
            default -> textMsg;
        };
    }

    /**
     * Метод для обработки ввода пользователя.
     * Если введена специальная команда, то вызывается метод getSpecialCommand.
     * Если введена не специальная команда, то вызывается метод getNonSpecialCommand.
     */
    public String processInput(Long chatId, String textMsg) {
        textMsg = handle(textMsg);
        if (textMsg.startsWith("/")) {
            return getSpecialCommand(chatId, textMsg);
        }
        return getNonSpecialCommand(chatId, textMsg);
    }

    /**
     * Метод для обработки специальных команд.
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private String getSpecialCommand(Long chatId, String textMsg) {
        switch (textMsg) {
            case "/start":
                return Report.START_MESSAGE;
            case "/help":
                return Report.HELP_MESSAGE;
            case "/addHabit":
                lastMessage = Report.HABIT_ADD;
                return Report.HABIT_ADD;
            case "/removeHabit":
                lastMessage = Report.HABIT_REMOVE;
                return Report.HABIT_REMOVE;
            case "/showHabit":
                return Report.HABIT_SHOW;
            case "/editHabit":
                lastMessage = Report.HABIT_EDIT;
                return Report.HABIT_EDIT;
            case "/markHabit":
                lastMessage = Report.HABIT_MARK;
                return Report.HABIT_MARK;
            default:
                return Report.DEFAULT_MESSAGE;
        }
    }

    private String getNonSpecialCommand(Long chatId, String textMsg) {
        return switch (lastMessage) {
            case Report.HABIT_ADD -> addHabit(chatId, textMsg);
            case Report.HABIT_REMOVE -> removeHabit(chatId, textMsg);
            case Report.HABIT_EDIT -> getEditHabit(chatId, textMsg);
            case Report.HABIT_EDIT_GETTER -> editHabit(chatId, textMsg);
            case Report.HABIT_MARK -> markHabit(chatId, textMsg);
            default -> Report.DEFAULT_MESSAGE;
        };
    }


    /**
     * Метод для добавления привычки.
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private String addHabit(Long chatId, String textMsg) {
        String[] habitContains = textMsg.split("\\r?\\n");
        if (habitContains.length == 3) {
            String habitName = habitContains[0];
            String habitDescription = habitContains[1];
            String habitDayDurationString = habitContains[2];
            int habitDayDurationInt;
            try {
                habitDayDurationInt = Integer.parseInt(habitDayDurationString);
            } catch (NumberFormatException e) {
                return Report.HABIT_ADD_FAIL;
            }
            Habit habit = new Habit(habitName, habitDescription, habitDayDurationInt);
            if (habitsTrackerRepository.addHabit(chatId, habit)) {
                return Report.HABIT_ADD_SUCCESS;
            }
        }
        return Report.HABIT_ADD_FAIL;
    }

    /**
     * Метод для удаления привычки.
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private String removeHabit(Long chatId, String textMsg) {
        if (habitsTrackerRepository.removeHabit(chatId, textMsg)) {
            return Report.HABIT_REMOVE_SUCCESS;
        }
        return Report.HABIT_REMOVE_FAIL;
    }

    /**
     * Метод для начала редактирования привычки.
     *
     * @param chatId    идентификатор чата
     * @param habitName название привычки
     */
    private String getEditHabit(Long chatId, String habitName) {
        if (habitsTrackerRepository.checkHabit(chatId, habitName)) {
            lastMessage = Report.HABIT_EDIT_GETTER;
            nameHabitThatUserWantsToEdit = habitName;
            return Report.HABIT_EDIT_GETTER;
        }
        return Report.HABIT_EDIT_FAIL1;
    }

    /**
     * Метод для редактирования привычки.
     *
     * @param chatId  идентификатор чата
     * @param message сообщение
     */
    private String editHabit(Long chatId, String message) {
        String[] lines = message.split("\n");
        String name = null;
        String description = null;
        int duration = -1;

        for (String line : lines) {
            if (line.startsWith("Название:")) {
                name = extractValue(line);
            } else if (line.startsWith("Описание:")) {
                description = extractValue(line);
            } else if (line.startsWith("Продолжительность:")) {
                duration = extractDurationValue(line);
            }
        }
        if (name == null && description == null && duration < 0) {
            return Report.HABIT_EDIT_FAIL2;
        }
        habitsTrackerRepository.editHabit(chatId, nameHabitThatUserWantsToEdit, name, description, duration);
        return Report.HABIT_EDIT_SUCCESS;
    }

    private String extractValue(String line) {
        return line.substring(line.indexOf(":") + 1).trim();
    }

    private int extractDurationValue(String line) {
        try {
            return Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Метод для отметки выполнения привычки.
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private String markHabit(Long chatId, String textMsg) {
        if (habitsTrackerRepository.markHabit(chatId, textMsg)) {
            return Report.HABIT_MARK_SUCCESS;
        }
        return Report.HABIT_MARK_FAIL;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
