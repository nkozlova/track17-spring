package track.messenger.messages;


public enum Status {
    AUTHORIZED, // пользователь авторизован
    NOT_AUTHORIZED, // пользователь не авторизован
    AUTHORIZATION_ERROR, // ошибка при авторизации
    USER_CREATED, // пользователь создан
    USER_EXISTS, // пользователь существует
    MESSAGE_DELIVERED, // сообщение доставлено
    WRONG_DESTINATION, // ошибка в доставке сообщения
    CHAT_CREATED, // чат создан
    CHAT_EXISTS, // чат существует
    EXIT, // пользователь вышел
    ERROR // ошибка
}

