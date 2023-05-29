package org.example.NoteStrusture;
/**
 * Класс для описания одной задачи
 */
public class Task {

    private TaskStatus status;

    private TaskState state;

    private String description;

    private String details;

    public Task(String description){
        this.description = description;
    }

    public void chahgeState(){
        state = TaskState.DONE;
    }

    public String getDescription(){
        return description;
    }

    public TaskState getState(){return state;}

}
