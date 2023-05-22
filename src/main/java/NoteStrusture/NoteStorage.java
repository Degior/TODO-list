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
     * Метод создающий заметку по дате
     */
    public void appendNote(LocalDate localDate)throws NoteException{

        if (allNotes.containsKey(localDate)){
            throw new NoteException("Такая заметка уже есть");
        }
        allNotes.put(localDate, new Note());

        currentNote = allNotes.get(localDate);
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

    /**
     *Метод возвращающий задачи из заметки
     */
    public String getNoteText(LocalDate localDate) throws NoteException{
        if (allNotes.containsKey(localDate)){
            currentNote = allNotes.get(localDate);
            return currentNote.getText();
        }
        throw new NoteException("Заметки с такой датой не существует");
    }

    public boolean deleteNote(LocalDate localDate){
        if (allNotes.containsKey(localDate)){
            allNotes.remove(localDate);
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

}