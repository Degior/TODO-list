package org.example.NoteStrusture;


import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NoteStorage {

    /**
     * одному id соответствует Map из даты в качестве ключа и заметки в качестве значения
     */
    private final Map<Long, Map<LocalDate, Note>> allNotes;

    private Map<Long, Note> currentNote;


    public NoteStorage() {
        allNotes = new HashMap<>();
        currentNote = new HashMap<>();
    }

    /**
     * Метод создающий заметку по дате
     *
     * @param chatId    - id чата, в котором создается заметка
     * @param localDate - дата, на которую создается заметка
     */

    public boolean addNote(Long chatId, LocalDate localDate) {
        Map<LocalDate, Note> userNotes = allNotes.computeIfAbsent(chatId, key -> new HashMap<>());
        if (userNotes.containsKey(localDate)) {
            return false;
        }
        userNotes.put(localDate, new Note());
        currentNote.put(chatId, userNotes.get(localDate));
        return true;
    }

    /**
     * Метод добавляющий задачу в текущую заметку
     *
     * @param task - задача, которую нужно добавить
     */
    public void addTaskToNote(Long chatId, String task) {
        currentNote.get(chatId).addTask(task);
    }

    /**
     * Метод возвращающий множество всех заметок пользователя
     *
     * @param chatId id чата пользователя
     * @return список дат всех существующих заметок
     */
    @Nullable
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
        currentNote.put(chatId, note);
        return note;
    }

    /**
     * Метод удаляющий заметку
     *
     * @return true, если заметка успешно удалена, false, если заметки по такой дате не существует
     */
    public boolean deleteNote(Long chatId, LocalDate localDate) {
        if (!allNotes.containsKey(chatId)) {
            return false;
        }
        if (allNotes.get(chatId).containsKey(localDate)) {
            allNotes.get(chatId).remove(localDate);
            return true;
        }
        return false;
    }


    /**
     * Метод, который удаляет задачу из текущей заметки
     *
     * @param index - индекс задачи, которую нужно удалить
     */
    public boolean deleteTextFromNote(Long chatId, int index) {
        return currentNote.get(chatId).deleteTask(index);
    }


    /**
     * Метод, который помечает задачу по индексу как выполненную
     *
     * @param index - индекс задачи, которую нужно отметить
     */
    public void markTaskAsCompleted(Long chatId, int index) {
        currentNote.get(chatId).markTaskAsCompleted(index);
    }

    public void resetNote(Long chatId) {
        currentNote.put(chatId, null);
    }
}