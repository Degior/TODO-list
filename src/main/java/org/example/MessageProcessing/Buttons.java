
package org.example.MessageProcessing;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public static InlineKeyboardMarkup getAllStatuses(List<String> statuses){
        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        for (String status: statuses){
            InlineKeyboardButton userStatus = new InlineKeyboardButton();
            userStatus.setText(status);
            userStatus.setCallbackData(status);
            keyboardButtonsRow.add(userStatus);
        }
        List<List<InlineKeyboardButton>> list = List.of(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup endNoteEditing(){
        InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton userStatus = new InlineKeyboardButton();
        userStatus.setText("Закончить работу с заметкой");
        userStatus.setCallbackData("Заметка сохранена");
        keyboardButtonsRow.add(userStatus);
        List<List<InlineKeyboardButton>> list = List.of(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }
}

