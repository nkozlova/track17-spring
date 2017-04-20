package track.messenger.messages;

import track.messenger.User;


public class ChatListMessage extends Message {

    public ChatListMessage() {
        super(null, Type.MSG_CHAT_LIST);
    }

    public ChatListMessage(User sender) throws InstantiationException {
        super(sender,Type.MSG_CHAT_LIST);
        if (sender == null) {
            throw new InstantiationException("You aren't authorization - need login (or register)");
        }
    }

}
