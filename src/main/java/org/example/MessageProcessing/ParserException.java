package org.example.MessageProcessing;

/**
 * Класс ParserException для проброса исключений, связанных с логикой работы класса Parser
 */
public class ParserException extends Exception {

    public ParserException(String message) {
        super(message);
    }
}
