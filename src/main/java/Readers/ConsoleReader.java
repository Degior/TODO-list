package Readers;

import java.util.Scanner;

/**
 * Класс, отвечающий за пользовательский ввод
 */
public class ConsoleReader implements Reader{

    Scanner scanner;

    public ConsoleReader(){
        scanner = new Scanner(System.in);
    }

    /**
     * Метод читает очередную команду от пользователя
     */
    @Override
    public String toReadText() {
        String text = scanner.nextLine();
        return text;
    }

}
