package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;
import org.example.Report;

import java.time.LocalDate;
import java.util.Set;
/**
 * Класс NoteFormatte. Отвечает за представление заметки в удобном виде  для пользователя
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
        if (note == null){
            throw new FormatterException(Report.NO_SUCH_NOTE);
        }
        return note.getText();
    }

    /**
     * Метод для получения дат всех существующих у пользователя заметок
     *
     * @param dates список заметок
     * @return список заметок в текстовом виде
     * @throws FormatterException если у пользователя нет заметок
     */

    public static String getAllNotes(Set<LocalDate> dates) throws FormatterException {
        if (dates == null){
            throw new FormatterException(Report.NO_NOTES);
        }
        String allNotes = "";
        for (LocalDate date: dates){
            allNotes = allNotes + date + "\n";
        }
        return allNotes;

    }
}
