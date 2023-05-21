package MessageProcessing;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Filter {

    /**
     * Метод преобразует пользовательский ввод даты в более удобный для работы вид
     * @param message - пользовательский ввод даты
     */

    public static LocalDate toFilterOutData(String message) throws FilterException {

        char symbol;
        String[] date;
        try {
            symbol = message.charAt(2);
        } catch (StringIndexOutOfBoundsException e){
            throw new FilterException("Неверный формат ввода");
        }

        date = getStringData(message, symbol);

        int[] dateMonth;

        try {
            dateMonth =new int[]{Integer.parseInt(date[0]), Integer.parseInt(date[1])};
        }catch (ArrayIndexOutOfBoundsException e){
            throw new  FilterException("Неверный формат ввода");
        }

        LocalDate localDate;
        try{
            localDate = LocalDate.of(LocalDate.now().getYear(),
                    dateMonth[1],
                    dateMonth[0]);
        }catch (DateTimeException e){
            throw new FilterException("Неверный формат ввода");
        }

        return localDate;
    }

    private static String[] getStringData(String message, char symbol){
        String[] date = new String[]{"00"};
        switch (symbol) {
            case ('/'):
                date = message.split("/");
                break;
            case (' '):
                date = message.split(" ");
                break;
            case ('.'):
                date = message.split("\\.");
                break;
        }
        return date;
    }

}
