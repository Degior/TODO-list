package org.example.NoteStrusture;

import org.example.Report;

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
    }

    /**
     * Метод добавляет задачу в заметку
     * @param text текст задачи
     */
    public void addTask(String text){
        Task task = new Task(text);
        tasksList.add(task);
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
            if (task.getState() == TaskState.DONE){
                noteText.append("V ").append(task.getDescription()).append("\n");
            }
            else {
                noteText.append(counter).append(". ").append(task.getDescription()).append("\n");
            }

            counter++;
        }
        return noteText.toString();
    }

    public boolean deleteTask(int index){
        if (tasksList.size() < index){
            return false;
            //throw new NoteException(Report.WRONG_TASK_INDEX);
        }
        tasksList.remove(index - 1);
        return true;
    }

    public void markTask(int index) {
        tasksList.get(index - 1).chahgeState();
    }
}