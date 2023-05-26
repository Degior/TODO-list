package MessageProcessing;
import Commands.Commands;
import NoteStrusture.NoteException;


/**
 * Класс, который принимает и обрабатывает сообщения пользователя
 * С логикой заметок работает через класс NotesLogic
 */
public class MessageHandler {

    private NotesLogic notesLogic;

    private MessageHandlerState state;
    public MessageHandler(){
        state = MessageHandlerState.DEFAULT;
        notesLogic = new NotesLogic();
    }

    /**
     * Метод, обрабытывающий сообщение от пользователя
     * Обрабатывает только базовые команды
     * @param message - ввод пользователя
     */
    public String processCommand(String message){
        switch (message){
            case ("/start"):
                System.out.println("/getNotesList /createNote /openNote");
                return Commands.START;
            case ("/menu"):
                state = MessageHandlerState.DEFAULT;
                System.out.println("/getNotesList /createNote /openNote");
                notesLogic.changeLogic();
                return Commands.MENU;
            case ("/getNotesList"):
                state = MessageHandlerState.DEFAULT;
                System.out.println("/menu /openNote /createNote");
                notesLogic.changeLogic();
                return notesLogic.getAllNotes();
            case ("/createNote"):
                System.out.println("/menu");
                state = MessageHandlerState.CREATING_NOTE_DATE;
                return Commands.NOTE_CREATION;
            case ("/openNote"):
                System.out.println("/menu /getNotesList");
                state = MessageHandlerState.SEARCHING_NOTE;
                return Commands.NOTE_SEARCH;
            case ("/deleteNote"):
                System.out.println("/yes /no");
                state = MessageHandlerState.DELETING_NOTE;
                return Commands.DELETE_NOTE;
            case ("/yes"):
                return toDeleteNote();
            case ("/no"):

            default:
                return processUserInput(message);
        }
    }
    /**
     * Метод, обрабытывающий специфичный пользовательский ввод (не зарезервированные команды)
     * @param message - ввод пользователя
     */

    private String processUserInput(String message){
        return switch (state){
            case CREATING_NOTE_DATE -> appendNote(message);
            case PROCESSING_NOTE -> toProcessExistingNote(message);
            case SEARCHING_NOTE -> toLookForNote(message);
            //case DELETING_NOTE -> toDeleteNote();
            default -> Commands.INCORRECT_INPUT;
        };
    }

    private String appendNote(String message){
        try {
            notesLogic.addNote(Filter.toFilterOutData(message));
            state = MessageHandlerState.PROCESSING_NOTE;
        }catch (NoteException e){
            System.out.println("/menu");
            return Commands.NOTE_ALREADY_EXIST;
        }
        catch (FilterException e){
            return Commands.INCORRECT_INPUT;
        }
        System.out.println(" /getNotesList /menu");
        return Commands.NOTE_MODIFICATION;
    }

    private String toProcessExistingNote(String message){
        notesLogic.addTextToNote(message);
        System.out.println("/menu /getNotesList /editNote /deleteNote");
        return Commands.TASK_ADDED;
    }

    private String toLookForNote(String message){
        boolean flag = false;
        try {
            state = MessageHandlerState.DEFAULT;
            return notesLogic.getNote(Filter.toFilterOutData(message));
        }catch (NoteException e){
            flag = true;
            System.out.print("/createNote");
            state = MessageHandlerState.SEARCHING_NOTE;
            return Commands.NO_SUCH_NOTE;
        }catch (FilterException e){
            flag = true;
            state = MessageHandlerState.SEARCHING_NOTE;
            return Commands.INCORRECT_INPUT;
        }finally {
            if (!flag){
                System.out.print("/editNote /deleteNote");
            }
            System.out.println("/menu");
        }
    }

    private String toDeleteNote(){
        state = MessageHandlerState.DEFAULT;
        System.out.println("/menu /getNotesList");
        if (notesLogic.deleteNote()){
            return Commands.NOTE_DELETED;
        }
        else {
            return Commands.NO_SUCH_NOTE;
        }
    }

}