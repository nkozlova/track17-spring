package track.messenger.messages;

import track.messenger.User;


public class QuitMessage extends Message {

    public QuitMessage() {
        super(null, Type.MSG_QUIT);
    }

    public QuitMessage(User user) {
        super(user, Type.MSG_QUIT);
    }
}
