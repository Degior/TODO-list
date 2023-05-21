package MessagehandlerClasses;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Filter {
    //по-хорошему еще сделать интерфейс
    //мб еще какую ошибку метод выкинет, проверить

    /**
     * Метод преобразует пользовательский ввод даты в более удобный для работы вид
     * @param message - пользовательский ввод даты
     */

    public static LocalDate toFilterOutData(String message) throws FilterException{

        char symbol;
        String[] date = new String[]{"00"};
        try {
            symbol = message.charAt(2);
        } catch (StringIndexOutOfBoundsException e){
            throw new FilterException("Неверный формат ввода");
        }


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

}
