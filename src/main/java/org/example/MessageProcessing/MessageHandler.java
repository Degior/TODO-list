package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;
import org.example.NoteStrusture.NoteStorage;
import org.example.NoteStrusture.TaskStatus;
import org.example.Telegram.MessageSender;
import org.example.statistics.Statistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс отвечает за обработку сообщений пользователя
 */
public class MessageHandler {

    MessageSender messageSender;
    Map<Long, MessageHandlerState> messageHandlerState = new HashMap<>();
    private final NoteStorage noteStorage = new NoteStorage();
    private final NotificationRepository notificationRepository;

    TaskStatus taskStatus = new TaskStatus();

    public MessageHandler(MessageSender messageSender, NotificationRepository notificationRepository) {
        this.messageSender = messageSender;
        this.notificationRepository = notificationRepository;
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
    public void processInput(Long chatId, String textMsg) {
        messageHandlerState.putIfAbsent(chatId, MessageHandlerState.DEFAULT);
        taskStatus.initUser(chatId);
        textMsg = getCommand(textMsg);
        if (textMsg.startsWith("/")) {
            performSpecialCommand(chatId, textMsg);
        }
        else {
            performNonSpecialCommand(chatId, textMsg);
        }
    }

    /**
     * Обработка специальных команд.
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private void performSpecialCommand(Long chatId, String textMsg) {
        switch (textMsg) {
            case "/start":
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.START_MESSAGE);
                break;
            case "/help":
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.HELP_MESSAGE);
                break;
            case "/getNotesList":
                messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
                noteStorage.resetNote(chatId);
                try {
                    messageSender.sendMessage(chatId, org.example.MessageProcessing.NoteFormatter.getAllNotes(noteStorage.getAllNotes(chatId)));
                } catch (FormatterException e) {
                    messageSender.sendMessage(chatId, e.getMessage());
                }
                break;
            case "/createNote":
                messageHandlerState.put(chatId, MessageHandlerState.CREATING_NOTE_DATE);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.NOTE_CREATION);
                break;
            case "/openNote":
                messageHandlerState.put(chatId, MessageHandlerState.SEARCHING_NOTE);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.NOTE_SEARCH);
                break;
            case "/deleteNote":
                messageHandlerState.put(chatId, MessageHandlerState.DELETING_NOTE);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.DELETE_NOTE);
                break;
            case "/editNote":
                messageHandlerState.put(chatId, MessageHandlerState.EDITING_NOTE);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.EDIT_NOTE);
                break;
            case "/getStatistics":
                messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
                messageSender.sendMessage(chatId, getStatistics(chatId));
                break;
            case "/addNotification":
                messageHandlerState.put(chatId, MessageHandlerState.ADDING_NOTIFICATION);
                messageSender.sendMessage(chatId, org.example.Messages.ADDING_NOTIFICATION);
                break;
            case "/cancel":
                messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.CANCEL);
                break;
            default:
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.DEFAULT_MESSAGE);
        }
    }

    /**
     * Обработка специфичных команд пользователя
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение о виде редактирования (удаление, добавление, выполнение задачи)
     */
    private void performNonSpecialCommand(Long chatId, String textMsg) {
        switch (messageHandlerState.get(chatId)) {
            case CREATING_NOTE_DATE -> addNote(chatId, textMsg);
            case PROCESSING_NOTE -> toProcessExistingNote(chatId, textMsg);
            case SEARCHING_NOTE -> searchNote(chatId, textMsg);
            case DELETING_NOTE -> deleteNote(chatId, textMsg);
            case EDITING_NOTE -> toEditNoteState(chatId, textMsg);
            case PROCESSING_EDITING_NOTE -> getOption(chatId, textMsg);
            case ADDING_NOTIFICATION -> toAddNotification(chatId, textMsg);
            case CHOOSING_TASK_STATUS -> setTaskStatus(chatId, textMsg);
           // case STATUS_CHOSEN ->
            default -> messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.DEFAULT_MESSAGE);
        };
    }

    private void setTaskStatus(Long chatId, String message){
        //messageHandlerState.put(chatId, MessageHandlerState.STATUS_CHOSEN);
        messageSender.sendMessage(chatId, "Введите следующую задачу:");
    }

