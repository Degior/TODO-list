
import MessageProcessing.MessageHandler;
import Readers.Reader;
/**
 * Класс запускающий всю логику (предшественник телеграма)
 */
public class Telegram implements MessageSender{
    Reader reader;

    MessageHandler messageHandler;

    public Telegram(Reader reader){
        this.reader = reader;
        messageHandler = new MessageHandler();
    }

    /**
     * Метод, считывающий сообщение пользователя и отправляющий ему ответ
     */
    public void doLogic(){
        System.out.println("введите \"/start\"");
        while (true){
            String nextCommand = reader.toReadText();
            String messageToUser = messageHandler.processCommand(nextCommand);
            sendMessage(1l, messageToUser);
        }

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