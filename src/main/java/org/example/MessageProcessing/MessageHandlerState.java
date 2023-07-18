package org.example.MessageProcessing;


/**
 * Состояния класса MessageHandler
 */
public enum MessageHandlerState {
    CREATING_NOTE_DATE,
    PROCESSING_NOTE,
    SEARCHING_NOTE,
    DEFAULT,
    DELETING_NOTE,
    EDITING_NOTE,
    PROCESSING_EDITING_NOTE,
    ADDING_NOTIFICATION,

    CHOOSING_TASK_STATUS, STATUS_CHOSEN, ADDING_STATUS,
}
