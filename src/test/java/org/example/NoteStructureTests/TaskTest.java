package org.example.NoteStructureTests;

import org.example.NoteStrusture.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {

    String description = "новая важная задача";
    private Task task = new Task(description);

    @Test
    public void setDoneTest() {
        task.setDone();
        Assertions.assertTrue(task.isDone());
    }

    @Test
    public void getDescriptionTest() {
        Assertions.assertEquals("новая важная задача", task.getDescription());
    }
}
