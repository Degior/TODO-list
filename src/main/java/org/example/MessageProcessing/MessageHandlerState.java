package org.example.MessageProcessing;

public enum MessageHandlerState {
    CREATING_NOTE_DATE,
    PROCESSING_NOTE,
    SEARCHING_NOTE,
    DEFAULT,
    DELETING_NOTE,

    HABIT_ADDING,
    HABIT_REMOVING,
    HABIT_EDITING,
    HABIT_EDITING_GETTER,
    HABIT_MARKING,
}
