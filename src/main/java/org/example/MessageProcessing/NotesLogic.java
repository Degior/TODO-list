package org.example.MessageProcessing;

import org.example.NoteStrusture.NoteException;
import org.example.NoteStrusture.NoteStorage;

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
            }
            return allNotes;
        }else {
            return "У вас нет заметок";
        }
    }

     /**
      * Метод, возвращающий текст заметки
      */
    public String getNote(LocalDate date) throws NoteException {
        return noteStorage.getNoteText(date);
    }

    public boolean deleteNote(LocalDate message){
        if (noteStorage.deleteNote(message)){
            return true;
        }
        return false;
    }

     /**
      * Метод, сбрасывающий текущую заметку
      */
     public void changeLogic() {
        noteStorage.resetNote();
     }
 }
