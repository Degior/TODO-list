package MessagehandlerClasses;

import NoteStrusture.NoteException;
import NoteStrusture.NoteStorage;

import java.time.LocalDate;

 /**
 * Класс для работы с заметками
 */
public class NotesLogic {

    private NoteStorage noteStorage;

    public NotesLogic(){
        noteStorage = new NoteStorage();
    }


     /**
      * Метод, добавляющий заметку в список заметок
      */
    public void addNote(LocalDate date) throws NoteException{
        noteStorage.appendNote(date);
    }
     /**
      * Метод, добавляющий текст в заметку
      */
    public void addTextToNote(String message){
        noteStorage.fillNote(message);
    }

     /**
      * Метод, возвращающий названия всех заметок (пока это даты)
      */
    public String getAllNotes(){
        if (noteStorage.getAllNotes().size() > 0){
            String allNotes = "";
            for (LocalDate date: noteStorage.getAllNotes()){
                allNotes = allNotes + date + "\n";
                //System.out.println(date);
            }
            return allNotes;
        }else {
            return "У вас нет заметок";
        }
    }

    public String getNote(LocalDate date) throws NoteException {
        return noteStorage.getNoteText(date);
    }

     public void changeLogic() {
        noteStorage.resetNote();
     }
 }
