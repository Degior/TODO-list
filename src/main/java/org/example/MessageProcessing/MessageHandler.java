package org.example.MessageProcessing;

import org.example.Entity.Habit;
import org.example.NoteStrusture.NoteException;
import org.example.Report;
import org.example.Repository.HabitsTrackerRepository;

/**
 * Класс MessageHandler. Отвечает за логику работы программы.
 */
public class MessageHandler {
    private final HabitsTrackerRepository habitsTrackerRepository;
    private NotesLogic notesLogic = new NotesLogic();

    private MessageHandlerState messageHandlerState = MessageHandlerState.DEFAULT;

    public MessageHandler(HabitsTrackerRepository habitsTrackerRepository) {
        this.habitsTrackerRepository = habitsTrackerRepository;
    }

    private String nameHabitThatUserWantsToEdit = null;

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
            case "Список заметок" -> "/getNotesList";
            case "Добавить заметку" -> "/createNote";
            case "Открыть заметку" -> "/openNote";
            case "Удалить заметку" -> "/deleteNote";
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
            case "/getNotesList":
                messageHandlerState = MessageHandlerState.DEFAULT;
                notesLogic.changeLogic();
                return notesLogic.getAllNotes(chatId);
            case "/createNote":
                messageHandlerState = MessageHandlerState.CREATING_NOTE_DATE;
                return Report.NOTE_CREATION;
            case "/openNote":
                messageHandlerState = MessageHandlerState.SEARCHING_NOTE;
                return Report.NOTE_SEARCH;
            case "/deleteNote":
                messageHandlerState = MessageHandlerState.DELETING_NOTE;
                return Report.DELETE_NOTE;
            case "/addHabit":
                messageHandlerState = MessageHandlerState.HABIT_ADDING;
                return Report.HABIT_ADD;
            case "/removeHabit":
                messageHandlerState = MessageHandlerState.HABIT_REMOVING;
                return Report.HABIT_REMOVE;
            case "/showHabit":
                return Report.HABIT_SHOW;
            case "/editHabit":
                messageHandlerState = MessageHandlerState.HABIT_EDITING;
                return Report.HABIT_EDIT;
            case "/markHabit":
                messageHandlerState = MessageHandlerState.HABIT_MARKING;
                return Report.HABIT_MARK;
            default:
                return Report.DEFAULT_MESSAGE;
        }
    }

    private String getNonSpecialCommand(Long chatId, String textMsg) {
        return switch (messageHandlerState) {
            case CREATING_NOTE_DATE -> appendNote(chatId, textMsg);
            case PROCESSING_NOTE -> toProcessExistingNote(textMsg);
            case SEARCHING_NOTE -> toLookForNote(chatId, textMsg);
            case DELETING_NOTE -> toDeleteNote(chatId, textMsg);
            case HABIT_ADDING -> addHabit(chatId, textMsg);
            case HABIT_REMOVING -> removeHabit(chatId, textMsg);
            case HABIT_EDITING -> getEditHabit(chatId, textMsg);
            case HABIT_EDITING_GETTER -> editHabit(chatId, textMsg);
            case HABIT_MARKING -> markHabit(chatId, textMsg);
            default -> Report.DEFAULT_MESSAGE;
        };
    }

    /**
     * Метод для добавления заметки.
     *
     * @param chatId  идентификатор чата
     * @param message дата заметки
     * @return сообщение о результате работы метода
     */
    private String appendNote(Long chatId, String message) {
        try {
            notesLogic.addNote(chatId, Filter.toFilterOutData(message));
            messageHandlerState = MessageHandlerState.PROCESSING_NOTE;
        } catch (NoteException e) {
            return Report.NOTE_ALREADY_EXIST;
        } catch (FilterException e) {
            return Report.DEFAULT_MESSAGE;
        }
        return Report.NOTE_MODIFICATION;
    }

    /**
     * Метод для добавления задач в заметку.
     *
     * @param message текст новой задачи для заметки
     *                (в заметке несколько задач)
     * @return сообщение о результате работы метода
     */
    private String toProcessExistingNote(String message) {
        notesLogic.addTextToNote(message);
        return Report.TASK_ADDED;
    }

    /**
     * Метод для поиска заметки по дате
     *
     * @param message дата заметки
     * @return сообщение о результате работы метода
     */
    private String toLookForNote(Long chatId, String message) {
        try {
            messageHandlerState = MessageHandlerState.DEFAULT;
            return notesLogic.getNote(chatId, Filter.toFilterOutData(message));
        } catch (NoteException e) {
            messageHandlerState = MessageHandlerState.SEARCHING_NOTE;
            return Report.NO_SUCH_NOTE;
        } catch (FilterException e) {
            messageHandlerState = MessageHandlerState.SEARCHING_NOTE;
            return Report.DEFAULT_MESSAGE;
        }
    }

    /**
     * Метод для удаления заметки по дате
     *
     * @param chatId  идентификатор чата
     * @param message дата заметки
     * @return сообщение о результате работы метода
     */
    private String toDeleteNote(Long chatId, String message) {

        try {
            messageHandlerState = MessageHandlerState.DEFAULT;
            if (notesLogic.deleteNote(chatId, Filter.toFilterOutData(message))) {
                return Report.NOTE_DELETED;
            } else {
                return Report.NO_SUCH_NOTE;
            }
        } catch (FilterException e) {
            messageHandlerState = MessageHandlerState.DELETING_NOTE;
            return Report.DEFAULT_MESSAGE;
        }
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
            messageHandlerState = MessageHandlerState.HABIT_EDITING_GETTER;
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
}
