package org.example.MessageProcessing;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Конвертация пользовательского ввода даты в объект типа LocalDate
 */
public class Parser {

    /**
     * Конвертация пользовательского ввода даты в объект типа LocalDate
     *
     * @param message - пользовательский ввод даты
     * @return localDate - переменная типа LocalDate
     */
    public static LocalDate parseData(String message) throws ParserException {

        try {
            char symbol2 = message.charAt(2);

            String[] date2 = getStringData(message, symbol2);

            int[] dateMonth2 = new int[]{Integer.parseInt(date2[0]), Integer.parseInt(date2[1])};

            try {
                LocalDate localDate = LocalDate.of(LocalDate.now().getYear(),
                        dateMonth2[1],
                        dateMonth2[0]);

                return localDate;
            } catch (DateTimeException e) {
                throw new ParserException(Messages.WRONG_DATE);
            }

        }catch (Exception e){
            throw new ParserException(Messages.WRONG_INPUT);
        }

    }

    /**
     * Преобразование пользовательского ввод даты в массив строк из месяца и даты
     *
     * @param message - пользовательский ввод даты
     * @param symbol  - символ-разделитель между месяцем и числом
     * @return список строк, разделенных по разделителю
     */
    private static String[] getStringData(String message, char symbol) {
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
