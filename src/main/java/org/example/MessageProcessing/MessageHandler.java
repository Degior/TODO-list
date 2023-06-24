package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;
import org.example.NoteStrusture.NoteStorage;
import org.example.statistics.Statistics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс отвечает за обработку сообщений пользователя
 */
public class MessageHandler {
    Map<Long, MessageHandlerState> messageHandlerState = new HashMap<>();
    private NoteStorage noteStorage;

    public MessageHandler(NoteStorage noteStorage) {
        this.noteStorage = noteStorage;
    }


    /**
     * Замена сообщений на специальные команды
     *
     * @param textMsg сообщение
     * @return специальная команда
     */
    private String getCommand(String textMsg) {
        return switch (textMsg) {
            case "/Начать" -> "/start";
            case "/Помощь" -> "/help";
            case "/Список заметок" -> "/getNotesList";
            case "/Добавить заметку" -> "/createNote";
            case "/Открыть заметку" -> "/openNote";
            case "/Удалить заметку" -> "/deleteNote";
            case "/Редактировать заметку" -> "/editNote";
            case "/Посмотреть статистику" -> "/getStatistics";
            case "/Отмена" -> "/cancel";
            default -> textMsg;
        };
    }

    /**
     * Метод для обработки ввода пользователя.
     * Если введена специальная команда, то вызывается метод getSpecialCommand.
     * Если введена не специальная команда, то вызывается метод getNonSpecialCommand.
     */
    public String processInput(Long chatId, String textMsg) {
        messageHandlerState.putIfAbsent(chatId, MessageHandlerState.DEFAULT);
        textMsg = getCommand(textMsg);
        if (textMsg.startsWith("/")) {
            return performSpecialCommand(chatId, textMsg);
        }
        return performNonSpecialCommand(chatId, textMsg);
    }

    /**
     * Обработка специальных команд.
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private String performSpecialCommand(Long chatId, String textMsg) {
        switch (textMsg) {
            case "/start":
                return org.example.MessageProcessing.Messages.START_MESSAGE;
            case "/help":
                return org.example.MessageProcessing.Messages.HELP_MESSAGE;
            case "/getNotesList":
                messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
                noteStorage.resetNote(chatId);
                try {
                    return org.example.MessageProcessing.NoteFormatter.getAllNotes(noteStorage.getAllNotes(chatId));
                } catch (FormatterException e) {
                    return e.getMessage();
                }
            case "/createNote":
                messageHandlerState.put(chatId, MessageHandlerState.CREATING_NOTE_DATE);
                return org.example.MessageProcessing.Messages.NOTE_CREATION;
            case "/openNote":
                messageHandlerState.put(chatId, MessageHandlerState.SEARCHING_NOTE);
                return org.example.MessageProcessing.Messages.NOTE_SEARCH;
            case "/deleteNote":
                messageHandlerState.put(chatId, MessageHandlerState.DELETING_NOTE);
                return org.example.MessageProcessing.Messages.DELETE_NOTE;
            case "/editNote":
                messageHandlerState.put(chatId, MessageHandlerState.EDITING_NOTE);
                return org.example.MessageProcessing.Messages.EDIT_NOTE;
            case "/getStatistics":
                messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
                return getStatistics(chatId);
            case "/cancel":
                messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
                return org.example.MessageProcessing.Messages.CANCEL;
            default:
                return org.example.MessageProcessing.Messages.DEFAULT_MESSAGE;
        }
    }

    /**
     * Обработка специфичных команд пользователя
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение о виде редактирования (удаление, добавление, выполнение задачи)
     * @return сообщение о результате работы метода
     */
    private String performNonSpecialCommand(Long chatId, String textMsg) {
        return switch (messageHandlerState.get(chatId)) {
            case CREATING_NOTE_DATE -> addNote(chatId, textMsg);
            case PROCESSING_NOTE -> toProcessExistingNote(chatId, textMsg);
            case SEARCHING_NOTE -> searchNote(chatId, textMsg);
            case DELETING_NOTE -> deleteNote(chatId, textMsg);
            case EDITING_NOTE -> toEditNoteState(chatId, textMsg);
            case PROCESSING_EDITING_NOTE -> getOption(chatId, textMsg);
            default -> org.example.MessageProcessing.Messages.DEFAULT_MESSAGE;
        };
    }


    private String getStatistics(Long chatId) {
        List<String> statisticsList = new ArrayList<>();
        for (LocalDate localDate: noteStorage.getAllNotes(chatId)){
            Note note = noteStorage.getNote(chatId, localDate);
            statisticsList.add(NoteFormatter.formatNoteStatistics(localDate, Statistics.getNoteStatistics(note)));
        }
        return NoteFormatter.formatIdStatistics(statisticsList);
    }


