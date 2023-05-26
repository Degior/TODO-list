public interface MessageSender {
    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param chatId
     * @param message
     */
    void sendMessage(Long chatId, String message);
}