package org.example.NoteStrusture;

import org.example.Report;

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
    public void appendNote(Long chatId, LocalDate localDate)throws NoteException{
        Map<LocalDate, Note> currentMap;
        if (allNotes.containsKey(chatId)) {
            currentMap = allNotes.get(chatId);
            if (currentMap.containsKey(localDate)) {
                throw new NoteException("Такая заметка уже есть");
            }
            allNotes.get(chatId).put(localDate, new Note());
        } else {
            currentMap = new HashMap<>();
            currentMap.put(localDate, new Note());
            allNotes.put(chatId, currentMap);
        }
        currentNote = currentMap.get(localDate);

    }

    /**
     * Метод добавляющий задачу в текущую заметку
     *
     * @param tasks - задача, которую нужно добавить
     */
    public void fillNote(String tasks) {
        currentNote.addTask(tasks);
    }

    /**
     * Метод возвращающий список всех заметок
     */
    public Set<LocalDate> getAllNotes(Long chatId) throws NoteException {
        if (allNotes.containsKey(chatId)) {
            return allNotes.get(chatId).keySet();
        }
        throw new NoteException(Report.NO_NOTES);

    }

    /**
     *Метод возвращающий задачи из заметки
     *
     * @param chatId - id чата, в котором создается заметка
     * @param localDate - дата, на которую создается заметка
     * @return список задач
     */
    public String getNoteText(Long chatId, LocalDate localDate) throws NoteException{
        if (!allNotes.containsKey(chatId)){
            throw new NoteException(Report.NO_SUCH_NOTE);
        }
        Map<LocalDate, Note> currentMap = allNotes.get(chatId);
        if (currentMap.containsKey(localDate)){
            currentNote = currentMap.get(localDate);
            return currentNote.getText();
        }
        throw new NoteException(Report.NO_SUCH_NOTE);
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
    public void deleteTextFromNote(int index) {
        currentNote.deleteTask(index);
    }

    /**
     * Метод, помечающий задачу из текущей заметки как выполненную
     *
     * @param index - индекс задачи, которую нужно пометить
     */
    public void markNote(int index) {
        currentNote.markTask(index);
    }
}