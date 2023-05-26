package NoteStructureTests;

import NoteStrusture.Note;
import NoteStrusture.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoteTest {

    Note note;

    @BeforeEach
    public void setUo(){
        note = new Note();
    }

    @Test
    public void getTextTask(){
        note.addTask("новое задание");
        Assertions.assertEquals(note.getText(), "-новое задание\n");
    }

    @Test
    public void getTextTask2(){
        note.addTask("новое задание");
        note.addTask("еще одно задание");
        Assertions.assertEquals(note.getText(), "-новое задание\n-еще одно задание\n");
    }

}
