package track.messenger.messages;

import track.messenger.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ChatCreateMessage extends Message {

    private List<Long> participants;

    public List<Long> getParticipants() {
        return participants;
    }

    public ChatCreateMessage() {
        super(null, Type.MSG_CHAT_CREATE);
    }

    public ChatCreateMessage(User sender, String data) throws InstantiationException {
        super(sender,Type.MSG_CHAT_CREATE);
        if (sender == null) {
            throw new InstantiationException("You aren't authorization - need login (or register)");
        }
        try {
            participants = Arrays.stream(data.split(" "))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new InstantiationException("Invalid data - need \"/chat_create <user1Id> <user2Id> ...\"");
        }
    }
}
