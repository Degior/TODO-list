package org.example.NoteStrusture;

import org.example.Report;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс хранящий все заметки
 */
public class NoteStorage {

    private Map<Long, Map<LocalDate, Note>> allNotes;

    private Note currentNote;

    public NoteStorage(){
        allNotes = new HashMap<>();
        currentNote = null;
    }

    /**
     * Метод создающий заметку по дате
     *
     * @param chatId    - id чата, в котором создается заметка
     * @param localDate - дата, на которую создается заметка
     * @throws NoteException - если заметка уже существует
     */
    public boolean addNote(Long chatId, LocalDate localDate){
        Map<LocalDate, Note> currentMap;
        if (allNotes.containsKey(chatId)) {
            currentMap = allNotes.get(chatId);
            if (currentMap.containsKey(localDate)) {
                return false;
            }
            allNotes.get(chatId).put(localDate, new Note());
        } else {
            currentMap = new HashMap<>();
            currentMap.put(localDate, new Note());
            allNotes.put(chatId, currentMap);
        }
        currentNote = currentMap.get(localDate);
        return true;
    }

    /**
     * Метод добавляющий задачу в текущую заметку
     *
     * @param tasks - задача, которую нужно добавить
     */
    public void addTaskToNote(String tasks) {
        currentNote.addTask(tasks);
    }

    /**
     * Метод возвращающий список всех заметок
     * @throws NoteException формирует сообщение пользователю
     */

    @Nullable
    public Set<LocalDate> getAllNotes(Long chatId){
        if (allNotes.containsKey(chatId)) {
            return allNotes.get(chatId).keySet();
        }
        return null;

    }

    /**
     *Метод возвращающий задачи из заметки
     *
     * @param chatId - id чата, в котором создается заметка
     * @param localDate - дата, на которую создается заметка
     * @return список задач
     */

    @Nullable
    public Note getNote(Long chatId, LocalDate localDate){
        if (!allNotes.containsKey(chatId)){
            return null;
        }
        Map<LocalDate, Note> currentMap = allNotes.get(chatId);
        if (currentMap.containsKey(localDate)){
            currentNote = currentMap.get(localDate);
            return currentNote;
        }
        return null;
    }

    /**
     * Метод удаляющий заметку
     *
     * @return true, если заметка успешно удалена, false, если заметки по такой дате не существует
     */
    public boolean deleteNote(Long chatId, LocalDate localDate){
        if (!allNotes.containsKey(chatId)){
            return false;
        }
        if (allNotes.get(chatId).containsKey(localDate)){
            allNotes.get(chatId).remove(localDate);
            return true;
        }
        return false;
    }


    /**
     * Метод, который прекращает работу с текущей заметкой
     */
    public void resetNote() {
        currentNote = null;
    }

    /**
     * Метод, который удаляет задачу из текущей заметки
     *
     * @param index - индекс задачи, которую нужно удалить
     */
    public boolean deleteTextFromNote(int index){
        return currentNote.deleteTask(index);
    }

    /**
     * Метод, помечающий задачу из текущей заметки как выполненную
     *
     * @param index - индекс задачи, которую нужно пометить
     */
    public void markNoteAsCompleted(int index) {
        currentNote.markTask(index);
    }
}