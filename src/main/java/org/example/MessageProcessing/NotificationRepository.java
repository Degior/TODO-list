package org.example.MessageProcessing;

import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {
    private final List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification, Long chatId) {
        notifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }


    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }
}
