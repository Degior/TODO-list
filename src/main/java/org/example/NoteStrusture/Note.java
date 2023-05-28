package org.example.NoteStrusture;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий собой заметку на день
 * Хранит несколько Task'ов - дел на этот день
 */
public class Note {
    private final List<Task> tasksList;

    private int numOfTasks;

    public Note(){
        tasksList = new ArrayList<>();
        numOfTasks = 0;
    }

    /**
     * Метод добавляет задачу в заметку
     * @param text текст задачи
     */
    public void addTask(String text){
        Task task = new Task(text);
        tasksList.add(task);
        numOfTasks+=1;
    }

    /**
     * Метод добавляет задачу в заметку
     * @return текст заметки
     */
    public String getText(){
        System.out.println();
        StringBuilder noteText = new StringBuilder();
        int counter = 1;
        for (Task task : tasksList) {
            noteText.append(counter).append(". ").append(task.getDescription()).append("\n");
            counter++;
        }
        return noteText.toString();
    }

    public void deleteTask(int index) {
        tasksList.remove(index - 1);
        numOfTasks -= 1;
    }

    public void markTask(int index) {
        tasksList.get(index - 1).chahgeState();
    }
}