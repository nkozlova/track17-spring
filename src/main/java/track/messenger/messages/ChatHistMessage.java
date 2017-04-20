package track.messenger.messages;

import track.messenger.User;


public class ChatHistMessage extends Message {

    private Long chatId;

    public ChatHistMessage() {
        super(null, Type.MSG_CHAT_HIST);
    }

    public ChatHistMessage(User sender, String data) throws InstantiationException {
        super(sender, Type.MSG_CHAT_HIST);
        if (sender == null) {
            throw new InstantiationException("You aren't authorization - need login (or register)");
        }
        try {
            this.chatId = Long.parseLong(data);
        } catch (NumberFormatException e) {
            throw new InstantiationException("Invalid data - need \"/chat_hist <chatId>\"");
        }
    }

    public Long getChatId() {
        return chatId;
    }
}
