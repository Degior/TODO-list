package NoteStrusture;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс хранящий все заметки
 */
public class NoteStorage {

    private Map<LocalDate, Note> allNotes;

    private Note currentNote;

    public NoteStorage(){
        allNotes = new HashMap<>();
        currentNote = null;
    }

    /**
     * Метод возвращающий заметку по дате
     * Если заметки нет, создает ее
     */
    public void appendNote(LocalDate localDate)throws NoteException{

        if (allNotes.containsKey(localDate)){
            throw new NoteException("Такая заметка уже есть");
        }
        allNotes.put(localDate, new Note());

        currentNote = allNotes.get(localDate);
        //return allNotes.get(localDate);
    }

    public void fillNote(String tasks){
        currentNote.addTask(tasks);
    }
    /**
     *Метод возвращающий список всех заметок
     */
    public Set<LocalDate> getAllNotes(){
        return allNotes.keySet();
    }

    public String getNoteText(LocalDate localDate) throws NoteException{
        if (allNotes.containsKey(localDate)){
            currentNote = allNotes.get(localDate);
            return currentNote.getText();
        }
        throw new NoteException("Заметки с такой датой не существует");
    }

    public void resetNote() {
        currentNote = null;
    }

}