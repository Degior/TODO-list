import Readers.ConsoleReader;
import Readers.Reader;

public class Main {

    public static void main(String[] args) {
        Reader reader = new ConsoleReader();
        Telegram mainLogic = new Telegram(reader);
        mainLogic.doLogic();
    }

}