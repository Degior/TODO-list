package org.example.NoteStrusture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskStatus {

    private List<String> defaultStatuses = List.of("Работа", "Учеба", "Хобби");
    private Map<Long, List<String>> userStatus = new HashMap<>();

    public void initUser(Long chatId){
        userStatus.put(chatId, defaultStatuses);
    }

    public void setStatus(Long chatId, String newStatus){
        userStatus.get(chatId).add(newStatus);
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
