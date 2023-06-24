package org.example.statistics;


import org.example.NoteStrusture.Note;
import org.example.NoteStrusture.Task;

import java.util.List;

public class Statistics {

    public static int[] getNoteStatistics(Note note){
        List<Task> tasks = note.getTasks();
        int size = tasks.size();
        int done = 0;
        for (Task task : tasks){
            if (task.isDone())
                done+=1;
        }
        return new int[]{done, size};
    }
}