    /**
     * Метод для добавления заметки
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение
     */
    private void toAddNotification(Long chatId, String textMsg) {
        String[] lines = textMsg.split("\n");
        String dateTimeLine = lines[0];
        String title = lines[1];
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeLine, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        Notification notification = new Notification(title, chatId, dateTime);
        notificationRepository.addNotification(notification, chatId);
        messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
        messageSender.sendMessage(chatId, org.example.Messages.NOTIFICATION_ADDED);
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
     */
    public void getOption(Long chatId, String textMsg){
        try {
            if (textMsg.startsWith("Добавить")) {
                messageSender.sendButtonMessage(chatId,addTask(chatId, textMsg), Buttons.getAllStatuses(taskStatus.getUserStatuses(chatId)));
            } else if (textMsg.startsWith("Удалить")) {
                messageSender.sendMessage(chatId, deleteTask(chatId, textMsg));
            } else if (textMsg.startsWith("Отметить выполненным")) {
                messageSender.sendMessage(chatId, completeTask(chatId, textMsg));
            } else {
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.WRONG_COMMAND);
            }
        } catch (Exception e) {
            messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.WRONG_COMMAND);
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
        String message = textMsg.substring(9);
        toProcessExistingNote(chatId, message);
        noteStorage.addTaskToNote(chatId, message);
        messageHandlerState.put(chatId, MessageHandlerState.CHOOSING_TASK_STATUS);
        return org.example.MessageProcessing.Messages.TASK_ADDED + ": " + message;

    }


    /**
     * Вывод содержания текста заметки и начало работы с ней
     *
     * @param chatId  идентификатор чата
     * @param textMsg дата заметки
     */
    private void toEditNoteState(Long chatId, String textMsg) {
        try {
            messageHandlerState.put(chatId, MessageHandlerState.PROCESSING_EDITING_NOTE);
            Note note = noteStorage.getNote(chatId, Parser.parseData(textMsg));
            messageSender.sendMessage(chatId, NoteFormatter.getNoteText(note) + org.example.MessageProcessing.Messages.NOTE_EDITING);
        } catch (Exception e) {
            messageHandlerState.put(chatId, MessageHandlerState.EDITING_NOTE);
            messageSender.sendMessage(chatId, e.getMessage());
        }
    }

    /**
     * Добавление заметки по дате
     *
     * @param chatId  идентификатор чата
     * @param message дата заметки
     */
    private void addNote(Long chatId, String message) {
        try {
            if (noteStorage.addNote(chatId, Parser.parseData(message))) {
                messageHandlerState.put(chatId, MessageHandlerState.PROCESSING_NOTE);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.NOTE_MODIFICATION);
            } else {
                messageHandlerState.put(chatId, MessageHandlerState.CREATING_NOTE_DATE);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.NOTE_ALREADY_EXIST);
            }
        } catch (ParserException e) {
            messageSender.sendMessage(chatId, e.getMessage());
        }

    }

    /**
     * Добавление задач в заметку.
     *
     * @param message текст новой задачи для заметки
     *                (в заметке несколько задач)
     */
    private void toProcessExistingNote(Long chatId, String message) {
        noteStorage.addTaskToNote(chatId, message);
        messageHandlerState.put(chatId, MessageHandlerState.CHOOSING_TASK_STATUS);
        messageSender.sendButtonMessage(chatId, org.example.MessageProcessing.Messages.TASK_ADDED + ": " + message,
                Buttons.getAllStatuses(taskStatus.getUserStatuses(chatId)));
    }

    /**
     * Поиск заметки по дате
     *
     * @param message дата заметки
     */
    private void searchNote(Long chatId, String message) {
        try {
            messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
            Note note = noteStorage.getNote(chatId, Parser.parseData(message));
            messageSender.sendMessage(chatId, NoteFormatter.getNoteText(note));
        } catch (Exception e) {
            messageHandlerState.put(chatId, MessageHandlerState.SEARCHING_NOTE);
            messageSender.sendMessage(chatId, e.getMessage());
        }
    }

    /**
     * Удаление заметки по дате
     *
     * @param chatId  идентификатор чата
     * @param message дата заметки
     */
    private void deleteNote(Long chatId, String message) {

        try {
            messageHandlerState.put(chatId, MessageHandlerState.DEFAULT);
            LocalDate localDate = Parser.parseData(message);
            if (noteStorage.isPossibleToDeleteNote(chatId, localDate)) {
                noteStorage.deleteNote(chatId, localDate);
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.NOTE_DELETED);
            } else {
                messageSender.sendMessage(chatId, org.example.MessageProcessing.Messages.NO_SUCH_NOTE);
            }
        } catch (ParserException e) {
            messageHandlerState.put(chatId, MessageHandlerState.DELETING_NOTE);
            messageSender.sendMessage(chatId, e.getMessage());
        }
    }

}