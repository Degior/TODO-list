package org.example.MessageProcessing;

import org.example.NoteStrusture.NoteException;
import org.example.Report;

/**
 * Класс MessageHandler. Отвечает за логику работы программы.
 */
public class MessageHandler {
    private NotesLogic notesLogic = new NotesLogic();
    private MessageHandlerState messageHandlerState = MessageHandlerState.DEFAULT;

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
            case "Редактировать заметку" -> "/editNote";
            case "Отмена" -> "/cancel";
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
            case "/editNote":
                messageHandlerState = MessageHandlerState.EDITING_NOTE;
                return Report.EDIT_NOTE;
            case "/cancel":
                messageHandlerState = MessageHandlerState.DEFAULT;
                return Report.CANCEL;
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
            case EDITING_NOTE -> toEditNote(chatId, textMsg);
            case PROCESSING_EDITING_NOTE -> editNote(chatId, textMsg);
            default -> Report.DEFAULT_MESSAGE;
        };
    }

    /**
     * Метод для редактирования заметки
     *
     * @param chatId  идентификатор чата
     * @param textMsg сообщение о виде редактирования (удаление, добавление, выполнение задачи)
     * @return сообщение о результате работы метода
     */
    private String editNote(Long chatId, String textMsg) {
        try {
            if (textMsg.startsWith("Добавить")) {
                notesLogic.addTextToNote(textMsg.substring(9));
                return Report.TASK_ADDED;
            } else if (textMsg.startsWith("Удалить")) {
                try {
                    notesLogic.deleteTextFromNote(Integer.parseInt(textMsg.substring(8)));
                    return Report.DELETED_TASK;
                }
                catch (NoteException e){
                    return e.getMessage();
                }

            } else if (textMsg.startsWith("Отметить выполненным")) {
                notesLogic.markNote(Integer.parseInt(textMsg.substring(21)));
                messageHandlerState = MessageHandlerState.PROCESSING_NOTE;
                return Report.NOTE_EDITED;
            } else {
                return Report.WRONG_COMMAND;
            }
        } catch (Exception e) {
            return Report.WRONG_COMMAND;
        }
    }

    /**
     * Метод для редактирования заметки
     *
     * @param chatId  идентификатор чата
     * @param textMsg дата заметки
     * @return сообщение о результате работы метода
     */
    private String toEditNote(Long chatId, String textMsg) {
        try {
            messageHandlerState = MessageHandlerState.PROCESSING_EDITING_NOTE;
            return notesLogic.getNote(chatId, Filter.toFilterOutData(textMsg)) + Report.NOTE_EDITING;
        } catch (NoteException e) {
            messageHandlerState = MessageHandlerState.EDITING_NOTE;
            return e.getMessage();
        } catch (FilterException e) {
            messageHandlerState = MessageHandlerState.EDITING_NOTE;
            return e.getMessage();
        }
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
            return e.getMessage();
        } catch (FilterException e) {
            return e.getMessage();
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
        } catch (FilterException e) {
            messageHandlerState = MessageHandlerState.SEARCHING_NOTE;
            return e.getMessage();
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
            return e.getMessage();
        }
    }
}
