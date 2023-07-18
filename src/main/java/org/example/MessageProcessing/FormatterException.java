package org.example.MessageProcessing;

/**
 * Проброс исключений, связанных с логикой работы класса NoteFormatter
 */
public class FormatterException extends Exception {
    public FormatterException(String message) {
        super(message);
    }
}
