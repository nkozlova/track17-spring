package track.messenger.messages;

import track.messenger.User;

import java.util.List;


public class ChatHistResultMessage extends Message {

    List<String> history;

    public ChatHistResultMessage() {
        super(null, Type.MSG_CHAT_HIST_RESULT);
    }

    public ChatHistResultMessage(User sender, List<String> history) {
        super(sender, Type.MSG_CHAT_HIST_RESULT);
        this.history = history;

    }

    public List<String> getHistory() {
        return history;
    }
}
