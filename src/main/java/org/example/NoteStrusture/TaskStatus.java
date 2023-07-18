package org.example.NoteStrusture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskStatus {

    private List<String> defaultStatuses = List.of("Работа", "Учеба", "Хобби", "Пропустить");
    private Map<Long, List<String>> userStatus = new HashMap<>();

    public void initUser(Long chatId){
        userStatus.put(chatId, defaultStatuses);
    }

    public void setNewStatus(Long chatId, String newStatus){
        List<String> newlist = new ArrayList<>();
        newlist.addAll(defaultStatuses);
        newlist.add(newStatus);
        userStatus.put(chatId, newlist);
    }

    public void deleteStatus(Long chatId, String oldStatus){
        List<String> list = userStatus.get(chatId);
        if (list.contains(oldStatus)){
            list.remove(oldStatus);
        }
    }

    public List<String> getUserStatuses(Long chatId){
        return userStatus.get(chatId);
    }

}
