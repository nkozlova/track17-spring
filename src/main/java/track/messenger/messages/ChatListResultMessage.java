package track.messenger.messages;

import track.messenger.User;

import java.util.List;


public class ChatListResultMessage extends Message {
    private List<Long> chatIds;

    public List<Long> getChatIds() {
        return chatIds;
    }

    public ChatListResultMessage() {
        super(null, Type.MSG_CHAT_LIST_RESULT);
    }

    public ChatListResultMessage(User sender, List<Long> chatIds) throws InstantiationException {
        super(sender, Type.MSG_CHAT_LIST_RESULT);
        this.chatIds = chatIds;
    }
}
