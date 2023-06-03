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
     * Метод для получения текста заметки
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

    /**
     * Метод для удаления одной задачи по ее номеру
     * @param index номер заметки, начиная с 0
     * @return true если заметка успешно удалена
     */
    public boolean deleteTask(int index){
        if (tasksList.size() < index){
            return false;
        }
        tasksList.remove(index - 1);
        return true;
    }

    /**
     * Метод помечающий задачу как выполненную
     * @param index номер заметки, начиная с 0
     */

    public void markTaskAsCompleted(int index) {
        tasksList.get(index - 1).chahgeState();
    }
}