package org.example.MessageProcessing;
import org.example.NoteStrusture.Note;
import org.example.NoteStrusture.Task;
import org.example.Messages;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Класс отвечает за представление заметки в удобном виде для пользователя
 */
public class NoteFormatter {

    private boolean isFormattingStatustics = false;

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
        StringBuilder allNotes = new StringBuilder();
        for (LocalDate date : dates) {
            allNotes.append(date).append("\n");
        }
        return allNotes.toString();
    }

    public static String formatNoteStatistics(LocalDate localDate, int[] numbers){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(localDate).append(" вы выполнили ");
        stringBuilder.append(numbers[0]).append("/").append(numbers[1]);
        stringBuilder.append(" задач");
        return stringBuilder.toString();
    }

    public static String formatIdStatistics(List<String> list){
        StringBuilder statistics = new StringBuilder();
        for (String note: list){
            statistics.append(note).append("\n");
        }
        return statistics.toString();
    }
}
