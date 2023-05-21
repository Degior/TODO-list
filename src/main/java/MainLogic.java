
import MessagehandlerClasses.MessageHandler;
import Readers.Reader;

public class MainLogic implements MessageSender{
    Reader reader;

    //NoteStorage noteStorage;

    MessageHandler messageHandler;

    public MainLogic(Reader reader){
        this.reader = reader;
        //this.noteStorage = new NoteStorage();
        messageHandler = new MessageHandler();
    }

    /**
     * Метод, считывающий сообщение пользователя и отправляющий ему ответ
     */
    public void doLogic(){
        System.out.println("введите \"/start\"");
        while (true){//рандомный ввод

            String nextCommand = reader.toReadText();//обработать ошибки
            String messageToUser = messageHandler.processCommand(nextCommand);
            sendMessage(1l, messageToUser);
        }
        /*//тут будет \start
        //String message = reader.toReadText();
        String messageToSend = MessageHandler.processCommand("/start");
        String[] date = reader.toReadDate();//валидировать как-то, парсинг
        //тут либо непонятно..
        int month = Integer.parseInt((date[1]));//все это
        int day =  Integer.parseInt(date[0]);//убрать отсюда
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), month, day);
        Note currentNote = noteStorage.getNote(localDate);
        System.out.println(Commands.NOTE_MODIFICATION);
        //изменяем Note
        String firstTask = reader.toReadTask();
        //здесь может быть - закрыть заметку*/

    }

    /**
     * Метод,отправляющий пользователю ответ
     * Пока что в консоль
     */
    @Override
    public void sendMessage(Long chatId, String message) {
        System.out.println(message);

    }

}