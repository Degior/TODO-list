package NoteStrusture;

import java.util.ArrayList;
import java.util.List;

/*
 * Класс представляющий собой заметку на день
 * Хранит несколько Task'ов - дел на этот день
 */
public class Note {
    private List<Task> tasksList;

    public Note(){
        tasksList = new ArrayList<>();
    }

    //private String note;
   /* public Note(){
        //note = "";
        System.out.println("note - " + note);
    }*/

    public void addTask(String text){
        Task task = new Task(text);
        tasksList.add(task);
        //System.out.println("text - " + text);
    }

    public String getText(){
        String noteText = "";
        for (Task task : tasksList){
            noteText += "-" + task.getDescription() + "\n";
        }
        return noteText;
    }

}