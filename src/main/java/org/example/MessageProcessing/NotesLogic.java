package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;
import org.example.NoteStrusture.NoteException;
import org.example.NoteStrusture.NoteStorage;
import org.example.Report;

import java.time.LocalDate;

 /**
 * Класс для работы с заметками
 */
public class NotesLogic {

    private NoteStorage noteStorage;

    //private NoteFormatter noteFormatter;

    public NotesLogic(){
        noteStorage = new NoteStorage();
        //noteFormatter = new NoteFormatter();
    }


     /**
      * Метод, добавляющий заметку в список заметок
      */
    public void addNote(Long chatId, LocalDate date) throws NoteException{
        noteStorage.appendNote(chatId, date);
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
    public String getAllNotes(Long chatId){
        try {
            String allNotes = "";
            for (LocalDate date: noteStorage.getAllNotes(chatId)){
                allNotes = allNotes + date + "\n";
            }
            return allNotes;
        }
        catch (NoteException e){
            return e.getMessage();
        }
    }

     /**
      * Метод, возвращающий текст заметки
      */
    public String getNote(Long chatId, LocalDate date){
        Note note = noteStorage.getNote(chatId, date);
        String noteText = NoteFormatter.getNoteText(note);
        if (noteText.equals("")){
            return Report.NO_SUCH_NOTE;
        }
        return noteText;
    }

    public boolean deleteNote(Long chatId, LocalDate message){
        return noteStorage.deleteNote(chatId, message);
    }

     /**
      * Метод, сбрасывающий текущую заметку
      */
     public void changeLogic() {
        noteStorage.resetNote();
     }

     public void deleteTextFromNote(int index) throws NoteException {
         noteStorage.deleteTextFromNote(index);

     }

     public void markNote(int index) {
         noteStorage.markNote(index);
     }
 }
