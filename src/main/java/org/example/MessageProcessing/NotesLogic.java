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
        if (noteStorage.getAllNotes(chatId).size() > 0){
            String allNotes = "";
            for (LocalDate date: noteStorage.getAllNotes(chatId)){
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
    public String getNote(Long chatId, LocalDate date) throws NoteException {
        return noteStorage.getNoteText(chatId, date);
    }

    public boolean deleteNote(Long chatId, LocalDate message){
        if (noteStorage.deleteNote(chatId, message)){
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
