package track.messenger.commands;

import track.messenger.messages.Status;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;


public class QuitCommand implements Command {

    public QuitCommand() {}

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores)
            throws CommandException {
        try {
            session.send(new StatusMessage(session.getUser(), Status.EXIT));
            session.close();
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": error in exit ");
        }
    }
}
