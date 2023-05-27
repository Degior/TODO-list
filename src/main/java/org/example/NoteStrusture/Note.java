package org.example.NoteStrusture;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий собой заметку на день
 * Хранит несколько Task'ов - дел на этот день
 */
public class Note {
    private List<Task> tasksList;

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
        String noteText = "";
        int counter = 1;
        for (Task task : tasksList){
            noteText += counter + ". " + task.getDescription() + "\n";
            counter++;
        }
        return noteText;
    }

}