package org.example.NoteStrusture;

/**
 * Класс для описания одной задачи
 */
public class Task {

    private TaskState state = TaskState.NOT_DONE;

    private final String description;

    private String status;

    public Task(String description) {
        this.description = description;
    }

    /**
     * Метод помечающий задачу как выполненную
     */
    public void setDone() {
        state = TaskState.DONE;
    }
    public String getDescription() {
        return description;
    }
    public boolean isDone(){
        return state == TaskState.DONE;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public boolean hasStatus(){
        return !(status == null);
    }

    public String getStatus(){return status;}
}
