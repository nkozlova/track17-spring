package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;


public class InfoMessage extends Message implements Serializable {

    private Long requestedUser;

    public Long getRequestedUser() {
        return requestedUser;
    }

    protected InfoMessage() {
        super(null, Type.MSG_INFO);
    }

    public InfoMessage(User sender, String data) throws InstantiationException {
        super(sender, Type.MSG_INFO);
        if (sender == null) {
            throw new InstantiationException("You aren't authorization - need login (or register)");
        }
        if ("".equals(data)) {
            requestedUser = sender.getId();
        } else {
            try {
                requestedUser = Long.parseLong(data);
            } catch (NumberFormatException nfe) {
                throw new InstantiationException("Invalid data - need \" /text <chatId>\"");
            }
        }
    }
}
