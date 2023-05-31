package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;

public class NoteFormatter {

    public static String getNoteText(Note note){
        if (note != null){
            return note.getText();
        }
        else {
            return "";
        }
    }
}
