
package org.example.MessageProcessing;

import org.example.NoteStrusture.TaskStatus;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    /*SUGGEST_STATUS(taskStatusChose()),
    SET_STATUS(getAllStatuses());*/

    /*private ReplyKeyboard currentLayout;

    Buttons(InlineKeyboardMarkup layout) {
        currentLayout = layout;
    }*/

    public static InlineKeyboardMarkup taskStatusChose(){
        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton setStatus = new InlineKeyboardButton();
        setStatus.setText("Выбрать категорию");
        setStatus.setCallbackData("тыкните:");
        InlineKeyboardButton setStatusLater = new InlineKeyboardButton();
        setStatusLater.setText("Выбрать позже");
        setStatusLater.setCallbackData("Ну ладно");
        keyboardButtonsRow.add(setStatus);
        keyboardButtonsRow.add(setStatusLater);
        List<List<InlineKeyboardButton>> list = List.of(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getAllStatuses(List<String> statuses){
        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        for (String status: statuses){
            InlineKeyboardButton userStatus = new InlineKeyboardButton();
            userStatus.setText(status);
            userStatus.setCallbackData("ок");
            keyboardButtonsRow.add(userStatus);
        }
        List<List<InlineKeyboardButton>> list = List.of(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }
}

