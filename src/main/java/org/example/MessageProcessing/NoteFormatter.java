package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;
import org.example.NoteStrusture.Task;
import org.example.Messages;

import java.time.LocalDate;
import java.util.Set;

/**
 * Класс NoteFormatter. Отвечает за представление заметки в удобном виде для пользователя
 */
public class NoteFormatter {

    /**
     * Метод для получения текста заметки
     *
     * @param note заметка
     * @return заметка в текстовом виде
     * @throws FormatterException если заметка null
     */
    public static String getNoteText(Note note) throws FormatterException {
        if (note == null) {
            throw new FormatterException(Messages.NO_SUCH_NOTE);
        }
        StringBuilder noteText = new StringBuilder();
        int counter = 1;
        noteText.append("\n");
        for (Task task : note.getTasks()) {
            if (task.isDone()){
                noteText.append("V ").append(task.getDescription()).append("\n");
            }
            else {
                noteText.append(counter).append(". ").append(task.getDescription()).append("\n");
            }

            counter++;
        }
        return noteText.toString();
    }

    /**
     * Метод для получения дат всех существующих у пользователя заметок
     *
     * @param dates список заметок
     * @return список заметок в текстовом виде
     * @throws FormatterException если у пользователя нет заметок
     */
    public static String getAllNotes(Set<LocalDate> dates) throws FormatterException {
        if (dates.isEmpty()) {
            throw new FormatterException(Messages.NO_NOTES);
        }
        String allNotes = "";
        for (LocalDate date : dates) {
            allNotes = allNotes + date + "\n";
        }
        return allNotes;

    }
}