    /**
     * Выбор опции при редактировании заметки
     *
     * @param textMsg сообщение о виде редактирования (удаление, добавление, выполнение задачи)
     * @return сообщение о результате работы метода
     */
    public String getOption(Long chatId, String textMsg){
        try {
            if (textMsg.startsWith("Добавить")) {
                return addTask(chatId, textMsg);
            } else if (textMsg.startsWith("Удалить")) {
                return deleteTask(chatId, textMsg);
            } else if (textMsg.startsWith("Отметить выполненным")) {
                return completeTask(chatId, textMsg);
            } else {
                return org.example.MessageProcessing.Messages.WRONG_COMMAND;
            }
        } catch (Exception e) {
            return org.example.MessageProcessing.Messages.WRONG_COMMAND;
        }
    }

    /**
     * Удаление задачи по номеру из заметки
     *
     * @param textMsg номер задачи
     * @return сообщение о результате работы метода
     */
    private String deleteTask(Long chatId, String textMsg) {
        if (noteStorage.deleteTextFromNote(chatId, Integer.parseInt(textMsg.substring(8)))) {
            return org.example.MessageProcessing.Messages.DELETED_TASK;
        }
        return org.example.MessageProcessing.Messages.WRONG_TASK_INDEX;
    }

    /**
     * Отметка задачи как выполненной
     *
     * @param textMsg номер задачи
     * @return сообщение о результате работы метода
     */
    private String completeTask(Long chatId, String textMsg) {
        noteStorage.markTaskAsCompleted(chatId, Integer.parseInt(textMsg.substring(21)));
        messageHandlerState.put(chatId, MessageHandlerState.PROCESSING_NOTE);
        return org.example.MessageProcessing.Messages.NOTE_EDITED;
    }

    /**
     * Добавление задачи в заметку
     *
     * @param textMsg текст задачи
     * @return сообщение о результате работы метода
     */
    private String addTask(Long chatId, String textMsg) {
        noteStorage.addTaskToNote(chatId, textMsg.substring(9));
        return org.example.MessageProcessing.Messages.TASK_ADDED;
    }



    /**
     * Вывод содержания текста заметки и начало работы с ней
     *
     * @param chatId  идентификатор чата
     * @param textMsg дата заметки
     * @return сообщение о результате работы метода
     */
    private String toEditNoteState(Long chatId, String textMsg) {
        try {
            messageHandlerState.put(chatId, MessageHandlerState.PROCESSING_EDITING_NOTE);
            Note note = noteStorage.getNote(chatId, Parser.parseData(textMsg));
            return NoteFormatter.getNoteText(note) + org.example.MessageProcessing.Messages.NOTE_EDITING;
        } catch (Exception e) {
            messageHandlerState.put(chatId, MessageHandlerState.EDITING_NOTE);
            return e.getMessage();
        }
    }

    /**
     * Добавление заметки по дате
     *
     * @param chatId  идентификатор чата
     * @param message дата заметки
     * @return сообщение о результате работы метода
     */
    private String addNote(Long chatId, String message) {
        try {
            if (noteStorage.addNote(chatId, Parser.parseData(message))) {
                messageHandlerState.put(chatId, MessageHandlerState.PROCESSING_NOTE);
                return org.example.MessageProcessing.Messages.NOTE_MODIFICATION;
            } else {
                messageHandlerState.put(chatId, MessageHandlerState.CREATING_NOTE_DATE);
                return org.example.MessageProcessing.Messages.NOTE_ALREADY_EXIST;
            }
        } catch (ParserException e) {
            return e.getMessage();
        }

    }

    /**
     * Добавление задач в заметку.
     *
     * @param message текст новой задачи для заметки
     *                (в заметке несколько задач)
     * @return сообщение о результате работы метода
     */
    private String toProcessExistingNote(Long chatId, String message) {
        noteStorage.addTaskToNote(chatId, message);
        return org.example.MessageProcessing.Messages.TASK_ADDED;
    }

    /**
     * Поиск заметки по дате
     *
     * @param message дата заметки
     * @return сообщение о результате работы метода
     */
    private String searchNote(Long chatId, String message) {
        try {
            messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
            Note note = noteStorage.getNote(chatId, Parser.parseData(message));
            return NoteFormatter.getNoteText(note);
        } catch (Exception e) {
            messageHandlerState.put(chatId, MessageHandlerState.SEARCHING_NOTE);
            return e.getMessage();
        }
    }

    /**
     * Удаление заметки по дате
     *
     * @param chatId  идентификатор чата
     * @param message дата заметки
     * @return сообщение о результате работы метода
     */
    private String deleteNote(Long chatId, String message) {

        try {
            messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
            LocalDate localDate = Parser.parseData(message);
            if (noteStorage.isPossibleToDeleteNote(chatId, localDate)) {
                noteStorage.deleteNote(chatId, localDate);
                return org.example.MessageProcessing.Messages.NOTE_DELETED;
            } else {
                return org.example.MessageProcessing.Messages.NO_SUCH_NOTE;
            }
        } catch (ParserException e) {
            messageHandlerState.put(chatId, MessageHandlerState.DELETING_NOTE);
            return e.getMessage();
        }
    }
}