package MessageProcessing;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {

    /**
     * Метод преобразует пользовательский ввод даты в более удобный для работы вид
     * @param message - пользовательский ввод даты
     */

    public static LocalDate toFilterOutData(String message) throws FilterException {

        if (!checkRegex(message)){
            throw new FilterException("Неверный формат ввода");
        }
        char symbol;
        String[] date;

        symbol = message.charAt(2);

        date = getStringData(message, symbol);

        int[] dateMonth;

        dateMonth =new int[]{Integer.parseInt(date[0]), Integer.parseInt(date[1])};

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

    private static boolean checkRegex(String message){
        Matcher matcher;
        Pattern pattern = Pattern.compile("\\d{2}\\s\\d{2}");
        Pattern pattern2 = Pattern.compile("\\d{2}.\\d{2}");
        Pattern pattern3 = Pattern.compile("\\d{2}/\\d{2}");

        matcher = pattern.matcher(message);
        if (matcher.find()){
            return true;
        }
        matcher = pattern2.matcher(message);
        if (matcher.find()){
            return true;
        }
        matcher = pattern3.matcher(message);
        if (matcher.find()){
            return true;
        }
        return false;
    }
}
