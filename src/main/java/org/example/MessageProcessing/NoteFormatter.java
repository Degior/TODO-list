package org.example.MessageProcessing;

import org.example.NoteStrusture.Note;
import org.example.Report;

import java.time.LocalDate;
import java.util.Set;

public class NoteFormatter {

    public static String getNoteText(Note note) throws FormatterException {
        if (note == null){
            throw new FormatterException(Report.NO_SUCH_NOTE);
        }
        return note.getText();
    }

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
