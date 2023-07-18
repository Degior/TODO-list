package org.example.NoteStrusture;


import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.*;

public class NoteStorage {

    /**
     * одному id соответствует Map из даты в качестве ключа и заметки в качестве значения
     */
    private final Map<Long, Map<LocalDate, Note>> allNotes;

    private final Map<Long, Note> currentNotes;


    public NoteStorage() {
        allNotes = new HashMap<>();
        currentNotes = new HashMap<>();
    }

    /**
     * Метод проверящий возможность создать заметку по дате
     *
     * @param chatId    - id чата, в котором создается заметка
     * @param localDate - дата, на которую создается заметка
     */
    public boolean addNote(Long chatId, LocalDate localDate) {
        Map<LocalDate, Note> userNotes = allNotes.computeIfAbsent(chatId, key -> new HashMap<>());
        if (userNotes.containsKey(localDate)) {
            return false;
        }
        addGoodNote(userNotes, chatId, localDate);
        return true;
    }

    /**
     * Метод создающий заметку по дате
     *
     * @param userNotes - все заметки конкретного пользователя
     * @param chatId    - id чата, в котором создается заметка
     * @param localDate - дата, на которую создается заметка
     */
    private void addGoodNote(Map<LocalDate, Note> userNotes, Long chatId, LocalDate localDate){
        userNotes.put(localDate, new Note());
        currentNotes.put(chatId, userNotes.get(localDate));
    }

    /**
     * Метод добавляющий задачу в текущую заметку
     *
     * @param task - задача, которую нужно добавить
     */
    public void addTaskToNote(Long chatId, String task) {
        currentNotes.get(chatId).addTask(task);
    }

    /**
     * Метод возвращающий множество всех заметок пользователя
     *
     * @param chatId id чата пользователя
     * @return список дат всех существующих заметок
     */
    public Set<LocalDate> getAllNotes(Long chatId) {
        if (allNotes.containsKey(chatId)) {
            return allNotes.get(chatId).keySet();
        }
        return Set.of();
    }

    /**
     * Метод возвращающий задачи из заметки
     *
     * @param chatId    - id чата
     * @param localDate - дата, на которую была создана
     * @return список задач
     */
    @Nullable
    public Note getNote(Long chatId, LocalDate localDate) {
        Map<LocalDate, Note> currentMap = allNotes.getOrDefault(chatId, new HashMap<>());
        Note note = currentMap.getOrDefault(localDate, null);
        currentNotes.put(chatId, note);
        return note;
    }

    /**
     * Метод проверяющий возможность удаления заметки
     *
     * @return true, если , false, если заметки по такой дате не существует
     */
    public boolean isPossibleToDeleteNote(Long chatId, LocalDate localDate) {
        if (!allNotes.containsKey(chatId)) {
            return false;
        }
        if (allNotes.get(chatId).containsKey(localDate)) {
            return true;
        }
        return false;
    }

    /**
     * Метод удаляющий заметку по дате
     */
    public void deleteNote(Long chatId, LocalDate localDate){
        allNotes.get(chatId).remove(localDate);
    }


    /**
     * Метод, который удаляет задачу из текущей заметки
     *
     * @param index - индекс задачи, которую нужно удалить
     */
    public boolean deleteTextFromNote(Long chatId, int index) {
        return currentNotes.get(chatId).deleteTask(index);
    }


    /**
     * Метод, который помечает задачу по индексу как выполненную
     *
     * @param index - индекс задачи, которую нужно отметить
     */
    public void markTaskAsCompleted(Long chatId, int index) {
        currentNotes.get(chatId).markTaskAsCompleted(index);
    }

    public void resetNote(Long chatId) {
        currentNotes.put(chatId, null);
    }

    public void addTaskStatus(Long chatId, String message) {
        currentNotes.get(chatId).addTaskStatus(message);
    }
}